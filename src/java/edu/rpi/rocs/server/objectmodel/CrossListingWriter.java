package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;

import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Section;

public class CrossListingWriter implements DatabaseWriterVisitor<CrossListing> {

	private Session theSession = null;
	
	public void save(CrossListing object, Session session) {
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

	public void visit(CrossListing object) {
		// TODO Auto-generated method stub
		save(object, theSession);
	}

}
