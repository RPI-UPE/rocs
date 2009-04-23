package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;

/**
 * Describes a particular course section.
 * 
 * @author elsajg
 * @author ewpatton
 *
 */
public class Section extends MajorMinorRevisionObject {
    
    /**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = 4374452349065230201L;
	
	/** Protected members populated by @see edu.rpi.rocs.server.objectmodel.SectionImpl */
    protected int crn;
    protected int number;
    protected int students;
    protected int seats;
    protected boolean closed;
    protected ArrayList<Period> periods;
    protected ArrayList<String> notes;
    protected Course parent;
    
    /**
     * Default constructor needed for Serializable
     */
    public Section() {
    	periods = new ArrayList<Period>();
    	notes = new ArrayList<String>();
    }
    
    /**
     * Gets the CRN identifier for this section.
     * 
     * @return CRN
     */
    public int getCRN(){
        return crn;
    }
    
    /**
     * Gets the section number within its parent course.
     * 
     * @return Section number
     */
    public int getNumber(){
        return number;
    }
    
    /**
     * Gets the number of students in this section.
     * 
     * @return Number of registered students
     */
    public int getStudents(){
        return students;
    }
    
    /**
     * Gets the total number of seats in this section
     * 
     * @return Total number of seats
     */
    public int getSeats(){
        return seats;
    }
    
    /**
     * Gets whether the course is closed or not.
     * 
     * @return Closed flag
     */
    public boolean getClosed(){
        return closed;
    }
    
    /**
     * Gets a list of periods when this section meets.
     * 
     * @return List of periods
     */
    public ArrayList<Period> getPeriods() {
    	return new ArrayList<Period>(periods);
    }
    
    public ArrayList<String> getNotes() {
    	return new ArrayList<String>(notes);
    }
    
    public Course getParent() {
    	return parent;
    }
}
