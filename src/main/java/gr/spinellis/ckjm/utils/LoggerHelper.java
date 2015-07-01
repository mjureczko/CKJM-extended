/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
public class LoggerHelper {

    private static Logger mLogger = Logger.getLogger("ckjm");

    public static void printError(String error, RuntimeException re) {
        StringWriter trace = new StringWriter();
        re.printStackTrace(new PrintWriter(trace));

        printError( error + System.getProperty("line.separator") + trace.toString());
        
    }

    public static void printError(String error) {
        mLogger.severe(error);
    }

    public static void printWarning(String warning) {
        mLogger.warning(warning);
    }
}
