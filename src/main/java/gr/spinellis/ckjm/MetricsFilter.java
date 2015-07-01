/*
 * $Id: MetricsFilter.java 1.9 2005/08/10 16:53:36 dds Exp $
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

import gr.spinellis.ckjm.ant.PrintXmlResults;
import gr.spinellis.ckjm.utils.CmdLineParser;
import gr.spinellis.ckjm.utils.LoggerHelper;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Convert a list of classes into their metrics.
 * Process standard input lines or command line arguments
 * containing a class file name or a jar file name,
 * followed by a space and a class file name.
 * Display on the standard output the name of each class, followed by its
 * six Chidamber Kemerer metrics:
 * TODO: The list of metrics is constantly being changed...
 * WMC, DIT, NOC, CBO, RFC, LCOM, Ca, *Ce*,  NPM, LCOM3, CC
 *
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 * @version $Revision: 1.9 $
 * @see ClassMetrics
 */
public class MetricsFilter implements ICountingProperities {
    /**
     * True if the measurements should include calls to the Java JDK into account
     */
    private boolean mIncludeJdk = false;

    /**
     * True if the reports should only include public classes
     */
    private boolean mOnlyPublic = false;
    /**
     * The same instance of MoaClassVisitor must be used to process all class, so it must be a class field.
     */
    private MoaClassVisitor mMoaVisitor;
    /**      */
    private IClassMetricsContainer mMetricsContainer;

    public MetricsFilter() {
        mMetricsContainer = new ClassMetricsContainer(this);
        mMoaVisitor = new MoaClassVisitor(mMetricsContainer);
    }

    /**
     * The interface for other Java based applications.
     * Implement the outputhandler to catch the results
     *
     * @param files         Class files to be analyzed
     * @param outputHandler An implementation of the CkjmOutputHandler interface
     */
    public static void runMetrics(String[] files, CkjmOutputHandler outputHandler, boolean includeJDK) {
        MetricsFilter mf = new MetricsFilter();
        mf.mIncludeJdk = includeJDK;

        mf.runMetricsInternal(files, outputHandler);
    }

    /**
     * The filter's main body.
     * Process command line arguments and the standard input.
     */
    public static void main(String[] argv) {
        MetricsFilter mf = new MetricsFilter();
        CmdLineParser cmdParser = new CmdLineParser();

        cmdParser.parse(argv);

        if (cmdParser.isArgSet("s")) {
            mf.mIncludeJdk = true;
        }
        if (cmdParser.isArgSet("p")) {
            mf.mOnlyPublic = true;
        }

        CkjmOutputHandler handler;
        if (cmdParser.isArgSet("x")) {
            handler = new PrintXmlResults(new PrintStream(System.out));
        } else {
            handler = new PrintPlainResults(System.out);
        }

        String[] tmp = new String[1];
        mf.runMetricsInternal(cmdParser.getClassNames().toArray(tmp), handler);

    }

    /**
     * Return true if the measurements should include calls to the Java JDK into account
     */
    public boolean isJdkIncluded() {
        return mIncludeJdk;
    }

    /**
     * Return true if the measurements should include all classes
     */
    public boolean includeAll() {
        return !mOnlyPublic;
    }

    /**
     * Load and parse the specified class.
     * The class specification can be either a class file name, or
     * a jarfile, followed by space, followed by a class file name.
     */
    void processClass(String clspec) {
        int spc;
        JavaClass jc = null;

        if (clspec.toLowerCase().endsWith(".jar")) {
            JarFile jf;
            try {
                jf = new JarFile(clspec);
                Enumeration<JarEntry> entries = jf.entries();

                while (entries.hasMoreElements()) {
                    String cl = entries.nextElement().getName();
                    if (cl.toLowerCase().endsWith(".class")) {
                        try {
                            jc = new ClassParser(clspec, cl).parse();
                            processClass(jc);
                        } catch (IOException e) {
                            LoggerHelper.printError("Error loading " + cl + " from " + clspec + ": " + e);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(MetricsFilter.class.getName()).log(Level.SEVERE, "Unable to load jar file " + clspec, ex);
            }

        } else {
            try {
                jc = new ClassParser(clspec).parse();
                processClass(jc);
            } catch (IOException e) {
                LoggerHelper.printError("Error loading " + clspec + ": " + e);
            }
        }
    }

    private void processClass(JavaClass jc) {
        if (jc != null) {
            ClassVisitor visitor = new ClassVisitor(jc, mMetricsContainer, this);
            visitor.start();
            visitor.end();
            LocClassVisitor locVisitor = new LocClassVisitor(mMetricsContainer);
            locVisitor.visitJavaClass(jc);
            DamClassVisitor damVisitor = new DamClassVisitor(jc, mMetricsContainer);
            damVisitor.visitJavaClass(jc);
            mMoaVisitor.visitJavaClass(jc);
            MfaClassVisitor mfaVisitor = new MfaClassVisitor(mMetricsContainer);
            mfaVisitor.visitJavaClass(jc);
            CamClassVisitor camVisitor = new CamClassVisitor(mMetricsContainer);
            camVisitor.visitJavaClass(jc);
            IcAndCbmClassVisitor icVisitor = new IcAndCbmClassVisitor(mMetricsContainer);
            icVisitor.visitJavaClass(jc);
            AmcClassVisitor amcVisitor = new AmcClassVisitor(mMetricsContainer);
            amcVisitor.visitJavaClass(jc);
        }
    }

    /**
     * The interface for other Java based applications.
     * Implement the outputhandler to catch the results
     *
     * @param files         Class files to be analyzed
     * @param outputHandler An implementation of the CkjmOutputHandler interface
     */
    private void runMetricsInternal(String[] files, CkjmOutputHandler outputHandler) {

        for (int i = 0; i < files.length; i++)
            processClass(files[i]);

        mMoaVisitor.end();
        mMetricsContainer.printMetrics(outputHandler);
    }


}
