/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm.utils;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

/**
 *
 * @author mjureczk
 */
public class FieldAccess implements Comparable<FieldAccess> {
    private String mFieldName;
    private Method mAccessor;
    private JavaClass mAccessorClass;

    public FieldAccess(String field, Method method, JavaClass cl){
        mFieldName = field;
        mAccessor = method;
        mAccessorClass = cl;
    }

    /**
     * @return the mFieldName
     */
    public String getFieldName() {
        return mFieldName;
    }

    /**
     * @return the mAccessor
     */
    public Method getAccessor() {
        return mAccessor;
    }

    /**
     * @return the mAccessorClass
     */
    public JavaClass getAccessorClass() {
        return mAccessorClass;
    }

    @Override
    public String toString(){
        return mAccessorClass.getClassName() + "." + mAccessor.getName() + " uses " + mFieldName;
    }

    public int compareTo( FieldAccess fa ){
        int res = fa.getFieldName().compareTo( getFieldName() );
        res += fa.getAccessor().getName().compareTo( getAccessor().getName() );
        res += fa.getAccessorClass().getClassName().compareTo( getAccessorClass().getClassName() );
        return res;
    }
}

