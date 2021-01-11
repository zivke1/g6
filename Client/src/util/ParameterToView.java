package util;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import clientTry.ClientMain;

import javafx.scene.control.Button;

/**
 * 
 * @author Idan
 *Class for table in ApproveParametersController class 
 *the table in GUI
 */
public class ParameterToView implements Serializable {

	private static final long serialVersionUID = 1L;
	private String parkName,parameter;
//	final private String reject="Deny",approve="Accept";
	private Button rejectButton;
	private Button approveButton;
	private Integer newValue;
	private Date from,to;
	private Timestamp request;
	public ParameterToView(String parkName, String parameter, int newValue, Date from,
			Date to, Timestamp request,Button rej,Button app) {
		super();
		this.parkName = parkName;
		this.parameter = parameter;	
		this.newValue = newValue;
		this.from = from;
		this.to = to;
		this.request = request;
		this.rejectButton=rej;
		this.approveButton=app;
		
	}
	
	public ParameterToView()
	{
		
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public int getNewValue() {
		return newValue;
	}
	public Button getRejectButton() {
		return rejectButton;
	}
	public void setRejectButton(Button rejectButton) {
		this.rejectButton = rejectButton;
	
		rejPressed();
	}
	public Button getApproveButton() {
		return approveButton;
	}
	public void setApproveButton(Button approveButton) {
		this.approveButton = approveButton;
		appPressed();
	}
	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}
	public String getFrom() {
		if(from!=null)
		return Func.fixDate(from);
		else return "                          ";
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public String getTo() {
		if(to!=null)
		return Func.fixDate(to);
		else return "               ";
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getRequest() {
		return Func.fixDate(request);
	}
	public void setRequest(Timestamp request) {
		this.request = request;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ParameterToView [parkName=" + parkName + ", parameter=" + parameter + ", rejectButton=" + rejectButton
				+ ", approveButton=" + approveButton + ", newValue=" + newValue + ", from=" + from + ", to=" + to
				+ ", request=" + request + "]";
	}
	
	public void appPressed()
	{
		approveButton.setOnMouseClicked(t->{
			ArrayList<String> arr= new ArrayList<>();
			arr.add("SetPara");
			arr.add("y");
			arr.add(parkName);
			arr.add(parameter);
			arr.add(newValue.toString());
			arr.add(request.toString());
			if(parameter.equals("discount")) {
				arr.add(from.toString());
				arr.add(to.toString());
			}
			ClientMain.chat.accept(arr);
			approveButton.setVisible(false);
			rejectButton.setVisible(false);
		});
	}
	public void rejPressed()
	{
		rejectButton.setOnMouseClicked(t->{
			ArrayList<String> arr= new ArrayList<>();
			arr.add("SetPara");
			arr.add("n");
			arr.add(parkName);
			arr.add(parameter);
			arr.add(newValue.toString());
			arr.add(request.toString());
			
			ClientMain.chat.accept(arr);
	
			approveButton.setVisible(false);
			rejectButton.setVisible(false);
		});
	}
}