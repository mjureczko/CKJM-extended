/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
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
    public void anonymouseInternTest(){
        mCkjm.countMetrics(mPath2TestClass, mHandle);
        ClassMetrics cm = mHandle.getMetrics(mTestClass);
        assertEquals("AMC",5.034,cm.getAmc(),0.001);
        assertEquals("CA",0,cm.getCa(),0);
        assertEquals("CAM",0.2807,cm.getCam(),0.001);
        assertEquals("CBM",0,cm.getCbm());
        assertEquals("CBO",1,cm.getCbo());
        assertEquals("CE",1,cm.getCe());
        assertEquals("DAM",0,cm.getDam(),0.001);
        assertEquals("DIT",1,cm.getDit());
        assertEquals("IC",0,cm.getIc());
        assertEquals("LCOM",406,cm.getLcom());
        assertEquals("LCOM3",1.0,cm.getLcom3(),0.001);
        assertEquals("LOC",176,cm.getLoc());
        assertEquals("MFA",0,cm.getMfa(),0.001);
        assertEquals("MOA",0,cm.getMoa());
        assertEquals("NOC",0,cm.getNoc());
        assertEquals("NPM",28,cm.getNpm());
        assertEquals("RFC",29,cm.getRfc());
        assertEquals("WMC",29,cm.getWmc()); //Surprised why not 23? There are 29 methods in the binary code.
    }

    @Test
    public void abstractTest(){
        mCkjm.countMetrics(mPath2TestClass2, mHandle);
        ClassMetrics cm = mHandle.getMetrics(mTestClass2);
        assertEquals("AMC",2.33333,cm.getAmc(),0.001);
        assertEquals("CA",0,cm.getCa(),0);
        assertEquals("CAM",1,cm.getCam(),0.001);
        assertEquals("CBM",0,cm.getCbm());
        assertEquals("CBO",0,cm.getCbo());
        assertEquals("CE",0,cm.getCe());
        assertEquals("DAM",0,cm.getDam(),0.001);
        assertEquals("DIT",1,cm.getDit());
        assertEquals("IC",0,cm.getIc());
        assertEquals("LCOM",3,cm.getLcom());
        assertEquals("LCOM3",2.0,cm.getLcom3(),0.001);
        assertEquals("LOC",10,cm.getLoc());
        assertEquals("MFA",0,cm.getMfa(),0.001);
        assertEquals("MOA",0,cm.getMoa());
        assertEquals("NOC",0,cm.getNoc());
        assertEquals("NPM",1,cm.getNpm());
        assertEquals("RFC",5,cm.getRfc());
        assertEquals("WMC",3,cm.getWmc());
    }
}
