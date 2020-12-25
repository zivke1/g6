package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NextStages;
import util.Role;

public class CheckAvailabilityController implements Initializable{

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
    private TextField IDtxt;

    @FXML
    private Button btnMakeOrder;

    @FXML
    private Label IDErrMsg;
    
	private String fName, lName, role, userID, parkName;
	private int amountOfPeople;
	MouseEvent m_event, m_eventMain, m_previousPage;
	private String ownerUserID = null, ownerRole = null;
	private boolean occasional = true;	// order made by park employee is for occasional visitor
	private int openSpace = 0;
	
    @FXML // i only check if order active - not the time and date
    void MoveToNewOrder(MouseEvent event) {
    	String enteredID = IDtxt.getText();  // if id wasn't entered - present error message
    	if(ownerUserID.equals("")) {
    		IDErrMsg.setVisible(true);
    	}
    	//search id in DB - return and his role and amount of people if member
    	ArrayList<String> arr = new ArrayList<>();
    	arr.add("checkMemberIDInMembers");
    	arr.add(enteredID);		//can be memberID or regular ID
		ClientMain.chat.accept(arr);
		ArrayList<String> temp = ChatClient.dataInArrayList;
		if(temp.contains("notMember")) {	// check if ID is member ID
			arr.clear();
			arr.add("checkIdInMember");
			arr.add(enteredID);
			ClientMain.chat.accept(arr);
			ArrayList<String> temp2 = ChatClient.dataInArrayList;
			if(temp2.contains("user")) {   // check if userID in member
				// user
				ownerUserID = enteredID;
				ownerRole = Role.User.toString();
				amountOfPeople = 0 ; /////??? amount people for user
			}
			else {
				// member and ID is regular ID
				ownerUserID = enteredID;
				ownerRole = arr.get(2); 
				amountOfPeople = Integer.parseInt(arr.get(3));
			}
		}
		else {
			ownerUserID = arr.get(0);
			ownerRole = arr.get(3);
			amountOfPeople = Integer.parseInt(arr.get(4));
		}

    	// open new order page
    	NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml", "New Order By Employee", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		OrderController orderControl = loader.getController();
		orderControl.setDetails(fName, lName, role, userID, parkName);
		orderControl.setDetailsOfOwner(ownerUserID, ownerRole, occasional, amountOfPeople);	//order owner
		IDErrMsg.setVisible(false);
    	anchorMakeOrder.setVisible(false);
		orderControl.setPreviousPage(m_previousPage);
		orderControl.setMainPage(m_eventMain);
    }

    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_previousPage.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
    
    // return capacity - amount of orders form DB where status is active
    public String checkAvailability() {
    	ArrayList<String> arr = new ArrayList<>();
    	arr.add("countActiveOrders");
		ClientMain.chat.accept(arr);
		String ordersAmount = ChatClient.dataInArrayList.get(0);
		arr.clear();
		arr.add("checkCapacityAndAvarageVisitTime");
		arr.add(parkName);
		ClientMain.chat.accept(arr);
		Integer capacity = ChatClient.dataInArrayListInteger.get(0);
		int ordAmount = Integer.parseInt(ordersAmount);
		openSpace = capacity - ordAmount;
    	// if current capacity greater than 0 allow employee to make new order for customer
		if(openSpace > 0)
			anchorMakeOrder.setVisible(true);

    	return openSpace + "";
    }
    
    //logged in user
    public void setDetails(String fName, String lName, String role, String userID, String parkName){
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkName = parkName;
    }

	public void setMainPage(MouseEvent event) {
		m_eventMain=event;
	}
	
	public void setPreviousPage(MouseEvent event) {
		m_previousPage = event;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		parkNameText.setText(parkName);
		FreeSpaceLeft.setText(checkAvailability());
	}
}
