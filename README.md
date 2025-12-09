# CKJM extended - An extended version of Tool for Calculating Chidamber and Kemerer Java Metrics (and many other metrics)

Unless otherwise expressly stated, all original material on this page created by Diomidis Spinellis or Marian Jureczko is licensed under a (Creative Commons Attribution-Noncommercial-No Derivative Works 2.5 License)[http://creativecommons.org/licenses/by-nc-nd/2.5/].

## Table of Contents
- [Introduction](#introduction)
- [Operation](#operation)
- [Using Pipelines to Select Classes](#using-pipelines-to-select-classes)
- [Using Pipelines to Format the Output](#using-pipelines-to-format-the-output)
- [Metric Descriptions](#metric-descriptions)
- [Measurement Details](#measurement-details)
- [Using Ckjm With Ant](#using-ckjm-with-ant)
- [Web Links and Acknowledgements](#web-links-and-acknowledgements)
- [Bibliography](#bibliography)
- [Frequently Asked Questions](#frequently-asked-questions)
- [Contributors](#contributors)
- [Download](#download)

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

## Using Pipelines to Select Classes

Using the Unix find command to select the class files to process, provides infinite flexibility.
- You can specify a range of modification times for the files. As an example, the following command will print the metrics of the class files modified during the last week. 
```bash
find target -name '*.class' -mtime -7 -print | java -jar /usr/local/lib/ckjm.jar
```
- You can filter out specific patterns, either with the GNU find's regular expression options, or by piping its results through grep. As an example, the following command will not print metrics for internal classes (their name contains a $ character). 
```bash
find target -name '*.class' | fgrep -v '$' | java -jar /usr/local/lib/ckjm.jar
```
- You process contents from several directories. As an example, the following command will process the class files located in the build and lib directories. 
```bash
find build lib -name '*.class' -print | java -jar /usr/local/lib/ckjm.jar
```

## Using Pipelines to Format the Output

The output of ckjm is textual. It could be plain text or xml. In the case of xml an XSL transformation may be used to convert the output to html:  
```bash
xsltproc -o ckjm.html path_to_xsl/ckjm.xsl path_to_ckjm_xml_outputfile/ckjm.xml
```

The distribution contains in the xsl directory two sample XSL files.

You can also plot the results in various formats by using [gnuplot](http://www.gnuplot.info/). 
Here is a diagram depicting the distribution of the CBO metric within the classes of [Eclipse](https://www.eclipse.org/).

![image](doc/eclipse-cbo.png)

To copy the ckjm's output to the Microsoft Windows clipboard to later paste the results into an MS-Word table, simply pipe the output of ckjm to the winclip command of the Outwit tool suite. 

## Metric Descriptions

The metrics ckjm will calculate and display for each class are the following.

WMC - Weighted methods per class
A class's weighted methods per class WMC metric is simply the sum of the complexities of its methods. As a measure of complexity we can use the cyclomatic complexity, or we can abritrarily assign a complexity value of 1 to each method. The ckjm program assigns a complexity value of 1 to each method, and therefore the value of the WMC is equal to the number of methods in the class.

DIT - Depth of Inheritance Tree
The depth of inheritance tree (DIT) metric provides for each class a measure of the inheritance levels from the object hierarchy top. In Java where all classes inherit Object the minimum value of DIT is 1.

NOC - Number of Children
A class's number of children (NOC) metric simply measures the number of immediate descendants of the class.

CBO - Coupling between object classes
The coupling between object classes (CBO) metric represents the number of classes coupled to a given class (efferent couplings and afferent couplings). This coupling can occur through method calls, field accesses, inheritance, arguments, return types, and exceptions.

RFC - Response for a Class
The metric called the response for a class (RFC) measures the number of different methods that can be executed when an object of that class receives a message (when a method is invoked for that object). Ideally, we would want to find for each method of the class, the methods that class will call, and repeat this for each called method, calculating what is called the transitive closure of the method's call graph. This process can however be both expensive and quite inaccurate. In ckjm, we calculate a rough approximation to the response set by simply inspecting method calls within the class's method bodies. The value of RFC is the sum of number of methods called within the class's method bodies and the number of class's methods. This simplification was also used in the 1994 Chidamber and Kemerer description of the metrics.

LCOM - Lack of cohesion in methods
A class's lack of cohesion in methods (LCOM) metric counts the sets of methods in a class that are not related through the sharing of some of the class's fields. The original definition of this metric (which is the one used in ckjm) considers all pairs of a class's methods. In some of these pairs both methods access at least one common field of the class, while in other pairs the two methods to not share any common field accesses. The lack of cohesion in methods is then calculated by subtracting from the number of method pairs that don't share a field access the number of method pairs that do. Note that subsequent definitions of this metric used as a measurement basis the number of disjoint graph components of the class's methods. Others modified the definition of connectedness to include calls between the methods of the class. The program ckjm follows the original (1994) definition by Chidamber and Kemerer.

Ca - Afferent couplings
A class's afferent couplings is a measure of how many other classes use the specific class. Coupling has the same definition in context of Ca as that used for calculating CBO.

Ce - Efferent couplings
A class's efferent couplings is a measure of how many other classes is used by the specific class. Coupling has the same definition in context of Ce as that used for calculating CBO.

NPM - Number of Public Methods
The NPM metric simply counts all the methods in a class that are declared as public. It can be used to measure the size of an API provided by a package.

LCOM3 - Lack of cohesion in methods.
LCOM3 varies between 0 and 2.
m - number of procedures (methods) in class;
a - number of variables (attributes in class;
µ(A) - number of methods that access a variable (attribute);
![formula](doc/lcom3.png)
The constructors and static initializations are taking into accounts as separately methods.

LOC - Lines of Code.
The lines are counted from java binary code and it is the sum of number of fields, number of methods and number of instructions in every method of given class.

DAM: Data Access Metric
This metric is the ratio of the number of private (protected) attributes to the total number of attributes declared in the class. A high value for DAM is desired. (Range 0 to 1)

MOA: Measure of Aggregation
This metric measures the extent of the part-whole relationship, realized by using attributes. The metric is a count of the number of data declarations (class fields) whose types are user defined classes.

MFA: Measure of Functional Abstraction
This metric is the ratio of the number of methods inherited by a class to the total number of methods accessible by member methods of the class. The constructors and the java.lang.Object (as parent) are ignored. (Range 0 to 1)

CAM: Cohesion Among Methods of Class
This metric computes the relatedness among methods of a class based upon the parameter list of the methods. The metric is computed using the summation of number of different types of method parameters in every method divided by a multiplication of number of different method parameter types in whole class and number of methods. A metric value close to 1.0 is preferred. (Range 0 to 1).

IC: Inheritance Coupling
This metric provides the number of parent classes to which a given class is coupled. A class is coupled to its parent class if one of its inherited methods functionally dependent on the new or redefined methods in the class. A class is coupled to its parent class if one of the following conditions is satisfied:
- One of its inherited methods uses a variable (or data member) that is defined in a new/redefined method.
- One of its inherited methods calls a redefined method.
- One of its inherited methods is called by a redefined method and uses a parameter that is defined in the redefined method.

CBM: Coupling Between Methods
The metric measure the total number of new/redefined methods to which all the inherited methods are coupled. There is a coupling when one of the given in the IC metric definition conditions holds.

AMC: Average Method Complexity
This metric measures the average method size for each class. Size of a method is equal to the number of java binary codes in the method.

CC - The McCabe's cyclomatic complexity
It is equal to number of different paths in a method (function) plus one. The cyclomatic complexity is defined as:
CC = E - N + P
where
E - the number of edges of the graph;
N - the number of nodes of the graph;
P - the number of connected components.

## Measurement Details

The original definition of the metrics, and implementation details of both the program, and the Java language provide some leeway on how the metrics are measured. The following list contains the most important decisions. These are marked in the source code with a comment, such as the following.

- Interfaces are measured when considering a class's coupling. Rationale: changes to the interface may well require changes to the class.
- Use of Java SDK classes (java.*, javax.*, and some others) does not count toward a class's coupling. Rationale: the Java SDK classes are relatively stable, in comparison to the rest of the project. A command line argument switch (-s) is available for including the Java SDK classes into the calculation. 
- Calls to JDK methods are included in the RFC calculation. Rationale: the method calls increase the class's complexity. 
- The classes used for catching exceptions contribute toward the class's coupling measurements. Rationale: at the point where an exception is caught a new object of the corresponding type is instantiated. 
- The complexity of each method is considered 1, when calculating WMC. Rationale: ease of implementation, and compatibility with Chidamber and Kemerer. 
- LCOM is calculated following the 1994 paper description, and not by looking at disjoint graph components. Rationale: ease of implementation, and compatibility with Chidamber and Kemerer. 
- RFC is calculated up to the first method call level, and not through the transitive closure of all method calls. Rationale: ease of implementation, and compatibility with Chidamber and Kemerer. 
- A class's own methods contribute to its RFC. Rationale: the original Chidamber and Kemerer article describes RFC as a union of the set of methods called by the class and the set of methods in the class.

## Using Ckjm With Ant

First define the ant task in your build.xml file. The ckjm jar file should be in the classpath. 
```xml
<taskdef name="ckjm" classname="gr.spinellis.ckjm.ant.CkjmTask">
  <classpath>
    <pathelement location="path/to/ckjm1-2.jar"/>
  </classpath>
</taskdef>
```

Now you can make use of the ckjm task. The attributes of the ckjm task are the following:

format

'plain' or 'xml'. Default is 'plain'

outputfile

Required. Output will be written to outputfile.

classdir

Required (if classjars is not set). Base directory which contains the class files.

classjars

Required (if classdir is not set). Jar files that contains the classes to analyse.

The ckjm task supports the nested elements <include> and <exclude>, which can be used to select the class files and the nested element <extdirs>, which is used to specify other class files participating in the inheritance hierarchy. Please notice that extdirs must point to a directory. All jar files from the directory will be included to classpath (in fact the directory will be appended to java.ext.dirs and the classpaht won't changed). You can use extdirs neither to include class files nor to include one, given by name jar file. 
The elements support [path-like structures](http://ant.apache.org/manual/using.html#path). Example usage: 
```xml
<ckjm outputfile="ckjm.xml" format="xml" classdir="build/classes">
  <include name="**/*.class" />
  <exclude name="**/*Test.class" />
  <extdirs path="lib" />
</ckjm>
```

Example usage with the classjars attribute:
```xml
<ckjm outputfile="ckjm.xml" format="xml" classjars="ant.jar:bcel-5.1.jar">
  <extdirs path="bcel-5.1.jar" />
  <extdirs path="ant.jar" />
</ckjm>
```

You can use an XSL stylesheet to generate an HTML report from the XML output file. Example: 
```xml
<xslt in="ckjm.xml" style="path/to/ckjm.xsl" out="ckjm.html" />
```

The distribution contains in the xsl directory two sample XSL files. Here is a complete example of a build.xml file. 
```xml
<project name="myproject" default="ckjm">

<target name="compile">
  <!-- your compile instructions -->
</target>

<target name="ckjm" depends="compile">

  <taskdef name="ckjm" classname="gr.spinellis.ckjm.ant.CkjmTask">
    <classpath>
      <pathelement location="path/to/ckjm1-2.jar"/>
    </classpath>
  </taskdef>

  <ckjm outputfile="ckjm.xml" format="xml" classdir="build/classes">
    <include name="**/*.class" />
    <exclude name="**/*Test.class" />
  </ckjm>

  <xslt in="ckjm.xml" style="path/to/ckjm.xsl" out="ckjm.html" />
</target>

</project> 
```

If the analyzed files form part of a class hierarchy of other class files that are not part of the analysis, then the extdirs path-like structure of the ckjm task must be set to point to the directory containing the corresponding jar files. This will internally set the java.ext.dirs property so that ckjm can locate the jar files containing those classes. 

## Web Links and Acknowledgements

- [Metrics for Object Oriented Software Development](http://javaboutique.internet.com/tutorials/codemetrics/)
- [BCEL - Byte Code Engineering Library](http://jakarta.apache.org/bcel/)
- [An Open Source Java Metrics Toolset Is Hard to Find](http://www.dmst.aueb.gr/dds/blog/20050211/)
- [ckjm — Chidamber and Kemerer Java Metrics](https://www.spinellis.gr/sw/ckjm/)

This product includes software developed by the [Apache Software Foundation](http://www.apache.org/). 

## Bibliography

- Rajendra K. Bandi, Vijay K. Vaishnavi, and Daniel E. Turk. Predicting maintenance performance using object-oriented design complexity metrics. IEEE Transactions on Software Engineering, 29(1):77–87, 2003. (doi:10.1109/TSE.2003.1166590)
- Jagdish Bansiya and Carl G. Davis. A Hierarchical Model for Object-Oriented Design Quality Assessment. IEEE Transactions on Software Engineering, 28(1):4-17, 2002. (doi: 10.1109/32.979986)
- Jagdish Bansiya and Carl G. Davis. Class Cohesion Metric For Object-Oriented Designs. Journal of Object-Oriented Programming, 11(8):47-52, 1999.
- Victor R. Basili, Lionel C. Briand, and Walcélio L. Melo. A validation of object-oriented design metrics as quality indicators. IEEE Transactions on Software Engineering, 22(10):751–761, 1996. (doi:10.1109/32.544352)
- Shyam R. Chidamber and Chris F. Kemerer. A metrics suite for object oriented design. IEEE Transactions on Software Engineering, 20(6):476–493, 1994. (doi:10.1109/32.295895)
- Shyam R. Chidamber, David P. Darcy, and Chris F. Kemerer. Managerial use of metrics for object-oriented software: An exploratory analysis. IEEE Transactions on Software Engineering, 24(8):629–639, 1998. (doi:10.1109/32.707698)
- Rudolf Ferenc, István Siket, and Tibor Gyimóthy. Extracting facts from open source software. In ICSM '04: Proceedings of the 20th IEEE International Conference on Software Maintenance (ICSM'04), pages 60–69. IEEE Computer Society, 2004.
- Brian L. Henderson-Sellers, Larry L. Constantine, and Ian M. Graham. Coupling and cohesion: Towards a valid metrics suite for object-oriented analysis and design. Object Oriented Systems, 3(3):143–158, 1996.
- Brian Henderson-Sellers. Object-Oriented Metrics: Measures of Complexity. Prentice-Hall, Englewood Cliffs, NJ, 1996.
- Martin Hitz and Behzad Montazeri. Chidamber and Kemerer's metrics suite: A measurement theory perspective. IEEE Transactions on Software Engineering, 22(4):267–271, 1996. (doi:10.1109/32.491650)
- Tobias Mayer and Tracy Hall. A critical analysis of current OO design metrics. Software Quality Control, 8(2):97–110, 1999. (doi:10.1023/A:1008900825849)
- Sandeep Purao and Vijay Vaishnavi. Product metrics for object-oriented systems. ACM Computing Surveys, 35(2):191–221, 2003. (doi:10.1145/857076.857090)
- Linda Rosenberg, Ruth Stapko, and Al Gallo. Applying object-oriented metrics. In Sixth International Symposium on Software Metrics—Measurement for Object-Oriented Software Projects Workshop, November 1999. Presentation available online http://www.software.org/metrics99/rosenberg.ppt (December 2005).
- Linda Rosenberg, Ruth Stapko, and Al Gallo. Risk-based object oriented testing. In Twenty-Fourth Annual Software Engineering Workshop. NASA, Software Engineering Laboratory, December 1999.
- Linda H. Rosenberg. Applying and interpreting object oriented metrics. In Software Technology Conference '98, 1998.
- Diomidis Spinellis. Tool writing: A forgotten art?. IEEE Software, 22(4):9–11, July/August 2005. (doi:10.1109/MS.2005.111)
- Diomidis Spinellis. Code Quality: The Open Source Perspective. Addison-Wesley, Boston, MA, 2006.
- Mei-Huei Tang, Ming-Hung Kao and Mei-Hwa Chen. An Empirical Study on Object-Oriented Metrics. Proceedings of The Software Metrics Symposium 242-249, 1999. (10.1109/METRIC.1999.809745)
- Marian Jureczko and Diomidis D. Spinellis. Using object-oriented design metrics to predict software defects. In Models and Methodology of System Dependability — Proceedings of RELCOMEX 2010: Fifth International Conference on Dependability of Computer Systems (DepCoS), pages 69–81. Oficyna Wydawnicza Politechniki Wrocławskiej, Wrocław, Poland, 2010.
- Marian Jureczko and Lech Madeyski. Towards identifying software project clusters with regard to defect prediction. In Proceedings of the 6th International Conference on Predictive Models in Software Engineering (PROMISE ’10), Timisoara, Romania, September 12–13, 2010, pp. 1–10. (doi: 10.1145/1868328.1868342)

## Frequently Asked Questions


### The metrics calculated by the program do not agree with those I calculate by hand. How come?
The ckjm program calculates the metrics from the code appearing in the compiled bytecode files. The Java compiler optimizes away some elements of the code (for example static final fields, and these do not take part in the calculations. You may want to consult the disassembled code (using a command like javap -c -private to see what elements ckjm takes into account.

### How can I run the tool in a JDK 1.4 environment?
You can use the open source tool retroweaver to create a backwards-compatible jar file. (Suggested by Paul King).

### I'm getting a ClassNotFoundException. How can I fix it?
If you are getting messages like the one below, it means that ckjm can't locate the code for the corresponding classes, in order to properly calculate the depth of the inheritance tree (DIT) metric. java.lang.ClassNotFoundException: Exception while looking for class javax.servlet.http.HttpServlet: java.io.IOException: Couldn't find: javax.servlet.http.HttpServlet.class To solve this problem you must explicitly setup the java.ext.dirs property pointing to a directory containing the jar files where ckjm can locate those classes. Example: java -Djava.ext.dirs=lib -jar ckjm-1.8.jar *.class

### I'm using ckjm to collect metrics for a research. How shall I cite it?
The extended version of ckjm was originally reported in:
Jureczko, M., Spinellis, D.: Using object-oriented design metrics to predict software defects. In: Proceedings of the 5th International Conference on Dependability of Computer Systems, pp. 69–81 (2010)

```
@Inbook{Jur10,
author = {Jureczko, Marian and Spinellis, Diomidis},
title = {Using Object-Oriented Design Metrics to Predict Software Defects},
volume = {Models and Methodology of System Dependability},
series = {Monographs of System Dependability},
year = {2010},
isbn = {978-83-7493-526-5},
pages = {69-81},
publisher = {Oficyna Wydawnicza Politechniki Wroclawskiej},
address = {Wroclaw, Poland}
}
```

## Contributors

- Panagiotis Louridas

Fix for a script in the documentation

- Antti Pöyhönen

RFC fix, count exceptions

- Julien Rentrop

XML output and Ant task

- jazzmuesli

BCEL upgrade to 6.5.0 

## Download

[ckjm package - .jar](https://github.com/mjureczko/CKJM-extended/releases/download/v2.9/ckjm_ext.jar)

Maven:
```xml
        <dependency>
            <groupId>gr.spinellis.ckjm</groupId>
            <artifactId>ckjm_ext</artifactId>
            <version>2.10</version>
            <type>jar</type>
        </dependency>
```

Gradle:
```
implementation 'gr.spinellis.ckjm:ckjm_ext:2.10'
```