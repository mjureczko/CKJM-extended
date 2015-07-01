/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class LocClassVisitorTest extends AbstractClassVisitorT {
    

    public LocClassVisitorTest() {
    }

    @Test
    public void testVisitJavaClass(){
        LocClassVisitor locCounter;

        locCounter = new LocClassVisitor(mContainer);
        locCounter.visitJavaClass(mJavaClass1);
        assertEquals("LOC in KlasaTestowa", 1 + 3 + 6 + 80 + 25, mClassMetrics1.getLoc()); //num_of_fields+num_of_methods+instructions_in_methods

        locCounter = new LocClassVisitor(mContainer);
        locCounter.visitJavaClass(mJavaClass2);
        assertEquals("LOC in KlasaTestowaChld", 1 + 4 + 4 + 25 + 3 + 14, mClassMetrics2.getLoc());
    }

}