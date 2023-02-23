package gr.spinellis.ckjm;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author marian
 */
public class CkjmBeanTest {

    private static final char LIB_SEPARATOR = File.pathSeparatorChar;
    private ClassMetrics mMetrics;
    private String mName;
    private CkjmBean mCkjm = null;
    final private String mPath2TestClass1 = "target/test-classes/KlasaTestowa.class";
    final private String mTestClass1 = "KlasaTestowa";
    final private String mPath2TestClass2 = "target/test-classes/KlasaTestowa2.class";
    final private String mTestClass2 = "KlasaTestowa2";
    final private String mPath2TestClass3 = "target/test-classes/KlasaTestowaChld.class";
    final private String mTestClass3 = "KlasaTestowaChld";
    final private String mPath2TestClass4 = "target/test-classes/KlasaTestowaParent.class";
    final private String mTestClass4 = "KlasaTestowaParent";
    MemoryOutputHandler mHandle;

    @BeforeEach
    protected void setUp() throws Exception {
        mCkjm = new CkjmBean();
        mHandle = new MemoryOutputHandler();
    }

    /**
     * Test of countMetrics method, of class gr.spinellis.ckjm.ckjmBean.
     */
    @Test
    public void testCountMetrics() {

        try {
            mCkjm.countMetrics(null, mHandle);
            fail("countMetrics() works with null!!!");
        } catch (RuntimeException re) {
            assertTrue(true);
        }

        try {
            mCkjm.countMetrics(mPath2TestClass1, null);
            fail("countMetrics() works with null!!!");
        } catch (RuntimeException re) {
            assertTrue(true);
        }

    }

