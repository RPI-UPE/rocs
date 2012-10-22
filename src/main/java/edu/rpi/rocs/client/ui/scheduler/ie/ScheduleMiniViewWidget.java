package edu.rpi.rocs.client.ui.scheduler.ie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.ui.scheduler.RandomColorGenerator;

public class ScheduleMiniViewWidget extends FlowPanel implements HasClickHandlers {

	Schedule m_schedule=null;
	ArrayList<CustomFlowPanel> m_classes=new ArrayList<CustomFlowPanel>();
	RandomColorGenerator m_generator=null;

	public ScheduleMiniViewWidget(Schedule s, RandomColorGenerator g) {
		setWidth("80px");
		setHeight("84px");
		m_schedule = s;
		m_generator = g;
		setStyleName("schedule-mini-widget");
		ArrayList<Section> sections = m_schedule.getSections();
		for(Section section : sections) {
			List<Period> periods = section.getPeriods();
			String color = m_generator.randomlySelectColor(section.getParent());
			for(Period p : periods) {
				if(p.wasDeleted()) continue;
				int start = p.getStart().getAbsMinute();
				int end = p.getEnd().getAbsMinute();
				start /= 10;
				end /= 10;
				start -= 8*6;
				end -= 8*6;
				Iterator<Integer> d = p.getDays().iterator();
				while(d.hasNext()) {
					int day = d.next().intValue();
					CustomFlowPanel w = new CustomFlowPanel();
					w.setPosition(Integer.toString(day*10+5)+"px", Integer.toString(start+5)+"px");
					w.setSize("8px", Integer.toString(end-start-2)+"px");
					w.setBackgroundColor(color);
					m_classes.add(w);
				}
			}
		}
		for(CustomFlowPanel w : m_classes) {
			add(w);
		}
	}

	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		return addDomHandler(arg0, ClickEvent.getType());
	}

}
