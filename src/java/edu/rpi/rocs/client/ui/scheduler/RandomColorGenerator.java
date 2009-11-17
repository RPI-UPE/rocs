package edu.rpi.rocs.client.ui.scheduler;

import java.util.ArrayList;

public class RandomColorGenerator {
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
	
	RandomColorGenerator() {
		resetColorsAvailable();
	}
}
