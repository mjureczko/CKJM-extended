package gr.spinellis.ckjm.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals( -1, mc1.compareTo(mc4),"Compare - not equal" );
        assertEquals( 1, mc4.compareTo(mc1),"Compare - not equal");
        assertEquals( 0, mc2.compareTo(mc3), "Compare - equal" );
        assertEquals( 0, mc3.compareTo(mc2), "Compare - equal" );
        assertEquals( 0, mc2.compareTo(mc4), "Compare - equal" );
        assertEquals( 0, mc4.compareTo(mc2), "Compare - equal" );
    }

    @Test
    void shouldPassTheCaseFromBugReport69() {
        MethodCoupling mc1 = new MethodCoupling("Child", "run", "tICAndCBM", "step1");
        MethodCoupling mc2 = new MethodCoupling("Child", "runReverse", "tICAndCBM", "step2");

        assertEquals(-7, mc1.compareTo(mc2));
        assertEquals(7, mc2.compareTo(mc1));
    }

    @Test
    public void testCompareToTransitivity() {
        MethodCoupling a = new MethodCoupling("A", "a", "B", "b");
        MethodCoupling b = new MethodCoupling("B", "b", "A", "c");
        MethodCoupling c = new MethodCoupling("B", "b", "A", "d");

        assertTrue(a.compareTo(b) < 0, "a < b");
        assertTrue(b.compareTo(c) < 0, "b < c");
        assertTrue(a.compareTo(c) < 0, "a < c");
    }
}