package edu.rpi.rocs.client.services.updatemanager;

import java.io.Serializable;

import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;

public class UpdateItem implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6955888856152468185L;
	private MajorMinorRevisionObject object=null;
	private boolean isCourse=false, isMajor=false;
	
	// Empty constructor for GWT
	public UpdateItem() {
		
	}

	public UpdateItem(MajorMinorRevisionObject obj, boolean isCrs, boolean isMjr)
	{
		object = obj;
		isCourse = isCrs;
		isMajor = isMjr;
	}

	public MajorMinorRevisionObject getObject()
	{
		return object;
	}

	public boolean isCourse()
	{
		return isCourse;
	}

	public boolean isMajor()
	{
		return isMajor;
	}

}