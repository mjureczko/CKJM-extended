package gr.spinellis.ckjm;

import gr.spinellis.ckjm.ant.PrintXmlResults;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by marian on 20/07/15.
 * Defect Report https://github.com/mjureczko/CKJM-extended/issues/3
 */
public class CcDifferenceBugTest {

    @Test
    public void testXmlOutputForCC() {
        CkjmBean ckjm = new CkjmBean();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintXmlResults handler = new PrintXmlResults(new PrintStream(baos));

        ckjm.countMetrics("target/test-classes/testtoy.class", handler);
        String result = baos.toString();
        assertTrue(result.contains("<method name=\"public void _init_()\">1</method>"));
    }

    @Test
    public void testPlainOutputForCC() {
        CkjmBean ckjm = new CkjmBean();
        MemoryOutputHandler handler = new MemoryOutputHandler();

        ckjm.countMetrics("target/test-classes/testtoy.class", handler);
        ClassMetrics metrics = handler.getMetrics("testtoy");

        assertEquals(1, metrics.getCC("public void <init>()"));
    }
}

