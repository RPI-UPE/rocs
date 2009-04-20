package edu.rpi.rocs.client.objectmodel;

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
		if(schedule.getCredits() <= maxcreds) return true;
		return false;
	}

	public boolean shouldPruneTreeOnFailure() {
		return true;
	}

}
