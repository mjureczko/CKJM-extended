package gr.spinellis.ckjm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author marian
 */
public class ClassMetricsContainerTest {

    private ClassMetricsContainer cmc;

    public ClassMetricsContainerTest() {
    }

    @BeforeEach
    public void setUp() {
        ICountingProperities cp = createNiceMock( ICountingProperities.class );
        replay(cp);
        cmc = new ClassMetricsContainer(cp);
    }

    /**
     * Test of getMetrics method, of class ClassMetricsContainer.
     */
    @Test
    public void testGetMetrics() {
        
        String first = "first";
        String second = "second";

        ClassMetrics cm = cmc.getMetrics( first );
        assertNotNull(cm, "Get \"first\" Metrics first time.");
        ClassMetrics cm2 = cmc.getMetrics( second );
        assertNotNull(cm2, "Get \"second\" Metrics first time.");
        assertNotSame(cm2, cm, "Get \"second\" Metrics first time.");

        cm2 = cmc.getMetrics( first );
        assertSame(cm2, cm, "Get \"first\" Metrics second time.");
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