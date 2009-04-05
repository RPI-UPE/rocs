package test.objectModelTests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class objectModelTestSuite {
	
	public static Test suite(){
		
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite( courseDBTest.class );
		suite.addTestSuite( courseTest.class );
		suite.addTestSuite( crossListingTest.class );
		suite.addTestSuite( majorMinorTest.class );
		suite.addTestSuite( periodTest.class );
		suite.addTestSuite( sectionTest.class );
		
		return suite;
	}//end objectModelTest
}//end objectModelTestSuite
