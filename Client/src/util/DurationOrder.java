package util;

import java.io.Serializable;
/**
 * Class to save information about visit
 * for duration report
 *
 */
public class DurationOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type;
	private int duration,amount;
	public DurationOrder(String type, int duration, int amount) {
		super();
		this.type = type;
		this.duration = duration;
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
