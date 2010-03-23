package edu.rpi.rocs.client.objectmodel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.rpi.rocs.client.filters.schedule.ScheduleFilter;
import edu.rpi.rocs.client.filters.schedule.TimeSchedulerFilter;
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
	private transient static final long serialVersionUID = -7594397052857927655L;

	protected ArrayList<Section> sections = new ArrayList<Section>();
	protected String name = "";
	protected String owner = "";
	protected int creditMin = 0;
	protected int creditMax = 0;
	protected transient ArrayList<ArrayList<TimeBlockType>> times=null;
	
	protected enum TimeBlockType {
		Available(0),
		Desirable(1),
		Filled(2),
		Blocked(3);
		
		private int id;
		
		private TimeBlockType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
		public static TimeBlockType valueOf(int id) {
			switch(id) {
			case 0: return Available;
			case 1: return Desirable;
			case 2: return Filled;
			case 3: return Blocked;
			default: return null;
			}
		}
	}
	
	public Schedule() {
		times = new ArrayList<ArrayList<TimeBlockType>>();
		for(int j=0;j<7;j++) {
			ArrayList<TimeBlockType> temp = new ArrayList<TimeBlockType>();
			for(int i=0;i<24*6;i++)
				temp.add(TimeBlockType.Available);
			times.add(temp);
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
	
	
	private static ArrayList<Schedule> buildSchedulesGivenStartingPoint(Schedule start, Map<Course, Set<Section>> requiredCourses, Map<Course, Set<Section>> optionalCourses, ArrayList<ScheduleFilter> filters) {
		ArrayList<Schedule> results = new ArrayList<Schedule>();
		if(requiredCourses.size()==0) {
			boolean satisfies=true;
			for(ScheduleFilter filter : filters) {
				if(!filter.doesScheduleSatisfyFilter(start)) {
					satisfies = false;
				}
			}
			if(satisfies) {
				results.add(start);
			}
		}
		else {
			Course aCourse = requiredCourses.keySet().iterator().next();
			Set<Section> sections = requiredCourses.get(aCourse);
			Map<Course, Set<Section>> newCourses=new HashMap<Course, Set<Section>>(requiredCourses);
			newCourses.remove(aCourse);
			for(Section section : sections) {
				if(!start.willConflict(section)) {
					Schedule copy = new Schedule(start);
					if(!copy.add(section)) {
						//Log.debug("Went to add section, but add() returned false. Aborting");
						return results;
					}
					ArrayList<Schedule> temp = buildSchedulesGivenStartingPoint(copy,newCourses,optionalCourses,filters);
					results.addAll(temp);
				}
			}
		}
		return results;
	}
	
	
	public static ArrayList<Schedule> buildAllSchedulesGivenCoursesAndFilters(Collection<Course> requiredCourses, Collection<Course> optionalCourses, Collection<ScheduleFilter> filters) {
		//Log.debug("Inside buildAllSchedulesGivenCoursesAndFilters");
		//Log.debug("Building required courses hashmap");
		Map<Course, Set<Section>> requiredCourseMap = new HashMap<Course, Set<Section>>();
		for(Course c : requiredCourses) {
			Set<Section> set = new HashSet<Section>(c.getSections());
			requiredCourseMap.put(c, set);
		}
		//Log.debug("Building optional courses hashmap");
		Map<Course, Set<Section>> optionalCourseMap = new HashMap<Course, Set<Section>>();
		for(Course c : optionalCourses) {
			Set<Section> set = new HashSet<Section>(c.getSections());
			optionalCourseMap.put(c, set);
		}
		//Log.debug("Finding TimeSchedulerFilter");
		TimeSchedulerFilter timeFilter=null;
		for(ScheduleFilter filter : filters) {
			if(filter.getClass().equals(TimeSchedulerFilter.class)) {
				timeFilter = (TimeSchedulerFilter)filter;
				break;
			}
		}
		//Log.debug("Blocking out times specified in filter");
		HashMap<Integer, ArrayList<Time>> times = timeFilter.getTimes();
		Schedule start = new Schedule();
		for(int i=0;i<7;i++) {
			Integer day = new Integer(i);
			ArrayList<Time> blocked = times.get(day);
			for(Time t : blocked) {
				start.blockTime(i, t.getHour(), t.getMinute());
			}
		}
		//Log.debug("Removing the TimeSchedulerFilter");
		ArrayList<ScheduleFilter> newFilters = new ArrayList<ScheduleFilter>(filters);
		newFilters.remove(timeFilter);
		ArrayList<Schedule> schedules = buildSchedulesGivenStartingPoint(start, requiredCourseMap, optionalCourseMap, newFilters);
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		for(Schedule s : schedules) {
			if(s.getCourses().containsAll(requiredCourses)) {
				result.add(s);
			}
		}
		//if(result.size()==0) Window.alert("Conflict detected. Unable to generate any valid schedules.");
		
		return result;
	}
	
	
	public ArrayList<Course> getCourses() {
		ArrayList<Course> courses=new ArrayList<Course>();
		for(Section s : sections) {
			courses.add(s.getParent());
		}
		return courses;
	}

	public ArrayList<Section> getSections() {
		return new ArrayList<Section>(sections);
	}
	
	public void setSections(List<Section> list) {
		sections = new ArrayList<Section>(list);
		/* TODO: Process the list and compute the time blocks.*/
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
			starttime /= 10;
			endtime /= 10;
			
			Iterator<Integer> j = p.getDays().iterator();
			while(j.hasNext()) {
				int day = j.next().intValue();
				for(int k=starttime;k<endtime;k++) {
					TimeBlockType t = times.get(day).get(k);
					if(t == TimeBlockType.Filled || t == TimeBlockType.Blocked)
						return true;
				}
			}
		}
		return false;
	}
	
	public void blockTime(int day, int hour, int minute) {
		day = day % 7;
		while(day<0) day += 7;
		hour = hour % 24;
		while(hour<0) hour += 24;
		minute = minute % 60;
		while(minute<0) minute += 60;
		minute /= 10;
		times.get(day).set(hour*6+minute, TimeBlockType.Blocked);
	}
	
	public void unblockTime(int day, int hour, int minute) {
		day = day % 7;
		while(day<0) day += 7;
		hour = hour % 24;
		while(hour<0) hour += 24;
		minute = minute % 60;
		while(minute<0) minute += 60;
		minute /= 10;
		times.get(day).set(hour*6+minute, TimeBlockType.Available);
	}
	
	public boolean add(Section s) {
		if(willConflict(s)) return false;
		List<Period> periods = s.getPeriods();
		Iterator<Period> i = periods.iterator();
		while(i.hasNext()) {
			Period p = i.next();
			Time start = p.getStart();
			Time end = p.getEnd();
			int starttime = start.getAbsMinute();
			int endtime = end.getAbsMinute();
			starttime /= 10;
			endtime /= 10;
			List<Integer> days = p.getDays();
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
						times.get(day).set(time, TimeBlockType.Available);
					}
				}
			}
		}
	}

	public boolean isBlocked(int day, int hour, int minute) {
		day = day % 7;
		while(day<0) day += 7;
		hour = hour % 24;
		while(hour<0) hour += 24;
		minute = minute % 60;
		while(minute<0) minute += 60;
		minute /= 10;
		TimeBlockType t = times.get(day).get(hour*6+minute);
		return (t == TimeBlockType.Blocked);
	}
	
	private Long dbid=null;
	public void setDbid(Long id) {
		dbid = id;
	}
	public Long getDbid() {
		return dbid;
	}

	public void setOwner(String string) {
		// TODO Auto-generated method stub
		owner = string;
	}
	public String getOwner() {
		return owner;
	}
	
	private transient SchedulerManager mgrInstance=null;
	public void setManager(SchedulerManager mgr) {
		mgrInstance = mgr;
	}
	public SchedulerManager getManager() {
		return mgrInstance;
	}
}
