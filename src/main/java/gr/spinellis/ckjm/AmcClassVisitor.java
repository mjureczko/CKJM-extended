/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import gr.spinellis.ckjm.utils.LoggerHelper;
import org.apache.bcel.classfile.JavaClass;

/**
 * The metric will be counted correctly only when the WMC and LOC metric have been already counted.
 *
 * @author mjureczk
 */
public class AmcClassVisitor extends AbstractClassVisitor {

    public AmcClassVisitor(IClassMetricsContainer container) {
        super(container);
    }

    @Override
    protected void visitJavaClass_body(JavaClass jc) {
        double numberOfFields = jc.getFields().length;
        double wmc = mClassMetrics.getWmc();
        double loc = mClassMetrics.getLoc();
        double result;

        if (wmc == 0) {
            result = 0;
        } else {
            loc = loc - numberOfFields - wmc;
            if (loc < 0) {
                LoggerHelper.printError("The AMC metric probably is being counted before WMC or LOC!", new RuntimeException());
                result = 0;
            } else {
                result = loc / wmc;
            }
        }

        mClassMetrics.setAmc(result);
    }

}
