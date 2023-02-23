package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author marian
 */
public class DamClassVisitorTest extends AbstractClassVisitorT {


    /**
     * Test of visitJavaClass method, of class DamClassVisitor.
     */
    @Test
    public void testVisitJavaClass() throws IOException {
        DamClassVisitor damCounter;

        damCounter = new DamClassVisitor(mJavaClass1, mContainer);
        damCounter.visitJavaClass(mJavaClass1);
        assertEquals(0.0, mClassMetrics1.getDam(), 0.0001, "DAM in KlasaTestowa");

        damCounter = new DamClassVisitor(mJavaClass2, mContainer);
        damCounter.visitJavaClass(mJavaClass2);
        assertEquals(1.0, mClassMetrics2.getDam(), 0.0001, "DAM in KlasaTestowaChld");
    }

}