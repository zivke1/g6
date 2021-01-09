package echoServer;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.NextStages;
import util.SimulationDetails;
/**
 * 
 * simulating sending sms and email to the costumer(if they got out of the waiting list or their order is for tomorrow)
 *
 */
public class SimulationController {

	@FXML
	private AnchorPane anchor;

	@FXML
	private Text sms;

	@FXML
	private Label msg;

	@FXML
	private Text orderID;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button helpBtn;

	@FXML
	private Button backBtn;

	@FXML
	private Text phoneNumber;

	@FXML
	private Text emailAddress;

	@FXML
	private Label errorMsg;

	@FXML
	private Button cofirmBtn;

	@FXML
	private Label label;

	private String orderIDS;

	@FXML
	void cancelOrder(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(orderIDS);
		errorMsg.setText(mysqlConnection.CancelOrder(arr));
		cofirmBtn.setVisible(false);
		backBtn.setVisible(false);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void confirmOrder(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<>();
		arr.add(orderIDS);
		errorMsg.setText(mysqlConnection.WaitingForVisitOrder(arr));
		cofirmBtn.setVisible(false);
		backBtn.setVisible(false);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/echoServer/ContactUsPopUp.fxml", "Contact Us");
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	public void setDetails(String orderID, String phoneNum, String email, String msg) {
		this.orderIDS = orderID;
		if (phoneNum == null)
			sms.setVisible(false);
		else
			phoneNumber.setText(phoneNum);
		emailAddress.setText(email);
		label.setText(msg);
		this.orderID.setText(orderID);
	}

	public void hideAll() {
		Platform.runLater(() -> {
		cofirmBtn.setVisible(false);
		backBtn.setVisible(false);
		label.setText("We are sorry but you didn't respond in time\nand your order is been cancelled/expired");
		});
	}
}