    @Test
    public void testManyClasses() {
        mCkjm.countMetrics(mPath2TestClass1 + LIB_SEPARATOR + mPath2TestClass2 + LIB_SEPARATOR + mPath2TestClass3 + LIB_SEPARATOR + mPath2TestClass4, mHandle);

        ClassMetrics cm;
        List<String> methodNames;

        cm = mHandle.getMetrics(mTestClass1);
        assertNotNull(cm, "Results " + mTestClass1);
        assertEquals(3, cm.getWmc(), "WMC");
        assertEquals(1, cm.getDit(), "DIT");
        assertEquals(0, cm.getNoc(), "NOC");
        assertEquals(2, cm.getCbo(), "CBO");
        assertEquals(8, cm.getRfc(), "RFC");
        assertEquals(1, cm.getLcom(), "LCOM");
        assertEquals(2, cm.getCa(), "Ca");
        assertEquals(1, cm.getCe(), "Ce");
        assertEquals(1, cm.getNpm(), "NPM");
        assertEquals(0.0, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(115, cm.getLoc(), "LOC");
        assertEquals(0.0, cm.getDam(), 0.001, "DAM");
        assertEquals(0, cm.getMoa(), "MOA");
        assertEquals(0.0, cm.getMfa(), 0.001, "MFA");
        assertEquals(0.5555, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(37.0, cm.getAmc(), 0.001, "AMC");


        methodNames = cm.getMethodNames();
        assertEquals(3, methodNames.size());
        assertTrue(methodNames.contains("public void <init>()"));
        assertTrue(methodNames.contains("void m1()"));
        assertTrue(methodNames.contains("void m2(String name, java.util.List list, String c)"));
        assertEquals(1, cm.getCC("public void <init>()"), "CC1");
        assertEquals(7, cm.getCC("void m1()"), "CC2");
        assertEquals(6, cm.getCC("void m2(String name, java.util.List list, String c)"), "CC3");

        cm = mHandle.getMetrics(mTestClass2);
        assertNotNull(cm, "Results " + mTestClass2);
        assertEquals(5, cm.getWmc(), "WMC");
        assertEquals(1, cm.getDit(), "DIT");
        assertEquals(0, cm.getNoc(), "NOC");
        assertEquals(1, cm.getCbo(), "CBO");
        assertEquals(11, cm.getRfc(), "RFC");
        assertEquals(4, cm.getLcom(), "LCOM");
        assertEquals(1, cm.getCa(), "Ca");
        assertEquals(1, cm.getCe(), "Ce");
        assertEquals(0, cm.getNpm(), "NPM");
        assertEquals(0.4167, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(53, cm.getLoc(), "LOC");
        assertEquals(0.3333, cm.getDam(), 0.001, "DAM");
        assertEquals(1, cm.getMoa(), "MOA");
        assertEquals(0.0, cm.getMfa(), 0.001, "MFA");
        assertEquals(0.625, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(9.0, cm.getAmc(), 0.001, "AMC");
        methodNames = cm.getMethodNames();
        assertEquals(5, methodNames.size());
        assertTrue(methodNames.contains("void <init>()"));
        assertTrue(methodNames.contains("void m1()"));
        assertTrue(methodNames.contains("void m2()"));
        assertTrue(methodNames.contains("int m3(int jk)"));
        assertTrue(methodNames.contains("static void <clinit>()"));
        assertEquals(1, cm.getCC("void <init>()"), "CC1");
        assertEquals(2, cm.getCC("void m1()"), "CC2");
        assertEquals(1, cm.getCC("void m2()"), "CC3");
        assertEquals(1, cm.getCC("int m3(int jk)"), "CC2");
        assertEquals(1, cm.getCC("static void <clinit>()"), "CC3");


        cm = mHandle.getMetrics(mTestClass4);
        assertNotNull(cm, "Results " + mTestClass4);
        assertEquals(2, cm.getWmc(), "WMC");
        assertEquals(1, cm.getDit(), "DIT");
        assertEquals(1, cm.getNoc(), "NOC");
        assertEquals(2, cm.getCbo(), "CBO");
        assertEquals(5, cm.getRfc(), "RFC");
        assertEquals(0, cm.getLcom(), "LCOM");
        assertEquals(1, cm.getCa(), "Ca");
        assertEquals(1, cm.getCe(), "Ce");
        assertEquals(2, cm.getNpm(), "NPM");
        assertEquals(0.0, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(21, cm.getLoc(), "LOC");
        assertEquals(1.0, cm.getDam(), 0.001, "DAM");
        assertEquals(0, cm.getMoa(), "MOA");
        assertEquals(0.0, cm.getMfa(), 0.001, "MFA");
        assertEquals(0.75, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(9.0, cm.getAmc(), 0.001, "AMC");
        methodNames = cm.getMethodNames();
        assertEquals(2, methodNames.size());
        assertTrue(methodNames.contains("public int sru()"));
        assertTrue(methodNames.contains("public void <init>(int id)"));
        assertEquals(1, cm.getCC("public int sru()"), "CC1");
        assertEquals(1, cm.getCC("public void <init>(int id)"), "CC2");


        cm = mHandle.getMetrics(mTestClass3);
        assertNotNull(cm, "Results " + mTestClass3);
        assertEquals(5, cm.getWmc(), "WMC");
        assertEquals(2, cm.getDit(), "DIT");
        assertEquals(0, cm.getNoc(), "NOC");
        assertEquals(1, cm.getCbo(), "CBO");
        assertEquals(10, cm.getRfc(), "RFC");
        assertEquals(10, cm.getLcom(), "LCOM");
        assertEquals(0, cm.getCa(), "Ca");
        assertEquals(1, cm.getCe(), "Ce");
        assertEquals(4, cm.getNpm(), "NPM");
        assertEquals(0.5, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(51, cm.getLoc(), "LOC");
        assertEquals(1.0, cm.getDam(), 0.001, "DAM");
        assertEquals(0, cm.getMoa(), "MOA");
        assertEquals(0.2, cm.getMfa(), 0.001, "MFA");
        assertEquals(0.8, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(9.0, cm.getAmc(), 0.001, "AMC");

        methodNames = cm.getMethodNames();
        assertEquals(5, methodNames.size());
        assertTrue(methodNames.contains("public void <init>(int id)"));
        assertTrue(methodNames.contains("public Number getFloatingPoint(int i)"));
        assertTrue(methodNames.contains("public Object getValue()"));
        assertTrue(methodNames.contains("public Class getType(int i)"));
        assertEquals(1, cm.getCC("public void <init>(int id)"), "CC1");
        assertEquals(2, cm.getCC("public Number getFloatingPoint(int i)"), "CC2");
        assertEquals(1, cm.getCC("public Object getValue()"), "CC3");
        assertEquals(1, cm.getCC("public Class getType(int i)"), "CC3");
    }

    /**
     * Test of metricsNames method, of class gr.spinellis.ckjm.ckjmBean.
     */
    @Test
    public void testMetricsNames() {
        String tab[] = mCkjm.metricsNames();
        assertTrue(tab != null);
        assertEquals(tab.length, 18);
        assertEquals("WMC", tab[0]);
        assertEquals("DIT", tab[1]);
        assertEquals("NOC", tab[2]);
        assertEquals("CBO ", tab[3]);
        assertEquals("RFC ", tab[4]);
        assertEquals("LCOM", tab[5]);
        assertEquals("Ca", tab[6]);
        assertEquals("Ce", tab[7]);
        assertEquals("NPM", tab[8]);
        assertEquals("LCOM3 ", tab[9]);
        assertEquals("LOC", tab[10]);
        assertEquals("DAM", tab[11]);
        assertEquals("MO", tab[12]);
        assertEquals("MFA ", tab[13]);
        assertEquals("CAM", tab[14]);
        assertEquals("IC", tab[15]);
        assertEquals("CBM", tab[16]);
        assertEquals("AMC ", tab[17]);
    }
}
