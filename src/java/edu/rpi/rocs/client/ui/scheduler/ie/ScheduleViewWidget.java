package edu.rpi.rocs.client.ui.scheduler.ie;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.objectmodel.Time;
import edu.rpi.rocs.client.ui.HTMLTableList;
import edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget;
import edu.rpi.rocs.client.ui.scheduler.RandomColorGenerator;

public class ScheduleViewWidget extends FlowPanel {

	Schedule m_schedule;
	ScheduleBackgroundWidget m_background;
	private static final int MAJOR_Y_DISTANCE=26;
	private static final int MAJOR_X_DISTANCE=75;
	//private static final int MINOR_Y_DISTANCE=13;
	RandomColorGenerator m_generator;
	
	private class CustomFlowPanel extends FlowPanel {
		private native void setColorJS(Element e, String color)/*-{
			e.style.backgroundColor = color;
		}-*/;
		
		private native void setLeftJS(Element e, String left)/*-{
			e.style.left = left;
		}-*/;
		
		private native void setTopJS(Element e, String top)/*-{
			e.style.top = top;
		}-*/;
		
		public void setColor(String color) {
			setColorJS(getElement(), color);
		}
		
		public void setLeft(String left) {
			setLeftJS(getElement(), left);
		}
		
		public void setTop(String top) {
			setTopJS(getElement(), top);
		}
	}

	private class ScheduleBackgroundWidget extends HTMLTableList {
		HTMLTableListRow m_header;

		private void createBackground() {
			setStyleName("schedule");
			{
				HTMLTableListCell temp = new HTMLTableListCell(true);
				temp.addStyleName("firstcol");
				m_header.add(temp);
			}
			
			for(int i=0;i<7;i++) {
				HTMLTableListCell temp =new HTMLTableListCell(true);
				temp.setText(ScheduleTimeBlockFilterWidget.dayOfWeek(i));
				m_header.add(temp);
			}
			add(m_header);
			
			for(int row=8*2;row<22*2;row++) {
				HTMLTableListRow entry = new HTMLTableListRow();
				if(row%2==0) {
					HTMLTableListCell temp = new HTMLTableListCell(true);
					temp.setText(Integer.toString(row/2));
					temp.setRowSpan(2);
					temp.addStyleName("firstcol");
					entry.add(temp);
					entry.addStyleName("t00");
				}
				else {
					entry.addStyleName("t30");
				}
				for(int col=0;col<7;col++) {
					HTMLTableListCell temp = new HTMLTableListCell();
					temp.setHTML("&nbsp;");
					entry.add(temp);
				}
			}
		}

		private void createBlocked(Schedule s) {
			for(int day=0;day<7;day++) {
				for(int i=8;i<22;i++) {
					if(s.isBlocked(day, i, 0)) {
						HTMLTableListRow r = this.get(2*(i-8)+1);
						HTMLTableListCell c = r.get(day+1);
						c.addStyleName("blocked");
					}
					if(s.isBlocked(day, i, 30)) {
						HTMLTableListRow r = this.get(2*(i-8)+2);
						HTMLTableListCell c = r.get(day);
						c.addStyleName("blocked");
					}
				}
			}
		}

		public ScheduleBackgroundWidget(Schedule mSchedule) {
			createBackground();
			createBlocked(mSchedule);
		}
	}

	private class SectionGroupWidget extends FlowPanel {
		private ArrayList<CustomFlowPanel> m_divs;
		private Section m_section;

		public SectionGroupWidget(Section s) {
			m_section = s;
			m_divs = new ArrayList<CustomFlowPanel>();
			List<Period> times = s.getPeriods();
			for(Period p : times) {
				Time start = p.getStart();
				Time end = p.getEnd();
				int startPix = (int)((double)MAJOR_Y_DISTANCE*(double)((double)start.getMinute()/60.0));
				startPix += (start.getHour()-8)*MAJOR_Y_DISTANCE;
				int endPix = (int)((double)MAJOR_Y_DISTANCE*(double)((double)end.getMinute()/60.0));
				endPix += (end.getHour()-8)*MAJOR_Y_DISTANCE;

				// Adjust for header row
				startPix += MAJOR_Y_DISTANCE;
				endPix += MAJOR_Y_DISTANCE;

				for(Integer day : p.getDays()) {
					int d = day.intValue();
					int left = MAJOR_X_DISTANCE*d;
					int right = MAJOR_X_DISTANCE*(d+1);

					// Adjust for header column
					left += MAJOR_X_DISTANCE;
					right += MAJOR_X_DISTANCE;

					CustomFlowPanel w = new CustomFlowPanel();
					w.setLeft(Integer.toString(left));
					w.setTop(Integer.toString(startPix));
					w.setWidth(Integer.toString(right-left));
					w.setHeight(Integer.toString(endPix-startPix));
					w.addStyleName("course-div");
				}
			}
			for(CustomFlowPanel w : m_divs)
				add(w);
		}

		@SuppressWarnings("unused")
		public Section getSection() {
			return m_section;
		}

		public void setFillColor(String color) {
			for(CustomFlowPanel w : m_divs) {
				w.setColor(color);
			}
		}
	}

	public ScheduleViewWidget(Schedule s, RandomColorGenerator g) {
		setWidth("600");
		setHeight("400");
		m_schedule = s;
		m_generator = g;
		//ArrayList<Section> sections = s.getSections();
		m_background = new ScheduleBackgroundWidget(m_schedule);
		add(m_background);
		ArrayList<Section> sections = s.getSections();
		for(Section section : sections) {
			SectionGroupWidget w = new SectionGroupWidget(section);
			w.setFillColor(m_generator.randomlySelectColor(section.getParent()));
			add(w);
		}
	}
}
