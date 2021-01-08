package util;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
 
/**
 * class to hold information about order for the table view existing orders
 * @author shani
 *
 */
public class OrderToView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userID;
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
	
	public OrderToView(String userID, String orderID, String status, Date date) {
		this.userID = userID;
		this.orderID = orderID;
		this.status = status;
		this.date = date;
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
	

	
	

}
