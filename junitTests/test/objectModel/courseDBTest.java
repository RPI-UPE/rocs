//-S.Whitney

package test.objectModel;

import org.junit.Before;
import org.junit.Test;

import edu.rpi.rocs.client.objectmodel.*;
import junit.framework.TestCase;
import edu.rpi.rocs.server.objectmodel.*;

public class courseDBTest extends TestCase{
	
	//CourseDB	testObject;
	Semester testObject;
	String xmlLoc = "http://pattoe.stu.rpi.edu/rocs-portlet/sample.xml";
	
	@Before
	public void setUp(){
		try {
			SemsterParser.parse(xmlLoc);
			testObject = SemesterDB.getCurrentSemester();
			//CourseDBImpl.addCourseDB(xmlLoc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExistance(){
		try{
			assertFalse(testObject == null);
		} catch(NullPointerException e){
			fail("object not created" + e.toString());
		};
	}
	
	@Test
	public void testSemesters(){
		assertTrue("getSemesterDesc should be : " + "DTD Sample" +
				" actual : " + testObject.getSemesterDesc(),(testObject.getSemesterDesc()).equals("DTD Sample"));

		//TODO test setter functions (setSemesterDesc etc
		/*assertTrue("getSemesterDesc should be : " + "New Value" +
				" actual : " + testObject.getSemesterDesc(),(testObject.getSemesterDesc()).equals("New Value"));*/
		
		assertTrue("getSemesterId should be : " + "0" + 
				" actual : " + testObject.getSemesterId(), testObject.getSemesterId() == 0);
		
		assertTrue("getTimeStamp should be : " + "1238421894" +
				" actual : " + testObject.getTimeStamp(), testObject.getTimeStamp() == 1238421894);

	}
	
	@Test
	public void testTimeStamp(){
		try{
			assertFalse( testObject.getTimeStamp() == 0 );
		} catch(NullPointerException e){
			fail("null pointer exception on getTimeStamp : " + e.toString());
		};
		
		//testObject.setTimeStamp(12345);
		assertTrue("getTimeStamp should be : " + "1238421894" +
				" actual : " + testObject.getTimeStamp(), testObject.getTimeStamp() == 1238421894);
	}

	
}//end courseDB tests