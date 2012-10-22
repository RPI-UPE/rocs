//-S.Whitney

package test.objectModel;

import edu.rpi.rocs.client.objectmodel.*;
import junit.framework.TestCase;

public class majorMinorTest extends TestCase{

	private MajorMinorRevisionObject testObject = new MajorMinorRevisionObject();

	public void testCurrent(){
		try{
			assertFalse(MajorMinorRevisionObject.getCurrentRevision() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getCurrentRevision : " + e.toString());
		};
		
		MajorMinorRevisionObject.setCurrentRevision(123);
		assertTrue(MajorMinorRevisionObject.getCurrentRevision() == 123);
	}//end current revision tests
	
	public void testMajor(){
		try{
			assertTrue(testObject.getMajorRevision() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getMajorRevision : " + e.toString());
		};
		
		testObject.setMajorRevision((long) 456);
		assertTrue("matches getMajorRevision", testObject.getMajorRevision() == 456);
	}//end major revision tests
	
	public void testMinor(){
		try{
			assertTrue(testObject.getMinorRevision() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getMinorRevision : " + e.toString());
		};
		
		testObject.setMinorRevision((long)789);
		assertTrue("matches getMinorRevision", testObject.getMinorRevision() == 789);
	}//end minor revision tests

	
}//end majorMinor tests