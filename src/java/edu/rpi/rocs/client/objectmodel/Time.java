package edu.rpi.rocs.client.objectmodel;

import java.io.Serializable;

public class Time implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4616578540464471267L;
	
	private int hour;
	private int minute;
	
	public Time() {
		hour = 0;
		minute = 0;
	}
	
	public Time(int h, int m) {
		setHour(h);
		setMinute(m);
	}
	
	public Object clone() {
		return new Time(this.hour, this.minute);
	}
	
	public Time(String val) {
		if(val.length()==3) {
			val = "0" + val;
		}
		if(val.length()!=4) {
			hour = 0;
			minute = 0;
			return;
		}
		hour = Integer.valueOf(val.substring(0, 2)).intValue();
		minute = Integer.valueOf(val.substring(2,4)).intValue();
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public int getAbsMinute() {
		return 60*hour + minute;
	}
	
	public void setHour(int h) {
		hour = h % 24;
	}
	
	public void setMinute(int m) {
		minute = m % 60;
	}
}
