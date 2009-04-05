//-S.Whitney

package test.objectModelTests;

import edu.rpi.rocs.client.objectmodel.*;
import junit.framework.TestCase;

public class courseDBTest extends TestCase{

	CourseDB testObject = new CourseDB(0,0,"");

	public void testSemesters(){
		testObject.setSemesterDesc("winter");
		assertTrue( (testObject.getSemesterDesc()).equals("winter"));
		
		testObject.setSemesterId(1);
		assertTrue( testObject.getSemesterId() == 1);
		
		testObject.setTimeStamp(12345);
		assertTrue( testObject.getTimeStamp() == 12345);
	}
	
	public void testTimeStamp(){
		try{
			assertTrue( testObject.getTimeStamp() == 0 );
		} catch(NullPointerException e){};
		
		testObject.setTimeStamp(12345);
		assertTrue( testObject.getTimeStamp() == 12345);
	}

	
}//end courseDB tests