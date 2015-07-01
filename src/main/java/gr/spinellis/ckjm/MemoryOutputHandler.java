/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.util.HashMap;
import java.util.Map;

/**
 * Output handler, that stores the metrics in memory.
 * @author marian
 */
public class MemoryOutputHandler implements CkjmOutputHandler{

    private Map<String, ClassMetrics> mResults = new HashMap<String, ClassMetrics>();

    public void handleClass(String name, ClassMetrics c) {
        mResults.put( name, c );
    }

    public ClassMetrics getMetrics( String className ){
        return mResults.get(className);
    }
}
