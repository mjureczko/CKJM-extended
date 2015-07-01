/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class DamClassVisitorTest extends AbstractClassVisitorT {

    public DamClassVisitorTest() {
    }


    /**
     * Test of visitJavaClass method, of class DamClassVisitor.
     */
    @Test
    public void testVisitJavaClass() throws IOException {
        DamClassVisitor damCounter;

        damCounter = new DamClassVisitor(mJavaClass1, mContainer);
        damCounter.visitJavaClass(mJavaClass1);
        assertEquals( "DAM in KlasaTestowa", 0.0, mClassMetrics1.getDam(), 0.0001 );

        damCounter = new DamClassVisitor(mJavaClass2, mContainer);
        damCounter.visitJavaClass(mJavaClass2);
        assertEquals( "DAM in KlasaTestowaChld", 1.0, mClassMetrics2.getDam(), 0.0001 );
    }

}