package edu.rpi.rocs.client.services.updatemanager;

import edu.rpi.rocs.client.objectmodel.MajorMinorRevisionObject;

public class UpdateItem
{

	private MajorMinorRevisionObject object;
	private boolean isCourse, isMajor;

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