package edu.rpi.rocs.client.ui.svg;

public class SVGPathComponent {
	public enum Type {
		MoveTo,
		LineTo,
		ClosePath,
		HorizontalLineTo,
		VerticalLineTo,
		CurveTo,
		SmoothCurveTo,
		QuadraticBezierCurveTo,
		SmoothQuadraticBezierCurveTo,
		EllipticalArcTo
	}
	
	protected boolean m_relative;
	protected Type m_type;
	
	private SVGPathComponent() {
		
	}

	public void setRelative(boolean rel) {
		m_relative = rel;
	}
	
	public boolean getRelative() {
		return m_relative;
	}
	
	public Type getType() {
		return m_type;
	}
	
	private static class SVGPathComponentMove extends SVGPathComponent {
		String m_x,m_y;
		
		public SVGPathComponentMove(String x, String y) {
			m_x = x;
			m_y = y;
			m_type = Type.MoveTo;
		}
		
		public String toString() {
			String s = "";
			if(m_relative) {
				s += "m ";
			}
			else {
				s += "M ";
			}
			s += m_x+","+m_y+" ";
			return s;
		}
	}
	
	public static SVGPathComponent createMovePathComponent(String x, String y, boolean relative) {
		SVGPathComponent o = new SVGPathComponentMove(x,y);
		o.setRelative(relative);
		return o;
	}
	
	private static class SVGPathComponentLine extends SVGPathComponent {
		String m_x,m_y;
		
		public SVGPathComponentLine(String x, String y) {
			m_x = x;
			m_y = y;
			m_type = Type.LineTo;
		}
		
		public String toString() {
			String s = "";
			if(m_relative) {
				s += "l ";
			}
			else {
				s += "L ";
			}
			s += m_x+","+m_y+" ";
			return s;
		}
	}
	
	public static SVGPathComponent createLinePathComponent(String x, String y, boolean relative) {
		SVGPathComponent o = new SVGPathComponentLine(x, y);
		o.setRelative(relative);
		return o;
	}
	
	private static class SVGPathComponentClose extends SVGPathComponent {
		public SVGPathComponentClose() {}
		
		public String toString() {
			if(m_relative) {
				return "z ";
			}
			else {
				return "Z ";
			}
		}
	}
	
	public static SVGPathComponent createClosePathComponent(boolean relative) {
		SVGPathComponent o = new SVGPathComponentClose();
		o.setRelative(relative);
		return o;
	}

	private static class SVGPathComponentArc extends SVGPathComponent {
		String m_x,m_y,m_rx,m_ry,m_xrot;
		boolean m_large, m_sweep;
		
		public SVGPathComponentArc(String rad_x, String rad_y, String x_rot, String end_x, String end_y, 
				boolean large, boolean sweep) {
			m_rx = rad_x;
			m_ry = rad_y;
			m_xrot = x_rot;
			m_x = end_x;
			m_y = end_y;
			m_large = large;
			m_sweep = sweep;
			m_type = Type.EllipticalArcTo;
		}
		
		public String toString() {
			String s = "";
			if(m_relative) {
				s += "a ";
			}
			else {
				s += "A ";
			}
			s += m_rx+","+m_ry+" ";
			s += m_xrot+" ";
			s += (m_large ? "1" : "0")+","+(m_sweep ? "1" : "0")+" ";
			s += m_x+","+m_y+" ";
			return s;
		}
	}

	public static SVGPathComponent createEllipticalPathComponent(String rad_x, String rad_y, String x_rot,
			String end_x, String end_y, boolean large, boolean sweep, boolean relative) {
		SVGPathComponent x = new SVGPathComponentArc(rad_x, rad_y, x_rot, end_x, end_y, large, sweep);
		x.setRelative(relative);
		return x;
	}
	
}
