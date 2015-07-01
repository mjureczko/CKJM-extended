/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package specialcasses;

/**
 *
 * @author marian
 */
abstract class DummyClass{
    abstract protected void dummyMethod();

    public void realdMethod(){
        System.out.println("I'm doing somtehing very important!");
    }
}

