package edu.rpi.rocs.client.filters.schedule;

import java.util.HashSet;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.ScheduleFilterManager;
import edu.rpi.rocs.client.ui.filters.MaxCreditFilterWidget;

/**
 * A schedule filter which removes any schedule which is over
 * the number of specified credits.
 * 
 * @author ewpatton
 *
 */
public class MaxCreditFilter implements ScheduleFilter {
	
	private static String DISPLAY_NAME="Maximum Credit Filter";
	private static String QUALIFIED_NAME="edu.rpi.rocs.client.filters.schedule.MaxCreditFilter";
	
	public static boolean register() {
		ScheduleFilterManager.getInstance().registerFilter(DISPLAY_NAME, QUALIFIED_NAME);
		return true;
	}
	
	/**
	 * The unique identifier for serialization
	 */
	private static final long serialVersionUID = 2097081252969945097L;
	private transient MaxCreditFilterWidget widget = null;
	
	/**
	 * The credit limit imposed by this filter
	 */
	protected int maxcreds = Integer.MAX_VALUE;
	
	/**
	 * Default constructor sets the maximum number of credits to
	 * Integer.MAX_VALUE, effectively making it unlimited.
	 */
	public MaxCreditFilter() {
		maxcreds = 21;
	}
	
	/**
	 * Set the maximum limit to be max
	 * @param max The credit limit for this filter
	 */
	public MaxCreditFilter(int max) {
		maxcreds = max;
		for(ChangeHandler handler : changeHandlers) {
			handler.onChange(null);
		}
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
	
	/**
	 * @return the widget associated with this ScheduleFilter.
	 */
	public Widget getWidget() {
		// TODO Auto-generated method stub
		if(widget==null) widget = new MaxCreditFilterWidget(maxcreds);
		return widget;
	}

	/**
	 * @return the title to be displayed in the Filter list.
	 */
	public String getDisplayTitle() {
		// TODO Auto-generated method stub
		return DISPLAY_NAME;
	}
	
	private HashSet<ChangeHandler> changeHandlers = new HashSet<ChangeHandler>();

	/**
	 * Adds a Handler to be called when the value of the MaxCreditFilter changes.
	 */
	public void addChangeHandler(ChangeHandler e) {
		// TODO Auto-generated method stub
		changeHandlers.add(e);
	}

	/**
	 * Removes a Handler from the registered AddHandlers list.
	 */
	public void removeChangeHandler(ChangeHandler e) {
		// TODO Auto-generated method stub
		changeHandlers.remove(e);
	}

}
