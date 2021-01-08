package util;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.TreeMap;
/**
 * 
 * class holds amount of people in park in a specific date
 * using it in the waiting list process
 *
 */
public class VisitorsInDate {
	Date day;
	int[] visitors = new int[24];

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public VisitorsInDate(Date day) {
		this.day=day;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VisitorsInDate other = (VisitorsInDate) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		return true;
	}

	public int[] getVisitors() {
		return visitors; 
	}

	public void setVisitors(int[] visitors) {
		this.visitors = visitors;
	}
	
	
	

}
