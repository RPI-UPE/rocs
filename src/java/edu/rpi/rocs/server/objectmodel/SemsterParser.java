package edu.rpi.rocs.server.objectmodel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

/**
 * Course Database Implementation on the server side. Extends the implementation for the client side.
 * 
 * @author ewpatton
 *
 */
public class SemsterParser {
    /**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = -6328488261276368411L;
	

    public static void parse(String xmlFile) throws Exception {
    	//XML File should get parsed, key should
    	//be the semesterID from the XML file
    	Long oldrev = MajorMinorRevisionObject.getCurrentRevision();
    	MajorMinorRevisionObject.setCurrentRevision(System.currentTimeMillis()/1000);
    	Semester parsedSemester;
    	try {
    		parsedSemester = SemsterParser.LoadCourseDB(xmlFile);
    		SemesterDB.putInstance(parsedSemester.getSemesterId(), parsedSemester);
    	}
    	catch(Exception e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		throw e;
    	}
    }

    static private Semester LoadCourseDB(String path) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(new URL(path));
    }
    
    static private Semester LoadCourseDB(URL path) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(path.openStream());
    }
    
    static private Semester LoadCourseDB(InputStream stream) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(stream);
    	Semester semester=null;
    	if(doc.getDocumentElement().getNodeName() == "CourseDB") {
    		int time,num;
    		String desc;
    		time = Integer.parseInt(doc.getDocumentElement().getAttribute("timestamp"));
    		num = Integer.parseInt(doc.getDocumentElement().getAttribute("semesternumber"));
    		desc = doc.getDocumentElement().getAttribute("semesterdesc");
    		semester = new Semester(time, num, desc);
    		for(Node n = doc.getDocumentElement().getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
    			if(n.getNodeName().equalsIgnoreCase("CrossListing")) {
    				CrossListing c = CrossListingParser.parse(n);
    				semester.addCrosslisting(c);
    			}
    			else if(n.getNodeName().equalsIgnoreCase("Course")) {
    				Course c = CourseParser.parse(n);
    				semester.addCourse(c);
    			}
    			else if(n.getNodeName()=="#text") {
    				// Do nothing
    			}
    			else
    				throw new InvalidCourseDatabaseException("CourseDB contains node <" + n.getNodeName() + "> that is not a Course or CrossListing.");
    		}
    	}
    	else {
    		throw new InvalidCourseDatabaseException("Document does not contain a course database.");
    	}
    	return semester;
    }


}
