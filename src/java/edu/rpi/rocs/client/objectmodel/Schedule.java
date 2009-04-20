package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a particular schedule as a combination of Course-Section pair objects 
 * 
 * @author ewpatton
 *
 */
public class Schedule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594397052857927655L;

	protected ArrayList<Section> sections = new ArrayList<Section>();
	protected String name = "";
	protected int credits = 0;
	protected ArrayList<ArrayList<TimeBlockType>> times=null; 
	
	protected enum TimeBlockType {
		Unknown,
		Desirable,
		Blocked
	}
	
	public Schedule() {
		times = new ArrayList<ArrayList<TimeBlockType>>();
		for(int j=0;j<7;j++) {
			ArrayList<TimeBlockType> temp = new ArrayList<TimeBlockType>();
			for(int i=0;i<24*6;i++)
				temp.add(TimeBlockType.Unknown);
		}
	}
	
	public Schedule(Schedule other) {
		
	}
	
	public static Schedule buildScheduleGivenCourses(ArrayList<Section> required, ArrayList<Section> optional) {
		return null;
	}
	
	protected static ArrayList<Section> copyRemovingItem(ArrayList<Section> list, Section item) {
		ArrayList<Section> copy = new ArrayList<Section>(list);
		copy.remove(item);
		return copy;
	}
	
	protected static ArrayList<Schedule> buildSchedulesGivenStartingPoint(Schedule start, ArrayList<Map<Course, Section>> required, ArrayList<Map<Course, Section>> optional, ArrayList<ScheduleFilter> filters) {
		ArrayList<Schedule> finalList = new ArrayList<Schedule>();
		if(required.size()>0) {
			Map<Course, Section> course = required.get(0);
			Collection<Section> sections = course.values();
			Iterator<Section> i = sections.iterator();
			while(i.hasNext()) {
				Schedule newSchedule = new Schedule(start);
				if(newSchedule.add(i.next())) {
					for(int j=0;j<filters.size();j++) {
						
					}
				}
			}
		}
		return null;
	}
	
	public static ArrayList<Schedule> buildAllSchedulesGivenCourses(ArrayList<Time> blocked, ArrayList<Map<Course, Section>> required, ArrayList<Map<Course, Section>> optional, ArrayList<ScheduleFilter> filters) {
		return null;
	}
	
	public ArrayList<Section> getSections() {
		return new ArrayList<Section>(sections);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public boolean add(Section s) {
		ArrayList<Period> periods = s.getPeriods();
		Iterator<Period> i = periods.iterator();
		while(i.hasNext()) {
			Period p = i.next();
			Time start = (Time)p.getStart().clone();
			Time end = (Time)p.getEnd().clone();
			int starttime = start.getAbsMinute() / 10;
			int endtime = end.getAbsMinute() / 10;
			starttime *= 10;
			endtime *= 10;
			ArrayList<Integer> days = p.getDays();
			Iterator<Integer> dayItr = days.iterator();
			while(dayItr.hasNext()) {
				int day = dayItr.next().intValue();
				for(int j=starttime;j<endtime;j+=10) {
					if(times.get(day).get(j) == TimeBlockType.Blocked) return false;
					times.get(day).set(j, TimeBlockType.Blocked);
				}
			}
		}
		sections.add(s);
		return true;
	}
}
