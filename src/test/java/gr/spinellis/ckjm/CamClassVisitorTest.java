package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mjureczk
 */
public class CamClassVisitorTest extends AbstractClassVisitorT {


    public CamClassVisitorTest() {
        mPathToClass2 = "./target/test-classes/ChildOfChld.class";
    }

    /**
     * Test of visitJavaClass method, of class CamClassVisitor.
     */
    @Test
    public void testVisitJavaClass() {
        CamClassVisitor camCounter;

        camCounter = new CamClassVisitor(mContainer);
        camCounter.visitJavaClass(mJavaClass1);
        camCounter.visitJavaClass(mJavaClass2);
        camCounter.visitJavaClass(mJavaClass3);

        assertEquals(0.55556, mClassMetrics1.getCam(), 0.0001, "CAM in KlasaTestowa");
        assertEquals(0.5, mClassMetrics2.getCam(), 0.0001, "CAM in ChildOfChld");
        assertEquals(0.625, mClassMetrics3.getCam(), 0.0001, "CAM in KlasaTestowa2");
    }

}