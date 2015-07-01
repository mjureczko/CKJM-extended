/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import gr.spinellis.ckjm.utils.FieldAccess;
import gr.spinellis.ckjm.utils.LoggerHelper;
import gr.spinellis.ckjm.utils.MethodCoupling;
import gr.spinellis.ckjm.utils.MethodInvokation;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.util.*;


/**
 * @author marian
 */
public class IcAndCbmClassVisitor extends AbstractClassVisitor {

    private Method[] mMethods;
    private JavaClass mCurrentClass;
    private ConstantPoolGen mParentPool;
    private List<Method[]> mParentsMethods;
    private Set<MethodInvokation> mInvokationsFromParents;
    private Set<MethodInvokation> mInvoktionsFromCurrentClass;
    private Set<FieldAccess> mParentsReaders;
    private Set<FieldAccess> mCurrentClassSetters;
    private Set<MethodCoupling> mMethodCouplings;
    private JavaClass[] mParents;
    private JavaClass mParent;
    private String mParentClassName;

    /**
     * How many inheritet methods use a field, that is defined
     * in a new/redefined method.
     */
    private int mCase1;

    /**
     * How many inherited methods call a redefined method
     * and use the return value of the redefined method.
     */
    private int mCase2;

    /**
     * How many inherited methods are called by a redefined method
     * and use a parameter that is defined in the redefined method.
     */
    private int mCase3;


    IcAndCbmClassVisitor(IClassMetricsContainer classMap) {
        super(classMap);
    }

    @Override
    protected void visitJavaClass_body(JavaClass jc) {
        mCase1 = mCase2 = mCase3 = 0;
        mCurrentClass = jc;
        mParents = jc.getSuperClasses();
        mParentsMethods = new ArrayList<Method[]>();
        mMethods = jc.getMethods();
        mInvokationsFromParents = new TreeSet<MethodInvokation>();
        mInvoktionsFromCurrentClass = new TreeSet<MethodInvokation>();
        mParentsReaders = new TreeSet<FieldAccess>();
        mCurrentClassSetters = new TreeSet<FieldAccess>();
        mMethodCouplings = new TreeSet<MethodCoupling>();

        for (JavaClass j : mParents) {
            mParentPool = new ConstantPoolGen(j.getConstantPool());
            mParent = j;
            mParentsMethods.add(j.getMethods());
            for (Method m : j.getMethods()) {
                m.accept(this);
            }
        }

        for (Method m : mMethods) {
            if (hasBeenDefinedInParentToo(m)) {
                investigateMethod(m);
            }
            investigateMethodAndLookForSetters(m);
        }

        countCase1();
        countCase2(); //TODO: remove duplications
        countCase3();
        saveResults();
    }

