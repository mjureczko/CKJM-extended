package gr.spinellis.ckjm;

import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author marian
 */
public class ClassVisitorTest {

    private JavaClass jc;
    private ClassVisitor cv;

    @BeforeEach
    public void setUp() throws IOException {
        jc = new ClassParser("./target/test-classes/KlasaTestowa.class").parse();
        ICountingProperities cp = createNiceMock(ICountingProperities.class);
        replay(cp);
        IClassMetricsContainer cmc = new ClassMetricsContainer(cp);
        cv = new ClassVisitor(jc, cmc, cp);
    }

    /**
     * Test of visitJavaClass method, of class ClassVisitor.
     */
    @Test
    public void testVisitJavaClass() {

        cv.visitJavaClass(jc);
        assertEquals("KlasaTestowa", cv.getMyClassName(), "Test class name.");
        Field[] fields = cv.getFields();
        assertEquals(1, fields.length, "Only one field!");
        assertEquals("i", fields[0].getName(), "Field name i");
    }

    /**
     * Test of registerCoupling method, of class ClassVisitor.
     */
    @Test
    public void testRegisterCoupling_String() {
        cv.registerCoupling("AmazingClass");
        cv.registerCoupling("AmazingClass");
        cv.end();
        ClassMetrics cm = cv.getMetrics();
        assertEquals(1, cm.getCe(), "Efferent coupling has been registered");
    }


}