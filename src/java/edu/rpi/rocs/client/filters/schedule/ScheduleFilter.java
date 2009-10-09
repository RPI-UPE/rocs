package edu.rpi.rocs.client.filters.schedule;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;

public interface ScheduleFilter extends Serializable {
	public boolean doesScheduleSatisfyFilter(Schedule schedule);
	public boolean shouldPruneTreeOnFailure();
	public Widget getWidget();
	public String getDisplayTitle();
}
