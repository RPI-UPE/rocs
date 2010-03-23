package edu.rpi.rocs.client.services.updatemanager;

import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;

public class UpdateItem
{

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