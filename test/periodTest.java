//-S.Whitney

import edu.rpi.rocs.objectmodel.*;
import java.util.ArrayList;
import junit.framework.TestCase;
import java.util.Date;

public class periodTest extends TestCase{

	ArrayList<Integer> temp = new ArrayList<Integer>();
	
	Date a = new Date();
	
	Period testObject = new Period("", "", temp, a, a, "");
	
	public void testDays(){
		try{
			assertFalse(testObject.getDays() == null);
		} catch(NullPointerException e){};
		
		//test *(int)
		testObject.addDay(1);
		temp.add(1);
		
		assertTrue((testObject.getDays()).get(0) == temp.get(0));
		
		testObject.removeDay(0);
    	temp.remove(0);
    	
		assertTrue((testObject.getDays()).isEmpty() == temp.isEmpty());
		
		//test *(Integer)
		testObject.addDay(new Integer(1));
		temp.add(new Integer(1));
		
		assertTrue((testObject.getDays()).get(0) == temp.get(0));
		
		testObject.removeDay(new Integer(0));
		temp.remove(new Integer(0));
		
		assertTrue((testObject.getDays()).get(0) == temp.get(0));
    	
	}//end day tests
	
	public void testTime(){
		try{
			assertFalse(testObject.getStart() == null);
			assertFalse(testObject.getEnd() == null);
		} catch(NullPointerException e){};
		
		Date start = new Date(1000000);
		Date end = new Date(9999999);
		
		testObject.setStart(start);
		testObject.setEnd(end);
		
		assertTrue((testObject.getStart()).equals(start));
		assertTrue((testObject.getEnd()).equals(end));
		
		assertTrue((testObject.getStart()).before(testObject.getEnd()));
	}//end time tests
	
	public void testInstructor(){
		try{
			assertFalse(testObject.getInstructor() == null);
		} catch(NullPointerException e){};
		
		testObject.setInstructor("steve");
		assertTrue((testObject.getInstructor()).equals("steve"));
	}//end instructor tests
	
	public void testLoc(){
		try{
			assertFalse(testObject.getLocation() == null);
		} catch(NullPointerException e){};
		
		testObject.setLocation("DCC 308");
		assertTrue((testObject.getLocation()).equals("DCC 308"));
	}//end loc tests
	
	public void testType(){
		try{
			assertFalse(testObject.getType() == null);
		} catch(NullPointerException e){};
		
		testObject.setType("Lecture");
		assertTrue((testObject.getType()).equals("Lecture"));
	}//end type tests

}//end periodTest