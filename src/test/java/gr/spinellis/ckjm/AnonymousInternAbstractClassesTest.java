package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mjureczk
 */
public class AnonymousInternAbstractClassesTest extends AbstractClassVisitorT {

    private CkjmBean mCkjm;
    private MemoryOutputHandler mHandle;
    final private String mPath2TestClass = "./target/test-classes/AnotherChildOfChild$1.class";
    final private String mTestClass = "AnotherChildOfChild$1";
    final private String mPath2TestClass2 = "./target/test-classes/specialcasses/DummyClass.class";
    final private String mTestClass2 = "specialcasses.DummyClass";

    public AnonymousInternAbstractClassesTest() {
        mCkjm = new CkjmBean();
        mHandle = new MemoryOutputHandler();
    }

    @Test
    public void anonymouseInternTest() {
        mCkjm.countMetrics(mPath2TestClass, mHandle);
        ClassMetrics cm = mHandle.getMetrics(mTestClass);
        assertEquals(5.034, cm.getAmc(), 0.001, "AMC");
        assertEquals(0, cm.getCa(), 0, "CA");
        assertEquals(0.2807, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(1, cm.getCbo(), "CBO");
        assertEquals(1, cm.getCe(), "CE");
        assertEquals(0, cm.getDam(), 0.001, "DAM");
        assertEquals(1, cm.getDit(), "DIT");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(406, cm.getLcom(), "LCOM");
        assertEquals(1.0, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(176, cm.getLoc(), "LOC");
        assertEquals(0, cm.getMfa(), 0.001, "MFA");
        assertEquals(0, cm.getMoa(), "MOA");
        assertEquals(0, cm.getNoc(), "NOC");
        assertEquals(28, cm.getNpm(), "NPM");
        assertEquals(29, cm.getRfc(), "RFC");
        assertEquals(29, cm.getWmc(), "WMC - surprised why not 23? There are 29 methods in the binary code.");
    }

    @Test
    public void abstractTest() {
        mCkjm.countMetrics(mPath2TestClass2, mHandle);
        ClassMetrics cm = mHandle.getMetrics(mTestClass2);
        assertEquals(2.33333, cm.getAmc(), 0.001, "AMC");
        assertEquals(0, cm.getCa(), 0, "CA");
        assertEquals(1, cm.getCam(), 0.001, "CAM");
        assertEquals(0, cm.getCbm(), "CBM");
        assertEquals(0, cm.getCbo(), "CBO");
        assertEquals(0, cm.getCe(), "CE");
        assertEquals(0, cm.getDam(), 0.001, "DAM");
        assertEquals(1, cm.getDit(), "DIT");
        assertEquals(0, cm.getIc(), "IC");
        assertEquals(3, cm.getLcom(), "LCOM");
        assertEquals(2.0, cm.getLcom3(), 0.001, "LCOM3");
        assertEquals(10, cm.getLoc(), "LOC");
        assertEquals(0, cm.getMfa(), 0.001, "MFA");
        assertEquals(0, cm.getMoa(), "MOA");
        assertEquals(0, cm.getNoc(), "NOC");
        assertEquals(1, cm.getNpm(), "NPM");
        assertEquals(5, cm.getRfc(), "RFC");
        assertEquals(3, cm.getWmc(), "WMC");
    }
}
