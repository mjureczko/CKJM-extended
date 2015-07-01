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

        assertEquals( "CAM in KlasaTestowa", 0.55556, mClassMetrics1.getCam(), 0.0001 );
        assertEquals( "CAM in ChildOfChld", 0.5, mClassMetrics2.getCam(), 0.0001 );
        assertEquals( "CAM in KlasaTestowa2", 0.625, mClassMetrics3.getCam(), 0.0001 );
    }

}