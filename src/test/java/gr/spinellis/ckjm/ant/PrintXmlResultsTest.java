/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.ant;

import gr.spinellis.ckjm.ClassMetrics;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class PrintXmlResultsTest {

    PrintXmlResults mXmlRes;
    MyPrintStream mPrinter;

    public PrintXmlResultsTest() {
    }

    @Before
    public void setUp() {
        mPrinter = new MyPrintStream();
        mXmlRes = new PrintXmlResults( mPrinter );
    }

    /**
     * Test of printHeader method, of class PrintXmlResults.
     */
    @Test
    public void testPrintHeader() {
        mXmlRes.printHeader();
        assertEquals("<?xml version=\"1.0\"?>", mPrinter.mOut.get(0) );
        assertEquals("<ckjm>", mPrinter.mOut.get(1) );
    }

    /**
     * Test of handleClass method, of class PrintXmlResults.
     * The class under test is a dammy class. Only ithe structure of xml file is tested.
     */
    @Test
    public void testHandleClass() {
        String className = "EmptyClass";
        String methodSignature = "method";
        int cc = 7;

        ClassMetrics cm = new ClassMetrics();
        cm.addMethod(methodSignature, cc);
        mXmlRes.handleClass( className, cm );
        assertEquals( "\t<class>", mPrinter.mOut.get(0) );
        assertEquals( "\t\t<name>"+className+"</name>", mPrinter.mOut.get(1) );
        assertEquals( "\t\t<wmc>0</wmc>", mPrinter.mOut.get(2) );
        assertEquals( "\t\t<dit>0</dit>", mPrinter.mOut.get(3) );
        assertEquals( "\t\t<noc>0</noc>", mPrinter.mOut.get(4) );
        assertEquals( "\t\t<cbo>0</cbo>", mPrinter.mOut.get(5) );
        assertEquals( "\t\t<rfc>0</rfc>", mPrinter.mOut.get(6) );
        assertEquals( "\t\t<lcom>0</lcom>", mPrinter.mOut.get(7) );
        assertEquals( "\t\t<ca>0</ca>", mPrinter.mOut.get(8) );
        assertEquals( "\t\t<ce>0</ce>", mPrinter.mOut.get(9) );
        assertEquals( "\t\t<npm>0</npm>", mPrinter.mOut.get(10) );
        assertEquals( "\t\t<lcom3>0.0</lcom3>", mPrinter.mOut.get(11) );
        assertEquals( "\t\t<loc>0</loc>", mPrinter.mOut.get(12) );
        assertEquals( "\t\t<dam>0.0</dam>", mPrinter.mOut.get(13) );
        assertEquals( "\t\t<moa>0</moa>", mPrinter.mOut.get(14) );
        assertEquals( "\t\t<mfa>0.0</mfa>", mPrinter.mOut.get(15) );
        assertEquals( "\t\t<cam>0.0</cam>", mPrinter.mOut.get(16) );
        assertEquals( "\t\t<ic>0</ic>", mPrinter.mOut.get(17) );
        assertEquals( "\t\t<cbm>0</cbm>", mPrinter.mOut.get(18) );
        assertEquals( "\t\t<amc>0.0</amc>", mPrinter.mOut.get(19) );
        assertEquals( "\t\t<cc>"+System.getProperty("line.separator")+
                "\t\t\t<method name=\"method\">"+String.valueOf(cc)+"</method>"+System.getProperty("line.separator")
                +"\t\t</cc>",
                mPrinter.mOut.get(20) );

    }

    /**
     * Test of printFooter method, of class PrintXmlResults.
     */
    @Test
    public void testPrintFooter() {
        mXmlRes.printFooter();
        assertEquals("</ckjm>", mPrinter.mOut.get(0) );
    }

}

class MyPrintStream extends PrintStream{
    
    List<String> mOut;

    public MyPrintStream(){
        super(System.out);
        mOut = new ArrayList<String>();
    }

    @Override
    public void print(String str){
        mOut.add(str);
    }

    @Override
    public void println(String str){
        print(str);
    }
}