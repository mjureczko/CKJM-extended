/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;


/**
 *
 * @author marian
 */
public class ClassMetricsContainerTest {

    private ClassMetricsContainer cmc;

    public ClassMetricsContainerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        ICountingProperities cp = createNiceMock( ICountingProperities.class );
        replay(cp);
        cmc = new ClassMetricsContainer(cp);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getMetrics method, of class ClassMetricsContainer.
     */
    @Test
    public void testGetMetrics() {
        
        String first = "first";
        String second = "second";

        ClassMetrics cm = cmc.getMetrics( first );
        assertNotNull( "Get \"first\" Metrics first time.", cm );
        ClassMetrics cm2 = cmc.getMetrics( second );
        assertNotNull( "Get \"second\" Metrics first time.", cm2 );
        assertNotSame( "Get \"second\" Metrics first time.", cm2, cm);

        cm2 = cmc.getMetrics( first );
        assertSame( "Get \"first\" Metrics second time.", cm2, cm);
    }

    /**
     * Test of printMetrics method, of class ClassMetricsContainer.
     */
    @Test
    public void testPrintMetrics() {

        String first = "first";
        ICountingProperities cp = createNiceMock( ICountingProperities.class );

        ClassMetrics cm = cmc.getMetrics(first);
        cm.setVisited();

        CkjmOutputHandler coh = createStrictMock( CkjmOutputHandler.class );
        coh.handleClass(isA(String.class), isA(ClassMetrics.class));
        replay(coh);

        cmc.printMetrics(coh);
    }

}