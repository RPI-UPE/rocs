package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import edu.rpi.rocs.client.objectmodel.exceptions.InvalidScheduleException;

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
	protected int creditMin = 0;
	protected int creditMax = 0;
	protected ArrayList<ArrayList<TimeBlockType>> times=null; 
	
	protected enum TimeBlockType {
		Available,
		Desirable,
		Filled,
		Blocked
	}
	
	public Schedule() {
		times = new ArrayList<ArrayList<TimeBlockType>>();
		for(int j=0;j<7;j++) {
			ArrayList<TimeBlockType> temp = new ArrayList<TimeBlockType>();
			for(int i=0;i<24*6;i++)
				temp.add(TimeBlockType.Available);
		}
	}
	
	public Schedule(Schedule other) {
		times = new ArrayList<ArrayList<TimeBlockType>>();
		for(int i=0;i<7;i++) {
			ArrayList<TimeBlockType> temp = new ArrayList<TimeBlockType>(other.times.get(i));
			times.add(temp);
		}
		sections = new ArrayList<Section>(other.sections);
		if(other.name != "")
			name = other.name + " Copy";
		calculateCredits();
	}
	
	protected Schedule(ArrayList<Section> sections) throws InvalidScheduleException {
		this();
		Iterator<Section> i = sections.iterator();
		while(i.hasNext()) {
			Section s = i.next();
			if(!this.add(s))
				throw new InvalidScheduleException("Set of courses passed to schedule cannot be scheduled");
		}
	}
	
	protected void calculateCredits() {
		creditMin = 0;
		creditMax = 0;
		Iterator<Section> i = sections.iterator();
		while(i.hasNext()) {
			Section s = i.next();
			Course c = s.getParent();
			creditMin = c.getCredmin();
			creditMax = c.getCredmax();
		}
	}
	
	protected static ArrayList<Section> copyRemovingItem(ArrayList<Section> list, Section item) {
		ArrayList<Section> copy = new ArrayList<Section>(list);
		copy.remove(item);
		return copy;
	}
	
	protected static ArrayList<Map<Course, Section>> copyRemovingItem(ArrayList<Map<Course, Section>> list, Map<Course, Section> item) {
		ArrayList<Map<Course, Section>> copy = new ArrayList<Map<Course, Section>>(list);
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
						ScheduleFilter f = filters.get(j);
						if(f.doesScheduleSatisfyFilter(newSchedule) || !f.shouldPruneTreeOnFailure()) {
							finalList.add(newSchedule);
							finalList.addAll(buildSchedulesGivenStartingPoint(newSchedule, copyRemovingItem(required, course), optional, filters));
						}
					}
				}
			}
		}
		if(optional.size()>0) {
			Map<Course, Section> course = optional.get(0);
			Collection<Section> sections = course.values();
			Iterator<Section> i = sections.iterator();
			while(i.hasNext()) {
				Schedule newSchedule = new Schedule(start);
				if(newSchedule.add(i.next())) {
					for(int j=0;j<filters.size();j++) {
						ScheduleFilter f = filters.get(j);
						if(f.doesScheduleSatisfyFilter(newSchedule) || !f.shouldPruneTreeOnFailure()) {
							finalList.add(newSchedule);
							finalList.addAll(buildSchedulesGivenStartingPoint(newSchedule, copyRemovingItem(required, course), optional, filters));
						}
					}
				}
			}
		}
		for(int j=0;j<filters.size();j++) {
			ScheduleFilter f = filters.get(j);
			for(int i=0;i<finalList.size();i++) {
				if(!f.doesScheduleSatisfyFilter(finalList.get(i))) {
					finalList.remove(i);
					i--;
				}
			}
		}
		return finalList;
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
	
	public int getMinCredits() {
		return creditMin;
	}
	
	public int getMaxCredits() {
		return creditMax;
	}
	
	public boolean willConflict(Section s) {
		Iterator<Period> i = s.getPeriods().iterator();
		while(i.hasNext()) {
			Period p = i.next();
			int starttime = p.getStart().getAbsMinute();
			int endtime = p.getEnd().getAbsMinute();
			Iterator<Integer> dayItr = p.getDays().iterator();
			while(dayItr.hasNext()) {
				int day = dayItr.next().intValue();
				for(int time=starttime;time<endtime;time++) {
					if(times.get(day).get(time) == TimeBlockType.Blocked || 
							times.get(day).get(time) == TimeBlockType.Filled)
						return true;
				}
			}
		}
		return false;
	}
	
	public void blockTime(int day, int minute) {
		minute = minute / 10;
		times.get(day).set(minute, TimeBlockType.Blocked);
	}
	
	public void unblockTime(int day, int minute) {
		minute = minute / 10;
		times.get(day).set(minute, TimeBlockType.Available);
	}
	
	public boolean add(Section s) {
		ArrayList<Period> periods = s.getPeriods();
		if(willConflict(s)) return false;
		Iterator<Period> i = periods.iterator();
		while(i.hasNext()) {
			Period p = i.next();
			Time start = (Time)p.getStart().clone();
			Time end = (Time)p.getEnd().clone();
			int starttime = start.getAbsMinute();
			int endtime = end.getAbsMinute();
			ArrayList<Integer> days = p.getDays();
			Iterator<Integer> dayItr = days.iterator();
			while(dayItr.hasNext()) {
				int day = dayItr.next().intValue();
				for(int j=starttime;j<endtime;j++)
					times.get(day).set(j, TimeBlockType.Filled);
			}
		}
		sections.add(s);
		calculateCredits();
		return true;
	}
	
	public void remove(Section s) {
		if(sections.contains(s)) {
			sections.remove(s);
			Iterator<Period> i = s.getPeriods().iterator();
			while(i.hasNext()) {
				Period p = i.next();
				int starttime = p.getStart().getAbsMinute();
				int endtime = p.getEnd().getAbsMinute();
				Iterator<Integer> dayItr = p.getDays().iterator();
				while(dayItr.hasNext()) {
					int day = dayItr.next().intValue();
					for(int time=starttime;time<endtime;time++) {
						times.get(day).set(time, TimeBlockType.Available);					}
				}
			}
		}
	}
}
