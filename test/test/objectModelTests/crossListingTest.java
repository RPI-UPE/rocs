//-S.Whitney

package test.objectModelTests;

import edu.rpi.rocs.client.objectmodel.*;
import java.util.ArrayList;
import junit.framework.TestCase;

public class crossListingTest extends TestCase{

	CrossListing testObject = new CrossListing();

	public void testCRN(){
		assertTrue( (testObject.getCRNs()).isEmpty());
	
		ArrayList<Integer> temp = new ArrayList<Integer>();
	
		testObject.addCRNToCrossListing(12345);
		temp.add(new Integer(12345));
		
		assertTrue((testObject.getCRNs()).get(0) == 12345 );
		assertEquals(temp.get(0), (testObject.getCRNs()).get(0) );
	}//end crn tests
	
	public void testSeats(){
		assertTrue( testObject.getNumberOfSeats() == 0 );
		
		testObject.setNumberOfSeats(100);
		assertEquals(100, testObject.getNumberOfSeats());
		
		testObject.setNumberOfSeats(new Integer(500));
		assertEquals(500, testObject.getNumberOfSeats());
	}//end seat tests
	
	
}//end crossListing Test