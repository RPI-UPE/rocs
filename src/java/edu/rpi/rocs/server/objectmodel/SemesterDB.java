package edu.rpi.rocs.server.objectmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.server.hibernate.util.HibernateUtil;

public class SemesterDB {
	/** The latest course database by semester */
	static private Semester latest=null;
    
	/** All loaded semesters */
    private static final Map<Integer, Semester> semesters =
    	new HashMap<Integer, Semester>();
    
    public static Semester getInstance(Integer semesterId) {
    	return semesters.get(semesterId);
    }
    
    protected static void putInstance(Integer semesterId, Semester semester) {
    	if ((latest == null) || (semester.getSemesterId() > latest.getSemesterId()))
    		latest = semester;
    	semesters.put(semesterId, semester);
    }
    
    public static List<SemesterDescription> getSemesterList() {
    	List<SemesterDescription> semesterList = new ArrayList<SemesterDescription>();
    	for(Semester current : semesters.values()) {
    		semesterList.add(
    				new SemesterDescription(current.getSemesterId(),
    						current.getSemesterDesc()));
    	}
    	Collections.sort(semesterList);
    	return semesterList;
    }
    
    public static Semester getCurrentSemester() {
    	Integer max = -1;
    	for( Integer curr : semesters.keySet()) {
    		if (curr > max)
    			max = curr;
    	}
    	if (max != -1)
    		return semesters.get(max);
    	else
    		return null;
    }
    
	@SuppressWarnings("unchecked")
	public static void restoreSemesters() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List<Semester> x = session.createQuery("from Semester").list();
		for(Semester s : x) {
			System.out.println("Loading semester \""+s.getSemesterDesc()+"\"");
			semesters.put(s.getSemesterId(), s);
			touch(s);
		}
		
		session.getTransaction().commit();
	}

	private static void touch(Semester s) {
		for(Course c : s.getCourses()) {
			touch(c);
		}
		for(CrossListing cl : s.getCrossListings()) {
			cl.setSemester(s);
			touch(cl);
		}
	}

	private static void touch(CrossListing cl) {
		cl.processCRNs();
		for(Section s : cl.getSections()) {
			s.setCrossListing(cl);
			touch(s);
		}
	}

	private static void touch(Course c) {
		for(Section s : c.getSections()) {
			s.setParent(c);
			touch(s);
		}
	}

	private static void touch(Section s) {
		for(Period p : s.getPeriods()) {
			touch(p);
		}
	}

	private static void touch(Period p) {
		p.getDays();
	}
}
