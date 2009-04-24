package edu.rpi.rocs.client.ui.entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import edu.rpi.rocs.client.ui.coursesearch.CourseResultList;
import edu.rpi.rocs.client.ui.coursesearch.CourseSearchPanel;
import edu.rpi.rocs.client.ui.semesterselect.SemesterSelectionPanel;

public class MainEntryPoint implements EntryPoint {

	public void onModuleLoad() {
		Log.trace("Module loading");
		RootPanel.get("semesterSelect").add(SemesterSelectionPanel.getInstance());
		RootPanel.get("searchFilterPanel").add(CourseSearchPanel.getInstance());
		RootPanel.get("searchResultPanel").add(CourseResultList.getInstance());
	}

}
