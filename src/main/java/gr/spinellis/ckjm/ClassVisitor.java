/*
 * $Id: ClassVisitor.java 1.20 2007/07/25 12:24:00 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package gr.spinellis.ckjm;

import gr.spinellis.ckjm.utils.LoggerHelper;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.Constants;
import java.util.*;
import java.lang.reflect.Modifier;

/**
 * Visit a class updating its Chidamber-Kemerer metrics.
 *
 * @see ClassMetrics
 * @version $Revision: 1.20 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
public class ClassVisitor extends org.apache.bcel.classfile.EmptyVisitor {
    
    private ICountingProperities mProp=null;
    /** The class being visited. */
    private JavaClass mVisitedClass;
    /** The class'fieldName constant pool. */
    private ConstantPoolGen mPoolGen;
    /** The class'fieldName fully qualified name. */
    private String mMyClassName;
    /** The container where metrics for all classes are stored. */
    private IClassMetricsContainer mClassMetricsContainer;
    /** The emtrics for the class being visited. */
    private ClassMetrics mClassMetrics;
    
    /* Classes encountered.
     * Its cardinality is used for calculating the CBO and CE.
     */
    private HashSet<String> mEfferentCoupledClasses = new HashSet<String>();
    
    /** Methods encountered.
     * Its cardinality is used for calculating the RFC.
     */
    private HashSet<String> mResponseSet = new HashSet<String>();
    
    /** Use of fields in methods.
     * Every Tree (record of the ArrayList) contains the whole set of parameters used by coresponding method.
     * Its contents are used for calculating the LCOM.
     * We use a Tree rather than a Hash to calculate the
     * intersection in O(n) instead of O(n*n).
     */
    ArrayList<TreeSetWithId<String>> mFieldsUsedByMethods = new ArrayList<TreeSetWithId<String>>();
    
    /** Invocation of methods in methods.
     * It is used for calculating LCOM3 */
    private ArrayList<TreeSetWithId<String>> mMethodsUsedByMethods = new ArrayList<TreeSetWithId<String>>();
    
    /** Class fields */
    private Field[] mFields;
    
    public ClassVisitor(JavaClass jc, IClassMetricsContainer classMap, ICountingProperities prop) {
        if( prop==null ) throw new RuntimeException( "CountingProperties cannot be null" );
        mProp = prop;
        mVisitedClass = jc;
        mPoolGen = new ConstantPoolGen(mVisitedClass.getConstantPool());
        mClassMetricsContainer = classMap;
        mMyClassName = jc.getClassName();
        mClassMetrics = mClassMetricsContainer.getMetrics(getMyClassName());
    }

    public Field[] getFields(){
        return mFields;
    }

    /** Return the class'fieldName metrics container. */
    public ClassMetrics getMetrics() { 
        return mClassMetrics;
    }

    public void start() {
        visitJavaClass(mVisitedClass);
    }

    /** Calculate the class'fieldName metrics based on its elements. */
    @Override
    public void visitJavaClass(JavaClass jc) {
        String super_name   = jc.getSuperclassName();
        String package_name = jc.getPackageName();

        mClassMetrics.setVisited();
        if (jc.isPublic())
            mClassMetrics.setPublic();
        ClassMetrics pm = mClassMetricsContainer.getMetrics(super_name);

        pm.incNoc();
        mClassMetrics.setDit(jc.getSuperClasses().length);
        registerCoupling(super_name);

        String ifs[] = jc.getInterfaceNames();
        /* Measuring decision: couple interfaces */
        for (int i = 0; i < ifs.length; i++)
            registerCoupling(ifs[i]);

        mFields = jc.getFields();
        for(int i=0; i < mFields.length; i++)
            mFields[i].accept(this);

        Method[] methods = jc.getMethods();
        for(int i=0; i < methods.length; i++)
            methods[i].accept(this);
    }

    /** Add a given class to the classes we are coupled to */
    public void registerCoupling(String className) {
	/* Measuring decision: don't couple to Java SDK */
        if ((mProp.isJdkIncluded() || !ClassMetrics.isJdkClass(className))
                && !getMyClassName().equals(className))
            {
            mEfferentCoupledClasses.add(className);
            mClassMetricsContainer.getMetrics(className).addAfferentCoupling(getMyClassName());
        }
    }

    /* Add the type'fieldName class to the classes we are coupled to */
    public void registerCoupling(Type t) {
        registerCoupling(className(t));
    }

    /* Add a given class to the classes we are coupled to */
    void registerFieldAccess(String className, String fieldName) {
        registerCoupling(className);
        if (className.equals(getMyClassName()))
            mFieldsUsedByMethods.get(mFieldsUsedByMethods.size() - 1).add(fieldName);
    }

    /* Add a given method to our response set */
    void registerMethodInvocation(String className, String methodName, Type[] args) {
        registerCoupling(className);
        /* Measuring decision: calls to JDK methods are included in the RFC calculation */
        incRFC(className, methodName, args);
    }

    /** Called when a field access is encountered. */
    @Override
    public void visitField(Field field) {
        registerCoupling(field.getType());
    }

    /** Called when encountering a method that should be included in the
        class'fieldName RFC. */
    private void incRFC(String className, String methodName, Type[] arguments) {
        String argumentList = Arrays.asList(arguments).toString();
        // remove [ ] chars from begin and end
        String args = argumentList.substring(1, argumentList.length() - 1);
        String signature = className + "." + methodName + "(" + args + ")";
        mResponseSet.add(signature);
    }

    /** Called when a method invocation is encountered. */
    @Override
    public void visitMethod(Method method) {
        MethodGen mg = new MethodGen(method, mVisitedClass.getClassName(), mPoolGen);

        Type   result_type = mg.getReturnType();
        Type[] argTypes = mg.getArgumentTypes();

        registerCoupling(mg.getReturnType());
        for (int i = 0; i < argTypes.length; i++)
            registerCoupling(argTypes[i]);

        String[] exceptions = mg.getExceptions();
        for (int i = 0; i < exceptions.length; i++)
            registerCoupling(exceptions[i]);

        /* Measuring decision: A class'fieldName own methods contribute to its RFC */
        incRFC(getMyClassName(), method.getName(), argTypes);

        mClassMetrics.incWmc();
        if (Modifier.isPublic(method.getModifiers()))
            mClassMetrics.incNpm();

        TreeSetWithId<String> ntree = new TreeSetWithId<String>();
        ntree.setId( mg.getName() + mg.getSignature() );
        mFieldsUsedByMethods.add( ntree );

        MethodVisitor factory = new MethodVisitor(mg, this);
        factory.start();
        
        ntree = factory.getMethodsNames();
        ntree.setId( mg.getName() + mg.getSignature() ); 
        mMethodsUsedByMethods.add( ntree );
        
    }

    /** Return a class name associated with a type. */
    static String className(Type t) {
        String ts = t.toString();

        if (t.getType() <= Constants.T_VOID) {
            return "java.PRIMITIVE";
        } else if(t instanceof ArrayType) {
            ArrayType at = (ArrayType)t;
            return className(at.getBasicType());
        } else {
            return t.toString();
        }
    }

    /** Do final accounting at the end of the visit. */
    public void end() {
        mClassMetrics.setCe(mEfferentCoupledClasses);
        mClassMetrics.setRfc(mResponseSet.size());
        /*
         * Calculate LCOM  as |P| - |Q| if |P| - |Q| > 0 or 0 otherwise
         * where
         * P = set of all empty set intersections
         * Q = set of all nonempty set intersections
         */
        int lcom = 0;
        for (int i = 0; i < mFieldsUsedByMethods.size(); i++)
            for (int j = i + 1; j < mFieldsUsedByMethods.size(); j++) {
            /* A shallow unknown-type copy is enough */
            TreeSet<?> intersection = (TreeSet<?>)mFieldsUsedByMethods.get(i).clone();
            intersection.retainAll(mFieldsUsedByMethods.get(j));
            if (intersection.size() == 0)
                lcom++;
            else
                lcom--;
            }
        mClassMetrics.setLcom(lcom > 0 ? lcom : 0);
        
        countLcom3();
    }

    /** Counts lack of cohesion in methods according to Henderson-Sellers definition. */
    private void countLcom3()
    {
        /** LCOM3 value. 2 is default because it is the worst vaule and it will be used only for degenerated classes. */
        double lcom3=2;
        /** Numer of methods. */
        int m=0;
        if( mFieldsUsedByMethods!=null ){
            m=mFieldsUsedByMethods.size();
        }

        /** Numer of attributes */
        int a=0;
        if( mFields!=null){
            a=mFields.length;
        }

        /** Numer of arcs in graph that comes from LCOM3 definition. */
        int arc=0;
            
        if( m!=0 && m!=1 && a!=0 ) //if the class isn't degenerated then we can count the LCOM3 value
        {
            for( int i=0; i<m; i++ )
            {
                Lcom3Counter lcom3c = new Lcom3Counter( removeInheritedFields(mFieldsUsedByMethods.get(i)),
                        ((TreeSetWithId<String>)mFieldsUsedByMethods.get(i)).getId(), mFieldsUsedByMethods );
                lcom3c.findUsedFields( ((TreeSetWithId<String>)mFieldsUsedByMethods.get(i)).getId() );
                arc += lcom3c.getFieldsNames().size();
                //System.out.println( "Method " + ((TreeSetWithId<String>)mFieldsUsedByMethods.get(i)).getId() + " : " + lcom3c.getFieldsNames().size() + " arcs");
            }
            
            lcom3 = ((double)arc / (double)a - m);
            lcom3 = lcom3 / (double)(1-m);
        }
        
        try{               
            mClassMetrics.setLcom3( lcom3 );
        } catch(RuntimeException e)
        {
            LoggerHelper.printError( "Exception in class " + getMyClassName() + ": " + e, e );
        }
    }
    
    /**
     * Removes from treeWithFields those fields which are not in this.fields; this.fields does not contain inherited fields.
     * @param treeWithFields Set of field names that are used direct by the method. 
     */
    TreeSet<String> removeInheritedFields( TreeSet<String> treeWithFields  )
    {
        String fieldName;
        int index;
        boolean remove;
        
        if( treeWithFields == null )
            return null;
        
        Iterator<String> itr = treeWithFields.iterator();
        //TreeSetWithId<String> newts = new TreeSetWithId<String>();
        //newts.setId( treeWithFields.getId() );
        while( itr.hasNext() )
        {
            fieldName = itr.next();
            remove = true;
            for( index=0; index<mFields.length; index++ )
                if( mFields[index].getName().compareTo(fieldName) == 0 )
                {
                    remove = false; //current field is in fields[], fields contains all fields that are defined in current class
                    break;
                }
                        
            if( remove ) 
                itr.remove();

        }       
        return treeWithFields;
    }
    
    class Lcom3Counter
    {
        /** Used fields. */
        private TreeSet<String> mFieldsNames;
        
        /** Names of investigated methods */
        TreeSet<String> mMethodsNames;

        /** Every rekord of the array list contains all fields used by one method.*/
        ArrayList<TreeSetWithId<String>> mFieldsUsedByMethods;
               
        /**
         * Constructor
         * @param treeWithFields Set of field names that are used direct by the method.
         * @param method Method name. LCOM3 is caunted for this method.
         */
        Lcom3Counter( TreeSet<String> ts, String method, ArrayList<TreeSetWithId<String>> fieldsUsedByMethods )
        {
            this.mFieldsNames = ts;
            mMethodsNames = new TreeSet<String>();
            mMethodsNames.add( method );
            mFieldsUsedByMethods = fieldsUsedByMethods;
        }

        
        /**
         * Add fields names to treeWithFields, fields that are used by method "method" and all methods that are invoked by method "method". 
         * @param method Methopd name.
         */
        void findUsedFields( String method )
        {
            TreeSet<String> invokedMethods = getInvokedMethodsByMethodName( method );
            
            if( invokedMethods != null )
            {
                Iterator<String> itr = invokedMethods.iterator();
                while( itr.hasNext() )
                {
                    String name = itr.next();
                    TreeSet<String> fields = removeInheritedFields( getUsedFieldsByMethodName( name ) );
                    if( fields != null )
                        this.mFieldsNames.addAll( fields );
                }
            }
        }

        /** Return set of names of used fields. */
        TreeSet<String> getFieldsNames()
        {
            return mFieldsNames;
        }
        
         /**
         * Return collection of fields names that are invoked by method with name "name"
         * @param name Name of method.
         * @return Collection of fields names, or null.
         */
        private TreeSet<String> getUsedFieldsByMethodName( String name )
        {
            return getByMethodName( name, mFieldsUsedByMethods );
        }
        
        /**
         * Return collection of methods that are invoked by method with name "name"
         * @param name Name of method.
         * @return Collection of methods, or null.
         */
        private TreeSet<String> getInvokedMethodsByMethodName( String name )
        {
            return getByMethodName( name, mMethodsUsedByMethods );
        }
        
        private TreeSet<String> getByMethodName( String name, ArrayList<TreeSetWithId<String>> m )
        {
            for( int i=0; i<m.size(); i++ )
                if( m.get(i).getId().compareTo( name ) == 0 )
                    return m.get(i);
            return null;
        }
    }

    
    /** Return name of class associated with this ClassVisitor */
    public String getMyClassName()
    {
        return mMyClassName;
    }
}

/** It is designed to introduce a connection between method name (id) and the set of fields that are touched by this method */
class TreeSetWithId< type > extends TreeSet< type >
{
    private String id=null;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}


