package util;
/**
 * details of an order that needs a 1 day before the visit simulation
 * 
 *
 */
public class SimulationDetails {
	
	private String email,phoneNum,orderID;

	public SimulationDetails(String email, String phoneNum,String orderID) {
		this.email = email;
		this.phoneNum = phoneNum;
		this.orderID=orderID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() { 
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	
}
