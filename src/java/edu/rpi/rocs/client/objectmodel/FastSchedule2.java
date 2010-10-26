package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.List;

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
			days = new int[7];
			if(contents==null) return;
			for(Period p : contents.getPeriods()) {
				int start = p.getStartInt();
				int end = p.getEndInt();
				start /= 60;
				if(end%60==0) end -= 1;
				end /= 60;
				int flags=0;
				for(int i=start;i<=end;i++) {
					flags |= 1<<i;
				}
				for(int day : p.getDays()) {
					days[day]  = flags;
				}
			}
		}
		
		public void combine() {
			if(days==null) createBlock();
			if(next!=null) {
				if(next.combined == null) next.combine();
				if(combined==null) combined = new int[7];
				for(int i=0;i<7;i++) {
					combined[i] = days[i] | next.combined[i];
				}
			}
			else {
				if(combined==null) {
					combined = new int[7];
					for(int i=0;i<7;i++) {
						combined[i] = days[i];
					}
				}
			}
		}

		public boolean collides(int[] test) {
			if(combined==null) combine();
			for(int i=0;i<7;i++){ 
				if((test[i] & combined[i]) != 0) return true;
			}
			return false;
		}
	}
	
	private ArrayList<Node> topnodes = new ArrayList<Node>();
	
	private int[] createBlock(Section s) {
		int[] days = new int[7];
		for(Period p : s.getPeriods()) {
			int start = p.getStartInt();
			int end = p.getEndInt();
			start /= 60;
			if(end%60==0) end -= 1;
			end /= 60;
			int flags=0;
			for(int i=start;i<=end;i++) {
				flags |= 1<<i;
			}
			for(int day : p.getDays()) {
				days[day]  = flags;
			}
		}
		return days;
	}
	
	private ArrayList<Course> courses = new ArrayList<Course>();
	
	public boolean conflicts(Course c) {
		if(courses.size()==0) return false;
		if(courses.contains(c)) return false;
		List<Section> sections = c.getSections();
		if(sections.size()==0) return false;
		for(Section s : sections) {
			int[] days=createBlock(s);
			for(Node n : topnodes) {
				if(!n.collides(days)) return false;
			}
		}
		return true;
	}
	
	public void addCourse(Course c) {
		if(courses.contains(c)) return;
		Log.debug("Course: "+c.getName());
		if(courses.size()==0) {
			for(Section s : c.getSections()) {
				Node n = new Node();
				n.contents = s;
				n.createBlock();
				n.combine();
				topnodes.add(n);
			}
			courses.add(c);
			Log.debug("topnodes.size() = "+topnodes.size());
		}
		else {
			ArrayList<Node> newnodes = new ArrayList<Node>();
			for(Section s : c.getSections()) {
				int[] days = createBlock(s);
				for(Node n : topnodes) {
					if(!n.collides(days)) {
						Node x = new Node();
						x.contents = s;
						x.createBlock();
						x.next = n;
						x.combine();
						newnodes.add(x);
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
}