    /**
     * It is used to visit methods of parents of investigated class.
     */
    @Override
    public void visitMethod(final Method m) {
        MethodGen mg = new MethodGen(m, getParentClassName(), mParentPool);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {

                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitInvokeInstruction(InvokeInstruction ii) {
                            String methodName = "", className = "";
                            Type[] args = ii.getArgumentTypes(mParentPool);
                            methodName = ii.getMethodName(mParentPool);
                            className = ii.getClassName(mParentPool);

                            MethodInvokation mi = new MethodInvokation(className, methodName, args, getParentClassName(), m.getName(), m.getArgumentTypes());
                            mInvokationsFromParents.add(mi);

                        }

                        @Override
                        public void visitFieldInstruction(FieldInstruction fi) {
                            if (isGetInstruction(fi)) {
                                FieldAccess fa = new FieldAccess(fi.getFieldName(mParentPool), m, mParent);
                                mParentsReaders.add(fa);
                            }
                        }

                        private boolean isGetInstruction(FieldInstruction fi) {
                            String instr = fi.toString(mParentPool.getConstantPool());
                            return instr.startsWith("get");
                        }

                    });
                }
            }
        }
    }

    /**
     * Is mi a call of a redefined method?
     *
     * @param mi
     * @return Return true only when mi is an invokation of a method thas has been redefined in investigated class.
     */
    private boolean callsRedefinedMethod(MethodInvokation mi) {
        for (Method m : mMethods) {
            if (isInvocationOfTheMethod(m, mi)) {
                mi.setDestClass(mClassName);
                return true;
            }
        }
        return false;
    }

    private boolean compareTypes(Type[] args, Type[] args2) {
        boolean areEquals = args.length == args2.length;
        if (areEquals == true) {
            for (int i = 0; i < args.length; i++) {
                String miArgSignature = args2[i].getSignature();
                if (!args[i].getSignature().equals(miArgSignature)) {
                    areEquals = false;
                    break;
                }
            }
        }
        return areEquals;
    }

    private void countCase1() {
        for (FieldAccess fap : mParentsReaders) {
            if (!isFieldDefinedInCurrentClass(fap.getFieldName())) {
                for (FieldAccess fac : mCurrentClassSetters) {
                    if (fap.getFieldName().equals(fac.getFieldName())) {
                        MethodCoupling mc = new MethodCoupling(fap.getAccessorClass().getClassName(),
                                fap.getAccessor().getName(),
                                fac.getAccessorClass().getClassName(),
                                fac.getAccessor().getName());
                        if (mMethodCouplings.add(mc)) {
                            mCase1++;
                            //System.out.println("!!Case1!" + mc.toString() );
                            break;
                        }
                    }
                }
            }
        }
    }

    private void countCase2() {
        for (MethodInvokation mi : mInvokationsFromParents) {
            if (isFromParents(mi)) {
                if (mi.isNotConstructorInvocation()) {
                    if (callsRedefinedMethod(mi)) {
                        MethodCoupling mc = new MethodCoupling(mi.getDestClass(), mi.getDestMethod(),
                                mi.getSrcClass(), mi.getSrcMethod());
                        if (mMethodCouplings.add(mc)) {
                            //System.out.println( "!!Case2!" + mc.toString() );
                            mCase2++;
                        }
                    }
                }
            }
        }
    }

    private void countCase3() {
        boolean isFromParents = false;
        for (MethodInvokation mi : mInvoktionsFromCurrentClass) {
            if (mi.isNotConstructorInvocation() && !isRedefinedInCurrentClass(mi)) {
                for (int i = 0; i < mParentsMethods.size(); i++) {
                    for (Method m : mParentsMethods.get(i)) { //TODO: the while loop should be used
                        isFromParents = isInvocationOfTheMethod(m, mi);
                        if (isFromParents) {
                            mi.setDestClass(mParents[i].getClassName());
                            MethodCoupling mc = new MethodCoupling(mi.getDestClass(), mi.getDestMethod(),
                                    mi.getSrcClass(), mi.getSrcMethod());
                            if (mMethodCouplings.add(mc)) {
                                //System.out.println( "!!Case3!" + mc.toString() );
                                break;
                            }
                        }
                    }
                    if (isFromParents) {
                        break;
                    }
                }
                if (isFromParents) {
                    mCase3++;
                }
            }
        }
    }

    /**
     * Two methods are equal when the have the same name and the same set of arguments.
     */
    private boolean equalMethods(Method m, Method pm) {
        if (m.getName().equals(pm.getName())) {
            if (compareTypes(m.getArgumentTypes(), m.getArgumentTypes())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Investigates method - a member of the currently investigated class.
     */
    private void investigateMethod(final Method m) {
        MethodGen mg = new MethodGen(m, mClassName, mPoolGen);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {
                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitInvokeInstruction(InvokeInstruction ii) {
                            String methodName = "";
                            String className = "";
                            Type[] args = ii.getArgumentTypes(mPoolGen);
                            methodName = ii.getMethodName(mPoolGen);
                            className = ii.getClassName(mPoolGen);

                            if (args.length > 0) {
                                MethodInvokation mi = new MethodInvokation(className, methodName, args, mClassName, m.getName(), m.getArgumentTypes());
                                mInvoktionsFromCurrentClass.add(mi);
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Investigates method - a member of the currently investigated class.
     */
    private void investigateMethodAndLookForSetters(final Method m) {
        MethodGen mg = new MethodGen(m, mClassName, mPoolGen);
        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()) {
                Instruction i = ih.getInstruction();
                if (!visitInstruction(i)) {
                    i.accept(new org.apache.bcel.generic.EmptyVisitor() {

                        @Override
                        public void visitFieldInstruction(FieldInstruction fi) {

                            if (isSetInstruction(fi)) {
                                FieldAccess fa = new FieldAccess(fi.getFieldName(mPoolGen), m, mCurrentClass);
                                mCurrentClassSetters.add(fa);
                            }
                        }

                        private boolean isSetInstruction(FieldInstruction fi) {
                            String instr = fi.toString(mCurrentClass.getConstantPool());
                            return instr.startsWith("put");
                        }

                    });
                }
            }
        }
    }

    private boolean isFieldDefinedInCurrentClass(String fieldName) {
        for (Field f : mCurrentClass.getFields()) {
            if (f.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFromParents(MethodInvokation mi) {
        for (JavaClass jc : mParents) {
            if (jc.getClassName().equals(mi.getDestClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * It compares the method's names and the lists of method's arguments.
     */
    private boolean isInvocationOfTheMethod(Method m, MethodInvokation mi) {

        if (m.getName().equals(mi.getDestMethod())) {
            Type[] args = m.getArgumentTypes();
            boolean areEquals = compareTypes(args, mi.getDestMethodArgs());
            if (areEquals == true) {
                return true;
            }
        }
        return false;
    }


    private boolean hasBeenDefinedInParentToo(Method m) {

        String name = m.getName();
        if (name.equals("<init>") || name.equals("<clinit>")) {
            return false; //constructors cannot be redefined
        }

        Iterator<Method[]> itr = mParentsMethods.iterator();
        while (itr.hasNext()) {
            Method[] parentMethods = itr.next();
            for (Method pm : parentMethods) {
                if (equalMethods(m, pm)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isRedefinedInCurrentClass(MethodInvokation mi) {
        for (Method m : mMethods) {
            if (isInvocationOfTheMethod(m, mi)) {
                return true;
            }
        }
        return false;
    }

    private void saveResults() {
        int sum = mCase1 + mCase2 + mCase3;
        mClassMetrics.setCbm(sum);
        Set<String> coupledParents = new TreeSet<String>();

        for (MethodCoupling mc : mMethodCouplings) {
            if (mc.getClassA().equals(mClassName)) {
                coupledParents.add(mc.getClassB());
                if (mc.getClassB().equals(mClassName))
                    LoggerHelper.printError("Both of the involved in MethodCoupling classes are the investigated class!", new RuntimeException());
            } else {
                coupledParents.add(mc.getClassA());
                if (!mc.getClassB().equals(mClassName))
                    LoggerHelper.printError("None  of the involved in MethodCoupling classes is the investigated class!", new RuntimeException());
            }
        }
        mClassMetrics.setIc(coupledParents.size());
    }


    /**
     * Visit a single instruction.
     */
    private boolean visitInstruction(Instruction i) {
        short opcode = i.getOpcode();

        return ((InstructionConstants.INSTRUCTIONS[opcode] != null) /*&&
           !(i instanceof ConstantPushInstruction) &&
           !(i instanceof ReturnInstruction)*/);
    }

    /**
     * @return the Parent Class Name
     */
    private String getParentClassName() {
        return mParent.getClassName();
    }

}

