/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.spinellis.ckjm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mjureczko
 */
public class CmdLineParser {
    
    private Set<String> args;
    List<String> classes;
    
    public void parse(String[] argv){
        args = new HashSet<String>();
        classes = new ArrayList<String>();
        
        for( String s : argv ){
            if( s.startsWith("-")){
                for( int i=1; i<s.length(); i++){
                    args.add(s.substring(i, i+1));
                }   
            }
            else{
                addClass(s);
            }
        }
        
        if(classes.isEmpty()){
            readClassesFromStdio();
        }
    }

    private void addClass(String colonSeparatedNames) {
        String[] names = colonSeparatedNames.split(";");
        classes.addAll(Arrays.asList(names));
    }
    
    public boolean isArgSet(String arg){
        return args.contains(arg);
    }
    
    public List<String> getClassNames(){
        return classes;
    }

    private void readClassesFromStdio() {
        System.out.println("Please enter fully qualified names of the java classes to analyse.");
        System.out.println("Each class should be entered in separate line.");
        System.out.println("After the last class press enter to continue.");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s;
            while ((s = in.readLine()) != null && s.length()>0)
                addClass(s);
        } catch (Exception e) {
            LoggerHelper.printError( "Error reading line: " + e);
            System.exit(1);
        }
    }
            
}
