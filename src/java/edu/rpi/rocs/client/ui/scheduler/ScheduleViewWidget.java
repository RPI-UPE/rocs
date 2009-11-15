package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

import edu.rpi.rocs.client.objectmodel.Schedule;
import edu.rpi.rocs.client.ui.filters.ScheduleTimeBlockFilterWidget;
import edu.rpi.rocs.client.ui.svg.SVGCanvasWidget;
import edu.rpi.rocs.client.ui.svg.SVGGroupWidget;
import edu.rpi.rocs.client.ui.svg.SVGPathComponent;
import edu.rpi.rocs.client.ui.svg.SVGPathWidget;
import edu.rpi.rocs.client.ui.svg.SVGRectWidget;
import edu.rpi.rocs.client.ui.svg.SVGTextWidget;
import edu.rpi.rocs.client.ui.svg.IsSVGText.TextAnchor;

public class ScheduleViewWidget extends SVGCanvasWidget {

	Schedule m_schedule;
	ScheduleBackgroundWidget m_background;
	
	static String[] colors = {
		"#808080",
		"#8080b8",
		"#8080ff",
		"#80b880",
		"#80b8b8",
		"#80b8ff",
		"#80ff80",
		"#80ffb8",
		"#80ffff",
		"#b88080",
		"#b880b8",
		"#b880ff",
		"#b8b880",
		"#b8b8ff",
		"#b8ff80",
		"#b8ffb8",
		"#b8ffff",
		"#ff8080",
		"#ff80b8",
		"#ff80ff",
		"#ffb880",
		"#ffb8b8",
		"#ffb8ff",
		"#ffff80",
		"#ffffb8"
	};
	
	private ArrayList<Integer> m_colors_available;
	
	String randomlySelectColor() {
		int i = (int)(m_colors_available.size() * Math.random());
		i = m_colors_available.get(i);
		m_colors_available.remove(new Integer(i));
		return colors[i];
	}
	
	void resetColorsAvailable() {
		m_colors_available = new ArrayList<Integer>();
		for(int i=0;i<colors.length;i++) {
			m_colors_available.add(new Integer(i));
		}
	}
	
	private class ScheduleBackgroundWidget extends SVGGroupWidget {
		SVGPathWidget m_solid_lines;
		SVGPathWidget m_dashed_lines;
		SVGRectWidget m_background;
		SVGGroupWidget m_x_labels;
		SVGGroupWidget m_y_labels;
		
		public ScheduleBackgroundWidget() {
			m_background = new SVGRectWidget();
			m_background.setX("0");
			m_background.setY("0");
			m_background.setWidth("600");
			m_background.setHeight("390");
			m_background.setFillColor("#ffffff");
			m_background.setStrokeColor("#000000");
			m_background.setStrokeWidth("1");
			addSVGElement(m_background);
			m_solid_lines = new SVGPathWidget();
			ArrayList<SVGPathComponent> temp = new ArrayList<SVGPathComponent>();
			for(int i=0;i<14;i++) {
				temp.add(SVGPathComponent.createMovePathComponent("0", Integer.toString((i+1)*26), false));
				temp.add(SVGPathComponent.createLinePathComponent("600", "0", true));
			}
			for(int i=0;i<7;i++) {
				temp.add(SVGPathComponent.createMovePathComponent(Integer.toString((i+1)*75), "0", false));
				temp.add(SVGPathComponent.createLinePathComponent("0", "390", true));
			}
			m_solid_lines.addAll(temp);
			m_solid_lines.setStrokeColor("#000000");
			m_solid_lines.setStrokeWidth("1");
			addSVGElement(m_solid_lines);
			m_dashed_lines = new SVGPathWidget();
			temp = new ArrayList<SVGPathComponent>();
			for(int i=0;i<14;i++) {
				temp.add(SVGPathComponent.createMovePathComponent("75", Integer.toString((i+1)*26+13), false));
				temp.add(SVGPathComponent.createLinePathComponent("525", "0", true));
			}
			m_dashed_lines.addAll(temp);
			m_dashed_lines.setStrokeColor("#888888");
			m_dashed_lines.setStrokeWidth("1");
			ArrayList<String> dash = new ArrayList<String>();
			dash.add("4");
			dash.add("4");
			m_dashed_lines.setStrokeDashArray(dash);
			addSVGElement(m_dashed_lines);
			m_y_labels = new SVGGroupWidget();
			for(int i=0;i<7;i++) {
				SVGTextWidget text = new SVGTextWidget();
				text.setFillColor("#000000");
				text.setTextAnchor(TextAnchor.Middle);
				text.setText(ScheduleTimeBlockFilterWidget.dayOfWeek(i));
				text.setX(Integer.toString((i+1)*75+75/2));
				text.setY("18");
				text.setFontSize("18");
				m_y_labels.addSVGElement(text);
			}
			addSVGElement(m_y_labels);
			m_x_labels = new SVGGroupWidget();
			for(int i=0;i<14;i++) {
				SVGTextWidget text = new SVGTextWidget();
				text.setFillColor("#000000");
				text.setTextAnchor(TextAnchor.End);
				text.setText(Integer.toString(i+8));
				text.setX("72");
				text.setY(Integer.toString((i+1)*26+18));
				text.setFontSize("18");
				m_x_labels.addSVGElement(text);
			}
			addSVGElement(m_x_labels);
		}
	}
	
