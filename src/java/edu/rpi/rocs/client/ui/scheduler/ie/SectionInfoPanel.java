package edu.rpi.rocs.client.ui.scheduler.ie;

import java.util.List;

import com.google.gwt.user.client.ui.HTML;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.Time;

public class SectionInfoPanel extends HTML {

	protected String time;
	protected String seats;
	protected String name;
	protected String num;
	protected String periods;
	protected String notes;
	protected boolean showing=false;
	
	public SectionInfoPanel() {
		super();
		setStyleName("section-info");
		this.setHTML("No course selected.");
	}
	
	public void displayInfoForSection(Section s) {
		if(s == null) {
			clear();
			return;
		}
		name = s.getParent().getName();
		num = "Section "+s.getNumber()+" ("+s.getCrn()+")";
		int hr=0,min=0,total=0;
		for(Period p : s.getPeriods()) {
			total += (p.getEndInt() - p.getStartInt())*p.getDays().size();
		}
		hr = total/60;
		min = total%60;
		String minStr;
		if(min < 10) minStr = "0"+min;
		else minStr = ""+min;
		time = "Class time: "+hr+":"+minStr+" per week";
		seats = "Seats: "+s.getSeats()+" ("+(s.getSeats()-s.getStudents())+" remaining)";
		String text = "<p>Class times:</p>";
		text += "<ul>";
		for(Period p : s.getPeriods()) {
			String type = p.getType();
			Time start = p.getStart();
			Time end = p.getEnd();
			List<Integer> days = p.getDays();
			String prof = p.getInstructor();
			text += "<li>"+type+"<br/>";
			if(start.getMeridiem().equals(end.getMeridiem())) {
				text += start.get12HRString(false);
				text += " - ";
				text += end.get12HRString();
			}
			else {
				text += start.get12HRString();
				text += " - ";
				text += end.get12HRString();
			}
			text += " on ";
			for(int i=0;i<days.size();i++) {
				text += dayOfWeek(days.get(i));
				if(i!=days.size()-1) {
					text+=",";
				}
			}
			text += "<br/>";
			text += "Professor: "+prof;
			text += "</li>";
		}
		text += "</ul>";
		periods = text;
		text = "";
		for(String t : s.getNotes()) {
			text += t + "<br/>";
		}
		notes = text;
		setHTML(name+"<br/>"+num+"<br/>"+time+"<br/>"+seats+"<br/>"+periods+"<br/>"+notes+"<br/>");
	}
	
	private String dayOfWeek(int day) {
		switch(day) {
		case 0:
			return "Mon";
		case 1:
			return "Tue";
		case 2:
			return "Wed";
		case 3:
			return "Thu";
		case 4:
			return "Fri";
		case 5:
			return "Sat";
		case 6:
			return "Sun";
		default:
			return "???";
		}
	}

	public void clear() {
		showing = false;
		this.setHTML("No course selected.");
	}
}
