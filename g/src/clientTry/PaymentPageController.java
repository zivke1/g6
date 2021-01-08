package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NextStages;

/**
 * this page show the user the order details and the price of the order and the
 * user need to confirm and pay
 * 
 * @author zivke
 *
 */
public class PaymentPageController {
	String m_fName, m_lName, m_role, m_userID, m_parkName, m_price, m_orderNumber;
	ArrayList<String> m_inviteDetails;
	MouseEvent m_event, m_eventMain;
	String m_orderDetails = "";
	boolean m_occasional;
	@FXML
	private ImageView imgContactUs;

	@FXML
	private Label txtCrumViaHomePageLabel;

	@FXML
	private Label txtCrumLabel;

	@FXML
	private Button backBtn;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Text totalP;

	@FXML
	private Text dateLabel;

	@FXML
	private Text parkNameLabel;

	@FXML
	private Text numberOfvisitorsLabel;

	@FXML
	private Text emailLabel;

	@FXML
	private Label informationLabel;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();
	}

	/**
	 * set the invite in the DB
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void finishOrderClicked(MouseEvent event) throws IOException {
		if (m_price.equals("") == false) {
			m_inviteDetails.add(m_price);
		}
		m_inviteDetails.add(0, "setInvite");
		ClientMain.chat.accept(m_inviteDetails);
		m_orderNumber = ChatClient.dataInArrayList.get(0);
		// TODO open the next page

		// open the next page
//		BorderPane borderPane = null;
//		FXMLLoader loader = new FXMLLoader();
//		Stage primaryStage = new Stage();
		FXMLLoader loader;

//		loader.setLocation(getClass().getResource("../fxmlFiles/OrderConfirmed.fxml"));
//		borderPane = loader.load();
		NextStages nextStages = new NextStages("/fxmlFiles/OrderConfirmed.fxml", "Order Confirmed", m_userID);
		loader = nextStages.goToNextStage(event);
		OrderConfirmedController orderConfirmedController = loader.getController();
		orderConfirmedController.setMainPage(m_eventMain);
		orderConfirmedController.setOrderNumber(m_orderNumber);
		orderConfirmedController.setOccasional(m_occasional);
//		Scene scene = new Scene(borderPane);
//		primaryStage.setTitle("Order Confirmed");
//		primaryStage.setScene(scene);
//		primaryStage.setOnCloseRequest(evt -> {
//			if (ClientMain.chat.checkConnection()) {
//				ArrayList<String> arr = new ArrayList<String>();
//				arr.add("closeAndSetIdNull");
//				arr.add(m_userID);
//				ClientMain.chat.accept(arr);
//				ClientMain.chat.stopConnection();
//			}
//		});
//		((Node) event.getSource()).getScene().getWindow().hide();
//		primaryStage.show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", m_userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page show the amount of order \nand order's details"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		m_fName = fName;
		m_lName = lName;
		m_role = role;
		m_userID = userID;
		m_parkName = parkName;

	}

	public void setOrderDetails(ArrayList<String> inviteDetails, String price) {
		m_inviteDetails = inviteDetails;
		m_price = price;
		if (m_price.equals("") == false) {

			totalP.setText(m_price+"$");
		} else {
			if (m_inviteDetails.get(8).equals("payBefore") == false) {
				totalP.setText(m_inviteDetails.get(8)+"$");
			}else {
				totalP.setText(m_inviteDetails.get(9)+"$");
			}
		}
		String temp = m_inviteDetails.get(3);
		String date = temp.substring(8, 10) + temp.substring(4, 8) + temp.substring(0, 4);

		dateLabel.setText(date + " At " + m_inviteDetails.get(2));
		parkNameLabel.setText(m_inviteDetails.get(1));
		numberOfvisitorsLabel.setText(m_inviteDetails.get(4) + " people");
		emailLabel.setText(m_inviteDetails.get(5));
	}

	public void setPreviousPage(MouseEvent event) {

		m_event = event;
	}

	public void setMainPage(MouseEvent eventMain) {

		m_eventMain = eventMain;
	}

	public void setOrderDetails(String orderDetails) {
		m_orderDetails = orderDetails;
		informationLabel.setText(m_orderDetails);
	}

	public void setOccasional(boolean occasional) {
		// TODO Auto-generated method stub
		m_occasional = occasional;
		if (m_occasional) {
			txtCrumLabel.setVisible(true);
		} else {
			txtCrumViaHomePageLabel.setVisible(true);
		}
	}

}
