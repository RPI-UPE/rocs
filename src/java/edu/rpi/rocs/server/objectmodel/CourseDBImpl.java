package edu.rpi.rocs.server.objectmodel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

/**
 * Course Database Implementation on the server side. Extends the implementation for the client side.
 * 
 * @author ewpatton
 *
 */
public class CourseDBImpl extends edu.rpi.rocs.client.objectmodel.CourseDB {
    /**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = -6328488261276368411L;

	/** The latest course database by semester */
	static private CourseDB latest=null;
    
	/** All loaded semesters */
    private static final Map<Integer, CourseDB> semesters =
    	new HashMap<Integer, CourseDB>();

    /**
     * Constructor
     * 
     * @param timeStamp Unix time when the database was generated
     * @param semesterNumber Semester identifier
     * @param semesterDesc Human-readable description for semester
     * @deprecated Use {@link #addCourseDB(String)} instead
     */
	public CourseDBImpl(int timeStamp, int semesterNumber, String semesterDesc) {
		super(timeStamp, semesterNumber, semesterDesc);
		// TODO Auto-generated constructor stub
	}

    public static void addCourseDB(String xmlFile) throws Exception {
    	//XML File should get parsed, key should
    	//be the semesterID from the XML file
    	Long oldrev = MajorMinorRevisionObject.getCurrentRevision();
    	MajorMinorRevisionObject.setCurrentRevision(System.currentTimeMillis()/1000);
    	CourseDB newdb;
    	try {
    		newdb = CourseDBImpl.LoadCourseDB(xmlFile);
    	}
    	catch(Exception e) {
    		MajorMinorRevisionObject.setCurrentRevision(oldrev);
    		throw e;
    	}
   		if(latest == null) latest = newdb;
   		else if(latest.getSemesterId() < newdb.getSemesterId()) latest = newdb;
   		semesters.put(new Integer(newdb.getSemesterId()), newdb);
    }
    
    public static CourseDB getInstance(Integer semesterId) {
    	return semesters.get(semesterId);
    }
    
    public static List<SemesterDescription> getSemesterList() {
    	List<SemesterDescription> semesterList = new ArrayList<SemesterDescription>();
    	for(CourseDB current : semesters.values()) {
    		semesterList.add(
    				new SemesterDescription(current.getSemesterId(),
    						current.getSemesterDesc()));
    	}
    	return semesterList;
    }
    
    public static CourseDB getCurrentSemester() {
    	Integer max = -1;
    	for( Integer curr : semesters.keySet()) {
    		if (curr > max)
    			max = curr;
    	}
    	return semesters.get(max);
    }
    
    public void addCourse(Course course) {
    	courses.put(course.getDept() + Integer.toString(course.getNum()), course);
    }

    static public CourseDB LoadCourseDB(String path) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(new URL(path));
    }
    
    static public CourseDB LoadCourseDB(URL path) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(path.openStream());
    	CourseDBImpl database=null;
    	if(doc.getDocumentElement().getNodeName() == "CourseDB") {
    		int time,num;
    		String desc;
    		time = Integer.parseInt(doc.getDocumentElement().getAttribute("timestamp"));
    		num = Integer.parseInt(doc.getDocumentElement().getAttribute("semesternumber"));
    		desc = doc.getDocumentElement().getAttribute("semesterdesc");
    		database = new CourseDBImpl(time, num, desc);
    		for(Node n = doc.getDocumentElement().getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
    			if(n.getNodeName()=="CrossListing") {
    				CrossListingImpl c = new CrossListingImpl(n);
    				database.addCrosslisting(c);
    			}
    			else if(n.getNodeName()=="Course") {
    				Course c = new CourseImpl(n);
    				database.addCourse(c);
    			}
    			else if(n.getNodeName()=="#text") {
    				// Do nothing
    			}
    			else
    				throw new InvalidCourseDatabaseException("CourseDB contains node that is not a Course or CrossListing.");
    		}
    	}
    	else {
    		throw new InvalidCourseDatabaseException("Document does not contain a course database.");
    	}
    	return database;
    }

    public void setTimeStamp(int newValue){
        timestamp = newValue;
    }
    
    public void setSemesterId(int newValue){
        semesterId = newValue;
    }
    
    public void setSemesterDesc(String newValue){
        semesterdesc = newValue;
    }
    
    public void removeCourse(Course course) {
    	courses.remove(course.getDept() + Integer.toString(course.getNum()));
    }
    
    public Integer addCrosslisting(CrossListingImpl c) {
    	c.setUID(counter);
    	crosslistings.put(new Integer(counter), c);
    	counter++;
    	return new Integer(c.getUID());
    }
    
    public void removeCrosslisting(Integer id) {
    	crosslistings.remove(id);
    }
}
