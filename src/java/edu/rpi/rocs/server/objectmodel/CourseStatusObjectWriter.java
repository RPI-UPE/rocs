package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.CourseStatusObject;

public class CourseStatusObjectWriter implements DatabaseWriterVisitor<CourseStatusObject> {

	private Session theSession = null;
	
	public void save(CourseStatusObject object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(CourseStatusObject object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
