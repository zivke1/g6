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
}
