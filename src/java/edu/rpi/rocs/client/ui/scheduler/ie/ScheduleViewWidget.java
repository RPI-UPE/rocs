package edu.rpi.rocs.client.ui.scheduler.ie;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;
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

	Schedule m_schedule=null;
	ScheduleBackgroundWidget m_background=null;
	private static final int X_OFFSET=23;
	private static final int Y_OFFSET=15;
	private static final int MAJOR_Y_DISTANCE=24;
	private static final int MAJOR_X_DISTANCE=80;
	//private static final int MINOR_Y_DISTANCE=13;
	RandomColorGenerator m_generator=null;
	
	private class CustomFlowPanel extends FlowPanel implements HasClickHandlers {
		CustomFlowPanel() {
			super();
			this.addStyleName("with-cursor");
		}
		
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

		public HandlerRegistration addClickHandler(ClickHandler arg0) {
			return addDomHandler(arg0, ClickEvent.getType());
		}
	}

	private class ScheduleBackgroundWidget extends HTMLTableList {
		HTMLTableListRow m_header=null;

		private void createBackground() {
			setStyleName("schedule");
			m_header = new HTMLTableListRow();
			m_header.setStyleName("header");
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
					HTMLTableListCell temp = new HTMLTableListCell(true);
					temp.addStyleName("firstcol");
					temp.setHTML("&nbsp;");
					entry.add(temp);
					entry.addStyleName("t30");
				}
				for(int col=0;col<7;col++) {
					HTMLTableListCell temp = new HTMLTableListCell();
					temp.setHTML("&nbsp;");
					entry.add(temp);
				}
				add(entry);
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
	
	class CustomClickHandler implements ClickHandler {
		Section section;
		
		CustomClickHandler(Section s) { 
			section = s;
		}

		public void onClick(ClickEvent arg0) {
			SchedulerDisplayPanel.getInstance().getInfoPanel().displayInfoForSection(section);
		}
	}

	private class SectionGroupWidget extends FlowPanel {
		private ArrayList<CustomFlowPanel> m_divs=null;
		private Section m_section=null;

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
				startPix += Y_OFFSET;
				endPix += Y_OFFSET;

				for(Integer day : p.getDays()) {
					int d = day.intValue();
					int left = (MAJOR_X_DISTANCE+3)*d;
					int right = (MAJOR_X_DISTANCE+3)*(d+1);

					// Adjust for header column
					left += X_OFFSET-1;
					right += X_OFFSET-3;

					CustomFlowPanel w = new CustomFlowPanel();
					w.setLeft(Integer.toString(left)+"px");
					w.setTop(Integer.toString(startPix)+"px");
					w.setWidth(Integer.toString(right-left)+"px");
					w.setHeight(Integer.toString(endPix-startPix)+"px");
					w.addStyleName("course-div");
					Label lbl = new Label(s.getParent().getId());
					w.add(lbl);
					w.addClickHandler(new CustomClickHandler(s));
					m_divs.add(w);
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
		
		public ArrayList<CustomFlowPanel> getWidgets() {
			return m_divs;
		}
	}

	public ScheduleViewWidget(Schedule s, RandomColorGenerator g) {
		setWidth("600");
		setHeight("400");
		setStyleName("schedule-wrapper");
		m_schedule = s;
		m_generator = g;
		//ArrayList<Section> sections = s.getSections();
		m_background = new ScheduleBackgroundWidget(m_schedule);
		add(m_background);
		ArrayList<Section> sections = s.getSections();
		for(Section section : sections) {
			SectionGroupWidget w = new SectionGroupWidget(section);
			w.setFillColor(m_generator.randomlySelectColor(section.getParent()));
			ArrayList<CustomFlowPanel> widgets = w.getWidgets();
			for(CustomFlowPanel widget : widgets)
				add(widget);
		}
	}
}
