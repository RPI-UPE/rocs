package edu.rpi.rocs.server.objectmodel;

import org.hibernate.Session;


public interface DatabaseWriterVisitor<T> extends Visitor<T> {
	public void save(T object, Session session);
	public void setSession(Session session);
}
