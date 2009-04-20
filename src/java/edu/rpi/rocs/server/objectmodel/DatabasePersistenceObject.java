package edu.rpi.rocs.server.objectmodel;

public interface DatabasePersistenceObject {
	public boolean writeToDatabase();
	public boolean loadFromDatabase();
}
