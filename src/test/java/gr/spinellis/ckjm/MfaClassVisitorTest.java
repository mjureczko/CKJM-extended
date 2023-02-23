package gr.spinellis.ckjm;

import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author marian
 */
public class MfaClassVisitorTest extends AbstractClassVisitorT {

    /**
     * Test of visitJavaClass method, of class MfaClassVisitor.
     */
    @Test
    public void testVisitJavaClass() throws IOException {
        MfaClassVisitor mfaCounter = new MfaClassVisitor(mContainer);

        JavaClass jc4 = new ClassParser("./target/test-classes/ChildOfChld.class").parse();
        ClassMetrics cm4 = mContainer.getMetrics(jc4.getClassName());

        mfaCounter.visitJavaClass(mJavaClass1);
        mfaCounter.visitJavaClass(mJavaClass2);
        mfaCounter.visitJavaClass(mJavaClass3);
        mfaCounter.visitJavaClass(jc4);

        assertEquals(0, mClassMetrics1.getMfa(), 0.0001, "MFA in KlasaTestowa");
        assertEquals(0.2, mClassMetrics2.getMfa(), 0.0001, "MFA in KlasaTestowaChld");
        assertEquals(0, mClassMetrics3.getMfa(), 0.0001, "MFA in KlasaTestowa2");
        assertEquals(0.5556, cm4.getMfa(), 0.0001, "MFA in ChildOfChld");
    }

}