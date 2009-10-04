package edu.rpi.rocs.client.filters.schedule;

import java.util.Iterator;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;

public class NoMoreThanNumAtLevelFilter implements ScheduleFilter {

	/**
	 * The unique identifier for serialization
	 */
	private static final long serialVersionUID = -8071519178249185461L;
	/**
	 * The level to check against
	 */
	protected int level=1000;
	/**
	 * The maximum number of courses allowed at this level
	 */
	protected int max=3;

	/**
	 * Creates a new filter to restrict the number of courses at a particular level
	 * @param level The level to filter
	 * @param max The maximum number of courses at level
	 */
	public NoMoreThanNumAtLevelFilter(int level, int max) {
		this.level = level;
		this.max = max;
	}
	
	/**
	 * Checks a schedule against this filter.
	 * @return true if the schedule has max or less courses at level
	 */
	public boolean doesScheduleSatisfyFilter(Schedule schedule) {
		// TODO Auto-generated method stub
		Iterator<Section> i = schedule.getSections().iterator();
		int counter=0;
		while(i.hasNext()) {
			Section s = i.next();
			if(s.getParent().getLevel() == level) {
				counter++;
				if(counter==max) return false;
			}
		}
		return true;
	}

	/**
	 * Any schedule generated with this schedule as a base will also fail this filter, so prune the tree.
	 */
	public boolean shouldPruneTreeOnFailure() {
		// TODO Auto-generated method stub
		return true;
	}

}
