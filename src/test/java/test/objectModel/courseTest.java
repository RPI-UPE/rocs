//
//-S.Whitney

package test.objectModel;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import edu.rpi.rocs.client.objectmodel.Course;

public class courseTest extends TestCase{

	ArrayList<String> temp = new ArrayList<String>();
	
	Course testObject;

	@Before
	public void setUp(){
		temp.add("note1");
		testObject = new Course("name", "dept", 1234, 1, 4, "type",temp);
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
	public void testCredits(){
		try{
			assertFalse( testObject.getCredmax() == 0 );
		} catch(NullPointerException e){
			fail("null pointer exception on getCredmax : " + e.toString());
		};
		try{
			assertFalse( testObject.getCredmin() == 0 );
		} catch(NullPointerException e){
			fail("null pointer exception on getCredmin : " + e.toString());
		};
		
		//testObject.setCredmax(10);
		//testObject.setCredmin(20);
		
		assertEquals("credmax should be : " + "4" +
				"actual : " + testObject.getCredmax(),4, testObject.getCredmax());
		assertEquals("credmin should be : " + "1" +
				"actual : " + testObject.getCredmin(),1, testObject.getCredmin());
	}

	@Test
	public void testDept(){
		try{
			assertFalse( testObject.getDept() == null );
		} catch(NullPointerException e){
			fail("null pointer exception on getDept : " + e.toString());
		};
		
		//testObject.setDept("MATH");
		assertTrue("dept matches", (testObject.getDept()).equals("dept") );
	}

	@Test
	public void testName(){
		try{
			assertFalse( testObject.getName() == null );
		} catch(NullPointerException e){
			fail("null pointer exception on getName");
		};

		//testObject.setName("Calc 1");
		
		assertTrue("name matches", (testObject.getName()).equals("name") );
	}

	@Test
	public void testNotes(){		
		try{
			assertFalse( testObject.getNotes() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getNotes : " + e.toString());
		};
		
		//testObject.addNote("Sample Note");
		//temp.add("Sample Note");
		
		assertTrue("notes matches", ((testObject.getNotes()).get(0)).equals(temp.get(0)));
		
		//testObject.removeNote("Sample Note");
		//temp.remove("Sample Note");
		
		//assertTrue( (testObject.getNotes()).isEmpty());
	}	

	@Test
	public void testGradeType(){
		try{
			assertFalse( testObject.getGradetype() == null );
		} catch(NullPointerException e){
			fail("null pointer exception on getGradetype : " + e.toString());
		};
		
		//testObject.setGradetype("letter");
		assertTrue("type matches", (testObject.getGradetype()).equals("type") );
	}

}
