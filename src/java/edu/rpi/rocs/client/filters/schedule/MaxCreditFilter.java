package edu.rpi.rocs.client.filters.schedule;

import edu.rpi.rocs.client.objectmodel.Schedule;

public class MaxCreditFilter implements ScheduleFilter {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2097081252969945097L;
	protected int maxcreds = Integer.MAX_VALUE;
	
	public MaxCreditFilter() {
		maxcreds = Integer.MAX_VALUE;
	}
	
	public MaxCreditFilter(int max) {
		maxcreds = max;
	}
	
	public int getThreshold() {
		return maxcreds;
	}
	
	public void setThreshold(int max) {
		maxcreds = max;
	}

	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		if(schedule.getMaxCredits() <= maxcreds) return true;
		return false;
	}

	public boolean shouldPruneTreeOnFailure() {
		return true;
	}

}
