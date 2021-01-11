package clientTry;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

import javax.management.openmbean.OpenDataException;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.NextStages;
import util.Role;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * this controller manage invitation it get if the invite is occasional or not
 * and open waiting list page if this date and time full if not it will send you
 * to payment page
 * 
 *
 */
public class OrderController implements Initializable {
	
	@FXML
	private Label txtCrumViaHomePage;

	@FXML
	private Label txtCrum;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private	Button helpBtn;

	@FXML
	private Text guideWelcomeText;
    
	@FXML
    private Label MemberOrderlab;

	@FXML
	private ComboBox<String> parkNameCombo;

	@FXML
	private ComboBox<String> numberOfVistorsCombo;

	@FXML
	private DatePicker pickDatePicker;

	@FXML
	private ComboBox<String> hourCombo;

	@FXML
	private TextField emailTextFiled;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private Text notAllfieldFilledLabel;

	@FXML
	private CheckBox payTimeCheckBox;

	@FXML
	private Label emailNotVaild;

	String m_fName, m_lName, m_role, m_userID, m_parkName;
	String m_ownerUserID, m_status;
	int m_amountOfPeople;
	boolean m_occasional;
	ArrayList<String> invite;
	MouseEvent m_event, m_eventMain, m_previousPage;
	String m_backTo, m_orderDetails="";

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_previousPage.getSource()).getScene().getWindow()).show();
	}

	
	/**
	 * check if the order details right or not and check if there is a place in the park
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void finishOrderClicked(MouseEvent event) throws IOException {
		m_event = event;
		invite = new ArrayList<String>();
		invite.add("checkInvite");
		try {
			invite.add(m_ownerUserID);
			invite.add(parkNameCombo.getValue().toString());
			invite.add(hourCombo.getValue().toString());
			invite.add(pickDatePicker.getValue().toString());// check if the date is valid TODO 
			invite.add(numberOfVistorsCombo.getValue().toString());
			if (checkEmail(emailTextFiled.getText()) == false) {
				return;
			}
			emailNotVaild.setVisible(false);
			invite.add(emailTextFiled.getText());

			if (m_occasional == true) {
				invite.add("occasional");
			} else {
				invite.add("notOccasional");
			}
			invite.add(m_status);
			if (payTimeCheckBox.isSelected()) {
				invite.add("payBefore");
			}
		} catch (Exception e) {
			Platform.runLater(() -> {
				notAllfieldFilledLabel.setVisible(true);
				
			});
			return;
		}

		if (invite.contains("")) {
			notAllfieldFilledLabel.setVisible(true);
			return;
		} else {
			notAllfieldFilledLabel.setVisible(false);
			// TODO send and check if we have place
			ClientMain.chat.accept(invite);

			if (ChatClient.dataInArrayList.contains("TheParkIsFull")) {
				int size = ChatClient.dataInArrayList.size();
				m_orderDetails = ChatClient.dataInArrayList.get(size-1);
				ChatClient.dataInArrayList.remove(size-1);
				ChatClient.dataInArrayList.remove("TheParkIsFull");
				// TODO show the waiting list page
				openWaitingListPage();

			} else if (ChatClient.dataInArrayList.contains("InviteConfirm")) {
				ChatClient.dataInArrayList.remove("InviteConfirm");
				int size = ChatClient.dataInArrayList.size();
				m_orderDetails = ChatClient.dataInArrayList.get(size-1);
				ChatClient.dataInArrayList.remove(size-1);
				// show successful page and message to confirm the message
				OpenInviteConfirmPage();

			}
		//	System.out.println(invite);
		}

	}
/**
 * There is a place in the park hence we open confirm order page
 * @throws IOException
 */
	private void OpenInviteConfirmPage() throws IOException {
		// TODO Auto-generated method stub
//		BorderPane borderPane = null;
//		FXMLLoader loader = new FXMLLoader();
//		Stage primaryStage = new Stage();
		// Pane root =
		FXMLLoader loader ;
		// loader.load(getClass().getResource("../fxmlFiles/HomePageForEmployee.fxml").openStream());
     	NextStages nextStages = new NextStages("/fxmlFiles/PaymentPage.fxml", "Payment Page", m_userID);
    	loader = nextStages.goToNextStage(m_event);
//		loader.setLocation(getClass().getResource("../fxmlFiles/PaymentPage.fxml"));
//		borderPane = loader.load();
		PaymentPageController paymentPageController = loader.getController();
		paymentPageController.setDetails(m_fName, m_lName, m_role, m_userID, m_parkName);
		invite.remove(0);

		paymentPageController.setOrderDetails(invite, ChatClient.dataInArrayList.get(0));
		paymentPageController.setPreviousPage(m_event);
		paymentPageController.setMainPage(m_eventMain);
		paymentPageController.setOrderDetails(m_orderDetails);
		paymentPageController.setOccasional(m_occasional);
//		Scene scene = new Scene(borderPane);
//		primaryStage.setTitle("Home Page");
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
//		((Node) m_event.getSource()).getScene().getWindow().hide();
//		primaryStage.show();

	}
