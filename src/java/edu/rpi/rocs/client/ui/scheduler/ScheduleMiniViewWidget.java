package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import edu.rpi.rocs.client.objectmodel.Period;
import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.objectmodel.Section;
import edu.rpi.rocs.client.ui.svg.SVGCanvasWidget;
import edu.rpi.rocs.client.ui.svg.SVGRectWidget;

public class ScheduleMiniViewWidget extends SVGCanvasWidget implements
		HasMouseOutHandlers, HasMouseOverHandlers, HasClickHandlers {

	Schedule m_schedule;
	SVGRectWidget m_background;
	ArrayList<SVGRectWidget> m_classes=new ArrayList<SVGRectWidget>();
	RandomColorGenerator m_generator;

	public ScheduleMiniViewWidget(Schedule s, RandomColorGenerator g) {
		super("80","94");
		m_schedule = s;
		m_generator = g;
		m_background = new SVGRectWidget();
		m_background.setX("0");
		m_background.setY("0");
		m_background.setWidth("80");
		m_background.setHeight(Integer.toString(6*(22-8)+10));
		m_background.setStrokeColor("#000000");
		m_background.setStrokeWidth("1");
		m_background.setFillColor("#ffffff");
		ArrayList<Section> sections = m_schedule.getSections();
		for(Section section : sections) {
			ArrayList<Period> periods = section.getPeriods();
			String color = m_generator.randomlySelectColor(section.getParent());
			for(Period p : periods) {
				int start = p.getStart().getAbsMinute();
				int end = p.getEnd().getAbsMinute();
				start /= 10;
				end /= 10;
				start -= 8*6;
				end -= 8*6;
				Iterator<Integer> d = p.getDays().iterator();
				while(d.hasNext()) {
					int day = d.next().intValue();
					SVGRectWidget rect = new SVGRectWidget();
					rect.setX(Integer.toString(day*10+5));
					rect.setY(Integer.toString(start+5));
					rect.setWidth("10");
					rect.setHeight(Integer.toString(end-start));
					rect.setFillColor(color);
					rect.setStrokeColor("#000000");
					rect.setStrokeWidth("1");
					m_classes.add(rect);
				}
			}
		}
		addSVGElement(m_background);
		for(SVGRectWidget w : m_classes) {
			addSVGElement(w);
		}
		addMouseOverHandler(new MouseOverHandler() {

			public void onMouseOver(MouseOverEvent arg0) {
				m_background.setStrokeColor("#b8b8b8");
				m_background.setStrokeWidth("2");
			}
			
		});
		addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent arg0) {
				m_background.setStrokeColor("#000000");
				m_background.setStrokeWidth("1");
			}
		});
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler arg0) {
		return addDomHandler(arg0, MouseOutEvent.getType());
	}

	public HandlerRegistration addMouseOverHandler(MouseOverHandler arg0) {
		return addDomHandler(arg0, MouseOverEvent.getType());
	}

	public HandlerRegistration addClickHandler(ClickHandler arg0) {
		return addDomHandler(arg0, ClickEvent.getType());
	}

}
