package gr.spinellis.ckjm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author marian
 */
public class TreeSetWithIdTest {

    public TreeSetWithId<Integer> tswi;

    @BeforeEach
    public void setUp() {
        tswi = new TreeSetWithId<Integer>();
    }

    /**
     * Test of getId and setId method, of class TreeSetWithId.
     */
    @Test
    public void testId() {
        String id = "ID";
        assertNull(tswi.getId(), "Id hasn'b been set yet.");
        tswi.setId(id);
        assertEquals(id, tswi.getId(), "Id has been set.");
    }


}