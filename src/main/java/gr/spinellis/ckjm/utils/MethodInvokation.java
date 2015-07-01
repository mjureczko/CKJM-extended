/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import org.apache.bcel.generic.Type;

/**
 *
 * @author mjureczk
 */
public class MethodInvokation implements Comparable<MethodInvokation>{
    private String mDestClass;
    private String mDestMethod;
    private Type[] mDestMethodArgs;
    private String mSrcClass;
    private String mSrcMethod;
    private Type[] mSrcMethodArgs;

    /** Non of the parameters can be null! */
    public MethodInvokation(String destClass, String destMethod, Type[] destArgs, String srcClass, String srcMethod, Type[] srcArgs ){
        mDestClass = destClass;
        mDestMethod = destMethod;
        mDestMethodArgs = destArgs;
        mSrcClass = srcClass;
        mSrcMethod = srcMethod;
        mSrcMethodArgs = srcArgs;
    }

    public void setDestClass(String destClass) {
        if( destClass == null ){
            LoggerHelper.printError( "Destination class shouldn't be setted to null in the MethodInvokation class!", new RuntimeException() );
        }
        mDestClass = destClass; 
    }

    /**
     * @return the mDestinationClass
     */
    public String getDestClass() {
        return mDestClass;
    }

    /**
     * @return the mMethodName
     */
    public String getDestMethod() {
        return mDestMethod;
    }

    /**
     * @return the mMethodArs
     */
    public Type[] getDestMethodArgs() {
        return mDestMethodArgs;
    }

    public boolean isNotConstructorInvocation() {
        if( !mDestMethod.equals("<init>") && !mDestMethod.equals("<clinit>") ){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return mDestClass + "." + mDestMethod + "(" + mDestMethodArgs.length + ")";
    }

    /**
     * @return the mSrcClass
     */
    public String getSrcClass() {
        return mSrcClass;
    }

    /**
     * @return the mSrcMethod
     */
    public String getSrcMethod() {
        return mSrcMethod;
    }

    /**
     * @return the mSrcMethodArgs
     */
    public Type[] getSrcMethodArgs() {
        return mSrcMethodArgs;
    }

    public int compareTo(MethodInvokation mi) {
        int sum = mDestClass.compareTo( mi.getDestClass() );
        sum += mDestMethod.compareTo( mi.getDestMethod() );
        sum += mSrcClass.compareTo( mi.getSrcClass() );
        sum += mSrcMethod.compareTo( mi.getSrcMethod() );

        String destArgsLocal = typesToString( mDestMethodArgs );
        String destArgsParam = typesToString( mi.getDestMethodArgs() );
        sum += destArgsLocal.compareTo( destArgsParam );

        String srcArgsLocal = typesToString( mSrcMethodArgs );
        String srcArgsParam = typesToString( mi.getSrcMethodArgs() );
        sum += srcArgsLocal.compareTo( srcArgsParam );

        return sum;
    }

    private String typesToString(Type[] types) {
        StringBuilder sb = new StringBuilder();
        for( Type t : types){
            sb.append( t.getSignature() );
        }
        return sb.toString();
    }
}


