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
        
        assertEquals( "MFA in KlasaTestowa", 0, mClassMetrics1.getMfa(), 0.0001 );
    }

}
