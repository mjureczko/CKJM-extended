package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author mjureczk
 */
public class MfaClassVisitorBugTest  extends AbstractClassVisitorT {

    public MfaClassVisitorBugTest() {
        mPathToClass1 = "./target/test-classes/specialcasses/MfaBugClass.class";
    }

    /**
     * Test of visitJavaClass method, of class MfaClassVisitor.
     */
    @Test
    public void testVisitJavaClass() throws IOException {
        MfaClassVisitor mfaCounter = new MfaClassVisitor(mContainer);

        mfaCounter.visitJavaClass(mJavaClass1);
        
        assertEquals( 0, mClassMetrics1.getMfa(), 0.0001, "MFA in KlasaTestowa" );
    }

}
