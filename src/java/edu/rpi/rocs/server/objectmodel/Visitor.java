package edu.rpi.rocs.server.objectmodel;

public interface Visitor<T> {
	public void visit(T object);
}
