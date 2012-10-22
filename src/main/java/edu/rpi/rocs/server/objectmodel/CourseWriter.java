package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.Section;

public class CourseWriter implements DatabaseWriterVisitor<Course> {

	private Session theSession=null;
	
	public void save(Course object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
		SectionWriter sw = new SectionWriter();
		sw.setSession(session);
		for(Section s : object.getSections()) {
			sw.visit(s);
		}
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(Course object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
