package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
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

        assertEquals(0, mClassMetrics1.getMoa(), () -> "MOA in KlasaTestowa");
        assertEquals(0, mClassMetrics2.getMoa(), () -> "MOA in KlasaTestowaChld");
        assertEquals(1, mClassMetrics3.getMoa(), () -> "MOA in KlasaTestowa2");
    }

}