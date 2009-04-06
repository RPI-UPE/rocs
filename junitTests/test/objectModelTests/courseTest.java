//-S.Whitney

package test.objectModelTests;

import java.util.ArrayList;
import junit.framework.TestCase;

import edu.rpi.rocs.client.objectmodel.Course;

public class courseTest extends TestCase{

	ArrayList<String> temp = new ArrayList<String>();
	
	Course testObject = new Course("", "", 0, 0, 0, "",temp);
	
	public void testCredits(){
		try{
			assertTrue( testObject.getCredmax() == 0 );
		} catch(NullPointerException e){};
		try{
			assertTrue( testObject.getCredmin() == 0 );
		} catch(NullPointerException e){};
		
		testObject.setCredmax(10);
		testObject.setCredmin(20);
		
		assertEquals(10, testObject.getCredmax());
		assertEquals(20, testObject.getCredmin());
	}
	
	public void testDept(){
		try{
			assertFalse( testObject.getDept() == null );
		} catch(NullPointerException e){};
		
		testObject.setDept("MATH");
		assertTrue( (testObject.getDept()).equals("MATH") );
	}
	
	public void testName(){
		try{
			assertFalse( testObject.getName() == null );
		} catch(NullPointerException e){};

		testObject.setName("Calc 1");
		
		assertTrue( (testObject.getName()).equals("Calc 1") );
	}
	
	public void testNotes(){		
		try{
			assertFalse( testObject.getNotes() == null);
		} catch(NullPointerException e){};
		
		testObject.addNote("Sample Note");
		temp.add("Sample Note");
		
		assertTrue( ((testObject.getNotes()).get(1)).equals(temp.get(1)));
		
		testObject.removeNote("Sample Note");
		temp.remove("Sample Note");
		
		assertTrue( (testObject.getNotes()).isEmpty());
	}
	
	public void testGradeType(){
		try{
			assertFalse( testObject.getGradetype() == null );
		} catch(NullPointerException e){};
		
		testObject.setGradetype("letter");
		assertTrue( (testObject.getGradetype()).equals("letter") );
	}


}//end course tests