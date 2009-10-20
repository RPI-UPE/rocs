package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Window;

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
	
	public static ArrayList<Schedule> buildAllSchedulesGivenCoursesAndFilters(Collection<Course> requiredCourses, Collection<Course> optionalCourses, Collection<ScheduleFilter> filters) {
		Log.debug("Inside buildAllSchedulesGivenCoursesAndFilters");
		Log.debug("Building required courses hashmap");
		Map<Course, Set<Section>> requiredCourseMap = new HashMap<Course, Set<Section>>();
		for(Course c : requiredCourses) {
			Set<Section> set = new HashSet<Section>(c.getSections());
			requiredCourseMap.put(c, set);
		}
		Log.debug("Building optional courses hashmap");
		Map<Course, Set<Section>> optionalCourseMap = new HashMap<Course, Set<Section>>();
		for(Course c : optionalCourses) {
			Set<Section> set = new HashSet<Section>(c.getSections());
			optionalCourseMap.put(c, set);
		}
		Log.debug("Finding TimeSchedulerFilter");
		TimeSchedulerFilter timeFilter=null;
		for(ScheduleFilter filter : filters) {
			if(filter.getClass().equals(TimeSchedulerFilter.class)) {
				timeFilter = (TimeSchedulerFilter)filter;
				break;
			}
		}
		Log.debug("Blocking out times specified in filter");
		HashMap<Integer, ArrayList<Time>> times = timeFilter.getTimes();
		Schedule start = new Schedule();
		for(int i=0;i<7;i++) {
			Integer day = new Integer(i);
			ArrayList<Time> blocked = times.get(day);
			ArrayList<TimeBlockType> temp = start.times.get(i);
			for(Time t : blocked) {
				temp.set(t.getAbsMinute()/10, TimeBlockType.Blocked);
			}
		}
		Log.debug("Removing the TimeSchedulerFilter");
		ArrayList<ScheduleFilter> newFilters = new ArrayList<ScheduleFilter>(filters);
		newFilters.remove(timeFilter);
		return buildSchedulesGivenStartingPoint(start, requiredCourseMap, optionalCourseMap, newFilters);
	}
	
	private static ArrayList<Schedule> buildSchedulesGivenStartingPoint(
			Schedule start, Map<Course, Set<Section>> requiredCourseMap,
			Map<Course, Set<Section>> optionalCourseMap,
			ArrayList<ScheduleFilter> filters) {
		// TODO Auto-generated method stub
		ArrayList<Schedule> temp = new ArrayList<Schedule>();
		if(requiredCourseMap.size()>0) {
			Log.debug("Getting entry in required course map");
			Entry<Course, Set<Section>> entry = requiredCourseMap.entrySet().iterator().next();
			Course course = entry.getKey();
			Set<Section> sections = entry.getValue();
			Log.debug("Have course and section set");
			boolean couldNotPlaceCourse = true;
			for(Section s : sections) {
				Log.debug("Processing section " + course.getDept()+"-"+course.getNum()+"-"+s.getNumber());
				if(start.willConflict(s)) continue;
				couldNotPlaceCourse = false;
				Log.debug("Copying current schedule");
				Schedule newStart = new Schedule(start);
				Log.debug("Placing section " + course.getDept()+"-"+course.getNum()+"-"+s.getNumber() + " in new schedule");
				newStart.add(s);
				
				boolean prune=false;
				for(ScheduleFilter filter : filters) {
					if(!filter.doesScheduleSatisfyFilter(newStart) && filter.shouldPruneTreeOnFailure()) {
						prune = true;
						break;
					}
				}
				if(!prune) {
					if(requiredCourseMap.size()==1) // Only add a schedule if it has all required courses
						temp.add(newStart);
					
					Map<Course, Set<Section>> copy = new HashMap<Course, Set<Section>>();
					copy.putAll(requiredCourseMap);
					copy.remove(course);
					
					ArrayList<Schedule> recurse = buildSchedulesGivenStartingPoint(start, copy, optionalCourseMap, filters);
					if(recurse != null)
						temp.addAll(recurse);
				}
			}
			ArrayList<Schedule> result = new ArrayList<Schedule>();
			if(couldNotPlaceCourse) {
				Window.alert("Unable to fit the course " + course.getName() + " into the schedule. You may want to mark it optional.");
				return result;
			}
			for(Schedule s : temp) {
				for(ScheduleFilter filter : filters) {
					if(filter.doesScheduleSatisfyFilter(s)) {
						result.add(s);
					}
				}
			}
			return result;
		}
		else if(optionalCourseMap.size()>0) {
			for(Entry<Course, Set<Section>> entry : optionalCourseMap.entrySet()) {
				Course course = entry.getKey();
				Set<Section> sections = entry.getValue();
				for(Section s : sections) {
					if(start.willConflict(s)) continue;
					Schedule newStart = new Schedule(start);
					newStart.add(s);
					
					boolean prune=false;
					for(ScheduleFilter filter : filters) {
						if(!filter.doesScheduleSatisfyFilter(newStart) && filter.shouldPruneTreeOnFailure()) {
							prune = true;
							break;
						}
					}
					if(!prune) {
						temp.add(newStart);
						
						Map<Course, Set<Section>> copy = new HashMap<Course, Set<Section>>();
						copy.putAll(optionalCourseMap);
						copy.remove(course);
						
						ArrayList<Schedule> recurse = buildSchedulesGivenStartingPoint(start, requiredCourseMap, copy, filters);
						if(recurse != null)
							temp.addAll(recurse);
					}
				}
			}
			ArrayList<Schedule> result = new ArrayList<Schedule>();
			for(Schedule s : temp) {
				for(ScheduleFilter filter : filters) {
					if(filter.doesScheduleSatisfyFilter(s)) {
						result.add(s);
					}
				}
			}
			return result;
		}
		else {
			return null;
		}
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
		Log.debug("In Schedule.willConflict(Section)");
		Iterator<Period> i = s.getPeriods().iterator();
		while(i.hasNext()) {
			Log.debug("Getting next time period...");
			Period p = i.next();
			Log.debug("Period: " + p.toString());
			int starttime = p.getStart().getAbsMinute();
			int endtime = p.getEnd().getAbsMinute();
			starttime /= 30;
			if(endtime % 30 != 0) {
				endtime /= 30;
				endtime++;
			}
			else
				endtime /= 30;
			Log.debug("Start: " + starttime + " End: " + endtime);
			Iterator<Integer> dayItr = p.getDays().iterator();
			while(dayItr.hasNext()) {
				int day = dayItr.next().intValue();
				Log.debug("Checking day " + day);
				Log.debug("Length of day: " + times.get(day).size());
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
						times.get(day).set(time, TimeBlockType.Available);
					}
				}
			}
		}
	}

}
