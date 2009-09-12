package edu.rpi.rocs.server.services.coursedb;

import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.rpi.rocs.client.objectmodel.Semester;
import edu.rpi.rocs.client.objectmodel.SemesterDescription;
import edu.rpi.rocs.server.objectmodel.SemesterDB;
import edu.rpi.rocs.server.objectmodel.SemsterParser;

public class CourseDBServiceImpl extends RemoteServiceServlet implements
		edu.rpi.rocs.client.services.coursedb.CourseDBService {
	
	private static Logger LOG = Logger.getLogger(CourseDBServiceImpl.class);
	
	public void init() throws ServletException {
			super.init();
			LOG.debug("Creating courseDB service");
			try {
				SemsterParser.parse("http://wineagent.tw.rpi.edu/rocs-portlet/current.xml");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServletException();
			}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198710711253036931L;

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

}
