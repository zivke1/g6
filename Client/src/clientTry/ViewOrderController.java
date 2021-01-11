package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Func;
import util.NextStages;
import util.Role;

/**
 * 
 * with this page the user can see his order details
 *
 */
public class ViewOrderController {

	@FXML
	private Label errorMsg;

	@FXML
	private Label customerOrder;

	@FXML
	private Label employeeOrder;

	@FXML
	private Button helpBtn;

	@FXML
	private Label typeOfOr;

	@FXML
	private Label pName;

	@FXML
	private Label amOfVisit;

	@FXML
	private Label date;

	@FXML
	private Label hour;

	@FXML
	private Label typeOfOrder;

	@FXML
	private Label numVisit;

	@FXML
	private Label email;

	@FXML
	private Label orderID;

	@FXML
	private Label orderStatus;

	@FXML
	private Label totalCost;

	@FXML
	private Button cancelBtn;

	@FXML
	private AnchorPane anchorAll;

	private String fNameH;

	private String lNameH;

	private String roleH;

	private String userIDH;

	private String parkNameH;

	private String orderIDH;

	private String orderStatusH;

	MouseEvent m_event;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();
	}

	/**
	 * if the user wants to cancel the order
	 * @param event
	 */
	@FXML
	void cancelOrder(MouseEvent event) {
		if (orderStatusH.equals("Waiting List") || orderStatusH.equals("Waiting To Approve")
				|| orderStatusH.equals("Waiting To Visit")) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("CancelOrder");
			arr.add(orderIDH);
			ClientMain.chat.accept(arr);
			cancelBtn.setVisible(false);
			orderStatus.setText("cancelled");
			errorMsg.setText(ChatClient.dataInArrayList.get(0));
		} else
			errorMsg.setText("Cannot cancel active/finished/exipred/cancelled order");
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", userIDH);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page show the order details \n"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");
		helpBtn.setTooltip(tt);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName, String orderID) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		this.orderIDH = orderID;

		if (roleH.equals(Role.Member.toString().toLowerCase()) || roleH.equals(Role.User.toString().toLowerCase())
				|| roleH.equals(Role.Guide.toString().toLowerCase())) {
			cancelBtn.setVisible(true);
			customerOrder.setVisible(true);
			employeeOrder.setVisible(false);
		} else {
			cancelBtn.setVisible(false);
			customerOrder.setVisible(false);
			employeeOrder.setVisible(true);
		}
		ArrayList<String> arr = new ArrayList<>();
		arr.add("ViewOrder");
		arr.add(orderID);
		ClientMain.chat.accept(arr);
		arr = ChatClient.dataInArrayList;
		this.orderID.setText(arr.get(0));
		pName.setText(arr.get(1));
		hour.setText(arr.get(2));
		date.setText(Func.fixDateString(arr.get(3)));
		numVisit.setText(arr.get(4));
		typeOfOrder.setText(arr.get(5) + "'s order");
		if (arr.get(6).equals("waitingToVisit"))
			orderStatus.setText("Waiting To Visit");
		else if (arr.get(6).equals("waitingToApprove"))
			orderStatus.setText("Waiting To Approve");
		else if (arr.get(6).equals("waitingList"))
			orderStatus.setText("Waiting List");
		else
			orderStatus.setText(arr.get(6));
		this.orderStatusH = orderStatus.getText();
		totalCost.setText(arr.get(7) + "$");
		email.setText(arr.get(8));
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}