/**
 * The park is full and we open him a page that shows the
 *  free place and option to enter the waiting list
 * @throws IOException
 */
	private void openWaitingListPage() throws IOException {
		// TODO Auto-generated method stub
//		BorderPane borderPane = null;
//		FXMLLoader loader = new FXMLLoader();
//	
//		Stage primaryStage = new Stage();
		FXMLLoader loader;
		// Pane root =
		// loader.load(getClass().getResource("../fxmlFiles/HomePageForEmployee.fxml").openStream());

//		loader.setLocation(getClass().getResource("../fxmlFiles/WaitingList.fxml"));
//		borderPane = loader.load();
     	NextStages nextStages = new NextStages("/fxmlFiles/WaitingList.fxml", "Waiting List", m_userID);
    	loader = nextStages.goToNextStage(m_event);
		WaitingListController waitingListController = loader.getController();
		waitingListController.setDetails(m_fName, m_lName, m_role, m_userID, m_parkName);
		waitingListController.setMainPage(m_eventMain);
		waitingListController.setPreviousPage(m_event);
		invite.remove(0);
		waitingListController.setOrderDetails(m_orderDetails);
		waitingListController.setOrderDetails(invite, ChatClient.dataInArrayList.get(0));
//		Scene scene = new Scene(borderPane);
//		primaryStage.setTitle("Waiting List");
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
//		((Node) m_event.getSource()).getScene().getWindow().hide();
//		primaryStage.show();
	}

	private boolean checkEmail(String emailS) {
		if (!((emailS.split("@").length == 2) && (emailS.indexOf("@") != 0) && (emailS.indexOf(".") != 0)
				&& (emailS.lastIndexOf(".") != emailS.length() - 1) && (emailS.length() >= 5 && emailS.length() <= 30)
				&& (emailS.indexOf("@") != emailS.indexOf(".") + 1 || emailS.indexOf("@") != (emailS.indexOf(".") + 1))
				&& emailS.contains(".") && emailS.lastIndexOf('.') > emailS.indexOf('@')) || emailS.length() == 0) {
			emailNotVaild.setVisible(true);
			return false;
		}
		return true;
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page is intended \nfor placing an order"); // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	ObservableList<String> list;

	// creating list of number of visitors
	/**
	 * set in the combo box the number of people the user can invite for them
	 * 
	 * @param num
	 */
	private void setNumberOfVistors(int num) {
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 1; i <= num; i++) {
			al.add(String.valueOf(i));
		}

		list = FXCollections.observableArrayList(al);
		numberOfVistorsCombo.setItems(list);
	}

	// we need to call this function when we know the park the costumer chose
	/**
	 * set the time the user can invite if the invite is occasional the employee can
	 * set only now
	 * 
	 * @param fromTime
	 * @param toTime
	 */
	private void setHourCombo(Time fromTime, Time toTime) {
		ArrayList<String> al = new ArrayList<String>();
		if (fromTime != null) {
			while (fromTime.compareTo(toTime) < 0) {
				al.add(String.valueOf(fromTime.toString()));
				fromTime.setHours(fromTime.getHours() + 1);

			}
			list = FXCollections.observableArrayList(al);
			hourCombo.setItems(list);
		} else {
			Date currentDate = new Date();
			String today = currentDate.toString();
			String[] result = today.split(" ");
			String time = result[3].substring(0, 8);
			al.add(String.valueOf(time));
			list = FXCollections.observableArrayList(al);
			hourCombo.setItems(list);
			hourCombo.setValue(time);
		}
	}

	private void setParkCombo(ArrayList<String> parkList) {
		// TODO we need in the initialize func to get the list of park name and send it
		// to here
		list = FXCollections.observableArrayList(parkList);
		parkNameCombo.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
/*
		setDetails("", "", "", "", "Carmel Park");
		setDetailsOfOwner("315766014", "member", false, "amount of members", "backTo");
*/
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", m_userID);
		FXMLLoader loader = nextStages.openPopUp();
//		loader.getController();
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		m_fName = fName;
		m_lName = lName;
		m_role = role;
		m_userID = userID;
		m_parkName = parkName;
	}

	/**
	 * this controller need to know witch previous page open it and if it is
	 * occasional visit i will give the employee to enter number of visitors that is
	 * valid
	 * 
	 * @param ownerUserID
	 * @param status
	 * @param occasional
	 * @param membersAmount
	 * @param backTo
	 */
