package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;

public class ScheduleWriter implements DatabaseWriterVisitor<Schedule> {

	private Session theSession=null;
	
	public void save(Schedule object, Session session) {
		// TODO Auto-generated method stub
		SectionWriter sw = new SectionWriter();
		sw.setSession(session);
		for(Section s : object.getSections()) {
			sw.visit(s);
		}
		SchedulerManagerWriter smw = new SchedulerManagerWriter();
		smw.setSession(session);
		smw.visit(object.getManager());
		session.save(object);
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(Schedule object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
