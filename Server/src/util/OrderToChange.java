
package util;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class OrderToChange implements Comparable<OrderToChange> {

	/**
	 * 
	 */
	private String userID, orderID, pName, type, status, email;
	private Time expectedEnterTime;
	private java.sql.Date VisitDate;
	private int amount;
	private boolean Occasional;
	private float payment;
	private Timestamp enteredWaiting;
	private String phoneNum,msg;
	public OrderToChange(String userID, String orderID, String pName, String type, String status, String email,
			Time expectedEnterTime, java.sql.Date visitDate, int amount, boolean occasional, float payment,
			Timestamp enteredWaiting,String phoneNum,String msg) {
		this.userID = userID;
		this.orderID = orderID;
		this.pName = pName;
		this.type = type;
		this.status = status;
		this.email = email;
		this.expectedEnterTime = expectedEnterTime;
		VisitDate = visitDate;
		this.amount = amount;
		Occasional = occasional;
		this.payment = payment;
		this.enteredWaiting = enteredWaiting;
		this.phoneNum=phoneNum;
		this.msg=msg;
	}

	public float getPayment() {
		return payment;
	}

	public void setPayment(float payment) {
		this.payment = payment;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Time getExpectedEnterTime() {
		return expectedEnterTime;
	}

	public void setExpectedEnterTime(Time expectedEnterTime) {
		this.expectedEnterTime = expectedEnterTime;
	}

	public java.sql.Date getVisitDate() {
		return VisitDate;
	}

	public void setVisitDate(java.sql.Date visitDate) {
		VisitDate = visitDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public boolean isOccasional() {
		return Occasional;
	}

	public void setOccasional(boolean occasional) {
		Occasional = occasional;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public int compareTo(OrderToChange o) {
		if (enteredWaiting == null)
			return 0;
		return enteredWaiting.compareTo(o.enteredWaiting);

	}

}
