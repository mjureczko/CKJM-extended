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
public class IcAndCbmClassVisitorTest extends AbstractClassVisitorT{

    public IcAndCbmClassVisitorTest() {
        mPathToClass3 = "./target/test-classes/ChildOfChld.class";
    }


    @Test
    public void testVisitJavaClass() throws IOException  {
        IcAndCbmClassVisitor icCounter;

        icCounter = new IcAndCbmClassVisitor( mContainer);
        icCounter.visitJavaClass(mJavaClass1);
        assertEquals( "Cbm in KlasaTestowa", 0, mClassMetrics1.getCbm() );
        assertEquals( "Ic in KlasaTestowa", 0, mClassMetrics1.getIc() );

        icCounter = new IcAndCbmClassVisitor(mContainer);
        icCounter.visitJavaClass(mJavaClass3);
        assertEquals( "Cbm in ChildOfChld", 4, mClassMetrics3.getCbm() );
        assertEquals( "IC in ChildOfChld", 1, mClassMetrics3.getIc() );

        icCounter = new IcAndCbmClassVisitor(mContainer);
        icCounter.visitJavaClass(mJavaClass2);
        assertEquals( "CBM in KlasaTestowaChld", 0, mClassMetrics2.getCbm() );
        assertEquals( "IC in KlasaTestowaChld", 0, mClassMetrics2.getIc() );

        JavaClass javaClass4 = new ClassParser("./target/test-classes/AnotherChildOfChild.class").parse();
        ClassMetrics classMetrics4 = mContainer.getMetrics(javaClass4.getClassName());
        icCounter.visitJavaClass(javaClass4);
        assertEquals( "CBM in AnotherChildOfChld", 3, classMetrics4.getCbm() );
        assertEquals( "IC in AnotherChildOfChld", 1, classMetrics4.getIc() );

    }

}