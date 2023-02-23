package gr.spinellis.ckjm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author marian
 */
public class MemoryOutputHandlerTest {

    /**
     * Test of handleClass method, of class MemoryOutputHandler.
     */
    @Test
    public void testHandleClass() {
        ClassMetrics cm = new ClassMetrics();
        MemoryOutputHandler out = new MemoryOutputHandler();
        String className = "SimpleClass";

        assertNull(out.getMetrics(className), "Any classes has been measured");
        out.handleClass(className, cm);
        assertSame(cm, out.getMetrics(className), "Get ClassMetrics");
        assertNull(out.getMetrics("BlahBlah"), "There was no such class");
        assertNull(out.getMetrics(null), "Null class");
    }

}