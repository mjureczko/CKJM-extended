/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marian
 */
public class MetricsFilterTest {

    public MetricsFilterTest() {
    }


    @Before
    public void setUp() {
    }



    /**
     * Test of main method, of class MetricsFilter.
     */
    @Test
    public void testMain() throws IOException {

        String[] mExpected =
        {
            "KlasaTestowaParent 2 1 0 1 5 0 0 1 2 0,0000 21 1,0000 0 0,0000 0,7500 0 0 9,0000",
            " ~ public int sru(): 1",
            " ~ public void <init>(int id): 1",
            "KlasaTestowa2 5 1 0 1 11 4 1 1 0 0,4167 53 0,3333 1 0,0000 0,6250 0 0 9,0000",
            " ~ void <init>(): 1",
            " ~ static void <clinit>(): 1",
            " ~ int m3(int jk): 1",
            " ~ void m2(): 1",
            " ~ void m1(): 2",
            "KlasaTestowa 3 1 0 2 8 1 2 1 1 0,0000 115 0,0000 0 0,0000 0,5556 0 0 37,0000",
            " ~ public void <init>(): 1",
            " ~ void m2(String name, java.util.List list, String c): 6",
            " ~ void m1(): 7"
        };
        String out = "system_out.txt";
        
        System.setOut(new PrintStream(new FileOutputStream(out)));
        Locale sysLocale = Locale.getDefault();
        try {
            Locale.setDefault(new Locale("pl", "PL"));
            String argv[] = {"./target/test-classes/KlasaTestowa.class",
                    "./target/test-classes/KlasaTestowa2.class",
                    "./target/test-classes/KlasaTestowaParent.class"};
            MetricsFilter.main(argv);

            String[] outContent = readFile(out);

            Arrays.sort(mExpected);
            Arrays.sort(outContent);

            for (int i = 0; i < outContent.length; i++) {
                assertEquals("testing: " + outContent[i], mExpected[i], outContent[i]);

            }
        }finally {
            //TODO: The standard output should be restored.
            if(sysLocale != null){
                Locale.setDefault(sysLocale);
            }
        }

    }
    
    @Test
    public void testMainXML() throws IOException{
        String[] expected={"\t<class>",
            "\t\t<name>KlasaTestowa</name>",
            "\t\t<wmc>3</wmc>",
            "\t\t<dit>1</dit>",
            "\t\t<noc>0</noc>",
            "\t\t<cbo>1</cbo>",
            "\t\t<rfc>8</rfc>",
            "\t\t<lcom>1</lcom>",
            "\t\t<ca>0</ca>",
            "\t\t<ce>1</ce>",
            "\t\t<npm>1</npm>",
            "\t\t<lcom3>0.0</lcom3>",
            "\t\t<loc>115</loc>",
            "\t\t<dam>0.0</dam>",
            "\t\t<moa>0</moa>",
            "\t\t<mfa>0.0</mfa>",
            "\t\t<cam>0.5555555555555556</cam>",
            "\t\t<ic>0</ic>",
            "\t\t<cbm>0</cbm>",
            "\t\t<amc>37.0</amc>",
            "\t\t<cc>",
            "\t\t\t<method name=\"public void _init_()\">1</method>",
            "\t\t\t<method name=\"void m2(String name, java.util.List list, String c)\">6</method>",
            "\t\t\t<method name=\"void m1()\">7</method>",
            "\t\t</cc>",
            "\t</class>"};
        String out = "system_out.xml";
        
        System.setOut(new PrintStream(new FileOutputStream(out)));
        String argv[] = {"./target/test-classes/KlasaTestowa.class","-x"};
        MetricsFilter.main(argv);

        String[] outContent = readFile(out);

        Arrays.sort(expected);
        Arrays.sort(outContent);

        for( int i=0; i<outContent.length; i++ ){
            assertEquals( "testing: "+outContent[i], expected[i], outContent[i] );

        }
    }

    public static String[] readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> result = new ArrayList<String>();
        String[] tmp = new String[1];
        while ((line = reader.readLine()) != null) {
            if( !line.matches("\\s*") )
                result.add(line);
        }
        return result.toArray( tmp );
    }

}