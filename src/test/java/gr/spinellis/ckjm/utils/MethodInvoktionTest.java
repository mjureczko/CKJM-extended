/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import org.apache.bcel.generic.Type;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mjureczk
 */
public class MethodInvoktionTest {

    class MyType extends Type{
        MyType(String signature){
            super( (byte)0, signature );
            
        }
    }

    MethodInvokation mi1, mi2, mi3;

    public MethodInvoktionTest() {
        Type[] destArg = { new MyType("destArg") };
        Type[] srcArg = { new MyType("srcArg") };

        mi1 = new MethodInvokation( "DestCl", "DestMe", destArg, "SrcCl", "SrcMe", srcArg);
        mi2 = new MethodInvokation( "DestCl", "DestMe2", destArg, "SrcCl", "SrcMe", srcArg);
        mi3 = new MethodInvokation( "DestCl", "DestMe", destArg, "SrcCl", "SrcMe", srcArg);

    }


    /**
     * Test of isNotConstructorInvocation method, of class MethodInvoktion.
     */
    @Test
    public void testIsNotConstructorInvocation() {
        MethodInvokation init = new MethodInvokation(null,"<init>",null,null,null,null);
        assertFalse("Standard constructor", init.isNotConstructorInvocation() );

        MethodInvokation clinit = new MethodInvokation(null,"<clinit>",null,null,null,null);
        assertFalse("Static constructor", clinit.isNotConstructorInvocation() );

        MethodInvokation ordinaryMethod = new MethodInvokation(null,"ordinary",null,null,null,null);
        assertTrue("Ordinary method", ordinaryMethod.isNotConstructorInvocation() );

    }

    /**
     * Test of toString method, of class MethodInvoktion.
     */
//    @Test
//    public void testToString() {
//        //assertEquals( "ToString", "", mi1.toString())
//    }

    /**
     * Test of compareTo method, of class MethodInvoktion.
     */
    @Test
    public void testCompareTo() {
        assertEquals( "Compare - not equal", -1, mi1.compareTo(mi2) );
        assertEquals( "Compare - not equal", 1, mi2.compareTo(mi1) );

        assertEquals( "Compare - equal", 0, mi1.compareTo(mi3) );
        assertEquals( "Compare - equal", 0, mi3.compareTo(mi1) );
    }

    

}