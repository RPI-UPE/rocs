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
	protected Long timestamp;
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
    	timestamp = -1L;
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
    public Semester(long aTimeStamp, int aSemesterNumber, String aSemesterDesc, String aChangeTime){
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
    public long getTimeStamp() {
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

    public void setTimeStamp(long newValue){
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
    	List<Section> sections = course.getSections();
    	for(Section s : sections) {
    		crnmap.remove(new Integer(s.getCrn()));
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
    	List<Section> sections = course.getSections();
    	for(Section s : sections) {
    		crnmap.put(new Integer(s.getCrn()), s);
    	}
    }
    
    public Course getCourse(String id) {
    	return courses.get(id);
    }

	public int compareTo(Semester o) {
		// This will sort semesters in reverse order, i.e., newest semester first.
		return o.getSemesterId() - semesterId.intValue();
	}
	
	public void setCourses(List<Course> list) {
		courses = new HashMap<String, Course>();
		for(Course c : list) {
			courses.put(c.getDept() + Integer.toString(c.getNum()), c);
		}
	}
	
	public void setCrossListings(List<CrossListing> list) {
		crosslistings = new HashMap<Integer, CrossListing>();
		for(CrossListing c : list) {
			if(c==null) {
				System.out.println("Found null CrossListing in semester.");
				continue;
			}
			crosslistings.put(c.getUID(), c);
		}
	}
	
	private Long dbid;
	public Long getDbid() {
		return dbid;
	}
	public void setDbid(Long id) {
		dbid = id;
	}

	public void setLastChangeTime(String changeTime) {
		lastChangeTime = changeTime;
	}

	public void clearCrossListings() {
		crosslistings.clear();
	}
	
	public void examineNewVersion(Semester parsedSemester) {
		for(Course c : getCourses()) {
			Course d = parsedSemester.getCourse(c.getId());
			if(d==null) c.delete();
			else {
				c.examineNewVersion(d);
			}
		}
		for(Course c : parsedSemester.getCourses()) {
			if(null == getCourse(c.getId())) {
				addCourse(c);
			}
		}
		List<CrossListing> parsed = parsedSemester.getCrossListings();
		List<CrossListing> mine = getCrossListings();
		for(CrossListing c : mine) {
			if(!parsed.contains(c)) {
				c.delete();
			}
		}
		for(CrossListing c : parsed) {
			int pos = -1;
			if((pos = mine.lastIndexOf(c))>=0) {
				CrossListing m = mine.get(pos);
				if(m.getNumberOfSeats()!=c.getNumberOfSeats()) {
					m.setNumberOfSeats(c.getNumberOfSeats());
				}
			}
			else {
				c.setSemester(this);
				c.processCRNs();
				addCrosslisting(c);
				for(Section s : c.getSections()) {
					s.updateMinorRevision();
				}
			}
		}
	}
}
