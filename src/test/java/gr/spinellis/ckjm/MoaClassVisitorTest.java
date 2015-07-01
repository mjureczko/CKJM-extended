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
public class MoaClassVisitorTest extends AbstractClassVisitorT {

    /**
     * Test of visitJavaClass method, of class MoaClassVisitor.
     */
    @Test
    public void testVisitJavaClass() throws IOException {
        MoaClassVisitor moaCounter;

        moaCounter = new MoaClassVisitor(mContainer);
        moaCounter.visitJavaClass(mJavaClass1);
        moaCounter.visitJavaClass(mJavaClass2);
        moaCounter.visitJavaClass(mJavaClass3);
        moaCounter.end();

        assertEquals( "MOA in KlasaTestowa", 0, mClassMetrics1.getMoa() );
        assertEquals( "MOA in KlasaTestowaChld", 0, mClassMetrics2.getMoa() );
        assertEquals( "MOA in KlasaTestowa2", 1, mClassMetrics3.getMoa() );
    }

}