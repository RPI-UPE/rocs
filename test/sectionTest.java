//-S.Whitney

import edu.rpi.rocs.objectmodel.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class sectionTest extends TestCase{

	Section testObject = new Section();

	public void testCRN(){
		assertTrue(testObject.getCRN() == 0);
		
		testObject.setCRN(12345);
		assertTrue(testObject.getCRN() == 12345);
	}//end crn tests
	
	public void testNum(){
		assertTrue(testObject.getNum() == 0);
		
		testObject.setNum(123);
		assertTrue((testObject.getNum()) == 123);
	}//end num tests
	
	public void testStudents(){
		assertTrue(testObject.getStudents() == 0);
		
		testObject.setStudents(456);
		assertTrue((testObject.getStudents()) == 456);	
	}//end students tests
	
	public void testSeats(){
		assertTrue(testObject.getSeats() == 0);
		
		testObject.setSeats(789);
		assertTrue((testObject.getSeats()) == 789);	
	}//end seats tests
	
	public void testClosed(){
		assertTrue(testObject.getClosed() == false);
		
		testObject.setClosed(true);
		assertTrue(testObject.getClosed() == true);
	}
	
	public static Test suite(){
		return new TestSuite(sectionTest.class);
	}
	
}//end sectionTests