//-S.Whitney

package test.objectModel;


import edu.rpi.rocs.server.objectmodel.CrossListingImpl;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Node;
import org.w3c.dom.Document;

import junit.framework.TestCase;
public class crossListingTest extends TestCase{
	
	String xmlLoc = "http://pattoe.stu.rpi.edu/rocs-portlet/sample.xml";
	ArrayList<CrossListingImpl> crossListings = new ArrayList<CrossListingImpl>();
	CrossListingImpl testObject;
	
	@Before
	public void setUp(){
		try{
			URL xml = new URL(xmlLoc);
			InputStream stream = xml.openStream();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document doc = db.parse(stream);
	    	if(doc.getDocumentElement().getNodeName() == "CourseDB") {
				for(Node n = doc.getDocumentElement().getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
					if(n.getNodeName()=="CrossListing") {
						CrossListingImpl c = new CrossListingImpl(n);
						crossListings.add(c);
					}//end if crosslisting
				}//end for
			}//end if coursedb
	    	else{fail("invalid XML file provided"); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end setup
	
	@Test
	public void testExistance(){
		try{
			testObject = crossListings.get(0);
			for(int i = 0; i < crossListings.size(); i++){
				assertFalse(testObject == null);
				testObject = crossListings.get(i);
			}
		} catch(NullPointerException e){
			fail("courseDB object not created" + e.toString());
		};
	}
	
	@Test
	public void testCRN(){
		testObject = crossListings.get(0);
		try{
			assertFalse("getCRNs isEmpty", (testObject.getCRNs()).isEmpty());
		} catch(NullPointerException e){
			fail("null pointer exception on getCRNs : " + e.toString());
		};
	
		ArrayList<Integer> temp = new ArrayList<Integer>();
	
		//testObject.addCRNToCrossListing(12345);
		temp.add(new Integer(12345));
		
		assertTrue("getCRNs(0) should be : " + 45307 +
				" actual : " + (testObject.getCRNs()).get(0),(testObject.getCRNs()).get(0) == 45307 );
		assertTrue("getCRNs(1) should be : " + 45308 +
				" actual : " + (testObject.getCRNs()).get(1),(testObject.getCRNs()).get(1) == 45308 );
	}//end crn tests

	@Test
	public void testSeats(){
		testObject = crossListings.get(0);
		try{
			assertTrue( testObject.getNumberOfSeats() == 0 );
		} catch(NullPointerException e){
			fail("null pointer exception on getNumberOfSeats : " + e.toString());
		};
		
		//testObject.setNumberOfSeats(100);
		assertEquals(0, testObject.getNumberOfSeats());
	}//end seat tests
	
	
}//end crossListing Test