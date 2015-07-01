/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.bcel.classfile.EmptyVisitor;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

/**
 *
 * @author marian
 */
public class MoaClassVisitor extends EmptyVisitor {

    protected IClassMetricsContainer mMetricsContainer;
    private List<JavaClass> mClasses;

    public MoaClassVisitor(IClassMetricsContainer classMap){
        mMetricsContainer = classMap;
        mClasses = new LinkedList<JavaClass>();
    }


    public void end(){
        Iterator<JavaClass> itrClass = mClasses.iterator();
        while( itrClass.hasNext() ){
            JavaClass jc = itrClass.next();
            countMoaInClass( jc );
        }
    }

    protected ClassMetrics getClassMetrics(String className){
        return mMetricsContainer.getMetrics(className);
    }

    private void countMoaInClass(JavaClass currentClass) {
        Field[] fields = currentClass.getFields();
        int moa = 0;

        for( Field f : fields ){ //we count the self associations too
            Iterator<JavaClass> itr = mClasses.iterator();
            while( itr.hasNext() ) {
                String userClass = itr.next().getClassName();
                String fieldClass = ClassVisitor.className(f.getType());
                if( fieldClass.compareTo(userClass) == 0 ){
                    moa++;
                }
            }
        }
        getClassMetrics( currentClass.getClassName() ).setMoa(moa);
    }

    @Override
    public void visitJavaClass(JavaClass jc) {
        mClasses.add(jc);
    }
}
