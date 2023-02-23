package gr.spinellis.ckjm.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mjureczko
 */
public class CmdLineParserTest {

    private CmdLineParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CmdLineParser();
    }


    /**
     * Test of parse method, of class CmdLineParser.
     */
    @Test
    public void testParseSeparatedArgs() {

        parser.parse(new String[]{"-s", "-p", "class"});
        assertTrue(parser.isArgSet("s"));
        assertTrue(parser.isArgSet("p"));
        assertFalse(parser.isArgSet("x"));
    }

    @Test
    public void testParseJoinedAargs() {
        parser.parse(new String[]{"-xs", "-p", "class"});
        assertTrue(parser.isArgSet("s"));
        assertTrue(parser.isArgSet("p"));
        assertTrue(parser.isArgSet("x"));
        assertFalse(parser.isArgSet("g"));
    }

    @Test
    public void testParseClasses() {
        parser.parse(new String[]{"-x", "blah.jar;blah2.jar", "class"});
        assertTrue(parser.isArgSet("x"));
        List<String> classes = parser.getClassNames();
        assertEquals(3, classes.size());
        assertTrue(classes.contains("blah.jar"));
        assertTrue(classes.contains("blah2.jar"));
        assertTrue(classes.contains("class"));
    }
}
