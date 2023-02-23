package gr.spinellis.ckjm.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author mjureczk
 */
public class MethodCouplingTest {

    /**
     * Test of toString method, of class MethodCoupling.
     */
    @Test
    public void testToString() {
        MethodCoupling mc = new MethodCoupling( "ClassA", "MethodA", "ClassB", "MethodB");
        assertEquals("ClassA.MethodA is coupled to ClassB.MethodB", mc.toString(), "ToString");
    }

    /**
     * Test of compareTo method, of class MethodCoupling.
     */
    @Test
    public void testCompareTo() {
        MethodCoupling mc1 = new MethodCoupling( "ClassA", "MethodA", "ClassB", "MethodB");

        MethodCoupling mc2 = new MethodCoupling( "ClassA", "MethodC", "ClassB", "MethodB");
        MethodCoupling mc3 = new MethodCoupling( "ClassA", "MethodC", "ClassB", "MethodB");
        MethodCoupling mc4 = new MethodCoupling( "ClassB", "MethodB", "ClassA", "MethodC");

        assertEquals( -2, mc1.compareTo(mc2), "Compare - not equal" );
        assertEquals( 2, mc2.compareTo(mc1), "Compare - not equal");
        assertEquals( -2, mc1.compareTo(mc3), "Compare - not equal" );
        assertEquals( 2, mc3.compareTo(mc1),"Compare - not equal");
        assertEquals( -2, mc1.compareTo(mc4),"Compare - not equal" );
        assertEquals( 2, mc4.compareTo(mc1),"Compare - not equal");
        assertEquals( 0, mc2.compareTo(mc3), "Compare - equal" );
        assertEquals( 0, mc3.compareTo(mc2), "Compare - equal" );
        assertEquals( 0, mc2.compareTo(mc4), "Compare - equal" );
        assertEquals( 0, mc4.compareTo(mc2), "Compare - equal" );
    }

}