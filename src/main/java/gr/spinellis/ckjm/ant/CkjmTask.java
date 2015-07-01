/*
 * $Id: CkjmTask.java 1.3 2007/07/25 15:19:09 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis, Julien Rentrop
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

package gr.spinellis.ckjm.ant;


import gr.spinellis.ckjm.MetricsFilter;
import gr.spinellis.ckjm.PrintPlainResults;
import gr.spinellis.ckjm.utils.LoggerHelper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;

import java.io.*;

/**
 * Ant task definition for the CKJM metrics tool.
 *
 * @author Julien Rentrop
 * @version $Revision: 1.3 $
 */
public class CkjmTask extends MatchingTask {
    private File mOutputFile;

    private File mClassDir;

    private Path mExtdirs;

    private String mFormat;

    private String[] mJars;

    public CkjmTask() {
        this.mFormat = "plain";

    }

    /**
     * Sets the format of the output file.
     *
     * @param format the format of the output file. Allowable values are 'plain' or
     *               'xml'.
     */
    public void setFormat(String format) {
        this.mFormat = format;

    }

    /**
     * Sets the outputfile
     *
     * @param outputfile Location of outputfile
     */
    public void setOutputfile(File outputfile) {
        this.mOutputFile = outputfile;
    }

    /**
     * Sets the dir which contains the class files that will be analyzed
     *
     * @param classDir Location of class files
     */
    public void setClassdir(File classDir) {
        this.mClassDir = classDir;
    }

    /**
     * Sets the extension directories that will be used by ckjm.
     *
     * @param e xtdirs a path containing .jar files
     */
    public void setExtdirs(Path e) {
        if (mExtdirs == null) {
            mExtdirs = e;
        } else {
            mExtdirs.append(e);
        }
    }

    /**
     * Gets the extension directories that will be used by ckjm.
     *
     * @return the extension directories as a path
     */
    public Path getExtdirs() {
        return mExtdirs;
    }

    /**
     * Adds a path to extdirs.
     *
     * @return a path to be modified
     */
    public Path createExtdirs() {
        if (mExtdirs == null) {
            mExtdirs = new Path(getProject());
        }
        return mExtdirs.createPath();
    }

    /**
     * Executes the CKJM Ant Task. This method redirects the output of the CKJM
     * tool to a file. When XML format is used it will buffer the output and
     * translate it to the XML format.
     *
     * @throws BuildException if an error occurs.
     */
    @Override
    public void execute() throws BuildException {

        if (getExtdirs() != null && getExtdirs().size() > 0) {
            if (System.getProperty("java.ext.dirs") == null || System.getProperty("java.ext.dirs").length() == 0)
                System.setProperty("java.ext.dirs", mExtdirs.toString());
            else
                System.setProperty("java.ext.dirs", System.getProperty("java.ext.dirs") + File.pathSeparator + mExtdirs);
            LoggerHelper.printWarning("You're using following java.ext.dirs: " + System.getProperty("java.ext.dirs"));
        }

        String[] files;
        if (mJars != null) {
            files = countJars();
        } else {
            files = countDirectory();
        }

        if (files.length == 0) {
            LoggerHelper.printError("No class files in specified location!");
        } else {
            try {
                OutputStream outputStream = new FileOutputStream(mOutputFile);

                if (mFormat.equals("xml")) {
                    PrintXmlResults outputXml = new PrintXmlResults(new PrintStream(outputStream));

                    outputXml.printHeader();
                    MetricsFilter.runMetrics(files, outputXml, includeJDK);
                    outputXml.printFooter();
                } else {
                    PrintPlainResults outputPlain = new PrintPlainResults(new PrintStream(outputStream));
                    MetricsFilter.runMetrics(files, outputPlain, includeJDK);
                }
                outputStream.close();

            } catch (IOException ioe) {
                throw new BuildException("Error file handling: " + ioe.getMessage());
            }
        }
    }

    /**
     * includes JDK classes in CBO, CA and CE metric
     */
    private boolean includeJDK = false;

    public void setIncludeJDK(boolean includeJDK) {
        this.includeJDK = includeJDK;
    }

    /**
     * @param jars ; separated list of jar files, that should be investigated
     */
    public void setClassJars(String jars) {
        mJars = jars.split(":");
    }

    private String[] countDirectory() {
        if (mClassDir == null) {
            throw new BuildException("classdir attribute must be set!");
        }
        if (!mClassDir.exists()) {
            throw new BuildException("classdir does not exist!");
        }
        if (!mClassDir.isDirectory()) {
            throw new BuildException("classdir is not a directory!");
        }

        DirectoryScanner ds = super.getDirectoryScanner(mClassDir);

        String files[] = ds.getIncludedFiles();

        for (int i = 0; i < files.length; i++) {
            files[i] = mClassDir.getPath() + File.separatorChar + files[i];
        }
        return files;

    }

    private String[] countJars() {
        for (String name : mJars) {
            if (!name.toLowerCase().endsWith(".jar")) {
                throw new BuildException("Given jar file isn't in fact a jar file: " + name + "!");
            }
            File f = new File(name);
            if (!f.exists()) {
                throw new BuildException("Given jar file doesn't exist: " + name + "!");
            }
            if (!f.isFile()) {
                throw new BuildException("Given jar file isn't in fact a jar file: " + name + "!");
            }
        }
        return mJars;
    }
}
