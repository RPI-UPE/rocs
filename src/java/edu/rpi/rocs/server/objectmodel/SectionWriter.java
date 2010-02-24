package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Section;

public class SectionWriter implements DatabaseWriterVisitor<Section> {

	private Session theSession=null;
	
	public void save(Section object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
		PeriodWriter pw = new PeriodWriter();
		pw.setSession(session);
		for(Period p : object.getPeriods()) {
			pw.visit(p);
		}
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(Section object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
