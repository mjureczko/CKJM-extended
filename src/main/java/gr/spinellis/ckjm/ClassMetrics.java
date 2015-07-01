/*
 * $Id: ClassMetrics.java 1.12 2007/07/25 12:24:00 dds Exp $
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

import gr.spinellis.ckjm.utils.LoggerHelper;

import java.util.*;

/**
 * Store details needed for calculating a class's Chidamber-Kemerer metrics.
 * Most fields in this class are set by ClassVisitor.
 * This class also encapsulates some policy decision regarding metrics
 * measurement.
 *
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 * @version $Revision: 1.12 $
 * @see ClassVisitor
 */
public class ClassMetrics {
    private static final String endl = System.getProperty("line.separator");
    private static final String mLcomToHigh = "Value of LCOM3 cannot be set to more than 2.";
    private static final String mLcomToLow = "Value of LCOM3 cannot be set to less than 0.";
    /**
     * Line of codes per class
     */
    private int mLoc;
    /**
     * Weighted methods per class
     */
    private int mWmc;
    /**
     * Number of children
     */
    private int mNoc;
    /**
     * Response for a Class
     */
    private int mRfc;
    /**
     * Coupled classes: classes being used by this class
     */
    private HashSet<String> mEfferentCoupledClasses;
    /**
     * Depth of inheritence tree
     */
    private int mDit;
    /**
     * Lack of cohesion in methods
     */
    private int mLcom;
    /**
     * Lack of cohesion in methods - Henderson-Sellers definition
     */
    private double mLcom3;
    /**
     * Number of public methods
     */
    private int mNpm;
    /**
     * True if the class has been visited by the metrics gatherer
     */
    private boolean mVisited;
    /**
     * True if the class is public
     */
    private boolean mIsPublicClass;
    /**
     * Coupled classes: classes that use this class
     */
    private HashSet<String> mAfferentCoupledClasses;
    /**
     * Signatures of methods and values of McCabe Cyclomatic Complexity
     */
    private Map<String, Integer> mMapCyclomaticComlpexity;
    /**
     * Data Access Metric
     */
    private double mDam;
    /**
     * Measure of Aggregation
     */
    private int mMoa;
    /**
     * Measure of Functional Abstraction
     */
    private double mMfa;
    /**
     * Cohesion Among Methods of Class
     */
    private double mCam;
    /**
     * Inheritance Coupling
     */
    private int mIc;
    /**
     * Coupling Between Methods
     */
    private int mCBM;
    /**
     * Average Method Complexity
     */
    private double mAmc;

    /**
     * Default constructor.
     */
    public ClassMetrics() {
        mEfferentCoupledClasses = new HashSet<String>();
        mVisited = false;
        mAfferentCoupledClasses = new HashSet<String>();
        mMapCyclomaticComlpexity = new HashMap<String, Integer>();
    }

    /**
     * Return true if the class name is part of the Java SDK
     */
    public static boolean isJdkClass(String s) {
        return (s.startsWith("java.") ||
                s.startsWith("javax.") ||
                s.startsWith("org.omg.") ||
                s.startsWith("org.w3c.dom.") ||
                s.startsWith("org.xml.sax."));
    }

    /**
     * Increments the lines of code count
     */
    public void addLoc(int a) {
        mLoc += a;
    }

    /**
     * Return the lines of code metric
     */
    public int getLoc() {
        return mLoc;
    }

    /**
     * Increment the weighted methods count
     */
    public void incWmc() {
        mWmc++;
    }

    /**
     * Return the weighted methods per class metric
     */
    public int getWmc() {
        return mWmc;
    }

    /**
     * Increment the number of children
     */
    public void incNoc() {
        mNoc++;
    }

    /**
     * Return the number of children
     */
    public int getNoc() {
        return mNoc;
    }

    /**
     * Return the Response for a Class
     */
    public int getRfc() {
        return mRfc;
    }

    /**
     * Increment the Response for a Class
     */
    public void setRfc(int r) {
        mRfc = r;
    }

    /**
     * Return the depth of the class's inheritance tree
     */
    public int getDit() {
        return mDit;
    }

    /**
     * Set the depth of inheritence tree metric
     */
    public void setDit(int d) {
        mDit = d;
    }

    /** Set the coupling between object classes metric */

    /**
     * Return the coupling between object classes metric
     */
    public int getCbo() {
        HashSet<String> mCoupledClasses = new HashSet<String>();
        mCoupledClasses.addAll(mAfferentCoupledClasses);
        mCoupledClasses.addAll(mEfferentCoupledClasses);
        return mCoupledClasses.size();
    }

    /**
     * Return the class's afferent couplings metric
     */
    public int getCe() {
        return mEfferentCoupledClasses.size();
    }

    /**
     * Set the efferent coupling
     */
    public void setCe(HashSet<String> c) {
        mEfferentCoupledClasses = c;
    }

    /**
     * Return the class's lack of cohesion in methods metric
     */
    public int getLcom() {
        return mLcom;
    }

    /**
     * Set the class's lack of cohesion in methods metric
     */
    public void setLcom(int l) {
        mLcom = l;
    }

    /**
     * Return the class's afferent couplings metric
     */
    public int getCa() {
        return mAfferentCoupledClasses.size();
    }