//amountInoccasional - free space in park in case of occasional visit
	public void setDetailsOfOwner(String ownerUserID, String status, boolean occasional, int membersAmount, int amountInoccasional) {// in status i want to know if
		ArrayList<String> tempArrayList = new ArrayList<String>(); // the user owner is a
		// member user or guide
		m_ownerUserID = ownerUserID;
		m_status = status;
		m_occasional = occasional;
		m_amountOfPeople = membersAmount;
		if (m_occasional) {
			txtCrumViaHomePage.setVisible(true);
			tempArrayList.add(m_parkName);
			setParkCombo(tempArrayList);
			parkNameCombo.setValue(m_parkName);
			pickDatePicker.setValue(LocalDate.now());
			setHourCombo(null, null);

			setNumberOfVistors(amountInoccasional);
			pickDatePicker.setValue(LocalDate.now());
			pickDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {

				@Override
				public DateCell call(DatePicker param) {
					return new DateCell() {
						@Override
						public void updateItem(LocalDate item, boolean empty) {
							super.updateItem(item, empty);
							LocalDate today = LocalDate.now();
							setDisable(item.compareTo(today) != 0);
						}
					};
				}
			});
			if (status.equals("member")) {
				MemberOrderlab.setVisible(true);
			}

//			 setNumberOfVistors("free place");//for occasional visit i need to set the number of visitors to the one i get from the previous page

		} else {
			txtCrum.setVisible(true);
			setHourCombo(new Time(8, 0, 0), new Time(16, 29, 0));//the time coustumer can enter to the patk
			tempArrayList.add("Carmel Park");
			tempArrayList.add("Tal Park");
			tempArrayList.add("Jordan Park");
			setParkCombo(tempArrayList);
			pickDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {

				@Override
				public DateCell call(DatePicker param) {
					return new DateCell() {
						@Override
						public void updateItem(LocalDate item, boolean empty) {
							super.updateItem(item, empty);
							LocalDate today = LocalDate.now();
							LocalDate tommorow = today.minusDays(-2);
							setDisable(item.compareTo(tommorow) < 0);
						}
					};
				}
			});
			if (status.equals("member")) {
				setNumberOfVistors(membersAmount);
				MemberOrderlab.setVisible(true);
			} else {
				setNumberOfVistors(15);
			}
		}
		if (m_status.equals("guide")) {
			guideWelcomeText.setVisible(true);
			if (occasional == false) {
				payTimeCheckBox.setVisible(true);
			}
		}
		if(m_status.equals(Role.Member.toString().toLowerCase())) {
			setNumberOfVistors(m_amountOfPeople);
		}
	}

	public void setMainPage(MouseEvent event) {
		m_eventMain = event;
	}

	public void setPreviousPage(MouseEvent event) {
		// TODO Auto-generated method stub
		m_previousPage = event;
	}

}
