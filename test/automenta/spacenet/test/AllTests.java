package automenta.spacenet.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for automenta.spacenet.space.test");
		//$JUnit-BEGIN$
		//suite.addTestSuite(TestRDFNet.class);
		suite.addTestSuite(TestNet.class);
		suite.addTestSuite(TestXMLNet.class);
		//suite.addTestSuite(TestRDFEntity.class);
		suite.addTestSuite(TestNode.class);
		suite.addTestSuite(TestVar.class);
		suite.addTestSuite(TestVector3.class);
		suite.addTestSuite(TestBuilder.class);
		suite.addTestSuite(TestCollections.class);
		suite.addTestSuite(TestTimed.class);
		//$JUnit-END$
		return suite;
	}

	
}
