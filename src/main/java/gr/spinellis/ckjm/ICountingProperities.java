/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

/**
 *
 * @author marian
 */
public interface ICountingProperities
{
    /** Return true if the measurements should include calls to the Java JDK into account */
    public boolean isJdkIncluded();

    /** Return true if the measurements should include all classes */
    public boolean includeAll();
}
