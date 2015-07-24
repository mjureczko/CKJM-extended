/*
 * $Id: PrintXmlResults.java 1.4 2005/11/05 08:33:18 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis, Julien Rentrop
 *
 * Permission to use, copy, and distribute this software and its documentation
 * for any purpose and without fee is hereby granted, provided that the above
 * copyright notice appear in all copies and that both that copyright notice and
 * this permission notice appear in supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package gr.spinellis.ckjm.ant;


import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

/**
 * XML output formatter
 *
 * @author Julien Rentrop
 */
public class PrintXmlResults implements CkjmOutputHandler {
    private PrintStream p;
    private static String endl = System.getProperty("line.separator");

    public PrintXmlResults(PrintStream p) {
        this.p = p;
    }

    public void printHeader() {
        p.println("<?xml version=\"1.0\"?>");
        p.println("<ckjm>");
    }

    public void handleClass(String name, ClassMetrics c) {
        p.println("\t<class>");
        p.println("\t\t<name>" + name + "</name>");
        p.println("\t\t<wmc>" + c.getWmc() + "</wmc>");
        p.println("\t\t<dit>" + c.getDit() + "</dit>");
        p.println("\t\t<noc>" + c.getNoc() + "</noc>");
        p.println("\t\t<cbo>" + c.getCbo() + "</cbo>");
        p.println("\t\t<rfc>" + c.getRfc() + "</rfc>");
        p.println("\t\t<lcom>" + c.getLcom() + "</lcom>");
        p.println("\t\t<ca>" + c.getCa() + "</ca>");
        p.println("\t\t<ce>" + c.getCe() + "</ce>");
        p.println("\t\t<npm>" + c.getNpm() + "</npm>");
        p.println("\t\t<lcom3>" + c.getLcom3() + "</lcom3>");
        p.println("\t\t<loc>" + c.getLoc() + "</loc>");
        p.println("\t\t<dam>" + c.getDam() + "</dam>");
        p.println("\t\t<moa>" + c.getMoa() + "</moa>");
        p.println("\t\t<mfa>" + c.getMfa() + "</mfa>");
        p.println("\t\t<cam>" + c.getCam() + "</cam>");
        p.println("\t\t<ic>" + c.getIc() + "</ic>");
        p.println("\t\t<cbm>" + c.getCbm() + "</cbm>");
        p.println("\t\t<amc>" + c.getAmc() + "</amc>");
        p.println(printXmlCC(c));
        p.println("\t</class>");
    }

    public void printFooter() {
        p.println("</ckjm>");
    }

    private String printXmlCC(ClassMetrics cm) {
        StringBuilder xmlCC = new StringBuilder();
        List<String> methodNames = cm.getMethodNames();
        Iterator<String> itr = methodNames.iterator();
        String name;

        xmlCC.append("\t\t<cc>").append(endl);
        while (itr.hasNext()) {
            name = itr.next();
            xmlCC.append(String.format("\t\t\t<method name=\"%s\">", name.replaceAll("<|>", "_")));
            xmlCC.append(cm.getCC(name));
            xmlCC.append("</method>").append(endl);
        }
        xmlCC.append("\t\t</cc>");

        return xmlCC.toString();
    }
}
