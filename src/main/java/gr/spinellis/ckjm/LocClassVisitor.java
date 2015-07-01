/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;

/**
 * ClassVisitor, that counts lines of code in a class.
 * @author marian
 */
public class LocClassVisitor extends AbstractClassVisitor {

     

    public LocClassVisitor( IClassMetricsContainer classMap){
        super(classMap);
    }


    @Override
    protected void visitJavaClass_body(JavaClass jc) {
        String t;

        mClassMetrics.addLoc( jc.getFields().length );
        Method[] methods=jc.getMethods();
        mClassMetrics.addLoc( methods.length );

        for( Method m : methods ){
            m.accept( this );
        }
    }

    @Override
    public void visitMethod(Method meth){
        MethodGen mg = new MethodGen(meth, mClassName, mPoolGen);
        InstructionList il = mg.getInstructionList();

        if( il != null ){
            int instr = il.getLength();
            mClassMetrics.addLoc(instr);
        }
    }
}
