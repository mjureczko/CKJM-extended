/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;


/**
 *
 * @author mjureczk
 */
public class MethodCoupling implements Comparable<MethodCoupling>{
    private String mClassA;
    private String mClassB;
    private String mMethodA;
    private String mMethodB;
    

    public MethodCoupling( String classA, String methodA, String classB, String methodB) {
        mClassA = classA;
        mMethodA = methodA;
        mClassB = classB;
        mMethodB = methodB;

        if( classA.equals(classB) && methodA.equals(methodB) ){
            LoggerHelper.printError( "Method "+classA+"."+methodA+" is coupled to itself!", new RuntimeException() );

        }
        else if( classA.equals(classB) ){
            LoggerHelper.printError( "Coupling within methods in the same class ("+classA+"): "+methodA+" is coupled to "+methodB+"!", new RuntimeException() );
        }

    }


    @Override
    public String toString(){
        return mClassA+"."+mMethodA+" is coupled to "+mClassB+"."+mMethodB;
    }

    public int compareTo(MethodCoupling mc) {
        int res=0;

        res = mClassA.compareTo( mc.getClassB() );
        res += mClassB.compareTo( mc.getClassA() );
        res += mMethodA.compareTo( mc.getMethodB() );
        res += mMethodB.compareTo( mc.getMethodA() );

        if( res == 0 ){
            return res;
        }

        res = mClassA.compareTo( mc.getClassA() );
        res += mClassB.compareTo( mc.getClassB() );
        res += mMethodA.compareTo( mc.getMethodA() );
        res += mMethodB.compareTo( mc.getMethodB() );

        return res;
    }

    /**
     * @return the mClassA
     */
    public String getClassA() {
        return mClassA;
    }

    /**
     * @return the mClassB
     */
    public String getClassB() {
        return mClassB;
    }

    /**
     * @return the mMethodA
     */
    public String getMethodA() {
        return mMethodA;
    }

    /**
     * @return the mMethodB
     */
    public String getMethodB() {
        return mMethodB;
    }



}
