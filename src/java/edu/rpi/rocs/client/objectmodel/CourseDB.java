package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.rpi.rocs.client.objectmodel.CourseDB;
import edu.rpi.rocs.client.objectmodel.CrossListing;

/**
 * Stores all information about courses across multiple semesters.
 * 
 * @author elsajg
 * @author ewpatton
 *
 */
public class CourseDB implements Serializable {
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
    protected int counter;
    
    /**
     * Empty constructor to satisfy Serializable interface
     */
    public CourseDB() {
    	counter = 0;
    	timestamp = -1;
    	semesterId = -1;
    	semesterdesc = "";
    	courses = new HashMap<String, Course>();
    	crosslistings = new HashMap<Integer, CrossListing>();
    }
    
    /**
     * Custom constructor to populate fields
     * 
     * @param aTimeStamp Time the database was generated
     * @param aSemesterNumber Semester number in YYYYMM format
     * @param aSemesterDesc Written description for this semester
     */
    public CourseDB(int aTimeStamp, int aSemesterNumber, String aSemesterDesc){
    	counter = 0;
        timestamp = aTimeStamp;
        semesterId = aSemesterNumber;
        semesterdesc = aSemesterDesc;
        courses = new HashMap<String, Course>();
        crosslistings = new HashMap<Integer, CrossListing>();
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
}
