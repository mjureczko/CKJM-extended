/* Computes the slope of a simple linear regression line for an array of 2D
data points.
*/

import org.apache.commons.math3.stat.regression.SimpleRegression;

//###############################################################################
public class testtoy
{
    public static void main (String args[])
    {
        SimpleRegression sReg = new SimpleRegression();
        for (int i = 0; i < 50; i++)
            sReg.addData(i, i);
        double m = sReg.getSlope();
        System.out.println("slope = " + m);
    }// end method main
}// end class test-toy