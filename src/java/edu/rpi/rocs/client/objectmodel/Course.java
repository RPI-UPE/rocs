package edu.rpi.rocs.client.objectmodel;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Describes a course object to the GWT client.
 *
 * @author elsajg
 * @author ewpatton
 *
 */
public class Course extends MajorMinorRevisionObject implements Comparable<Course> {

	public static class CourseComparator implements Comparator<Course> {
		public final int compare(Course a, Course b) {
			int result;
			if(a==null&&b==null) return 0;
			if(b==null) return 1;
			if(a==null) return -1;
			result = a.getDept().compareTo(b.getDept());
			if(result!=0) return result;
			return a.getNum()-b.getNum();
		}
	}

    /**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = -4156341852068456290L;

	/** Protected data set by @see edu.rpi.rocs.server.objectmodel.CourseImpl */
    protected String name=null;
    protected String dept=null;
    protected int num=0;
    protected int credmin=0;
    protected int credmax=0;
    protected String gradetype=null;
    protected ArrayList<String> notes=null;
    protected ArrayList<Section> sections=null;

    /**
     * Empty constructor needed by interface Serializable
     */
    public Course() {
    	name = "";
    	dept = "";
    	num = 0;
    	credmin = 0;
    	credmax = 0;
    	gradetype= "";
    	notes = new ArrayList<String>();
    	sections = new ArrayList<Section>();
    }

    /**
     * Custom constructor to populate every field
     *
     * @param aName Written name of the course
     * @param aDept Department identifier
     * @param aNumber Course number
     * @param aCredMin Credit minimum
     * @param aCredMax Credit maximum
     * @param aGradeType Grade type used by this course
     * @param someNotes Any plain-text notes for end users
     */
    public Course(String aName, String aDept, int aNumber, int aCredMin, int aCredMax, String aGradeType, ArrayList<String> someNotes){
        name = aName;
        dept = aDept;
        num = aNumber;
        credmin = aCredMin;
        credmax = aCredMax;
        gradetype = aGradeType;
        notes = someNotes;
        if(someNotes == null)
        	notes = new ArrayList<String>();
        sections = new ArrayList<Section>();
    }

    /**
     * Gets the name of this course
     *
     * @return The course name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the department this course is offered in.
     *
     * @return The department ID
     */
    public String getDept(){
        return dept;
    }

    /**
     * Gets the course number for this course
     *
     * @return The course number
     */
    public int getNum(){
        return num;
    }

    /**
     * Gets the minimum number of credits this course can be
     *
     * @return Credit minimum
     */
    public int getCredmin(){
        return credmin;
    }

    /**
     * Gets the maximum number of credits this course can be
     *
     * @return Credit maximum
     */
    public int getCredmax(){
        return credmax;
    }

    /**
     * Gets the grade type of this course
     *
     * @return The grade type
     */
    public String getGradetype(){
        return gradetype;
    }

    /**
     * Gets any notes added to this course.
     *
     * @return The course notes
     */
    public ArrayList<String> getNotes() {
    	return new ArrayList<String>(notes);
    }

    /**
     * Gets a list of sections in this course
     *
     * @return Course sections
     */
    public List<Section> getSections() {
    	return new ArrayList<Section>(sections);
    }

	public int getLevel() {
		return num / 1000;
	}

	   //accesssor functions
    public void setName(String newValue){
        name = newValue;
        updateMajorRevision();
    }

    public void setDept(String newValue){
        dept = newValue;
        updateMajorRevision();
    }

    public void setNum(int newValue){
        num = newValue;
        updateMajorRevision();
    }

    public void setCredmin(int newValue){
        credmin = newValue;
        updateMajorRevision();
    }

    public void setCredmax(int newValue){
        credmax = newValue;
        updateMajorRevision();
    }

    public void setGradetype(String newValue){
        gradetype = newValue;
        updateMajorRevision();
    }

    public void addNote(String newValue) {
    	notes.add(newValue);
    	updateMajorRevision();
    }

    public void removeNote(String note) {
    	notes.remove(note);
    	updateMajorRevision();
    }

    public void addSection(Section s) {
    	sections.add(s);
    	s.setParent(this);
    	updateMajorRevision();
    }

    public void removeSection(Section s) {
    	sections.remove(s);
    	updateMajorRevision();
    }

    public boolean isClosed()
    {
    	for(Section s : sections) if(!s.isClosed()) return false;
    	return true;
    }

    public String getListDescription() {
    	String result="&nbsp;&nbsp;";
    	result += dept;
    	result += "&nbsp;&nbsp;";
    	result += num;
    	result += "&nbsp;&nbsp;";
    	result += this.getFilledSeats()+"/"+this.getTotalSeats();
    	result += "&nbsp;&nbsp;";
    	result += name;
    	int size = 40 - name.length();
    	size = (size > 1 ? size : 1);
    	for(int i=0;i<size;i++) {
    		result += "&nbsp;";
    	}
    	result += getProfessors();
    	return result;
    }

