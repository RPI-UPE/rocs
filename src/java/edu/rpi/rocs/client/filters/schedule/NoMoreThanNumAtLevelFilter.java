package edu.rpi.rocs.client.filters.schedule;

import java.util.Iterator;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;

public class NoMoreThanNumAtLevelFilter implements ScheduleFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8071519178249185461L;
	protected int level=1;
	protected int max=3;

	public NoMoreThanNumAtLevelFilter(int level, int max) {
		this.level = level;
		this.max = max;
	}
	
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

	public boolean shouldPruneTreeOnFailure() {
		// TODO Auto-generated method stub
		return true;
	}

}
