/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.apache.bcel.classfile.AccessFlags;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

/**
 *
 * @author marian
 */
public class DamClassVisitor extends AbstractClassVisitor {

    public DamClassVisitor(JavaClass jc, IClassMetricsContainer classMap){
        super( classMap );
    }

    @Override
    protected void visitJavaClass_body(JavaClass jc) {
                Field[] fields = jc.getFields();
        AccessFlags af;
        double all = fields.length;
        double encapsulated = 0;

        if( all == 0 ){ //there is nothing to count
            return;
        }

        for( Field f : fields){
            af = new AccessFlags(f.getModifiers()) {};
            if( af.isPrivate() || af.isProtected() ){
                encapsulated += 1;
            }
        }

        mClassMetrics.setDam( encapsulated/all );
    }
}
