/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gr.spinellis.ckjm;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;

/**
 * Contains the common code of ClassVisitors tests.
 * @author marian
 */
public abstract class AbstractClassVisitorT {

    protected JavaClass mJavaClass1, mJavaClass2, mJavaClass3;
    protected IClassMetricsContainer mContainer;
    protected ICountingProperities mCountingProps;
    protected ClassMetrics mClassMetrics1, mClassMetrics2, mClassMetrics3;
    protected String mPathToClass1 = "./target/test-classes/KlasaTestowa.class";
    protected String mPathToClass2 = "./target/test-classes/KlasaTestowaChld.class";
    protected String mPathToClass3 = "./target/test-classes/KlasaTestowa2.class";

    @BeforeEach
    public void setUp() throws IOException {
        mJavaClass1 = new ClassParser(mPathToClass1).parse();
        mCountingProps = createNiceMock(ICountingProperities.class);
        replay(mCountingProps);
        mContainer = new ClassMetricsContainer(mCountingProps);
                
        mJavaClass2 = new ClassParser(mPathToClass2).parse();
        mJavaClass3 = new ClassParser(mPathToClass3).parse();

        mClassMetrics1 = mContainer.getMetrics(mJavaClass1.getClassName());
        mClassMetrics2 = mContainer.getMetrics(mJavaClass2.getClassName());
        mClassMetrics3 = mContainer.getMetrics(mJavaClass3.getClassName());
    }

}
