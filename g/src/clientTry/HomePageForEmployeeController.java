package clientTry;
import util.NextStages;
import util.OrderToView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import util.TableViewOrders;
public class HomePageForEmployeeController implements Initializable {

	private String fName, lName, role, userID, parkName;
	FXMLLoader loader = new FXMLLoader();

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
	private TableView<?> tblExistingOrder;

	@FXML
	private Label NoExistOrderMsg;

	@FXML
	void goToApproveP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ApproveParameters.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
	//	ApproveParametersController approvePcontrol = loader.getController();
		//approvePcontrol.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToAvailbilityCheck(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/CheckAvailability.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
	//	CheckAvailbilityController check = loader.getController();
		//check.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml","View Customer's Order");
		nextStages.openPopUp();
	}

	@FXML
	void goToGenerateReportDepManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportDepartmentManager.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
	//	ReportDepartmentManagerController repControl = loader.getController();
	//	repControl.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToGenerateReportParkManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportParkManager.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
	//	ReportParkManagerController repControl = loader.getController();
	//	repControl.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToInstructorRegistretion(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/AddInstructor.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
		AddInstructorController addInstructor = loader.getController();
	//	addInstructor.setDetails(fName, lName, role, userID, parkName);
	}
	//user sign out
	@FXML
	void goToLoginP(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("closeAndSetIdNull");
		arr.add(userID);
		ClientMain.chat.accept(arr);
		NextStages nextStages = new NextStages("/fxmlFiles/LoginP.fxml","Login");
		nextStages.goToNextStage(event);
		//LoginController logControl = loader.getController();
	}

	//Employee does
	@FXML
	void goToMakeOrderForCustomer(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/NewOrderEmployee.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
		NewOrderEmployeeController orderByEmployee = loader.getController();
//		orderByEmployee.setDetails(fName, lName, role, userID, parkName);
	}
	// customer does
	@FXML
	void goToNewOrderCustomer(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml","View Customer's Order");
		nextStages.goToNextStage(event);
		OrderController orderControl = loader.getController();
	//	orderControl.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToUpdateP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/Parameters.fxml","Update Parameters");
		nextStages.goToNextStage(event);
	//	UpdateParametersController updatePcontrol = loader.getController();
		//updatePcontrol.setDetails(fName, lName, role, userID, parkName);
	}
	//Employee view customers order
	@FXML
	void goToViewExistOrder(MouseEvent event) {
		 // here employee will need to enter customer id to view his order
		NextStages nextStages = new NextStages("/fxmlFiles/EmployeeEnterCustomerID.fxml","View Customer's Order"); 
		nextStages.goToNextStage(event);
//		ViewOrderController viewOrderControl = loader.getController();
	//	viewOrderControl.setDetails(fName, lName, role, userID, parkName);
	}
	
	// customer wants to view his orders
	@FXML
	void goToViewOrderCustomer(MouseEvent event) {
		//if customer Id is in orders table -> set visible tblExisitingOrder
		ArrayList<String> arr = new ArrayList<>();
		arr.add(userID);
		//arr.add("orders");
		arr.add("ReturnUserIDInTableOrders");
		ClientMain.chat.accept(arr);
		ArrayList<OrderToView> temp = ChatClient.dataInArrayListObject;
		if(temp != null) {
			TableView<OrderToView> table;
			//OrderID Column
			TableColumn<OrderToView, String> orderIDcolumn = new TableColumn<>("Order ID");
			orderIDcolumn.setMinWidth(200);
			orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
			
			//Status Column
			TableColumn<OrderToView, String> statusColumn = new TableColumn<>("Status");
			statusColumn.setMinWidth(200);
			statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));		
			
			//Date Column
			TableColumn<OrderToView, String> dateColumn = new TableColumn<>("Date");
			dateColumn.setMinWidth(200);
			dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));	
			
			TableViewOrders obsList = new TableViewOrders();
			
			table = new TableView<>();
			table.setItems(obsList.getOrders(temp));
			table.getColumns().addAll(orderIDcolumn, statusColumn, dateColumn);
			tblExistingOrder = table;
			tblExistingOrder.setVisible(true);
			
			//need to go to view existing order after pressing line in table
			//NextStages nextStages = new NextStages("/fxmlFiles/ViewOrder.fxml","View Order");
			//nextStages.goToNextStage(event);
		}
		else {
			NoExistOrderMsg.setVisible(true);
		}
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}


	@FXML
	void goToMemberRegistration(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/MembershipRegistration.fxml","Membership Registration");
		nextStages.goToNextStage(event);
//		MembershipRegistrationController memberRegControl = loader.getController();
	//	memberRegControl.setDetails(fName, lName, role, userID, parkName);
	}

	@FXML
	void goToParkDetail(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ParkDetails.fxml","Park Details");
		nextStages.goToNextStage(event);
		ParkDetailsController parkDetailsControl = loader.getController();
		parkDetailsControl.setDetails(fName, lName, role, userID, parkName);

	}
	
	//prepare form - preset all as invisible 
	public void setAllUnvisible() {
		anchorParkEmployee.setVisible(false);
		anchorParkManager.setVisible(false);
		anchorDepManager.setVisible(false);
		anchorCustomerrepResentativeEmp.setVisible(false);
		anchorCustomer.setVisible(false);
		tblExistingOrder.setVisible(false);
		NoExistOrderMsg.setVisible(false);
	}
	

	// receive from login form all user info and set visibility  
	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkName = parkName;
		String roleM = Role.Member.toString().toLowerCase();
		String rolePM = Role.ParkManager.toString().toLowerCase();
		String roleDM = Role.DepartmentManager.toString().toLowerCase();
		String rolePE = Role.ParkEmployee.toString().toLowerCase();
		String roleSR = Role.ServiceRepresentative.toString().toLowerCase();
		String roleU = Role.User.toString().toLowerCase();
		String roleG = Role.Guide.toString().toLowerCase();
		
		if (role.equals(rolePM)) {
			employeeCrums.setVisible(true);
			anchorParkManager.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", have a great day!");
			
		}
		else if (role.equals(roleDM)) {
			employeeCrums.setVisible(true);
			anchorDepManager.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", have a great day!");
			
		}
		else if (role.equals(rolePE)) {
			employeeCrums.setVisible(true);
			anchorParkEmployee.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", have a great day!");
			
		}
		else if (role.equals(roleSR)) {
			employeeCrums.setVisible(true);
			anchorCustomerrepResentativeEmp.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", have a great day!");
			
		}
		else if (role.equals(roleM)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", happy to see you!");
			
		}
		else if (role.equals(roleU)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText("Thank you for choosing us!");
			
		}
		else if (role.equals(roleG)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", happy to see you!");	
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAllUnvisible();
	}

}
