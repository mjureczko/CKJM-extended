/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mjureczk
 */
public class MethodCouplingTest {

    public MethodCouplingTest() {
    }

  
    /**
     * Test of toString method, of class MethodCoupling.
     */
    @Test
    public void testToString() {
        MethodCoupling mc = new MethodCoupling( "ClassA", "MethodA", "ClassB", "MethodB");
        assertEquals("ToString", "ClassA.MethodA is coupled to ClassB.MethodB", mc.toString() );
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

        assertEquals( "Compare - not equal", -2, mc1.compareTo(mc2) );
        assertEquals( "Compare - not equal", 2, mc2.compareTo(mc1) );
        assertEquals( "Compare - not equal", -2, mc1.compareTo(mc3) );
        assertEquals( "Compare - not equal", 2, mc3.compareTo(mc1) );
        assertEquals( "Compare - not equal", -2, mc1.compareTo(mc4) );
        assertEquals( "Compare - not equal", 2, mc4.compareTo(mc1) );

        assertEquals( "Compare - equal", 0, mc2.compareTo(mc3) );
        assertEquals( "Compare - equal", 0, mc3.compareTo(mc2) );
        assertEquals( "Compare - equal", 0, mc2.compareTo(mc4) );
        assertEquals( "Compare - equal", 0, mc4.compareTo(mc2) );
    }

}