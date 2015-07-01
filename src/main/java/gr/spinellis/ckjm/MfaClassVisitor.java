/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 *
 * @author marian
 */
public class MfaClassVisitor extends AbstractClassVisitor {

    public MfaClassVisitor(IClassMetricsContainer mc){
        super(mc);
    }

    /** 
     * All constructors (static too) are ignored.
     * @param methods array of methods
     * @return Number of not constructors in given array of methods
     */
    private int howManyIgnore(Method[] methods) {
        int ign = 0;

        for( Method m : methods ){
            if( m.getName().equals("<init>") || m.getName().equals("<clinit>") ){
                ign++;
            }
        }

        return ign;
    }

    private int getNumOfMethods( JavaClass jc ){
        if( jc.getClassName().compareTo("java.lang.Object") == 0 ){ //When the java.lang.Object is the parent
            return 0;                                               //we do not analyze the parent's methods.
        }
        Method[] methods = jc.getMethods();
        return methods.length - howManyIgnore(methods);
    }

    @Override
    protected void visitJavaClass_body(JavaClass jc) {
        JavaClass parent = jc.getSuperClass();

        double pNumOfMeth=0;
        JavaClass parents[] = jc.getSuperClasses();
        for( JavaClass p : parents ){
            pNumOfMeth += getNumOfMethods(p);
        }

        double cNumOfMeth = getNumOfMethods( jc );

        double result;
        if( cNumOfMeth+pNumOfMeth == 0 ){
            result = 0;
        }
        else{
            result = pNumOfMeth / (cNumOfMeth + pNumOfMeth);
        }
        
        mClassMetrics.setMfa(result);
    }
}
