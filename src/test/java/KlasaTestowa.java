/*
 * KlasaTestowa.java
 *
 * Created on 22 październik 2007, 18:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author marian
 */
public class KlasaTestowa
{
    int i=0;
    
    /** Creates a new instance of KlasaTestowa */
    public KlasaTestowa()
    {
    }
    
    void m1()
    {
        new KlasaTestowa2().m2();
        i = 1;
        int n = -1;
        if( i==1 )
            while(i<0)
            {
                n++;
                break;
            }
        else if( i==2 )
            System.out.println("2");

        
        for(n=0;n<10;n++)
            do
            {
                n++;
                continue;
            }while(n<8);
                n++;
                
         float f[] = {1,2,3,4,5};
         
         for( float x : f )
             System.out.println(x);
        
    }
    
    void m2(String name, List<Double> list, String c)
    {
        m1();
        int n = 17;
        switch(n)
        {
            case 0: System.out.println("zero");
                    break;
            case 17: System.out.println("duzo");
                    if(n>0) n=0;
                    break;
            case 20: n=2; break;
            default: System.out.println("unknown");
        }
    }
    
}

class KlasaTestowa2
{
    static int i=0;
    static int j=0;

    protected KlasaTestowa mKt = null;
    
    void m1()
    {
        mKt = new KlasaTestowa();
        i=1;
        try{
            i+=17;
            throw new Exception( Integer.toHexString(i) );
        }catch( Exception ex )
        {
            if( ex != null )
                System.err.println(ex.toString());
        }
    }
    
    void m2()
    {
        m1();
    }
    
    int m3(int jk)
    {
        m1();
        jk=j;
        return jk;
    }
    
}

class KlasaTestowaParent{
    protected int image;
    public KlasaTestowaParent(int id){
        image=id;
    }
    
    public int sru(){
        new KlasaTestowa().m1();
        return image++;
    }
            
}

class KlasaTestowaChld extends KlasaTestowaParent {
    public KlasaTestowaChld(int id) {
        super(id); //doesn't change the IC - it is not a redefined method.
    }

    protected Number number;

    public Number getFloatingPoint( int i) {
        if (this.number == null) {
            try {
                number = new Double(this.image);
            } catch (ArithmeticException e0) {
                number = new BigDecimal(this.image);
            }
        }
        return number;
    }

    public Object getValue() throws Exception {
        return getFloatingPoint(0);
    }

    public Class getType( int i ) throws Exception {
        getValue();
        System.out.println( getName() );
        return this.getFloatingPoint(0).getClass();
    }

    protected String getName( ){
        return "Funny";
    }
}

class ChildOfChld extends KlasaTestowaChld {

    public ChildOfChld() throws Exception{
        super(0);
        getType(0); 
        number = new Double(3.0);  //IC+1 p.1
    }

    static int getInt(){
        return 23761;
    }

    private final void notUsable(String slamp) throws Exception{
        final String s = "You won't be able to call this method!";
        System.out.println(s + slamp);
    }

    @Override
    public Object getValue() throws Exception { //IC+1 p.2 is used by super.getType(); super.getType() doesn't use the return value
        notUsable("slamp");
        getType( getInt() ); //IC+0 (p.3) Coupling ChildOfChld.getValue()<=> KlasaTestowaChld.getType() is already counted; see the above line
        return null;
    }

    @Override
    protected String getName(  ){ //IC+1 //p.2 (is used by super.getType())
        getFloatingPoint(0); //IC+1 //p.3
        return "Not funny";
    }

}

class AnotherChildOfChild extends KlasaTestowaChld{
    AnotherChildOfChild(){
        super(0);
        List<String> l = new List<String>(){

            public int size() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean isEmpty() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean contains(Object o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Iterator<String> iterator() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Object[] toArray() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public <T> T[] toArray(T[] a) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean add(String o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean remove(Object o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean containsAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean addAll(Collection<? extends String> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean addAll(int index, Collection<? extends String> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean removeAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public boolean retainAll(Collection<?> c) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void clear() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String get(int index) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String set(int index, String element) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void add(int index, String element) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String remove(int index) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public int indexOf(Object o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public int lastIndexOf(Object o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public ListIterator<String> listIterator() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public ListIterator<String> listIterator(int index) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public List<String> subList(int fromIndex, int toIndex) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        };
    }

    @Override
    public Number getFloatingPoint( int i) { //IC+2 it is called by getValue() and getType()
        return new Integer(i);
    }

    @Override
    protected String getName(  ){ //IC+1 //p.2 (is used by super.getType())
        getFloatingPoint(0); //IC won't change //p.3 - getFloatingPoint is redefined in AnotherChildOfChild
        return "May be funny";
    }
}

abstract class _DummyClass{
    abstract protected void dummyMethod();

    public void realdMethod(){
        System.out.println("I'm doing somtehing very important!");
    }
}
