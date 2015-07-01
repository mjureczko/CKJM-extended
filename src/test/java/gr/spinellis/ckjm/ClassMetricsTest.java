/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class ClassMetricsTest {

    ClassMetrics cm;
    private static final String mFirst="first";
    private static final String mSecond="second";
    private static final String mThird="third";

    public ClassMetricsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        cm = new ClassMetrics();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMethodNames method, of class ClassMetrics.
     */
    @Test
    public void testAddMethod() {

        int firstMethodCc = 17;
        int secondMethodCc = 0;

        assertEquals( "There are no methods.", 0, cm.getMethodNames().size() );
        assertEquals( "There are no methods.", 0, cm.getCC(mFirst) );
        
        cm.addMethod( mFirst, firstMethodCc );
        cm.addMethod( mSecond, secondMethodCc );
        assertTrue( "First method.", cm.getMethodNames().contains(mFirst) );
        assertEquals( "First method.", firstMethodCc, cm.getCC(mFirst) );
        assertTrue( "Second method.", cm.getMethodNames().contains(mSecond) );
        assertEquals( "Second method.", secondMethodCc, cm.getCC(mSecond) );
    }

    @Test
    public void testWMC(){
        assertEquals( "WMC 0", 0, cm.getWmc() );
        cm.incWmc();
        assertEquals( "WMC 1", 1, cm.getWmc() );
        cm.incWmc();
        assertEquals( "WMC 2", 2, cm.getWmc() );
    }

    @Test
    public void testDIT(){
        assertEquals( "DIT 0", 0, cm.getDit() );
        cm.setDit(7);
        assertEquals( "DIT 7", 7, cm.getDit() );
    }

    @Test
    public void testNOC(){
        assertEquals( "NOC 0", 0, cm.getNoc() );
        cm.incNoc();
        assertEquals( "NOC 1", 1, cm.getNoc() );
        cm.incNoc();
        assertEquals( "NOC 2", 2, cm.getNoc() );
    }

    @Test
    public void testCe(){
        HashSet<String> classNames = new HashSet<String>();
        classNames.add(mFirst);
        classNames.add(mSecond);

        assertEquals( "CE 0", 0, cm.getCe() );
        cm.setCe( classNames );
        assertEquals( "CE 2", 2, cm.getCe() );
    }

    @Test
    public void testRFC(){
        assertEquals( "RFC 0", 0, cm.getRfc() );
        cm.setRfc(7);
        assertEquals( "RFC 7", 7, cm.getRfc() );
    }

    @Test
    public void testLCOM(){
        assertEquals( "LCOM 0", 0, cm.getLcom() );
        cm.setLcom( 7 );
        assertEquals( "LCOM 7", 7, cm.getLcom() );
    }

    @Test
    public void testCA(){
        

        assertEquals( "CA 0", 0, cm.getCa() );
        cm.addAfferentCoupling(mFirst);
        assertEquals( "CA 1", 1, cm.getCa() );
        cm.addAfferentCoupling(mFirst);
        assertEquals( "CA 1", 1, cm.getCa() );
        cm.addAfferentCoupling(mSecond);
        assertEquals( "CA 2", 2, cm.getCa() );
    }

    @Test
    public void testNPM(){
        assertEquals( "NPM 0", 0, cm.getNpm() );
        cm.incNpm();
        assertEquals( "NPM 1", 1, cm.getNpm() );
        cm.incNpm();
        assertEquals( "NPM 2", 2, cm.getNpm() );
    }

    @Test
    public void testLCOM3(){
        assertEquals( "LCOM3 0", 0, cm.getLcom() );
        cm.setLcom3( 1.07 );
        assertEquals( "LCOM3 1.07", 1.07, cm.getLcom3(), 0.001 );
        try{
            cm.setLcom3(2.01);
            //fail("LCOM3 cann't be greater as 2!");
            assertEquals( "LCOM3 2.01", 2, cm.getLcom3(), 0.001 );
        }catch( RuntimeException re ){
            assertTrue(true);
        }
        try{
            cm.setLcom3(-0.01);
            //fail("LCOM3 cann't be lower as 0!");
            assertEquals( "LCOM3 -0.01", 0, cm.getLcom3(), 0.001 );
        }catch( RuntimeException re ){
            assertTrue(true);
        }
    }

    @Test
    public void testIsJdkClass() {
        assertTrue( "java.", ClassMetrics.isJdkClass("java.Class") );
        assertTrue( "javax.", ClassMetrics.isJdkClass("javax.Class") );
        assertTrue( "org.omg.", ClassMetrics.isJdkClass("org.omg.Class") );
        assertTrue( "org.w3c.dom.", ClassMetrics.isJdkClass("org.w3c.dom.Class") );
        assertTrue( "org.xml.sax.", ClassMetrics.isJdkClass("org.xml.sax.") );
        assertFalse( "gr.spinellis.ckjm.ClassMetrics", ClassMetrics.isJdkClass("gr.spinellis.ckjm.ClassMetrics") );
    }

    @Test
    public void testCBO() {
        assertEquals( "CBO 0", 0, cm.getCbo() );
        cm.addAfferentCoupling(mFirst);
        cm.addAfferentCoupling(mSecond);
        HashSet<String> efferentCoupling = new HashSet<String>();
        efferentCoupling.add(mSecond);
        efferentCoupling.add(mThird);
        cm.setCe(efferentCoupling);
        assertEquals( "CBO 3", 3, cm.getCbo() );
    }

    @Test
    public void testLOC() {
        int a=17;
        assertEquals( "LOC 0", 0, cm.getLoc() );
        cm.addLoc( a );
        assertEquals( "LOC 17", a, cm.getLoc() );
    }

    @Test
    public void testDAM() {
        double a=0.17;
        assertEquals( "DAM 0", 0.0, cm.getDam(), 0.0001 );
        cm.setDam(a);
        assertEquals( "DAM 0.17", a, cm.getDam(), 0.0001 );
    }
}