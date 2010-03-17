package edu.rpi.rocs.client.objectmodel;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CrossListing class which stores information about multiple classes if they 
 * are crosslisted in SIS.
 * 
 * @author ewpatton
 *
 */
public class CrossListing extends MajorMinorRevisionObject {
	/**
	 * UID for Serializable interface
	 */
	private static final long serialVersionUID = 3406898058852760862L;
	
	/** Protected members set by @see edu.rpi.rocs.server.objectmodel.CrossListing */
	protected ArrayList<Integer> crns;
	protected ArrayList<Section> sections;
	protected boolean processed=false;
	protected int numberOfSeats;
	protected int uid;
	protected Semester semester;
	
	/**
	 * Default constructor
	 */
	public CrossListing() {
		crns = new ArrayList<Integer>();
		sections = new ArrayList<Section>();
	}
	
	/**
	 * Gets the list of CRNs for this cross listing.
	 * 
	 * @return An array of CRNs
	 */
	public List<Integer> getCRNs() {
		return new ArrayList<Integer>(crns);
	}
	
	/**
	 * Gets the number of seats in this crosslisting
	 * 
	 * @return Total number of seats
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	
	public ArrayList<Integer> getCrns() {
		return crns;
	}

	public void addCrn(Integer crn) {
		crns.add(crn);
		updateMajorRevision();
		processed=false;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	/**
	 * Gets the unique identifier of this cross listing
	 * 
	 * @return Unique Identifier
	 */
	public int getUID() {
		return uid;
	}
	
	/**
	 * Adds a CRN to a cross listing.
	 * 
	 * @param crn The CRN to be added.
	 */
	public void addCrn(int crn) {
		addCrn(new Integer(crn));
		processed=false;
	}
	
	/**
	 * Remove a CRN from a cross listing.
	 * 
	 * @param crn The CRN to be removed.
	 */
	public void removeCRNFromCrossListing(int crn) {
		crns.remove(new Integer(crn));
		updateMajorRevision();
		processed=false;
	}
	
	/**
	 * Sets the unique identifier of this cross listing.
	 * 
	 * @param id New Unique Identifier
	 */
	public void setUID(int id) {
		uid = id;
	}
	
	public void processCRNs() {
		for(Section s : sections)
			s.removeCrossListing();
		sections.clear();
		for(int i : crns) {
			Section s = semester.getSectionByCRN(i);
			if(s != null) {
				s.setCrossListing(this);
				sections.add(s);
			}
		}
		processed = true;
	}
	
	public void setSemester(Semester s) {
		semester = s;
	}
	
	public Semester getSemester() {
		return semester;
	}
	
	public boolean isClosed() {
		if(!processed) processCRNs();
		int filledSeats = 0;
		for(Section s : sections) {
			filledSeats += s.getStudents();
		}
		return (filledSeats >= numberOfSeats);
	}
	
	public void setSections(List<Section> list) {
		sections = new ArrayList<Section>();
		Iterator<Section> i = list.iterator();
		while(i.hasNext()) {
			Section s = i.next();
			if(s!=null) sections.add(s);
			else System.out.println("CrossListing "+dbid+ " has a null section.");
		}
	}
	
	public List<Section> getSections() {
		return new ArrayList<Section>(sections);
	}
	
	public void setCrns(List<Integer> list) {
		crns = new ArrayList<Integer>(list);
	}
	
	private Long dbid;
	public void setDbid(Long id) {
		dbid = id;
	}
	public Long getDbid() {
		return dbid;
	}

	public int getNumberOfStudents() {
		int count = 0;
		for(Section s : sections) {
			count += s.getStudents();
		}
		return count;
	}
	
	public boolean equals(Object o) {
		if(o instanceof CrossListing) {
			CrossListing x = (CrossListing)o;
			List<Section> c = x.getSections();
			for(Section s : sections) {
				if(!c.contains(s)) return false;
			}
			return true;
		}
		else return false;
	}
}
