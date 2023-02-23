package gr.spinellis.ckjm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author marian
 */
public class ClassMetricsTest {

    ClassMetrics cm;
    private static final String mFirst = "first";
    private static final String mSecond = "second";
    private static final String mThird = "third";

    @BeforeEach
    public void setUp() {
        cm = new ClassMetrics();
    }

    /**
     * Test of getMethodNames method, of class ClassMetrics.
     */
    @Test
    public void testAddMethod() {

        int firstMethodCc = 17;
        int secondMethodCc = 0;

        assertEquals(cm.getMethodNames().size(), 0, "There are no methods.");
        assertEquals(cm.getCC(mFirst), 0, "There are no methods.");

        cm.addMethod(mFirst, firstMethodCc);
        cm.addMethod(mSecond, secondMethodCc);
        assertTrue(cm.getMethodNames().contains(mFirst), "First method.");
        assertEquals(firstMethodCc, cm.getCC(mFirst), "First method.");
        assertTrue(cm.getMethodNames().contains(mSecond), "Second method.");
        assertEquals(secondMethodCc, cm.getCC(mSecond), "Second method.");
    }

    @Test
    public void testWMC() {
        assertEquals(0, cm.getWmc(), "WMC 0");
        cm.incWmc();
        assertEquals(1, cm.getWmc(), "WMC 1");
        cm.incWmc();
        assertEquals(2, cm.getWmc(), "WMC 2");
    }

    @Test
    public void testDIT() {
        assertEquals(0, cm.getDit(), "DIT 0");
        cm.setDit(7);
        assertEquals(7, cm.getDit(), "DIT 7");
    }

    @Test
    public void testNOC() {
        assertEquals(0, cm.getNoc(), "NOC 0");
        cm.incNoc();
        assertEquals(1, cm.getNoc(), "NOC 1");
        cm.incNoc();
        assertEquals(2, cm.getNoc(), "NOC 2");
    }

    @Test
    public void testCe() {
        HashSet<String> classNames = new HashSet<String>();
        classNames.add(mFirst);
        classNames.add(mSecond);

        assertEquals(0, cm.getCe(), "CE 0");
        cm.setCe(classNames);
        assertEquals(2, cm.getCe(), "CE 2");
    }

    @Test
    public void testRFC() {
        assertEquals(0, cm.getRfc(), "RFC 0");
        cm.setRfc(7);
        assertEquals(7, cm.getRfc(), "RFC 7");
    }

    @Test
    public void testLCOM() {
        assertEquals(0, cm.getLcom(), "LCOM 0");
        cm.setLcom(7);
        assertEquals(7, cm.getLcom(), "LCOM 7");
    }

    @Test
    public void testCA() {


        assertEquals(0, cm.getCa(), "CA 0");
        cm.addAfferentCoupling(mFirst);
        assertEquals(1, cm.getCa(), "CA 1");
        cm.addAfferentCoupling(mFirst);
        assertEquals(1, cm.getCa(), "CA 1");
        cm.addAfferentCoupling(mSecond);
        assertEquals(2, cm.getCa(), "CA 2");
    }

    @Test
    public void testNPM() {
        assertEquals(0, cm.getNpm(), "NPM 0");
        cm.incNpm();
        assertEquals(1, cm.getNpm(), "NPM 1");
        cm.incNpm();
        assertEquals(2, cm.getNpm(), "NPM 2");
    }

    @Test
    public void testLCOM3() {
        assertEquals(0, cm.getLcom(), "LCOM3 0");
        cm.setLcom3(1.07);
        assertEquals(1.07, cm.getLcom3(), 0.001, "LCOM3 1.07");
        try {
            cm.setLcom3(2.01);
            //fail("LCOM3 cann't be greater as 2!");
            assertEquals(2, cm.getLcom3(), 0.001, "LCOM3 2.01");
        } catch (RuntimeException re) {
            assertTrue(true);
        }
        try {
            cm.setLcom3(-0.01);
            //fail("LCOM3 cann't be lower as 0!");
            assertEquals(0, cm.getLcom3(), 0.001, "LCOM3 -0.01");
        } catch (RuntimeException re) {
            assertTrue(true);
        }
    }

    @Test
    public void testIsJdkClass() {
        assertTrue(ClassMetrics.isJdkClass("java.Class"), "java.");
        assertTrue(ClassMetrics.isJdkClass("javax.Class"), "javax.");
        assertTrue(ClassMetrics.isJdkClass("org.omg.Class"), "org.omg.");
        assertTrue(ClassMetrics.isJdkClass("org.w3c.dom.Class"), "org.w3c.dom.");
        assertTrue(ClassMetrics.isJdkClass("org.xml.sax."), "org.xml.sax.");
        assertFalse(ClassMetrics.isJdkClass("gr.spinellis.ckjm.ClassMetrics"), "gr.spinellis.ckjm.ClassMetrics");
    }

    @Test
    public void testCBO() {
        assertEquals(0, cm.getCbo(), "CBO 0");
        cm.addAfferentCoupling(mFirst);
        cm.addAfferentCoupling(mSecond);
        HashSet<String> efferentCoupling = new HashSet<String>();
        efferentCoupling.add(mSecond);
        efferentCoupling.add(mThird);
        cm.setCe(efferentCoupling);
        assertEquals(3, cm.getCbo(), "CBO 3");
    }

    @Test
    public void testLOC() {
        int a = 17;
        assertEquals(0, cm.getLoc(), "LOC 0");
        cm.addLoc(a);
        assertEquals(a, cm.getLoc(), "LOC 17");
    }

    @Test
    public void testDAM() {
        double a = 0.17;
        assertEquals(0.0, cm.getDam(), 0.0001, "DAM 0");
        cm.setDam(a);
        assertEquals(a, cm.getDam(), 0.0001, "DAM 0.17");
    }
}