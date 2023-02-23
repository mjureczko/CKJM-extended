package gr.spinellis.ckjm.utils;

import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mjureczk
 */
public class FieldAccessTest {

    JavaClass mJavaClass;
    Method mMethod1;
    Method mMethod2;

    public FieldAccessTest() throws IOException {
        mJavaClass = new ClassParser("./target/test-classes/KlasaTestowa.class").parse();
        mMethod1 = mJavaClass.getMethods()[0];
        mMethod2 = mJavaClass.getMethods()[1];
    }

    /**
     * Test of toString method, of class FieldAccess.
     */
    @Test
    public void testToString() {
        FieldAccess fa = new FieldAccess("field1", mMethod1, mJavaClass);
        assertEquals(mJavaClass.getClassName() + "." + mMethod1.getName() + " uses field1", fa.toString(), "ToString");
    }

    /**
     * Test of compareTo method, of class FieldAccess.
     */
    @Test
    public void testCompareTo() {
        FieldAccess fa1 = new FieldAccess("field1", mMethod1, mJavaClass);
        FieldAccess fa2 = new FieldAccess("field2", mMethod2, mJavaClass);
        FieldAccess fa3 = new FieldAccess("field1", mMethod1, mJavaClass);

        assertEquals(50, fa1.compareTo(fa2), "CompareTo - not equal");
        assertEquals(-50, fa2.compareTo(fa1), "CompareTo - not equal");

        assertEquals(0, fa1.compareTo(fa3), "CompareTo - equal");
        assertEquals(0, fa3.compareTo(fa1), "CompareTo - equal");

    }

}