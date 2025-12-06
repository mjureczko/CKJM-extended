/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;


import java.util.Comparator;
import java.util.Objects;

/**
 * @author mjureczk
 */
public class MethodCoupling implements Comparable<MethodCoupling> {
    private String mClassA;
    private String mClassB;
    private String mMethodA;
    private String mMethodB;


    public MethodCoupling(String classA, String methodA, String classB, String methodB) {
        mClassA = classA;
        mMethodA = methodA;
        mClassB = classB;
        mMethodB = methodB;

        if (classA.equals(classB) && methodA.equals(methodB)) {
            LoggerHelper.printError("Method " + classA + "." + methodA + " is coupled to itself!", new RuntimeException());

        } else if (classA.equals(classB)) {
            LoggerHelper.printError("Coupling within methods in the same class (" + classA + "): " + methodA + " is coupled to " + methodB + "!", new RuntimeException());
        }

    }


    @Override
    public String toString() {
        return mClassA + "." + mMethodA + " is coupled to " + mClassB + "." + mMethodB;
    }

    public int compareTo(MethodCoupling mc) {
        MethodCoupling reverted = new MethodCoupling(mc.getClassB(), mc.getMethodB(), mc.getClassA(), mc.getMethodA());
        int compare = exactCompare(reverted);
        if (compare == 0) {
            return compare;
        }
        return exactCompare(mc);
    }

    private int exactCompare(MethodCoupling mc) {
        return Comparator
                .comparing(MethodCoupling::getClassA)
                .thenComparing(MethodCoupling::getClassB)
                .thenComparing(MethodCoupling::getMethodA)
                .thenComparing(MethodCoupling::getMethodB)
                .compare(this, mc);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MethodCoupling)) return false;

        MethodCoupling other = (MethodCoupling) obj;

        // Check both orders
        boolean direct = mClassA.equals(other.mClassA) &&
                mMethodA.equals(other.mMethodA) &&
                mClassB.equals(other.mClassB) &&
                mMethodB.equals(other.mMethodB);

        boolean reversed = mClassA.equals(other.mClassB) &&
                mMethodA.equals(other.mMethodB) &&
                mClassB.equals(other.mClassA) &&
                mMethodB.equals(other.mMethodA);

        return direct || reversed;
    }

    @Override
    public int hashCode() {
        // Since equals is symmetric, hash must also be symmetric
        // One way: combine the pairs in sorted order
        int hash1 = Objects.hash(mClassA, mMethodA) + Objects.hash(mClassB, mMethodB);
        int hash2 = Objects.hash(mClassB, mMethodB) + Objects.hash(mClassA, mMethodA);
        return hash1 ^ hash2; // XOR ensures symmetry
    }
}
