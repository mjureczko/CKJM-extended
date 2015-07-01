/*
 * CkjmBean.java
 *
 * Created on 26 pazdziernik 2007, 12:52
 */
package gr.spinellis.ckjm;

import java.beans.*;
import java.io.File;
import java.io.Serializable;

/**
 * @author marian
 */
public class CkjmBean extends Object implements Serializable {

    private PropertyChangeSupport propertySupport;
    /**
     * Holds value of property includeJdk.
     */
    private boolean includeJdk = false;
    /**
     * Holds value of property onlyPublic.
     */
    private boolean onlyPublic = false;

    public CkjmBean() {
        propertySupport = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    /**
     * Getter for property includeJdk.
     * @return Value of property includeJdk.
     */
    public boolean isIncludeJdk() {
        return this.includeJdk;
    }

    /**
     * Setter for property includeJdk.
     * @param includeJdk New value of property includeJdk.
     */
    public synchronized void setIncludeJdk(boolean includeJdk) {
        this.includeJdk = includeJdk;
    }

    /**
     * Getter for property onlyPublic.
     * @return Value of property onlyPublic.
     */
    public boolean isOnlyPublic() {
        return this.onlyPublic;
    }

    /**
     * Setter for property onlyPublic.
     * @param onlyPublic New value of property onlyPublic.
     */
    public synchronized void setOnlyPublic(boolean onlyPublic) {
        this.onlyPublic = onlyPublic;
    }

    /**
     * Counts values of metrics for given class.
     * @param classFile Names (with path) to a *.class files, separated with ':'.
     * @param outputHandler The metrics values may be stored there.
     */
    public synchronized void countMetrics(String classFile, CkjmOutputHandler outputHandler) {
        String[] names = classFile.split(File.pathSeparator);
        MetricsFilter.runMetrics(names, outputHandler, false);
    }

    public String[] metricsNames() {
        String[] tab = new String[18];

        tab[0] = "WMC";
        tab[1] = "DIT";
        tab[2] = "NOC";
        tab[3] = "CBO ";
        tab[4] = "RFC ";
        tab[5] = "LCOM";
        tab[6] = "Ca";
        tab[7] = "Ce";
        tab[8] = "NPM";
        tab[9] = "LCOM3 ";
        tab[10] = "LOC";
        tab[11] = "DAM";
        tab[12] = "MO";
        tab[13] = "MFA ";
        tab[14] = "CAM";
        tab[15] = "IC";
        tab[16] = "CBM";
        tab[17] = "AMC ";


        return tab;
    }
}
