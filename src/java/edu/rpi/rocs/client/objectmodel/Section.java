package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.HashSet;

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
    protected String number;
    protected int students;
    protected int seats;
    protected ArrayList<Period> periods;
    protected ArrayList<String> notes;
    protected Course parent;
    protected CrossListing cross;

    /**
     * Default constructor needed for Serializable
     */
    public Section() {
    	periods = new ArrayList<Period>();
    	notes = new ArrayList<String>();
    	cross = null;
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
    public String getNumber(){
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
    public boolean isClosed(){
    	if(cross!=null) {
    		if(cross.isClosed()) return true;
    	}
        return (students >= seats);
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


    public void setCRN(int newValue){
        crn = newValue;
    }

    public void setNumber(String newValue){
        number = newValue;
    }

    public void setStudents(int newValue){
        students = newValue;
    }

    public void setSeats(int newValue){
        seats = newValue;
    }

    public void addPeriod(Period p) {
    	periods.add(p);
    }

    public void removePeriod(Period p) {
    	periods.remove(p);
    }

    public void setParent(Course p) {
    	parent = p;
    }

    public void addNote(String s) {
    	notes.add(s);
    }

    public void clearNotes(){
    	notes.clear();
    }

    public void setCrossListing(CrossListing c) {
    	cross = c;
    }

    public void removeCrossListing() {
    	cross = null;
    }

    public CrossListing getCrossListing() {
    	return cross;
    }

    public String getCourseDescription() {
    	String result="";
    	if(isClosed()) {
    		result += "C";
    	}
    	else {
    		result += " ";
    	}
    	result += "  ";
    	result += parent.getDept();
    	result += "  ";
    	result += parent.getNum();
    	result += "  ";
    	result += parent.getName();
    	return result;
    }

	public HashSet<String> getProfessors() {
		HashSet<String> profs = new HashSet<String>();
		for(Period p : periods) {
			profs.add(p.getInstructor());
		}
		return profs;
	}
}
