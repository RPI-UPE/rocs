package edu.rpi.rocs.client.filters.schedule;

import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;

/**
 * A schedule filter which removes any schedule which is over
 * the number of specified credits.
 * 
 * @author ewpatton
 *
 */
public class MaxCreditFilter implements ScheduleFilter {
	
	/**
	 * The unique identifier for serialization
	 */
	private static final long serialVersionUID = 2097081252969945097L;
	/**
	 * The credit limit imposed by this filter
	 */
	protected int maxcreds = Integer.MAX_VALUE;
	
	/**
	 * Default constructor sets the maximum number of credits to
	 * Integer.MAX_VALUE, effectively making it unlimited.
	 */
	public MaxCreditFilter() {
		maxcreds = Integer.MAX_VALUE;
	}
	
	/**
	 * Set the maximum limit to be max
	 * @param max The credit limit for this filter
	 */
	public MaxCreditFilter(int max) {
		maxcreds = max;
	}
	
	/**
	 * Returns the threshold for other classes
	 * @return The maximum number of credits
	 */
	public int getThreshold() {
		return maxcreds;
	}
	
	/**
	 * Sets the threshold after instantiation
	 * @param max The maximum number of credits for this filter
	 */
	public void setThreshold(int max) {
		maxcreds = max;
	}

	/**
	 * Checks whether a given schedule conforms to this filter's specifications or not.
	 * @return true if the schedule conforms, otherwise false
	 */
	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		if(schedule.getMaxCredits() <= maxcreds) return true;
		return false;
	}

	/**
	 * Tell the scheduling algorithm that if a schedule fails this test, no schedules
	 * should be generated which are based on it. This speeds up the algorithm by pruning
	 * the search space.
	 */
	public boolean shouldPruneTreeOnFailure() {
		return true;
	}

	public Widget getWidget() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDisplayTitle() {
		// TODO Auto-generated method stub
		return "Maximum Credit Filter";
	}

}
