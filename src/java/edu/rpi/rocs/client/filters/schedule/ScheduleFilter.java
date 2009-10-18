package edu.rpi.rocs.client.filters.schedule;

import java.io.Serializable;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.Instantiable;
import edu.rpi.rocs.client.objectmodel.Schedule;

public interface ScheduleFilter extends Serializable, Instantiable {
	public abstract boolean doesScheduleSatisfyFilter(Schedule schedule);
	public abstract boolean shouldPruneTreeOnFailure();
	public abstract Widget getWidget();
	public abstract String getDisplayTitle();
	public abstract void addChangeHandler(ChangeHandler e);
	public abstract void removeChangeHandler(ChangeHandler e);
}
