package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.allen_sauer.gwt.log.client.Log;

/**
 * The FastSchedule2 algorithm constructs a graph of sections for the current required courses. 
 * When a new course is added, all of its sections are considered against all of the other preassembled
 * paths, checking to see if they collide. 
 * @author ewpatton
 *
 */
public class FastSchedule2 {
	class Node {
		public Section contents=null;
		public int[] days=null;
		public int[] combined=null;
		public Node next=null;
		
		/**
		 * Assume classes don't overlap within an hour
		 */
		public void createBlock() {
			days = new int[14];
			if(contents==null) return;
			for(Period p : contents.getPeriods()) {
				if(!p.wasDeleted()) {
					int start = p.getStartInt();
					int end = p.getEndInt();
					start /= 30;
					if(end%30==0) end -= 1;
					end /= 30;
					int flagsAM=0;
					int flagsPM=0;
					for(int i=start;i<=end;i++) {
						if(i>=24) flagsPM |= 1<<(i-24);
						else flagsAM |= 1<<i;
					}
					for(int day : p.getDays()) {
						days[2*day] |= flagsAM;
						days[2*day+1] |= flagsPM;
					}
				}
			}
		}
		
		public void combine() {
			if(days==null) createBlock();
			if(next!=null) {
				if(next.combined == null) next.combine();
				if(combined==null) combined = new int[14];
				for(int i=0;i<days.length;i++) {
					combined[i] = days[i] | next.combined[i];
				}
			}
			else {
				if(combined==null) {
					combined = new int[14];
					for(int i=0;i<days.length;i++) {
						combined[i] = days[i];
					}
				}
			}
		}

		public boolean collides(int[] test) {
			if(combined==null) combine();
			for(int i=0;i<test.length;i++){ 
				if((test[i] & combined[i]) != 0) return true;
			}
			return false;
		}
		
		public Set<Course> collides(int[] test, boolean differ) {
			Set<Course> result = new HashSet<Course>();
			for(int i=0;i<test.length;i++) {
				if((test[i] & combined[i]) != 0) {
					if(next != null) {
						Set<Course> subset = next.collides(test, differ);
						if(subset.size()>0) return subset;
					}
					result.add(this.contents.getParent());
					return result;
				}
			}
			return result;
		}
	}
	
	private ArrayList<Node> topnodes = new ArrayList<Node>();
	
	private int[] createBlock(Section s) {
		int[] days = new int[14];
		for(Period p : s.getPeriods()) {
			if(!p.wasDeleted()) {
				int start = p.getStartInt();
				int end = p.getEndInt();
				start /= 30;
				if(end%30==0) end -= 1;
				end /= 30;
				/*
				if(s.getParent().getNum()==1100) {
					Log.debug("start = "+start+", end = "+end);
				}
				*/
				int flagsAM=0;
				int flagsPM=0;
				for(int i=start;i<=end;i++) {
					if(i>=24) flagsPM |= 1<<(i-24);
					else flagsAM |= 1<<i;
				}
				for(int day : p.getDays()) {
					days[2*day] |= flagsAM;
					days[2*day+1] |= flagsPM;
				}
			}
		}
		//logDays("createBlock",days);
		return days;
	}
	
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	public boolean conflicts(Course c) {
		if(courses.size()==0) return false;
		if(courses.contains(c)) return false;
		List<Section> sections = c.getSections();
		if(sections.size()==0) return false;
		for(Section s : sections) {
			if(!s.wasDeleted()) {
				int[] days=createBlock(s);
				for(Node n : topnodes) {
					if(!n.collides(days)) return false;
				}
			}
		}
		return true;
	}
	
	public Set<Course> conflicts(Course c, boolean differ) {
		Set<Course> result = new HashSet<Course>();
		if(courses.size()==0) return result;
		if(courses.contains(c)) return result;
		List<Section> sections = c.getSections();
		if(sections.size()==0) return result;
		result = new HashSet<Course>();
		for(Section s : sections) {
			if(!s.wasDeleted()) {
				int[] days=createBlock(s);
				for(Node n : topnodes) {
					Set<Course> temp = n.collides(days, true);
					if(temp.size()==0) return new HashSet<Course>();
					result.addAll(temp);
				}
			}
		}
		return result;
	}
	
	public void addCourse(Course c) {
		if(courses.contains(c)) return;
		Log.debug("Course: "+c.getName());
		// Debug
		/*
		if(true) {
			for(Section s : c.getSections()) {
				int[] days = createBlock(s);
				debug(days);
			}
		}
		*/
		if(courses.size()==0) {
			Map<Section, SectionStatusObject> sections = SchedulerManager.getInstance().getSectionsForCourse(c);
			for(SectionStatusObject s : sections.values()) {
				if(s.getIncluded()&&!s.getSection().wasDeleted()) {
					Node n = new Node();
					n.contents = s.getSection();
					n.createBlock();
					n.combine();
					topnodes.add(n);
				}
			}
			courses.add(c);
			Log.debug("topnodes.size() = "+topnodes.size());
		}
		else {
			ArrayList<Node> newnodes = new ArrayList<Node>();
			Map<Section, SectionStatusObject> sections = SchedulerManager.getInstance().getSectionsForCourse(c);
			for(SectionStatusObject s : sections.values()) {
				if(s.getIncluded()&&!s.getSection().wasDeleted()) {
					int[] days = createBlock(s.getSection());
					for(Node n : topnodes) {
						if(!n.collides(days)) {
							Node x = new Node();
							x.contents = s.getSection();
							x.createBlock();
							x.next = n;
							x.combine();
							newnodes.add(x);
						}
					}
				}
			}
			topnodes = newnodes;
			courses.add(c);
			Log.debug("topnodes.size() = "+topnodes.size());
		}
	}

	public void removeCourse(Course course) {
		topnodes.clear();
		ArrayList<Course> temp = courses;
		temp.remove(course);
		courses = new ArrayList<Course>();
		for(Course c : temp) addCourse(c);
	}

	public void clear() {
		courses = new ArrayList<Course>();
		topnodes.clear();
	}
	
	public void logDays(String label, int[] days) {
		String data = "";
		for(int i=0;i<days.length;i++) {
			int day = days[i];
			int bits = 0x800000;
			while(bits!=0) {
				if((day&bits)==bits) {
					data += "1";
				}
				else {
					data += "0";
				}
				bits = bits >> 1;
			}
			data += "  ";
		}
		Log.debug(label+"  "+data);
	}
	
	public void debug(int[] days) {
		logDays("section",days);
		for(Node n : topnodes) {
			logDays("topnode",n.combined);
		}
	}
}