    /**
     * Add a class to the set of classes that depend on this class
     */
    public void addAfferentCoupling(String name) {
        mAfferentCoupledClasses.add(name);
    }

    /**
     * Increment the number of public methods count
     */
    public void incNpm() {
        mNpm++;
    }

    /**
     * Return the number of public methods metric
     */
    public int getNpm() {
        return mNpm;
    }

    /**
     * Return true if the class is public
     */
    public boolean isPublic() {
        return mIsPublicClass;
    }

    /**
     * Call to set the class as public
     */
    public void setPublic() {
        mIsPublicClass = true;
    }

    /**
     * Return the 6 CK metrics plus Ce pluc LCOM3 as a space-separated string
     */
    @Override
    public String toString() {
        return  //TODO: JUnit test is mising.
                getWmc() +
                        " " + getDit() +
                        " " + getNoc() +
                        " " + getCbo() +
                        " " + getRfc() +
                        " " + getLcom() +
                        " " + getCa() +
                        " " + getCe() +
                        " " + getNpm() +
                        " " + String.format("%.4f", getLcom3()) +
                        " " + getLoc() +
                        " " + String.format("%.4f", getDam()) +
                        " " + getMoa() +
                        " " + String.format("%.4f", getMfa()) +
                        " " + String.format("%.4f", getCam()) +
                        " " + getIc() +
                        " " + getCbm() +
                        " " + String.format("%.4f", getAmc()) +
                        endl + printPlainCC();
    }

    /**
     * Mark the instance as visited by the metrics analyzer
     */
    public void setVisited() {
        mVisited = true;
    }

    /**
     * Return true if the class has been visited by the metrics analyzer.
     * Classes may appear in the collection as a result of some kind
     * of coupling.  However, unless they are visited and analyzed,
     * we do not want them to appear in the output results.
     */
    public boolean isVisited() {
        return mVisited;
    }

    /**
     * Return te value of LCOM3 metric
     */
    public double getLcom3() {
        if (mLcom3 <= 0) return 0; //it is necessary becouse of round errors in values that are very close to 0
        return mLcom3;
    }

    /**
     * Set the value of LCOM3 metric
     */
    public void setLcom3(double lcom3) {
        if (lcom3 < 0) {
            LoggerHelper.printError(mLcomToLow, new RuntimeException());
            lcom3 = 0;
        }
        if (lcom3 > 2) {
            LoggerHelper.printError(mLcomToHigh, new RuntimeException());
            lcom3 = 2;
        }
        this.mLcom3 = lcom3;
    }

    /**
     * Returns names of methods in class. Value of McCabe Cyclomatic Complexity has been counted for those methods.
     */
    public List<String> getMethodNames() {
        LinkedList<String> methodNames = new LinkedList<String>();

        Set<String> mNames = mMapCyclomaticComlpexity.keySet();
        Iterator<String> itr = mNames.iterator();
        while (itr.hasNext())
            methodNames.add(itr.next());

        return methodNames;
    }

    /**
     * Return McCabe Cyclomatic Comlplexity for given method
     */
    public int getCC(String key) {
        Integer i = mMapCyclomaticComlpexity.get(key);
        if (i == null)
            return 0;
        else
            return i;
    }

    /**
     * Add method by signature and McCabe Cyclomatic Complexity value
     */
    public void addMethod(String signature, int cc) {
        if (signature == null)
            return;

        signature = signature.split("\n")[0]; //removes throws ExceptionName from the signature
        mMapCyclomaticComlpexity.put(signature, new Integer(cc));
    }

    public double getDam() {
        return mDam;
    }

    public void setDam(double a) {
        mDam = a;
    }

    /**
     * Return description of CC metrics
     */
    private String printPlainCC() {
        StringBuffer plainCC = new StringBuffer();
        List<String> methodNames = getMethodNames();
        Iterator<String> itr = methodNames.iterator();
        String name;

        while (itr.hasNext()) {
            name = itr.next();
            plainCC.append(" ~ " + name + ": ");
            plainCC.append(getCC(name));
            plainCC.append(endl);
        }

        return plainCC.toString();
    }

    public int getMoa() {
        return mMoa;
    }

    public void setMoa(int a) {
        mMoa = a;
    }

    public double getMfa() {
        return mMfa;
    }

    public void setMfa(double mfa) {
        mMfa = mfa;
    }

    public double getCam() {
        return mCam;
    }

    public void setCam(double cam) {
        mCam = cam;
    }

    /**
     * @return the mIc
     */
    public int getIc() {
        return mIc;
    }

    /**
     * @param Ic the mIc to set
     */
    public void setIc(int Ic) {
        this.mIc = Ic;
    }

    /**
     * @return the mCBM
     */
    public int getCbm() {
        return mCBM;
    }

    /**
     * @param mCBM the mCBM to set
     */
    public void setCbm(int mCBM) {
        this.mCBM = mCBM;
    }

    /**
     * @return the mAmc
     */
    public double getAmc() {
        return mAmc;
    }

    /**
     * @param mAmc the mAmc to set
     */
    public void setAmc(double mAmc) {
        this.mAmc = mAmc;
    }
}
