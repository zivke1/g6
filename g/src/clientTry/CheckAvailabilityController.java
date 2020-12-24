package clientTry;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class CheckAvailabilityController {

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
	private int currentCapacity = 0;
	
    @FXML
    void MoveToNewOrder(MouseEvent event) {
    	ArrayList<String> arr = new ArrayList<>();
    	ownerUserID = IDtxt.getText();  // if id wasn't entered - present error message
    	if(ownerUserID.equals("")) {
    		IDErrMsg.setVisible(true);
    	}
    	//search id in DB - return and his role and amount of people if member
    	
    	
    	// open new order page
    	NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml", "New Order By Employee", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		OrderController orderControl = loader.getController();
		orderControl.setDetails(fName, lName, role, userID, parkName);
		orderControl.setDetailsOfOwner(ownerUserID, ownerRole, occasional, amountOfPeople);	//order owner
		IDErrMsg.setVisible(false);
    	anchorMakeOrder.setVisible(false);
		orderControl.setPreviousPage(event);
		orderControl.setMainPage(event);
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
    void goToMakeOrderForCustomer(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
    
    // return amount of orders form DB where status is active
    public void checkAvailability() {
    	ArrayList<String> arr = new ArrayList<>();
    	arr.add("countActiveOrders");
    	
    	
    	// if current capacity greater than 0 allow employee to make new order for customer
    	anchorMakeOrder.setVisible(true);
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
}