	private String getProfessors() {
		HashSet<String> profs = new HashSet<String>();
		for(Section s : sections) {
			HashSet<String> subprofs = s.getProfessors();
			profs.addAll(subprofs);
		}
		profs.remove("** TBA **");
		if(profs.size()==0) {
			return "TBA";
		}
		profs.remove("Staff");
		if(profs.size()==0) {
			return "Staff";
		}
		boolean first=true;
		String result = "";
		for(String s : profs) {
			if(first) {
				result = s;
				first = false;
			}
			else {
				result += ", " + s;
			}
		}
		return result;
	}

	public int compareTo(Course o) {
		int res = dept.compareTo(o.dept);
		if(res!=0) return res;
		return num-o.num;
	}
	
	private Long dbid=null;
	
	public Long getDbid() {
		return dbid;
	}
	
	public void setDbid(Long id) {
		dbid = id;
	}
	
	public void setSections(List<Section> list) {
		sections = new ArrayList<Section>();
		Iterator<Section> i = list.iterator();
		boolean wasnull=false;
		while(i.hasNext()) {
			Section s = i.next();
			if(s!=null) sections.add(s);
			else {
				wasnull=true;
				//System.out.println("Course " + getId() + " has a null section.");
			}
		}
//		if(wasnull) Scheduler.getInstance().getLogger().debug("Course " + getId() + " has " + sections.size() + " sections.");
	}

	public int getFilledSeats() {
		int count = 0;
		for(Section s : sections) {
			if(s.getCrossListing()!=null) {
				int cldiff = s.getCrossListing().getNumberOfSeats()-s.getCrossListing().getNumberOfStudents();
				int sdiff = s.getSeats()-s.getStudents();
				if(cldiff<sdiff) {
					count += s.getCrossListing().getNumberOfStudents();
				}
				else {
					count += s.getStudents();
				}
			}
			else {
				count += s.getStudents();
			}
		}
		return count;
	}

	public int getTotalSeats() {
		int count = 0;
		for(Section s : sections) {
			if(s.getCrossListing()!=null) {
				int cldiff = s.getCrossListing().getNumberOfSeats()-s.getCrossListing().getNumberOfStudents();
				int sdiff = s.getSeats()-s.getStudents();
				if(cldiff<sdiff) {
					count += s.getCrossListing().getNumberOfSeats();
				}
				else {
					count += s.getSeats();
				}
			}
			else {
				count += s.getSeats();
			}
		}
		return count;
	}

	public String getInstructorString() {
		return getProfessors();
	}

	public String getId() {
		return dept+Integer.toString(num);
	}

	public void examineNewVersion(Course new_course) {
		if(!new_course.getName().equals(name)) {
			setName(new_course.getName());
		}
		else if(new_course.getNum() != num) {
			setNum(new_course.getNum());
		}
		else if(!new_course.getDept().equals(dept)) {
			setDept(new_course.getDept());
		}
		else if(new_course.getCredmax() != credmax) {
			setCredmax(new_course.getCredmax());
		}
		else if(new_course.getCredmin() != credmin) {
			setCredmin(new_course.getCredmin());
		}
		else if(!new_course.getGradetype().equals(gradetype)) {
			setGradetype(new_course.getGradetype());
		}
		List<Section> others = new_course.getSections();
		for(Section s : sections) {
			int pos=-1;
			if((pos=others.lastIndexOf(s))>=0) {
				Section t = others.get(pos);
				//System.out.println("Examining old section...");
				s.examineNewVersion(t);
			}
			else {
				//System.out.println("Deleting old section...");
				s.delete();
				updateMajorRevision();
			}
		}
		for(Section s : others) {
			if(!sections.contains(s)) {
				//System.out.println("Found new section...");
				sections.add(s);
				s.setParent(this);
				updateMajorRevision();
			}
		}
		ArrayList<String> temp = new_course.getNotes();
		ArrayList<String> copy = new ArrayList<String>(notes);
		for(String s : notes) {
			if(!temp.contains(s)) {
				copy.remove(s);
				updateMajorRevision();
			}
		}
		for(String s : temp) {
			if(!copy.contains(s)) {
				copy.add(s);
				updateMajorRevision();
			}
		}
	}
	
	@Override
	public void delete() {
		super.delete();
		this.setMajorRevision(Long.MAX_VALUE);
		this.setMinorRevision(Long.MAX_VALUE);
		for(Section s : sections) {
			s.delete();
		}
	}
	
	@Override
	public void updateMinorRevision() {
		super.updateMinorRevision();
//		Scheduler.getInstance().getLogger().debug("Course "+getId()+" has a minor revision.");
	}
}
