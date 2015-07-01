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
public class AmcClassVisitorTest extends AbstractClassVisitorT {

    public AmcClassVisitorTest() {
    }

    @Test
    public void testVisitJavaClass()  {
        AmcClassVisitor amcCounter;

        amcCounter = new AmcClassVisitor( mContainer);
        mClassMetrics1.addLoc( 100 );
        mClassMetrics1.incWmc();
        mClassMetrics1.incWmc();
        mClassMetrics1.incWmc();
        amcCounter.visitJavaClass(mJavaClass1);
        assertEquals("AMC in KlasaTestowa", (100.0-4.0)/3.0, mClassMetrics1.getAmc(), 0.0001);
    }
}