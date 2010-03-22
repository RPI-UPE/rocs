package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Semester;

public class SemesterWriter implements DatabaseWriterVisitor<Semester> {

	private Session theSession=null;
	
	public void save(Semester object, Session session) {
		CourseWriter cw = new CourseWriter();
		cw.setSession(session);
		for(Course c : object.getCourses()) {
			cw.visit(c);
		}
		CrossListingWriter clw = new CrossListingWriter();
		clw.setSession(session);
		for(CrossListing c : object.getCrossListings()) {
			clw.visit(c);
		}
		session.saveOrUpdate(object);
	}

	public void setSession(Session session) {
		theSession = session;
	}

	public void visit(Semester object) {
		save(object, theSession);
	}

}
