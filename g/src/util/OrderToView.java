package util;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;


public class OrderToView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderID;
	private String status;
	private Date date;
//	private String orderType;
	//private String parkName;
//	private Time enterTime;
//	private int visitorsAmount;
//	private String email;
//	private boolean occasional;
	//private float totalCost;
	
	public OrderToView(String orderID, String status, Date date) 
	{
		this.orderID = orderID;
		this.status = status;
		this.date = date;
//		this.orderType = orderType;
	//	this.parkName = parkName;
	//	this.enterTime = enterTime;
	//	this.visitorsAmount = visitorsAmount;
	//	this.email = email;
	//	this.occasional = occasional;
	//	this.totalCost = totalCost;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
