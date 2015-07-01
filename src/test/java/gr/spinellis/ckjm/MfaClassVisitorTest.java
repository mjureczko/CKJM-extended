/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.io.IOException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class MfaClassVisitorTest extends AbstractClassVisitorT {

    public MfaClassVisitorTest() {
    }

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

        assertEquals( "MFA in KlasaTestowa", 0, mClassMetrics1.getMfa(), 0.0001 );
        assertEquals( "MFA in KlasaTestowaChld", 0.2, mClassMetrics2.getMfa(), 0.0001 );
        assertEquals( "MFA in KlasaTestowa2", 0, mClassMetrics3.getMfa(), 0.0001 );
        assertEquals( "MFA in ChildOfChld", 0.5556, cm4.getMfa(), 0.0001 );
    }

}