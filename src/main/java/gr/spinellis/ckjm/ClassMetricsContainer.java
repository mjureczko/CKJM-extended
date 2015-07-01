/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A container of class metrics mapping class names to their metrics.
 * This class contains the the metrics for all class's during the filter's
 * operation.  Some metrics need to be updated as the program processes
 * other classes, so the class's metrics will be recovered from this
 * container to be updated.
 *
 * @version $Revision: 1.9 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
public class ClassMetricsContainer implements IClassMetricsContainer {

    /** The map from class names to the corresponding metrics */
    private HashMap<String, ClassMetrics> m = new HashMap<String, ClassMetrics>();

    private ICountingProperities prop=null;

    /** Return a class's metrics
     * @name Name of class. Metrics of the class are stored in coreponding ClassMetric object.
     */
    public ClassMetrics getMetrics(String name) {
        ClassMetrics cm = m.get(name);
        if (cm == null) {
            cm = new ClassMetrics();
            m.put(name, cm);
        }
        return cm;
    }

    /** Print the metrics of all the visited classes. */
    public void printMetrics(CkjmOutputHandler handler) {
        Set<Map.Entry<String, ClassMetrics>> entries = m.entrySet();
        Iterator<Map.Entry<String, ClassMetrics>> i;

        for (i = entries.iterator(); i.hasNext(); ) {
            Map.Entry<String, ClassMetrics> e = i.next();
            ClassMetrics cm = e.getValue();
            if (cm.isVisited() && (prop.includeAll() || cm.isPublic()))
            handler.handleClass(e.getKey(), cm);
        }
    }

    public ClassMetricsContainer(ICountingProperities cp)
    {
        if( cp==null )  throw new RuntimeException( "CountingProperties cannot be null" );
        prop = cp;
    }
}