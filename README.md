# CKJM extended - An extended version of Tool for Calculating Chidamber and Kemerer Java Metrics (and many other metrics)

Unless otherwise expressly stated, all original material on this page created by Diomidis Spinellis or Marian Jureczko is licensed under a (Creative Commons Attribution-Noncommercial-No Derivative Works 2.5 License)[http://creativecommons.org/licenses/by-nc-nd/2.5/].

## Introduction

The program CKJM extended calculates nineteen size and structure software metrics by processing the bytecode of compiled Java files.
The program calculates for each class the following metrics, and displays them on its standard output or saves in file, following the class's name:

    WMC: Weighted methods per class (NOM: Number of Methods in the QMOOD metric suite)
    DIT: Depth of Inheritance Tree
    NOC: Number of Children
    CBO: Coupling between object classes
    RFC: Response for a Class
    LCOM: Lack of cohesion in methods
    Ca: Afferent coupling (not a C&K metric)
    Ce: Efferent coupling (not a C&K metric)
    NPM: Number of Public Methods for a class (not a C&K metric; CIS: Class Interface Size in the QMOOD metric suite)
    LCOM3: Lack of cohesion in methods Henderson-Sellers version
    LCO: Lines of Code (not a C&K metric)
    DAM: Data Access Metric (QMOOD metric suite)
    MOA: Measure of Aggregation (QMOOD metric suite)
    MFA: Measure of Functional Abstraction (QMOOD metric suite)
    CAM: Cohesion Among Methods of Class (QMOOD metric suite)
    IC: Inheritance Coupling (quality oriented extension to C&ampK metric suite)
    CBM: Coupling Between Methods (quality oriented extension to C&ampK metric suite)
    AMC: Average Method Complexity (quality oriented extension to C&ampK metric suite)
    CC: McCabe's Cyclomatic Complexity

This program has been initially written as an extended version of ckjm 1.8. 
It was extended through increasing the number of software metrics that it calculates. 
The initial version of ckjm extended is 2.0. 
It was written on top of the ckjm 1.8 and thus it could be considered as the next version of the ckjm.

To run the program you simply specify the class files (or jar files) on its command line or standard input. 
The program will produce on its standard output a line for each class containing the complete name of the class and the values of the corresponding class metrics. 
Ckjm can be use as an ant task too (where offers a xml output). This operation model allows the tool to be easily extended using textual pre and post processors.

## Operation

To run ckjm you must run java with the -jar flag, providing as its argument the location of the file ckjm.jar. 
Next, you can specify as arguments the Java class files you want to analyze.
Example:
```bash
java -jar /usr/local/lib/ckjm_ext.jar build/classes/gr/spinellis/ckjm/*.class
```
Replace the sequence /usr/local/lib/ckjm-1.5.jar with the actual path and filename of the ckjm version you are using.

The command's output will be a list of class names (prefixed by the package they are defined in), followed by the corresponding metrics for that class: WMC, DIT, NOC, CBO, RFC, LCOM, Ca, Ce, NPM, LCOM3, LOC, DAM, MOA, MFA, CAM, IC, CBM and AMC.
In lines where at the begining is "~" is the value of CC. 
CC is counted for all methods in given class.
```
test.TestClass  3 1 0 1 8 1 1 1 1 0.0000 115 0.0000 0 0.0000 0.5556 0 0 37.0000
 ~ void m2(): 6
 ~ public void <init>(): 1
 ~ void m1(): 7


test.TestClass2 5 1 0 1 11 4 1 1 0 0.4167 53 0.3333 1 0.0000 0.6250 0 0 9.0000
 ~ int m3(int jk): 1
 ~ void <init>(): 1
 ~ void m2(): 1
 ~ static void <clinit>(): 1
 ~ void m1(): 2
```

If the classes are located in a jar archives, you can specify the archive name instead of filenames of Java classes. 
You can also specify several jar (or class) names separated with the semicolon colon, depending on your operating system.
```bash
java -jar /usr/local/lib/ckjm_ext.jar 'ant-jai.jar;bcel.jar'
```

If you specify neither jars nor classes to analyse ckjm will prompt you to type some.

Finally, instead of specifying the classes to be analyzed as the command's arguments, you pass them (as class files, or as jar file, class file pairs) on the command's standard input. 
The following example will process all class files located in the build directory.

```
find build -name '*.class' -print | java -jar /usr/local/lib/ckjm_ext.jar
```

The program, by default, will not take into account classes that belong to the Java SDK packages. 
The command-line option switch -s, can be used to enable this processing.

You can take into consideration only public method by setting the command-line option switch -p. 
By default, all methods are taken into account.

The command-line option switch -x will change the output format to xml. 
By default, it is plain text as in the above example.
Example:
```
java -jar /usr/local/lib/ckjm_ext.jar -x build/classes/gr/spinellis/ckjm/*.class
```

Please note that the program may report a ClassNotFoundException. 
It usually happens when the investigated classes depends on some libraries. 
In order to solve the issue, the dependencies should be added to the classpath. 
Detailed guide regarding classpath configuration is available (here)[http://javarevisited.blogspot.com/2011/01/how-classpath-work-in-java.html].

## Development

### Release
1. Update version in pom.xml
2. Execute Release action (against master)
3. Wait for new version at https://mvnrepository.com/artifact/gr.spinellis.ckjm/ckjm_ext (can take days)
4. Update docs at http://gromit.iiar.pwr.wroc.pl/p_inf/ckjm/
