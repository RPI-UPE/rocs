package edu.rpi.rocs.objectmodel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import edu.rpi.rocs.exceptions.InvalidCourseDatabaseException;

import java.util.Map;

public class CourseDB {
//coursedb is an object and return an instance of it
    
    //class variables
    private int timestamp;
    private int semesternumber;
    private String semesterdesc;
    private HashMap<String, Course> courses;
    private HashMap<Integer, CrossListing> crosslistings;
    static private CourseDB latest=null;
    private int counter;
    
    private static final Map<Integer, CourseDB> semesters =
    	new HashMap<Integer, CourseDB>();
    
    public static void addCourseDB(String xmlFile) throws InvalidCourseDatabaseException, SAXException, ParserConfigurationException, IOException {
    	//XML File should get parsed, key should
    	//be the semesterID from the XML file
   		CourseDB newdb = CourseDB.LoadCourseDB(xmlFile);
   		if(latest == null) latest = newdb;
   		else if(latest.getSemesterNum() < newdb.getSemesterNum()) latest = newdb;
   		semesters.put(new Integer(newdb.semesternumber), newdb);
    }
    
    public static CourseDB getInstance(Integer semesterId) {
    	return semesters.get(semesterId);
    }
    
    public static Collection<CourseDB> getSemesterList() {
    	//Make a copy of the semester list since returning
    	//semesters.values() would provide a list that
    	//would allow outside classes to directly modify
    	//the backing map
    	Collection<CourseDB> semesterList = new ArrayList<CourseDB>();
    	for(CourseDB current : semesters.values()) {
    		semesterList.add(current);
    	}
    	return semesterList;
    }
    
    //accessor functions
    public CourseDB(int aTimeStamp, int aSemesterNumber, String aSemesterDesc){
    	counter = 0;
        timestamp = aTimeStamp;
        semesternumber = aSemesterNumber;
        semesterdesc = aSemesterDesc;
        courses = new HashMap<String, Course>();
        crosslistings = new HashMap<Integer, CrossListing>();
    }
    
    static public CourseDB LoadCourseDB(String path) throws MalformedURLException, IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	return LoadCourseDB(new URL(path));
    }
    
    static public CourseDB LoadCourseDB(URL path) throws IOException, ParserConfigurationException, SAXException, InvalidCourseDatabaseException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	Document doc = db.parse(path.openStream());
    	CourseDB database=null;
    	if(doc.getDocumentElement().getNodeName() == "CourseDB") {
    		int time,num;
    		String desc;
    		time = Integer.parseInt(doc.getDocumentElement().getAttribute("timestamp"));
    		num = Integer.parseInt(doc.getDocumentElement().getAttribute("semesternumber"));
    		desc = doc.getDocumentElement().getAttribute("semesterdesc");
    		database = new CourseDB(time, num, desc);
    		for(Node n = doc.getDocumentElement().getFirstChild(); n.getNextSibling() != null; n = n.getNextSibling()) {
    			if(n.getNodeName()=="CrossListing") {
    				CrossListing c = new CrossListing(n);
    				database.addCrosslisting(c);
    			}
    			else if(n.getNodeName()=="Course") {
    				Course c = new Course(n);
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
    
    public int getTimeStamp(){
        return timestamp;
    }
    
    public void setSemesterNum(int newValue){
        semesternumber = newValue;
    }
    
    public int getSemesterNum(){
        return semesternumber;
    }
    
    public void setSemesterDesc(String newValue){
        semesterdesc = newValue;
    }
    
    public String getSemesterDesc(){
        return semesterdesc;
    }
    
    public void addCourse(Course course) {
    	courses.put(course.getDept() + Integer.toString(course.getNum()), course);
    }
    
    public Collection<Course> getCourses() {
    	return courses.values();
    }
    
    public void removeCourse(Course course) {
    	courses.remove(course.getDept() + Integer.toString(course.getNum()));
    }
    
    public Integer addCrosslisting(CrossListing c) {
    	c.setUID(counter);
    	crosslistings.put(new Integer(counter), c);
    	counter++;
    	return new Integer(c.getUID());
    }
    
    public void removeCrosslisting(Integer id) {
    	crosslistings.remove(id);
    }
}
