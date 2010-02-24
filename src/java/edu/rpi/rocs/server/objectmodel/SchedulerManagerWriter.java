package edu.rpi.rocs.server.objectmodel;

import java.util.Map;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;

public class SchedulerManagerWriter implements DatabaseWriterVisitor<SchedulerManager> {

	private Session theSession=null;
	
	public void save(SchedulerManager object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
		CourseWriter cw = new CourseWriter();
		CourseStatusObjectWriter csow = new CourseStatusObjectWriter();
		cw.setSession(session);
		csow.setSession(session);
		Map<Course, CourseStatusObject> map = object.getCurrentCourses();
		for(Map.Entry<Course, CourseStatusObject> entry : map.entrySet()) {
			csow.visit(entry.getValue());
		}
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(SchedulerManager object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
