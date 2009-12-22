package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Stores all information about courses across multiple semesters.
 * 
 * @author elsajg
 * @author ewpatton
 *
 */
public class Semester implements Serializable, Comparable<Semester> {
	/**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = 8117446154042777168L;

	/** Protected values set by @see edu.rpi.rocs.server.objectmodel.CourseDBImpl */
	protected Integer timestamp;
    protected Integer semesterId;
    protected String semesterdesc;
    protected HashMap<String, Course> courses;
    protected HashMap<Integer, CrossListing> crosslistings;
    protected HashMap<Integer, Section> crnmap = new HashMap<Integer, Section>();
    protected int counter;
    protected String lastChangeTime;
    
    /**
     * Empty constructor to satisfy Serializable interface
     */
    public Semester() {
    	counter = 0;
    	timestamp = -1;
    	semesterId = -1;
    	semesterdesc = "";
    	courses = new HashMap<String, Course>();
    	crosslistings = new HashMap<Integer, CrossListing>();
    	lastChangeTime = "";
    }
    
    /**
     * Custom constructor to populate fields
     * 
     * @param aTimeStamp Time the database was generated
     * @param aSemesterNumber Semester number in YYYYMM format
     * @param aSemesterDesc Written description for this semester
     */
    public Semester(int aTimeStamp, int aSemesterNumber, String aSemesterDesc, String aChangeTime){
    	counter = 0;
        timestamp = aTimeStamp;
        semesterId = aSemesterNumber;
        semesterdesc = aSemesterDesc;
        courses = new HashMap<String, Course>();
        crosslistings = new HashMap<Integer, CrossListing>();
        lastChangeTime = aChangeTime;
    }
    
    public String getLastChangeTime() {
    	return lastChangeTime;
    }
    
    /**
     * Gets the Unix time when the database was generated.
     * 
     * @return Number of seconds since Jan 1 1970
     */
    public int getTimeStamp(){
        return timestamp;
    }
    
    /**
     * Gets the semester ID. Should be along the lines of 200709 or 200801
     * 
     * @return The semester identifier
     */
    public int getSemesterId(){
        return semesterId;
    }
    
    /**
     * Gets the written semester description. Examples: "Fall 2009", "Spring 2010"
     * 
     * @return The semester description
     */
    public String getSemesterDesc(){
        return semesterdesc;
    }
    
    /**
     * Gets a list of all courses in this database.
     * 
     * @return Course list
     */
    public List<Course> getCourses() {
    	return new ArrayList<Course>(courses.values());
    }
    
    /**
     * Gets a list of all crosslistings in this database.
     * 
     * @return List of cross listings
     */
    public List<CrossListing> getCrossListings() {
    	return new ArrayList<CrossListing>(crosslistings.values());
    }
    
    public Section getSectionByCRN(int crn) {
    	return getSectionByCRN(new Integer(crn));
    }
    
    public Section getSectionByCRN(Integer crn) {
    	return crnmap.get(crn);
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
    	ArrayList<Section> sections = course.getSections();
    	for(Section s : sections) {
    		crnmap.remove(new Integer(s.getCRN()));
    	}
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
    
    public void addCourse(Course course) {
    	courses.put(course.getDept() + Integer.toString(course.getNum()), course);
    	ArrayList<Section> sections = course.getSections();
    	for(Section s : sections) {
    		crnmap.put(new Integer(s.getCRN()), s);
    	}
    }

	public int compareTo(Semester o) {
		// This will sort semesters in reverse order, i.e., newest semester first.
		return o.getSemesterId() - semesterId.intValue();
	}
}
