package edu.rpi.rocs.client.objectmodel;

public class MinCreditFilter implements ScheduleFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -682106636533161302L;
	protected int mincreds = 0;
	
	public MinCreditFilter() {
		mincreds = 0;
	}
	
	public MinCreditFilter(int creds) {
		mincreds = creds;
	}
	
	public int getThreshold() {
		return mincreds;
	}
	
	public void setThreshold(int creds) {
		mincreds = creds;
	}
	
	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		if(schedule.getCredits() >= mincreds) return true;
		return false;
	}

	public boolean shouldPruneTreeOnFailure() {
		return false;
	}
}
