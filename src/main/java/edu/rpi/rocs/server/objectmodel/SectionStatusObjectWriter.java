package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.SectionStatusObject;

public class SectionStatusObjectWriter implements DatabaseWriterVisitor<SectionStatusObject> {

	private Session theSession = null;
	
	public void save(SectionStatusObject object, Session session) {
		// TODO Auto-generated method stub
		session.saveOrUpdate(object);
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		theSession = session;
	}

	public void visit(SectionStatusObject object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
