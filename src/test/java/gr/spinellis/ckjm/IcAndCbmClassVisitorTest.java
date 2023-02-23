package gr.spinellis.ckjm;

import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author marian
 */
public class IcAndCbmClassVisitorTest extends AbstractClassVisitorT {

    public IcAndCbmClassVisitorTest() {
        mPathToClass3 = "./target/test-classes/ChildOfChld.class";
    }


    @Test
    public void testVisitJavaClass() throws IOException {
        IcAndCbmClassVisitor icCounter;

        icCounter = new IcAndCbmClassVisitor(mContainer);
        icCounter.visitJavaClass(mJavaClass1);
        assertEquals(0, mClassMetrics1.getCbm(), "Cbm in KlasaTestowa");
        assertEquals(0, mClassMetrics1.getIc(), "Ic in KlasaTestowa");

        icCounter = new IcAndCbmClassVisitor(mContainer);
        icCounter.visitJavaClass(mJavaClass3);
        assertEquals(4, mClassMetrics3.getCbm(), "Cbm in ChildOfChld");
        assertEquals(1, mClassMetrics3.getIc(), "IC in ChildOfChld");

        icCounter = new IcAndCbmClassVisitor(mContainer);
        icCounter.visitJavaClass(mJavaClass2);
        assertEquals(0, mClassMetrics2.getCbm(), "CBM in KlasaTestowaChld");
        assertEquals(0, mClassMetrics2.getIc(), "IC in KlasaTestowaChld");

        JavaClass javaClass4 = new ClassParser("./target/test-classes/AnotherChildOfChild.class").parse();
        ClassMetrics classMetrics4 = mContainer.getMetrics(javaClass4.getClassName());
        icCounter.visitJavaClass(javaClass4);
        assertEquals(3, classMetrics4.getCbm(), "CBM in AnotherChildOfChld");
        assertEquals(1, classMetrics4.getIc(), "IC in AnotherChildOfChld");

    }

}