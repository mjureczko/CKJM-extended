/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.io.IOException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

/**
 *
 * @author marian
 */
public class ClassVisitorTest {

    private JavaClass jc;
    private ClassVisitor cv;

    public ClassVisitorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws IOException {
        jc = new ClassParser("./target/test-classes/KlasaTestowa.class").parse();
        ICountingProperities cp = createNiceMock( ICountingProperities.class );
        replay(cp);
        IClassMetricsContainer cmc = new ClassMetricsContainer(cp);
        cv = new ClassVisitor(jc, cmc, cp);
    }

    @After
    public void tearDown() {
    }
   
    /**
     * Test of visitJavaClass method, of class ClassVisitor.
     */
    @Test
    public void testVisitJavaClass() {
        
        cv.visitJavaClass(jc);
        assertEquals( "Test class name.", "KlasaTestowa", cv.getMyClassName() );
        Field[] fields = cv.getFields();
        assertEquals( "Only one field!", 1, fields.length );
        assertEquals( "Field name i", "i", fields[0].getName() );
    }

    /**
     * Test of registerCoupling method, of class ClassVisitor.
     */
    @Test
    public void testRegisterCoupling_String() {
        cv.registerCoupling("AmazingClass");
        cv.registerCoupling("AmazingClass");
        cv.end();
        ClassMetrics cm=cv.getMetrics();
        assertEquals( "Efferent coupling has been registered", 1, cm.getCe() );
    }


}