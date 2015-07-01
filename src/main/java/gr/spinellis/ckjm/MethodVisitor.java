/*
 * $Id: MethodVisitor.java 1.8 2005/10/09 15:36:08 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package gr.spinellis.ckjm;

import org.apache.bcel.generic.*;
import org.apache.bcel.Constants;


/**
 * Visit a method calculating the class's Chidamber-Kemerer metrics.
 * A helper class for ClassVisitor.
 *
 * @see ClassVisitor
 * @version $Revision: 1.8 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
class MethodVisitor extends EmptyVisitor {
    /** Method generation template. */
    private MethodGen mg;
    /* The class's constant pool. */
    private ConstantPoolGen cp;
    /** The visitor of the class the method visitor is in. */
    private ClassVisitor cv;
    /** The metrics of the class the method visitor is in. */
    private ClassMetrics cm;

    /** Set of methods that are called by the mg method. */
    private TreeSetWithId<String> mm = new TreeSetWithId<String>();
    
    /** Constructor. */
    MethodVisitor(MethodGen m, ClassVisitor c) {
        mg  = m;
        cv = c;
        cp  = mg.getConstantPool();
        cm = cv.getMetrics();
        cm.addMethod(mg.toString(), 1);
    }

    /** Start the method's visit. */
    public void start() {   

        if (!mg.isAbstract() && !mg.isNative()) {
            for (InstructionHandle ih = mg.getInstructionList().getStart(); ih != null; ih = ih.getNext()){
                Instruction i = ih.getInstruction();

                if(!visitInstruction(i))
                    i.accept(this);
            }
            updateExceptionHandlers();
        }
    }

    /** Visit a single instruction. */
    private boolean visitInstruction(Instruction i) {
        short opcode = i.getOpcode();

        return ((InstructionConstants.INSTRUCTIONS[opcode] != null) &&
           !(i instanceof ConstantPushInstruction) &&
           !(i instanceof ReturnInstruction));
    }

    /** */
    @Override
    public void visitBranchInstruction(BranchInstruction obj) 
    {
        String instruction = obj.toString();
        String method=mg.toString();
        int oldValue = cm.getCC(method);
        
        if( instruction.contains( "lookupswitch") ) //if switch-case construction
        {
            char[] instr=instruction.toCharArray();
            int numb=0;
            
            for( int i=0; i<instr.length; i++ )
                if( instr[i] == '[' )           //number of '[' is equal to number of cases
                    numb++;
            cm.addMethod(method, oldValue+numb);
        }
        else if( !instruction.contains( "goto" ) )    
            cm.addMethod(method, oldValue+1);
        
        
    }
    
    /** Local variable use. */
    @Override
    public void visitLocalVariableInstruction(LocalVariableInstruction i) {
        if(i.getOpcode() != Constants.IINC)
            cv.registerCoupling(i.getType(cp));
    }

    /** Array use. */
    @Override
    public void visitArrayInstruction(ArrayInstruction i) {
        cv.registerCoupling(i.getType(cp));
    }

    /** Field access. */
    @Override
    public void visitFieldInstruction(FieldInstruction i) {
        cv.registerFieldAccess(i.getClassName(cp), i.getFieldName(cp));
        cv.registerCoupling(i.getFieldType(cp));
    }

    /** Method invocation. */
    @Override
    public void visitInvokeInstruction(InvokeInstruction i) {
        Type[] argTypes   = i.getArgumentTypes(cp);
        for (int j = 0; j < argTypes.length; j++)
            cv.registerCoupling(argTypes[j]);
        cv.registerCoupling(i.getReturnType(cp));
        /* Measuring decision: measure overloaded methods separately */
        cv.registerMethodInvocation(i.getClassName(cp), i.getMethodName(cp), argTypes);
    }

    /** Visit an instanceof instruction. */
    @Override
    public void visitINSTANCEOF(INSTANCEOF i) {
        cv.registerCoupling(i.getType(cp));
    }

    /** Visit checklast instruction. */
    @Override
    public void visitCHECKCAST(CHECKCAST i) {
        cv.registerCoupling(i.getType(cp));
    }

    /** Visit return instruction. */
    @Override
    public void visitReturnInstruction(ReturnInstruction i) {
        cv.registerCoupling(i.getType(cp));
    }

    /** Visit the method's exception handlers. */
    private void updateExceptionHandlers() {
        CodeExceptionGen[] handlers = mg.getExceptionHandlers();

        /* Measuring decision: couple exceptions */
        for(int i=0; i < handlers.length; i++) {
            Type t = handlers[i].getCatchType();
            if (t != null)
            cv.registerCoupling(t);
        }
    }

    @Override
    public void visitFieldOrMethod(FieldOrMethod obj)
    {
        if( obj.getName().startsWith("invoke") )
            if( obj.getClassName( cp ).equals(cv.getMyClassName()) )
            {
                mm.add( obj.getName(cp) + obj.getSignature(cp) );
            }
    }

    /** Return names of methods that are called by visited method. */
    public TreeSetWithId<String> getMethodsNames()
    {
        return mm;
    }
}
