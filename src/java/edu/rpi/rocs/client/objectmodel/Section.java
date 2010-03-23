package edu.rpi.rocs.client.objectmodel;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
    protected int crn=0;
    protected String number=null;
    protected int students=0;
    protected int seats=0;
    protected ArrayList<Period> periods=null;
    protected ArrayList<String> notes=null;
    protected Course parent=null;
    protected CrossListing cross=null;
    
    @Override
    public void updateMajorRevision() {
    	super.updateMajorRevision();
    	if(parent!=null && parent.getMajorRevision()!=getMajorRevision()) parent.updateMajorRevision();
    	if(cross!=null && cross.getMajorRevision()!=getMajorRevision()) cross.updateMajorRevision();
    }

    public void updateMinorRevision(boolean b) {
    	super.updateMinorRevision();
    	if(parent!=null && parent.getMinorRevision()!=getMinorRevision()) {
    		parent.updateMinorRevision();
    	}
    	else if(parent==null) {
    		System.out.println("Parent null for section "+crn);
    	}
    }
    
    @Override
    public void updateMinorRevision() {
    	super.updateMinorRevision();
    	if(parent!=null && parent.getMinorRevision()!=getMinorRevision()) {
    		parent.updateMinorRevision();
    	}
    	else if(parent==null) {
    		System.out.println("Parent null for section "+crn);
    	}
    	if(cross!=null && cross.getMinorRevision()!=getMinorRevision()) {
    		cross.updateMinorRevision(true);
    	}
    	else if(cross==null) {
    		System.out.println("Crosslisting null for section "+crn);
    	}
    }

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
    public int getCrn(){
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
    public List<Period> getPeriods() {
    	return new ArrayList<Period>(periods);
    }
    
    public void setPeriods(List<Period> list) {
    	periods = new ArrayList<Period>();
    	Iterator<Period> i = list.iterator();
    	while(i.hasNext()) {
    		Period p = i.next();
    		if(p!=null) periods.add(p);
    		else System.out.println("Warning: found a period in section "+crn+" that is null.");
    	}
    }

    public List<String> getNotes() {
    	return new ArrayList<String>(notes);
    }
    
    public void setNotes(List<String> list) {
    	notes = new ArrayList<String>(list);
    }

    public Course getParent() {
    	return parent;
    }

    public void setCrn(int newValue){
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
	
	private Long dbid=null;
	
	public Long getDbid() {
		return dbid;
	}
	
	public void setDbid(Long id) {
		if(dbid!=null) System.out.println("Setting dbid to "+id+" from "+dbid);
		dbid = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Section) {
			Section s = (Section)o;
			return s.crn == this.crn;
		}
		return false;
	}

	public void examineNewVersion(Section t) {
		if(students != t.students) {
			System.out.println("Updated CRN "+crn+" from students = "+students+" to students = "+t.students);
			students = t.students;
			updateMinorRevision();
		}
		if(seats != t.seats){
			System.out.println("Updated CRN "+crn+" from students = "+seats+" to students = "+t.seats);
			seats = t.seats;
			updateMinorRevision();
		}
		if(!number.equals(t.number)) {
			number = t.number;
			updateMajorRevision();
		}
		for(Period p : periods) {
			int pos=-1;
			if((pos=t.periods.lastIndexOf(p))>=0) {
				p.examineNewVersion(t.periods.get(pos));
				if(p.outOfDateMajor()) {
					updateMajorRevision();
				}
				else if(p.outOfDateMinor()) {
					updateMinorRevision();
				}
			}
			else {
				p.delete();
				updateMajorRevision();
			}
		}
		for(Period p : t.periods) {
			if(!periods.contains(p)) {
				periods.add(p);
				updateMajorRevision();
			}
		}
		for(String s : notes) {
			if(!t.notes.contains(s)) {
				updateMajorRevision();
				notes = t.notes;
				break;
			}
		}
		for(String s : t.notes) {
			if(!notes.contains(s)) {
				updateMajorRevision();
				notes = t.notes;
				break;
			}
		}
	}
}
