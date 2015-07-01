package gr.spinellis.ckjm.ant;

import gr.spinellis.ckjm.MetricsFilterTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author mjureczk
 */
public class CkjmTaskTest {

    private final String outFile = "test_out.txt";

    public CkjmTaskTest() {
        //TODO: delete test file if exists
    }


    /**
     * Test of execute method, of class CkjmTask.
     */
    @Test
    public void testExecuteJar() throws IOException {
        CkjmTask ckjm = new CkjmTask();
        ckjm.setFormat("xml");
        ckjm.setClassJars("fileThatDoesntExist.jar");
        ckjm.setOutputfile(new File(outFile));
        try {
            ckjm.execute();
            fail("An Exception should be thrown!");
        } catch (BuildException be) {
            assertTrue(true);
        }

        ckjm.setClassJars("src/test/resources/testClasses.jar");
        ckjm.execute();
        String[] out = MetricsFilterTest.readFile(outFile);
        assertNotNull(out);
        assertEquals("<?xml version=\"1.0\"?>", out[0]);
        assertEquals("<ckjm>", out[1]);
        assertEquals("\t<class>", out[2]);

        Set<String> outLines = new HashSet<String>();
        outLines.addAll(Arrays.asList(out));
        assertTrue(outLines.contains("\t\t<name>AnotherChildOfChild</name>"));
    }


}