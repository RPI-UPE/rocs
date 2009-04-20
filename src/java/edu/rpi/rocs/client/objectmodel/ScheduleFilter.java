package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

public interface ScheduleFilter extends Serializable {
	public boolean doesScheduleSatisfyFilter(Schedule schedule);
	public boolean shouldPruneTreeOnFailure();
}