	private class SectionGroupWidget extends SVGGroupWidget {
		private String m_identifier;
		private ArrayList<SVGRectWidget> m_rects;
		private ArrayList<SVGTextWidget> m_text;
		private String m_color;
		
		public SectionGroupWidget(boolean twohr) {
			if(twohr) {
				m_identifier = "CSCI 4210";
				m_color="#ffffff";
				m_rects = new ArrayList<SVGRectWidget>();
				m_text = new ArrayList<SVGTextWidget>();
				SVGRectWidget rect = new SVGRectWidget();
				rect.setX("150");
				rect.setY("78");
				rect.setWidth("75");
				rect.setHeight("48");
				rect.setFillColor(m_color);
				rect.setStrokeColor("#000000");
				rect.setStrokeWidth("1");
				m_rects.add(rect);
				rect = new SVGRectWidget();
				rect.setX("375");
				rect.setY("78");
				rect.setWidth("75");
				rect.setHeight("48");
				rect.setFillColor(m_color);
				rect.setStrokeColor("#000000");
				rect.setStrokeWidth("1");
				m_rects.add(rect);
				SVGTextWidget text = new SVGTextWidget();
				text.setX(Integer.toString(150+76/2));
				text.setY(Integer.toString(78+35/2));
				text.setFontSize("14");
				text.setTextAnchor(TextAnchor.Middle);
				text.setText(m_identifier);
				m_text.add(text);
				text = new SVGTextWidget();
				text.setX(Integer.toString(375+76/2));
				text.setY(Integer.toString(78+35/2));
				text.setFontSize("14");
				text.setTextAnchor(TextAnchor.Middle);
				text.setText(m_identifier);
				m_text.add(text);
				for(SVGRectWidget w : m_rects)
					addSVGElement(w);
				for(SVGTextWidget w : m_text)
					addSVGElement(w);
			}
			else {
				m_identifier = "CSCI 4320";
				m_color="#ffffff";
				m_rects = new ArrayList<SVGRectWidget>();
				m_text = new ArrayList<SVGTextWidget>();
				SVGRectWidget rect = new SVGRectWidget();
				rect.setX("150");
				rect.setY("130");
				rect.setWidth("75");
				rect.setHeight("35");
				rect.setFillColor(m_color);
				rect.setStrokeColor("#000000");
				rect.setStrokeWidth("1");
				m_rects.add(rect);
				rect = new SVGRectWidget();
				rect.setX("375");
				rect.setY("130");
				rect.setWidth("75");
				rect.setHeight("35");
				rect.setFillColor(m_color);
				rect.setStrokeColor("#000000");
				rect.setStrokeWidth("1");
				m_rects.add(rect);
				SVGTextWidget text = new SVGTextWidget();
				text.setX(Integer.toString(150+76/2));
				text.setY(Integer.toString(130+35/2));
				text.setFontSize("14");
				text.setTextAnchor(TextAnchor.Middle);
				text.setText(m_identifier);
				m_text.add(text);
				text = new SVGTextWidget();
				text.setX(Integer.toString(375+76/2));
				text.setY(Integer.toString(130+35/2));
				text.setFontSize("14");
				text.setTextAnchor(TextAnchor.Middle);
				text.setText(m_identifier);
				m_text.add(text);
				for(SVGRectWidget w : m_rects)
					addSVGElement(w);
				for(SVGTextWidget w : m_text)
					addSVGElement(w);
			}
		}
		
		public void setFillColor(String color) {
			super.setFillColor(color);
			m_color = color;
			for(SVGRectWidget w : m_rects)
				w.setFillColor(color);
		}
	}
	
	public ScheduleViewWidget(Schedule s) {
		super("schedule_viewer", "600", "400");
		resetColorsAvailable();
		m_schedule = s;
		//ArrayList<Section> sections = s.getSections();
		m_background = new ScheduleBackgroundWidget();
		addSVGElement(m_background);
		SectionGroupWidget w = new SectionGroupWidget(true);
		w.setFillColor(randomlySelectColor());
		addSVGElement(w);
		w = new SectionGroupWidget(false);
		w.setFillColor(randomlySelectColor());
		addSVGElement(w);
	}
}
