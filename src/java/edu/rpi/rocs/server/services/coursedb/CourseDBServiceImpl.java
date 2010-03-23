package edu.rpi.rocs.server.services.coursedb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.Scheduler;
import edu.rpi.rocs.client.objectmodel.Course;
import edu.rpi.rocs.client.objectmodel.CrossListing;
import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.server.objectmodel.SemesterDB;
import edu.rpi.rocs.client.services.updatemanager.UpdateItem;

public class CourseDBServiceImpl extends RemoteServiceServlet implements
		edu.rpi.rocs.client.services.coursedb.CourseDBService {

	//private static Logger LOG = Logger.getLogger(CourseDBServiceImpl.class);

	public void init() throws ServletException {
			super.init();
			/*
			LOG.debug("Creating courseDB service");
			try {
				SemesterParser.parse("http://wineagent.tw.rpi.edu/rocs-portlet/current.xml");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException();
			}
			*/
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -1198710711253036931L;

	// Update every 5 minutes
	public ArrayList<UpdateItem> getUpdateList(Integer semesterID, Long timeStamp)
	{
		Scheduler.getInstance().getLogger().info("User requested updates for "+semesterID+" at timestamp "+timeStamp);
		ArrayList<UpdateItem> retVal = new ArrayList<UpdateItem>();
		long curRev = timeStamp.longValue();

		Semester newSemester = SemesterDB.getInstance(semesterID);
		Scheduler.getInstance().getLogger().debug("latest = "+newSemester.getTimeStamp()+"; old = "+timeStamp);
		if (newSemester.getTimeStamp() > curRev) // TimeStamp diff!
		{
			System.out.println("User has outdated semester.");
			for (Course C : newSemester.getCourses())
			{
				if (C.getMajorRevision() > curRev) retVal.add(new UpdateItem(C, true, true));
				else if (C.getMinorRevision() > curRev) retVal.add(new UpdateItem(C, true, false));
			}
			for (CrossListing CL : newSemester.getCrossListings())
			{
				if (CL.getMajorRevision() > curRev) retVal.add(new UpdateItem(CL, false, true));
				else if (CL.getMinorRevision() > curRev) retVal.add(new UpdateItem(CL, false, false));
			}
		}
		Scheduler.getInstance().getLogger().debug("Sending back "+retVal.size()+" updates.");

		return retVal;
	}

	public Semester getSemesterData(Integer semesterId) {
		return SemesterDB.getInstance(semesterId);
	}

	public List<SemesterDescription> getSemesterList() {
		return SemesterDB.getSemesterList();
	}

	public SemesterDescription getCurrentSemester() {
		Semester semester = SemesterDB.getCurrentSemester();
		if(semester == null) {
			return new SemesterDescription(-1,"[Warning] No Course Database Loaded");
		}
		return new SemesterDescription(semester.getSemesterId(),
				semester.getSemesterDesc());
	}

	public String getUserName(String userid) {
		Map<?, ?> info = Scheduler.getInstance().getUserInfo(userid);
		String first = (String)info.get("user.name.given");
		String last = (String)info.get("user.name.family");
		return first + " " + last;
	}

}
