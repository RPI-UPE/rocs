package edu.rpi.rocs.server.objectmodel;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;
import edu.rpi.rocs.server.hibernate.util.HibernateUtil;

/**
 * Course Database Implementation on the server side. Extends the implementation for the client side.
 * 
 * @author ewpatton
 *
 */
public class SemesterParser {
    /**
	 * UID for Serializable interface
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -6328488261276368411L;
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SemesterParser.class);
	
	/**
	 * Parses an XML file from the server and uses the specified change time
	 * for computing ordering of changes.
	 * 
	 * @param xmlFile Absolute URL to an XML document containing a semester description, e\.g\. http://example\.com/semester\.xml
	 * @param changeTime The LastModified date provided in the HTTP headers from the server
	 */
    public static void parse(String xmlFile, String changeTime) {
    	//XML File should get parsed, key should
    	//be the semesterID from the XML file
    	Long oldrev = MajorMinorRevisionObject.getCurrentRevision();
    	MajorMinorRevisionObject.setCurrentRevision(System.currentTimeMillis()/1000);
    	Semester parsedSemester;
    	try {
    		long start = System.currentTimeMillis();
    		parsedSemester = SemesterParser.LoadCourseDB(xmlFile, changeTime);
    		log.info("Time to parse semester XML: " + (System.currentTimeMillis()-start) + " ms");
    		
    		start = System.currentTimeMillis();
    		Semester lastSemester = SemesterDB.getInstance(parsedSemester.getSemesterId());
    		if(lastSemester != null) {
    			lastSemester.examineNewVersion(parsedSemester);
    		}
    		log.info("Time to merge semesters: "+(System.currentTimeMillis()-start)+" ms");

    		if(lastSemester==null)
    			SemesterDB.putInstance(parsedSemester.getSemesterId(), parsedSemester);
    		else
    			lastSemester.setLastChangeTime(changeTime);

    		start = System.currentTimeMillis();
    		Session session=null;
    		Transaction tx=null;
    		try {
    			session = HibernateUtil.getSessionFactory().getCurrentSession();
	    		tx = session.beginTransaction();
	    		tx.begin();
	    		SemesterWriter sw = new SemesterWriter();
	    		sw.setSession(session);
	    		if(lastSemester==null)
	    			sw.visit(parsedSemester);
	    		else
	    			sw.visit(lastSemester);
	    		tx.commit();
    		}
    		catch(Exception ex) {
    			ex.printStackTrace();
    			if(tx!=null) {
    				tx.rollback();
    			}
    		}
    		finally {
    			if(session.isOpen()) session.close();
    		}
    		log.info("Time to commit transaction: "+(System.currentTimeMillis()-start)+" ms");
    	}
    	catch(InvalidCourseDatabaseException e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		log.warn("Invalid semester database", e);
    	}
    	catch(SAXException e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		log.warn("Invalid XML document supplied by server", e);
    	}
    	catch(ParserConfigurationException e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		log.warn("XML parser was incorrectly configured", e);
    	}
    	catch(IOException e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		log.warn("Unable to complete HTTP request with server", e);
    	}
    }

    static private Semester LoadCourseDB(String path, String changeTime) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(new URL(path), changeTime);
    }
    
    static private Semester LoadCourseDB(URL path, String changeTime) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(path.openStream(), changeTime);
    }
    
    static private Semester LoadCourseDB(InputStream stream, String changeTime) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	Long start = System.currentTimeMillis();
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(stream);
    	log.info("Time to retrieve document from SIS: "+(System.currentTimeMillis()-start)+" ms");
    	Semester semester=null;
    	if(doc.getDocumentElement().getNodeName() == "CourseDB") {
    		int time,num;
    		String desc;
    		start = System.currentTimeMillis();
    		time = Integer.parseInt(doc.getDocumentElement().getAttribute("timestamp"));
    		num = Integer.parseInt(doc.getDocumentElement().getAttribute("semesternumber"));
    		desc = doc.getDocumentElement().getAttribute("semesterdesc");
    		semester = new Semester(time, num, desc, changeTime);
    		for(Node n = doc.getDocumentElement().getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
    			if(n.getNodeName().equalsIgnoreCase("CrossListing")) {
    				CrossListing c = CrossListingParser.parse(n);
    				c.setSemester(semester);
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
    		log.info("Time to process document: " + (System.currentTimeMillis()-start)+" ms");
    	}
    	else {
    		throw new InvalidCourseDatabaseException("Document does not contain a course database.");
    	}
    	if(semester!=null) {
    		start = System.currentTimeMillis();
    		List<CrossListing> cls = semester.getCrossListings();
    		for(CrossListing cl : cls) {
    			cl.setSemester(semester);
    			cl.processCRNs();
    		}
    		log.info("Time to prepare crosslistings: "+(System.currentTimeMillis()-start)+" ms");
    	}
    	return semester;
    }


}
