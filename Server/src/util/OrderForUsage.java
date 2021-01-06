package util;

import java.sql.Date;
import java.sql.Time;

public class OrderForUsage {
	private Date visitDate;
	private Time enterTime;
	private Time exitTime;
	private int visitorsAmount;

	public OrderForUsage(Date visitDate, Time enterTime, Time exitTime, int visitorsAmount) {
		super();
		this.visitDate = visitDate;
		this.enterTime = enterTime;
		this.exitTime = exitTime;
		this.visitorsAmount = visitorsAmount;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Time getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Time enterTime) {
		this.enterTime = enterTime;
	}

	public Time getExitTime() {
		return exitTime;
	}

	public void setExitTime(Time exitTime) {
		this.exitTime = exitTime;
	}

	public int getVisitorsAmount() {
		return visitorsAmount;
	}

	public void setVisitorsAmount(int visitorsAmount) {
		this.visitorsAmount = visitorsAmount;
	}

}