/*
 * CkjmBeanTest.java
 * JUnit based test
 *
 * Created on 29 październik 2007, 12:48
 */
package gr.spinellis.ckjm;

import junit.framework.TestCase;

import java.io.File;
import java.util.List;

/**
 *
 * @author marian
 */
public class CkjmBeanTest extends TestCase {

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

    public CkjmBeanTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        mCkjm = new CkjmBean();
        mHandle = new MemoryOutputHandler();
    }

    /**
     * Test of countMetrics method, of class gr.spinellis.ckjm.ckjmBean.
     */
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

    public void testManyClasses() {


        mCkjm.countMetrics(mPath2TestClass1 + LIB_SEPARATOR + mPath2TestClass2 + LIB_SEPARATOR + mPath2TestClass3 +LIB_SEPARATOR + mPath2TestClass4, mHandle);

        ClassMetrics cm;
        List<String> methodNames;

        cm = mHandle.getMetrics(mTestClass1);
        assertNotNull("Results " + mTestClass1, cm);
        assertEquals("WMC", 3, cm.getWmc());
        assertEquals("DIT", 1, cm.getDit());
        assertEquals("NOC", 0, cm.getNoc());
        assertEquals("CBO", 2, cm.getCbo());
        assertEquals("RFC", 8, cm.getRfc());
        assertEquals("LCOM", 1, cm.getLcom());
        assertEquals("Ca", 2, cm.getCa());
        assertEquals("Ce", 1, cm.getCe());
        assertEquals("NPM", 1, cm.getNpm());
        assertEquals("LCOM3", 0.0, cm.getLcom3(), 0.001);

        assertEquals("LOC", 115, cm.getLoc());
        assertEquals("DAM", 0.0, cm.getDam(), 0.001);
        assertEquals("MOA", 0, cm.getMoa());
        assertEquals("MFA", 0.0, cm.getMfa(), 0.001);
        assertEquals("CAM", 0.5555, cm.getCam(), 0.001);
        assertEquals("IC", 0, cm.getIc());
        assertEquals("CBM", 0, cm.getCbm());
        assertEquals("AMC", 37.0, cm.getAmc(), 0.001);





        methodNames = cm.getMethodNames();
        assertEquals(3, methodNames.size());
        assertTrue(methodNames.contains("public void <init>()"));
        assertTrue(methodNames.contains("void m1()"));
        assertTrue(methodNames.contains("void m2(String name, java.util.List list, String c)"));
        assertEquals("CC1", 1, cm.getCC("public void <init>()"));
        assertEquals("CC2", 7, cm.getCC("void m1()"));
        assertEquals("CC3", 6, cm.getCC("void m2(String name, java.util.List list, String c)"));

        cm = mHandle.getMetrics(mTestClass2);
        assertNotNull("Results " + mTestClass2, cm);
        assertEquals("WMC", 5, cm.getWmc());
        assertEquals("DIT", 1, cm.getDit());
        assertEquals("NOC", 0, cm.getNoc());
        assertEquals("CBO", 1, cm.getCbo());
        assertEquals("RFC", 11, cm.getRfc());
        assertEquals("LCOM", 4, cm.getLcom());
        assertEquals("Ca", 1, cm.getCa());
        assertEquals("Ce", 1, cm.getCe());
        assertEquals("NPM", 0, cm.getNpm());
        assertEquals("LCOM3", 0.4167, cm.getLcom3(), 0.001);

        assertEquals("LOC", 53, cm.getLoc());
        assertEquals("DAM", 0.3333, cm.getDam(), 0.001);
        assertEquals("MOA", 1, cm.getMoa());
        assertEquals("MFA", 0.0, cm.getMfa(), 0.001);
        assertEquals("CAM", 0.625, cm.getCam(), 0.001);
        assertEquals("IC", 0, cm.getIc());
        assertEquals("CBM", 0, cm.getCbm());
        assertEquals("AMC", 9.0, cm.getAmc(), 0.001);
        methodNames = cm.getMethodNames();
        assertEquals(5, methodNames.size());
        assertTrue(methodNames.contains("void <init>()"));
        assertTrue(methodNames.contains("void m1()"));
        assertTrue(methodNames.contains("void m2()"));
        assertTrue(methodNames.contains("int m3(int jk)"));
        assertTrue(methodNames.contains("static void <clinit>()"));
        assertEquals("CC1", 1, cm.getCC("void <init>()"));
        assertEquals("CC2", 2, cm.getCC("void m1()"));
        assertEquals("CC3", 1, cm.getCC("void m2()"));
        assertEquals("CC2", 1, cm.getCC("int m3(int jk)"));
        assertEquals("CC3", 1, cm.getCC("static void <clinit>()"));




        cm = mHandle.getMetrics(mTestClass4);
        assertNotNull("Results " + mTestClass4, cm);
        assertEquals("WMC", 2, cm.getWmc());
        assertEquals("DIT", 1, cm.getDit());
        assertEquals("NOC", 1, cm.getNoc());
        assertEquals("CBO", 2, cm.getCbo());
        assertEquals("RFC", 5, cm.getRfc());
        assertEquals("LCOM", 0, cm.getLcom());
        assertEquals("Ca", 1, cm.getCa());
        assertEquals("Ce", 1, cm.getCe());
        assertEquals("NPM", 2, cm.getNpm());
        assertEquals("LCOM3", 0.0, cm.getLcom3(), 0.001);

        assertEquals("LOC", 21, cm.getLoc());
        assertEquals("DAM", 1.0, cm.getDam(), 0.001);
        assertEquals("MOA", 0, cm.getMoa());
        assertEquals("MFA", 0.0, cm.getMfa(), 0.001);
        assertEquals("CAM", 0.75, cm.getCam(), 0.001);
        assertEquals("IC", 0, cm.getIc());
        assertEquals("CBM", 0, cm.getCbm());
        assertEquals("AMC", 9.0, cm.getAmc(), 0.001);
        methodNames = cm.getMethodNames();
        assertEquals(2, methodNames.size());
        assertTrue(methodNames.contains("public int sru()"));
        assertTrue(methodNames.contains("public void <init>(int id)"));
        assertEquals("CC1", 1, cm.getCC("public int sru()"));
        assertEquals("CC2", 1, cm.getCC("public void <init>(int id)"));








        cm = mHandle.getMetrics(mTestClass3);
        assertNotNull("Results " + mTestClass3, cm);
        assertEquals("WMC", 5, cm.getWmc());
        assertEquals("DIT", 2, cm.getDit());
        assertEquals("NOC", 0, cm.getNoc());
        assertEquals("CBO", 1, cm.getCbo());
        assertEquals("RFC", 10, cm.getRfc());
        assertEquals("LCOM", 10, cm.getLcom());
        assertEquals("Ca", 0, cm.getCa());
        assertEquals("Ce", 1, cm.getCe());
        assertEquals("NPM", 4, cm.getNpm());
        assertEquals("LCOM3", 0.5, cm.getLcom3(), 0.001);

        assertEquals("LOC", 51, cm.getLoc());
        assertEquals("DAM", 1.0, cm.getDam(), 0.001);
        assertEquals("MOA", 0, cm.getMoa());
        assertEquals("MFA", 0.2, cm.getMfa(), 0.001);
        assertEquals("CAM", 0.8, cm.getCam(), 0.001);
        assertEquals("IC", 0, cm.getIc());
        assertEquals("CBM", 0, cm.getCbm());
        assertEquals("AMC", 9.0, cm.getAmc(), 0.001);

        methodNames = cm.getMethodNames();
        assertEquals(5, methodNames.size());
        assertTrue(methodNames.contains("public void <init>(int id)"));
        assertTrue(methodNames.contains("public Number getFloatingPoint(int i)"));
        assertTrue(methodNames.contains("public Object getValue()"));
        assertTrue(methodNames.contains("public Class getType(int i)"));
        assertEquals("CC1", 1, cm.getCC("public void <init>(int id)"));
        assertEquals("CC2", 2, cm.getCC("public Number getFloatingPoint(int i)"));
        assertEquals("CC3", 1, cm.getCC("public Object getValue()"));
        assertEquals("CC3", 1, cm.getCC("public Class getType(int i)"));
    }

    /**
     * Test of metricsNames method, of class gr.spinellis.ckjm.ckjmBean.
     */
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
