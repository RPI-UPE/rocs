package edu.rpi.rocs.server.objectmodel;

import edu.rpi.rocs.client.objectmodel.Schedule;

public class ScheduleImpl extends Schedule implements DatabasePersistenceObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9054185941185228657L;

	public ScheduleImpl() {
		super();
	}
	
	public ScheduleImpl(Schedule other) {
		super(other);
	}
	
	public boolean loadFromDatabase() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean writeToDatabase() {
		// TODO Auto-generated method stub
		return false;
	}

}
