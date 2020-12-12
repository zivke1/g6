package clientTry;

import util.NextStages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.NextStages;
import util.Role;
public class HomePageForEmployeeController implements Initializable {

	private String fName, lName, role, userID;

	@FXML
	private Label greetingMsg;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Label employeeCrums;

	@FXML
	private Label customerCrums;

	@FXML
	private Button helpBtn;

	@FXML
	private AnchorPane anchorCustomerrepResentativeEmp;

	@FXML
	private Button btnAddMember;

	@FXML
	private Button btnAddInstructor;

	@FXML
	private Button btnParkDetails;

	@FXML
	private AnchorPane anchorParkEmployee;

	@FXML
	private Button btnNewOrderFotC;

	@FXML
	private Button btnViewOrder;

	@FXML
	private Button btnAvailabilityCheck;

	@FXML
	private AnchorPane anchorParkManager;

	@FXML
	private Button btnUpdateP;

	@FXML
	private Button btnGenReportParkManager;

	@FXML
	private AnchorPane anchorDepManager;

	@FXML
	private Button btnApproveP;

	@FXML
	private Button btnGenReportDepManager;

	@FXML
	private Button btnSignOut;

	@FXML
	private AnchorPane anchorCustomer;

	@FXML
	private Button btnMakeOrderCustomer;

	@FXML
	private Button btnViewExistingOrder;

	@FXML
	private TableView<?> tblExisitingOrder;

	@FXML
	private Label NoExistOrderMsg;

	@FXML
	void goToApproveP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ApproveParameters.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToAvailbilityCheck(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/CheckAvailability.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToGenerateReportDepManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportDepartmentManager.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToGenerateReportParkManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportParkManager.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToInstructorRegistretion(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/AddInstructor.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToLoginP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/LoginP.fxml","View Customer's Order");
		nextStages.goToNextStage();
	////////// need to disconnect - UPDATE users SET Connect = null WHERE UserID=315766014
	}

	//Employee does
	@FXML
	void goToMakeOrderForCustomer(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/NewOrderEmployee.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}
	// customer does
	@FXML
	void goToNewOrderCustomer(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml","View Customer's Order");
		nextStages.goToNextStage();
	}

	@FXML
	void goToUpdateP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/Parameters.fxml","Update Parameters");
		nextStages.goToNextStage();
	}
	//Employee view customers order
	@FXML
	void goToViewExistOrder(MouseEvent event) {
		 // here employee will need to enter customer id to view his order
		NextStages nextStages = new NextStages("/fxmlFiles/EmployeeEnterCustomerID.fxml","View Customer's Order"); 
		nextStages.goToNextStage();
	}
	// customer wants to view his orders
	@FXML
	void goToViewOrderCustomer(MouseEvent event) {
		//if customer Id is in orders table -> set visible tblExisitingOrder
		ArrayList<String> arr = new ArrayList<>();
		arr.add(userID);
		arr.add("orders");
		arr.add("CheckUserIDInTable");
		ClientMain.chat.accept(arr);
		///חסר עמוד לעבור אליו שמציג הזמנה
		NextStages nextStages = new NextStages("/fxmlFiles/ViewOrder.fxml","View Order");
		nextStages.goToNextStage();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	@FXML
	void goToMemberRegistration(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/MembershipRegistration.fxml","Membership Registration");
		nextStages.goToNextStage();
		///details
	}

	@FXML
	void goToParkDetail(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ParkDetails.fxml","Park Details");
		nextStages.goToNextStage();
	}
	
	//prepare form - preset all as invisible 
	public void setAllUnvisible() {
		anchorParkManager.setVisible(false);
		anchorDepManager.setVisible(false);
		anchorCustomerrepResentativeEmp.setVisible(false);
		anchorCustomer.setVisible(false);
		tblExisitingOrder.setVisible(false);
		NoExistOrderMsg.setVisible(false);
	}
	

	// receive from login form all user info and set visibility  
	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		
		switch (role) {
		case "Park Manager": {
			employeeCrums.setVisible(true);
			anchorParkManager.setVisible(true);
			greetingMsg.setText(fName + ", " + role + ", have a great day!");
		}
		case "Department Manager": {
			employeeCrums.setVisible(true);
			anchorDepManager.setVisible(true);
			greetingMsg.setText(fName + ", " + role + ", have a great day!");
		}
		case "Park Employee": {
			employeeCrums.setVisible(true);
			anchorParkEmployee.setVisible(true);
			greetingMsg.setText(fName + ", " + role + ", have a great day!");
		}
		case "Service Representative": {
			employeeCrums.setVisible(true);
			anchorCustomerrepResentativeEmp.setVisible(true);
			greetingMsg.setText(fName + ", " + role + ", have a great day!");
		}
		case "Member": {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(fName + ", happy to see you!");
		}
		case "User":{
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText("Thank you for choosing us!");
		}
		case "Guide":{
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(fName + ", " + role + ", happy to see you!");
		}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAllUnvisible();
	}

}
