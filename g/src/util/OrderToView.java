package util;
import java.io.Serializable;

import java.sql.Time;
import java.util.Date;

//import java.sql.Date;

/**
 * class to hold information about order for the table view existing orders
 * @author shani
 *
 */

public class OrderToView implements Serializable {

	private static final long serialVersionUID = 1L;
	private String orderID;
	private String status;
	private Date date;

	
	public OrderToView(String orderID, String status, Date date) 
	{
		this.orderID = orderID;
		this.status = status;
		this.date = date;
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

	public String getDate() {
		return Func.fixDate(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
