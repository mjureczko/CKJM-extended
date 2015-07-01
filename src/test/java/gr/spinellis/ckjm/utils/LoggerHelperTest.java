/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class LoggerHelperTest {

    public LoggerHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of printError method, of class LoggerHelper.
     * Test does not work on selected environments thus is ignored.
     */
    @Ignore
    @Test
    public void testPrintError() throws IOException {
        String out = "temp.txt";
        System.setErr(new PrintStream(new FileOutputStream(out)) );
        RuntimeException re = new RuntimeException();
        LoggerHelper.printError("error", re);

        String[] output = readFile(out);
        assertEquals( "SEVERE: error", output[1] );
        assertEquals( "java.lang.RuntimeException", output[2]);
        assertTrue( output[3].contains( "at gr.spinellis.ckjm.utils.LoggerHelperTest.testPrintError" ) );
        assertTrue( output[4].contains( "at sun.reflect.NativeMethodAccessorImpl.invoke0" ) );
        assertTrue( output[5].contains( "at sun.reflect.NativeMethodAccessorImpl.invoke" ) );

    }

    private String[] readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> result = new ArrayList<String>();
        String[] tmp = new String[1];
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result.toArray( tmp );
    }

}