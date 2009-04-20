//-S.Whitney

package test.objectModel;

import edu.rpi.rocs.server.objectmodel.PeriodImpl;
import edu.rpi.rocs.client.objectmodel.Time;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import junit.framework.TestCase;

public class periodTest extends TestCase{
	
	Time start, end;
	ArrayList<PeriodImpl> testObject = new ArrayList<PeriodImpl>();
	
	String xmlLoc = "http://pattoe.stu.rpi.edu/rocs-portlet/sample.xml";
	
	ArrayList<Integer> days = new ArrayList<Integer>();
	
	@Before
	public void setUp(){
		try{
			URL xml = new URL(xmlLoc);
			InputStream stream = xml.openStream();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	Document doc = db.parse(stream);
	    	Node root = doc.getDocumentElement();
	    	if(root.getNodeName() == "CourseDB") {
	    		for(Node c = root.getFirstChild(); c.getNextSibling() != null; c = c.getNextSibling()) {
	    			if(c.getNodeName() == "Course" && c.hasChildNodes()){
						for(Node s = c.getFirstChild(); s.getNextSibling() != null; s = s.getNextSibling()) {
							if(s.getNodeName() == "Section" && s.hasChildNodes()) {
								for(Node n = s.getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
									if(n.getNodeName() == "Period") {
										PeriodImpl p = new PeriodImpl(n);
										testObject.add(p);
									}//end if
								}//end for period nodes
							}//end if crosslisting
						}//end for section children nodes
	    			}//end if course
	    		}//end for course nodes
			}//end if coursedb
	    	else{fail("invalid XML file provided"); }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end setup
	
	@Test
	public void testExistance(){
		assertTrue(!testObject.isEmpty());
	}
	
	@Test
	public void testDays(){
		try{
			assertFalse((testObject.get(0)).getDays() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getDays : " + e.toString());
		};
		
		testObject.get(0).addDay(1);
		days.add(0);
		days.add(3);
		days.add(1);
		
		assertTrue("day(0) should be : " + "0" +
				" actual : " + (testObject.get(0).getDays()).get(0), (testObject.get(0).getDays()).get(0) == 0);
		assertTrue("day(1) should be : " + "3" +
				" actual : " + (testObject.get(0).getDays()).get(1), (testObject.get(0).getDays()).get(1) == 3);
		assertTrue("day(2) should be : " + "1" +
				" actual : " + (testObject.get(0).getDays()).get(2), (testObject.get(0).getDays()).get(2) == 1);
		
		testObject.get(0).removeDay(0);
    	days.remove(0);
    	
    	assertTrue("remove day. size should be : " + "2" +
    			" actual : " + (testObject.get(0).getDays()).size(),(testObject.get(0).getDays()).size() == 2);
    	
    	
	}//end day tests
	
	@Test
	public void testTime(){
			try{
				assertFalse(testObject.get(0).getStart() == null);
				assertFalse(testObject.get(0).getEnd() == null);
			} catch(NullPointerException e){};
			
			start = new Time("900");
			end = new Time("950");
			
			assertTrue("getStart hr actual : " + testObject.get(0).getStart().getHour() +
					" should be : " + start, testObject.get(0).getStart().getHour() == start.getHour());
			assertTrue("getStart min actual : " + testObject.get(0).getStart() +
					" should be : " + start, (testObject.get(0).getStart().getMinute()) == start.getMinute());
			
			assertTrue("getEnd hr actual : " + testObject.get(0).getEnd().getHour() +
					" should be : " + end, (testObject.get(0).getEnd().getHour()) == end.getHour());
			assertTrue("getEnd min actual : " + testObject.get(0).getEnd() +
					" should be : " + end, (testObject.get(0).getEnd().getMinute()) == end.getMinute());
	}//end time tests
	
	@Test
	public void testInstructor(){
		try{
			assertFalse(testObject.get(0).getInstructor() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getInstructor : " + e.toString());
		};
		
		assertTrue("instructor should be : " + "Hardwick" +
				" actual : " + testObject.get(0).getInstructor(), (testObject.get(0).getInstructor()).equals("Hardwick"));
	}//end instructor tests
	
	@Test
	public void testLoc(){
		try{
			assertFalse(testObject.get(0).getLocation() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getLocation : " + e.toString());
		};
		
		//testObject.setLocation("DCC 308");
		assertTrue("location actual : " + testObject.get(0).getLocation() +
				" should be : " + "TBA", (testObject.get(0).getLocation()).equals("TBA"));
	}//end loc tests
	
	@Test
	public void testType(){
		try{
			assertFalse(testObject.get(0).getType() == null);
		} catch(NullPointerException e){
			fail("null pointer exception on getType : " + e.toString());
		};
		
		//testObject.setType("Lecture");
		assertTrue("type actual : " + testObject.get(0).getType() +
				" should be : " + "LEC", (testObject.get(0).getType()).equals("LEC"));
	}//end type tests

}//end periodTest