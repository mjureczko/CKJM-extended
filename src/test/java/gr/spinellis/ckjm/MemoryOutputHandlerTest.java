/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class MemoryOutputHandlerTest {

    public MemoryOutputHandlerTest() {
    }


    @Before
    public void setUp() {
    }

    /**
     * Test of handleClass method, of class MemoryOutputHandler.
     */
    @Test
    public void testHandleClass() {
        ClassMetrics cm = new ClassMetrics();
        MemoryOutputHandler out = new MemoryOutputHandler();
        String className = "SimpleClass";

        assertNull("Any classes has been measured", out.getMetrics(className));
        out.handleClass(className, cm);
        assertSame("Get ClassMetrics", cm, out.getMetrics(className ) );
        assertNull("There was no such class", out.getMetrics("BlahBlah"));
        assertNull("Null class", out.getMetrics(null));
    }

}