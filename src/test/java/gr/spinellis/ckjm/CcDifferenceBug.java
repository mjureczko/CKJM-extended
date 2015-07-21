package gr.spinellis.ckjm;

import org.junit.Test;

/**
 * Created by marian on 20/07/15.
 * Defect Report https://github.com/mjureczko/CKJM-extended/issues/3
 */
public class CcDifferenceBug {

    @Test
    public void testXml(){
        CkjmBean ckjm = new CkjmBean();
        MemoryOutputHandler handle = new MemoryOutputHandler();

        ckjm.countMetrics("target/test-classes/testtoy.class", handle);
        ClassMetrics metrics = handle.getMetrics("testtoy");
        System.out.println(metrics.getMethodNames());
    }
}
