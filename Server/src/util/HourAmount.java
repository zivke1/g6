package util;

import java.io.Serializable;
/**
 * Class holds information about order
 * for entrance report
 *
 */
public class HourAmount implements Serializable{
 
	private static final long serialVersionUID = 1L;
private String hour;
 private int amount;
 
public HourAmount(String hour, int amount) {
	super();
	this.hour = hour;
	this.amount = amount;
}
public String getHour() {
	return hour;
}
public void setHour(String hour) {
	this.hour = hour;
}
public int getAmount() {
	return amount;
}
public void setAmount(int amount) {
	this.amount = amount;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + amount;
	result = prime * result + ((hour == null) ? 0 : hour.hashCode());
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
	HourAmount other = (HourAmount) obj;
	if (amount != other.amount)
		return false;
	if (hour == null) {
		if (other.hour != null)
			return false;
	} else if (!hour.equals(other.hour))
		return false;
	return true;
}

}
