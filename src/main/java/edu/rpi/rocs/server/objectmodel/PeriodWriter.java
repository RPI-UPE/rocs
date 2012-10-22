package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.Period;

public class PeriodWriter implements DatabaseWriterVisitor<Period> {

	private Session theSession = null;
	
	public void save(Period object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(Period object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
