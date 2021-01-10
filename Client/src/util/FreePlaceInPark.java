package util;

import java.io.Serializable;
/**
 * Class save date and time of free space
 * for use of waiting list 
 */
public class FreePlaceInPark implements Serializable {

	private static final long serialVersionUID = 1L;
	String time, date;
	
	
	public FreePlaceInPark(String time,String date) {
	this.time = time;
	this.date = date;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
