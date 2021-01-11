package clientTry;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NextStages;
import util.Role;
import util.ParksNames;
/**
 * Check free space in parks
 * Allows park Employee to make a new order if there is free space 
 * @author shani
 *
 */
public class CheckAvailabilityController {

	@FXML
	private Button checkBtn;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Text parkNameText;

	@FXML
	private Text FreeSpaceLeft;

	@FXML
	private Button btnNewOrderFotCustomer;

	@FXML
	private AnchorPane anchorMakeOrder;
	
    @FXML
    private Label NoFreeSpaceLable;
    
	@FXML
	private TextField IDtxt;

	@FXML
	private ComboBox<String> parksNamesCombo;

	@FXML
	private Text freeSpacetxt;
	  
	@FXML
	private Button btnMakeOrder;

	@FXML
	private Label IDErrMsg;

	private String fName, lName, role, userID, parkName;
	private int amountOfPeople;
	MouseEvent m_event, m_eventMain, m_previousPage;
	private String ownerUserID = null, ownerRole = null;
	private boolean occasional = true; // order made by park employee is for occasional visitor
	private int openSpace = 0;
	private int amountInoccasional = 0;

	@FXML // i only check if order active - not the time and date
	void MoveToNewOrder(MouseEvent event) {
		IDErrMsg.setVisible(false);
		String enteredID = IDtxt.getText(); // if id wasn't entered - present error message

		if (enteredID.equals("")) {
			IDErrMsg.setVisible(true); 
			return;
		}
		// if the user enter incorrect id number
		char[] chars = enteredID.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				IDErrMsg.setVisible(true);
				return;
			}
		}

		// search id in DB - return and his role and amount of people if member
		ArrayList<String> arr = new ArrayList<>();
		arr.add("checkMemberIDInMembers"); // check if ID is member ID
		arr.add(enteredID); // can be memberID or regular ID
		ClientMain.chat.accept(arr);
		ArrayList<String> temp = ChatClient.dataInArrayList;
		if (temp.contains("notMember")) { // ID is not memberID
			arr.clear();
			arr.add("checkIdInMember"); // check if userID in member
			arr.add(enteredID);
			ClientMain.chat.accept(arr);
			ArrayList<String> temp2 = ChatClient.dataInArrayList;
			if (temp2.contains("user")) { // not a member
				// user
				ownerUserID = enteredID;
				ownerRole = Role.User.toString();
				amountOfPeople = 15; ///// ??? amount people for user
			} else {
				// is member and ID is regular ID
				ownerUserID = enteredID;
				ownerRole = temp2.get(3);
				amountOfPeople = Integer.parseInt(temp2.get(4));
			}
		} else {
			ownerUserID = temp.get(0);
			ownerRole = temp.get(3);
			amountOfPeople = Integer.parseInt(temp.get(4));
		}

		amountInoccasional = Math.min(amountOfPeople, openSpace);
		// open new order page
		NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml", "New Order By Employee", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		OrderController orderControl = loader.getController();
		orderControl.setDetails(fName, lName, role, userID, parkName);

		orderControl.setDetailsOfOwner(ownerUserID, ownerRole, occasional, amountOfPeople, amountInoccasional); // order
																												// owner

	//	anchorMakeOrder.setVisible(false);

		orderControl.setPreviousPage(event);
		orderControl.setMainPage(m_eventMain);

	}

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_previousPage.getSource()).getScene().getWindow()).show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void checkFreeSpaceDepManager(MouseEvent event) {
		this.parkName = parksNamesCombo.getValue().toString();
		FreeSpaceLeft.setText(checkAvailability());
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page shows the current available \nspace in this park"); // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

/**
 *  return capacity - amount of orders form DB where status is active
 * @return
 */
	public String checkAvailability() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("countActiveOrders");
		arr.add(parkName);
		ClientMain.chat.accept(arr);
		String ordersAmount = ChatClient.dataInArrayList.get(0);
		arr.clear();
		arr.add("checkCapacityAndAvarageVisitTime");
		arr.add(parkName);
		ClientMain.chat.accept(arr);
		Integer capacity = ChatClient.dataInArrayListInteger.get(0);
		int ordAmount = Integer.parseInt(ordersAmount);
		openSpace = capacity - ordAmount;
		if(openSpace <= 0) {
			NoFreeSpaceLable.setVisible(true);
			freeSpacetxt.setVisible(false);
			anchorMakeOrder.setVisible(false);
			FreeSpaceLeft.setVisible(false);
		}
		// if current capacity greater than 0 allow employee to make new order for
		// customer
		else if (openSpace > 0) {
			freeSpacetxt.setVisible(true);
			FreeSpaceLeft.setVisible(true);
			NoFreeSpaceLable.setVisible(false);
			if(role.equals(Role.ParkEmployee.toString()))
				anchorMakeOrder.setVisible(true);
		}
		return openSpace + "";
	}

	/**
	 *  logged in user
	 * @param fName
	 * @param lName
	 * @param role
	 * @param userID
	 * @param parkName
	 */
	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkName = parkName;
		
		freeSpacetxt.setVisible(false);
		NoFreeSpaceLable.setVisible(false);
		
		if (role.equals(Role.DepartmentManager.toString())) {
			parksNamesCombo.setVisible(true);
			setComboParkName();
			checkBtn.setVisible(true);
		} else {
			parksNamesCombo.setVisible(false);
			parkNameText.setText(parkName);
			parkNameText.setVisible(true);
			FreeSpaceLeft.setVisible(true);
			FreeSpaceLeft.setText(checkAvailability());
		}
	}

	public void setMainPage(MouseEvent event) {
		m_eventMain = event;
	}

	public void setPreviousPage(MouseEvent event) {
		m_previousPage = event;
	}

	private void setComboParkName() {
		ArrayList<String> al = new ArrayList<String>();
		for (ParksNames name : ParksNames.values()) {
			al.add(name.toString());
		}
		ObservableList<String> list;
		list = FXCollections.observableArrayList(al);
		parksNamesCombo.setItems(list);
		parksNamesCombo.setValue(ParksNames.Tal_Park.toString());
	}
}
