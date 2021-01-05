package util;

import java.io.Serializable;

public class HourAmount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hour;
	private int amount;

	public String getHour() { 
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public HourAmount(String hour, int amount) {
		super();
		this.hour = hour;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "HourAmount [hour=" + hour + ", amount=" + amount + "]";
	}
	
}
