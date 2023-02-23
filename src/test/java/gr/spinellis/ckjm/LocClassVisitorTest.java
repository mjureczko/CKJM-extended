package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(1 + 3 + 6 + 80 + 25, mClassMetrics1.getLoc(), "LOC in KlasaTestowa"); //num_of_fields+num_of_methods+instructions_in_methods

        locCounter = new LocClassVisitor(mContainer);
        locCounter.visitJavaClass(mJavaClass2);
        assertEquals(1 + 4 + 4 + 25 + 3 + 14, mClassMetrics2.getLoc(), "LOC in KlasaTestowaChld");
    }

}