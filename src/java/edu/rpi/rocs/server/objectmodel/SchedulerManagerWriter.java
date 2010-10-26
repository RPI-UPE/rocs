package edu.rpi.rocs.server.objectmodel;

import java.util.Map;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.SchedulerManager;
import edu.rpi.rocs.client.objectmodel.CourseStatusObject;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.SectionStatusObject;

public class SchedulerManagerWriter implements DatabaseWriterVisitor<SchedulerManager> {

	private Session theSession=null;
	
	public void save(SchedulerManager object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
		CourseWriter cw = new CourseWriter();
		CourseStatusObjectWriter csow = new CourseStatusObjectWriter();
		SectionStatusObjectWriter ssow = new SectionStatusObjectWriter();
		cw.setSession(session);
		csow.setSession(session);
		ssow.setSession(session);
		Map<Course, CourseStatusObject> map = object.getCurrentCourses();
		Map<Section, SectionStatusObject> map2 = object.getCurrentSections();
		for(Map.Entry<Section, SectionStatusObject> entry : map2.entrySet()) {
			ssow.visit(entry.getValue());
		}
		for(Map.Entry<Course, CourseStatusObject> entry : map.entrySet()) {
			csow.visit(entry.getValue());
		}
		ScheduleWriter sw = new ScheduleWriter();
		sw.setSession(session);
		sw.visit(object.getCurrentSchedule());
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
