//-S.Whitney

package test.objectModelTests;

import edu.rpi.rocs.client.objectmodel.*;
import junit.framework.TestCase;

public class majorMinorTest extends TestCase{

	MajorMinorRevisionObject testObject = new MajorMinorRevisionObject();

	public void testCurrent(){
		try{
			assertFalse(testObject.getCurrentRevision() == null);
		} catch(NullPointerException e){};
		
		testObject.setCurrentRevision(123);
		assertTrue(testObject.getCurrentRevision() == 123);
	}//end current revision tests
	
	public void testMajor(){
		assertTrue(testObject.getMajorRevision() == null);
		
		testObject.setMajorRevision(456);
		assertTrue(testObject.getMajorRevision() == 456);
	}//end major revision tests
	
	public void testMinor(){
		assertTrue(testObject.getMinorRevision() == null);
		
		testObject.setMinorRevision(789);
		assertTrue(testObject.getMinorRevision() == 789);
	}//end minor revision tests

	
}//end majorMinor tests