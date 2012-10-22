package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Section;

public class SectionWriter implements DatabaseWriterVisitor<Section> {

	private Session theSession=null;
	private Logger log = LoggerFactory.getLogger(SectionWriter.class);
	
	public void save(Section object, Session session) {
		log.debug("Saving section '"+object.getNumber()+"' for course "+object.getParent().getDept()+"-"+object.getParent().getNum());
		PeriodWriter pw = new PeriodWriter();
		pw.setSession(session);
		for(Period p : object.getPeriods()) {
			pw.visit(p);
		}
		session.saveOrUpdate(object);
	}

	public void setSession(Session session) {
		theSession = session;
	}

	public void visit(Section object) {
		save(object, theSession);
	}

}
