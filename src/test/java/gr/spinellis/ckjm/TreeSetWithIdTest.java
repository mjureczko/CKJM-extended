/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.junit.Before;
import org.junit.Test;
import gr.spinellis.ckjm.*;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class TreeSetWithIdTest {

    public TreeSetWithId<Integer> tswi;

    public TreeSetWithIdTest() {
    }

    @Before
    public void setUp() {
        tswi = new TreeSetWithId<Integer>();
    }

    /**
     * Test of getId and setId method, of class TreeSetWithId.
     */
    @Test
    public void testId() {
        String id = "ID";
        assertNull( "Id hasn'b been set yet.", tswi.getId() );
        tswi.setId( id );
        assertEquals( "Id has been set.", id, tswi.getId() );
    }


}