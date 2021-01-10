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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
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

/**
 * class Home Page for employees and customers
 * each client gets his own home page
 * each client can do different operations
 * load controllers and set functions if needed
 * @author shani
 *
 */

public class HomePageForEmployeeController implements Initializable {

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
	private Button btnViewOrder;

	@FXML
	private Button btnAvailabilityCheck;

	@FXML
	private AnchorPane anchorParkManager;

    @FXML
    private Button btnViewReportDepManager;
    
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
	private TableView<OrderToView> tblExistingOrder;

	@FXML
	private Label NoExistOrderMsg;
	
	private String fName, lName, role, userID, parkName;
	private int amountOfPeople;
	//FXMLLoader loader = new FXMLLoader();
	
	@FXML
	void goToApproveP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ApproveParameters.fxml", "Approve Parameters", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		ApproveParametersController approvePcontrol = loader.getController();
		approvePcontrol.setDetails(fName, lName, role, userID, parkName);
		approvePcontrol.setPreviousPage(event);
		approvePcontrol.setMainPage(event);
	}

	@FXML
	void goToAvailbilityCheck(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/CheckAvailability.fxml", "Availbility Check", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		CheckAvailabilityController check = loader.getController();
		check.setDetails(fName, lName, role, userID, parkName);
		check.setPreviousPage(event);
		check.setMainPage(event);
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		nextStages.openPopUp();
	}

	@FXML
	void goToGenerateReportDepManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportDepartmentManagerPage.fxml", "Generate Reports", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		ReportDepartmentManagerController repControl = loader.getController();
		repControl.setDetails(fName, lName, role, userID, parkName);
		repControl.setPreviousPage(event);
		//repControl.setMainPage(event);
	}
	
	// department manager can view submitted reports from Park Manager
    @FXML
    void goToViewReportDepManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ViewReportsDepManager.fxml", "View Reports", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		ViewReportDepartmentManagerController repControl = loader.getController();
		repControl.setDetails(fName, lName, role, userID, parkName);
		repControl.setPreviousPage(event);
    }
    
	@FXML
	void goToGenerateReportParkManager(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ReportParkManager.fxml", "Generate Reports", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		ReportParkManagerController repControl = loader.getController();
		repControl.setDetails(fName, lName, role, userID, parkName);
		repControl.setPreviousPage(event);
	}

	@FXML
	void goToInstructorRegistretion(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/AddInstructor.fxml", "Add New Instructor", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		AddInstructorController addInstructor = loader.getController();
		addInstructor.setDetails(fName, lName, role, userID, parkName);
		addInstructor.setPreviousPage(event);
		addInstructor.setMainPage(event);
	}

	/**
	 *  user sign out
	 * @param event
	 */
	@FXML
	void goToLoginP(MouseEvent event) {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("closeAndSetIdNull");
		arr.add(userID);
		ClientMain.chat.accept(arr);
		NextStages nextStages = new NextStages("/fxmlFiles/LoginP.fxml", "Login", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		LoginController logControl = loader.getController();
	}

	/**
	 *  customer does
	 * @param event
	 */
	@FXML
	void goToNewOrderCustomer(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/OrderNew.fxml", "New Order", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		OrderController orderControl = loader.getController();
		orderControl.setDetails(fName, lName, role, userID, parkName);
		orderControl.setDetailsOfOwner(userID, role, false, amountOfPeople, 0);
		tblExistingOrder.setVisible(false);
		NoExistOrderMsg.setVisible(false);
		orderControl.setPreviousPage(event);
		orderControl.setMainPage(event);
	}

	@FXML
	void goToUpdateP(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/Parameters.fxml", "Update Parameters", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		UpdateParametersController updatePcontrol = loader.getController();
		updatePcontrol.setDetails(fName, lName, role, userID, parkName);
		updatePcontrol.setPreviousPage(event);
		//updatePcontrol.setMainPage(event);
	}

	/**
	 *  Employee wants to view customers order
	 * @param event
	 */
	@FXML
	void goToViewExistOrder(MouseEvent event) {
		// here employee will need to enter customer id to view his order
		NextStages nextStages = new NextStages("/fxmlFiles/EmployeeEnterCustomerID.fxml", "View Customer's Order", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		EmployeeEnterCustomerIDController viewOrderControl = loader.getController();
		viewOrderControl.setDetails(fName, lName, role, userID, parkName);
		viewOrderControl.setPreviousPage(event);
	}

	/**
	 *  customer wants to view his orders
	 * @param event
	 */
	@FXML
	void goToViewOrderCustomer(MouseEvent event) {
		// if customer Id is in orders table -> set visible tblExisitingOrder
		ArrayList<String> arr = new ArrayList<>();
		arr.add(userID);
		arr.add("ReturnUserIDInTableOrders");
		ClientMain.chat.accept(arr);
		ArrayList<OrderToView> temp = ChatClient.dataInArrayListObject;
		if (!temp.isEmpty()) {
			tblExistingOrder.getColumns().clear();
			// order ID Column
			TableColumn<OrderToView, String> orderIDcolumn = new TableColumn<>("Order ID");
			orderIDcolumn.setMinWidth(150);
			orderIDcolumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));

			// Status Column
			TableColumn<OrderToView, String> statusColumn = new TableColumn<>("Status");
			statusColumn.setMinWidth(150);
			statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

			// Date Column
			TableColumn<OrderToView, String> dateColumn = new TableColumn<>("Date");
			dateColumn.setMinWidth(150);
			dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

			TableViewOrders obsList = new TableViewOrders();
			tblExistingOrder.setItems(obsList.getOrders(temp));
			
			tblExistingOrder.setRowFactory(tv -> {
				TableRow<OrderToView> row = new TableRow<>();
				row.setOnMouseClicked(evento -> {
					if (evento.getClickCount() == 2 && (!row.isEmpty())) {
						OrderToView rowData = row.getItem();
						NextStages nextStages = new NextStages("/fxmlFiles/ViewOrder.fxml", "View Order", userID);
						FXMLLoader loader = nextStages.goToNextStage(evento);
						ViewOrderController viewOrderControl = loader.getController();
						viewOrderControl.setDetails(fName, lName, role, userID, parkName, rowData.getOrderID());
						viewOrderControl.setPreviousPage(event);
						tblExistingOrder.setVisible(false);
					}
				});
				return row;
			});
			tblExistingOrder.getColumns().addAll(orderIDcolumn, statusColumn, dateColumn);
			tblExistingOrder.setVisible(true);
		} else {
			NoExistOrderMsg.setVisible(true);
		}
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("Please choose which page\nyou want to enter.");  // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; "
		    + "-fx-background-color: #F0F8FF; "
		    + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	@FXML
	void goToMemberRegistration(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/MembershipRegistration.fxml", "Membership Registration", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		MembershipRegistrationController memberRegControl = loader.getController();
		memberRegControl.setDetails(fName, lName, role, userID, parkName);
		memberRegControl.setPreviousPage(event);
		memberRegControl.setMainPage(event);
	}

	@FXML
	void goToParkDetail(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ParkDetails.fxml", "Park Details", userID);
		FXMLLoader loader = nextStages.goToNextStage(event);
		ParkDetailsController parkDetailsControl = loader.getController();
		parkDetailsControl.setDetails(fName, lName, role, userID, parkName);
		parkDetailsControl.setPreviousPage(event);
		//parkDetailsControl.setMainPage(event);
	}

	/**
	 * prepare form - preset all as invisible
	 */
	public void setAllUnvisible() {
		anchorParkEmployee.setVisible(false);
		anchorParkManager.setVisible(false);
		anchorDepManager.setVisible(false);
		anchorCustomerrepResentativeEmp.setVisible(false);
		anchorCustomer.setVisible(false);
		tblExistingOrder.setVisible(false);
		NoExistOrderMsg.setVisible(false);
	}
	
	public void setAmountForMember(int amountOfPeople){
		this.amountOfPeople = amountOfPeople;
	}
	/**
	 * 
	 * @param fName
	 * @param lName
	 * @param role
	 * @param userID
	 * @param parkName
	 * receive from login controller user details and set home page according this details
	 */
	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkName = parkName;

		String roleM = Role.Member.toString().toLowerCase();
		String rolePM = Role.ParkManager.toString();
		String roleDM = Role.DepartmentManager.toString();
		String rolePE = Role.ParkEmployee.toString();
		String roleSR = Role.ServiceRepresentative.toString();
		String roleU = Role.User.toString().toLowerCase();
		String roleG = Role.Guide.toString().toLowerCase();

		if (role.equals(rolePM)) {
			employeeCrums.setVisible(true);
			anchorParkManager.setVisible(true);
			greetingMsg.setText(
					fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + parkName + " Manager, have a great day!");

		} else if (role.equals(roleDM)) {
			employeeCrums.setVisible(true);
			anchorDepManager.setVisible(true);
			greetingMsg.setText(
					fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", Department Manager, have a great day!");

		} else if (role.equals(rolePE)) {
			employeeCrums.setVisible(true);
			anchorParkEmployee.setVisible(true);
			greetingMsg.setText(
					fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", Park Employee, have a great day!");

		} else if (role.equals(roleSR)) {
			employeeCrums.setVisible(true);
			anchorCustomerrepResentativeEmp.setVisible(true);
			greetingMsg.setText(
					fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", Service Representative, have a great day!");

		} else if (role.equals(roleM)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", happy to see you!");

		} else if (role.equals(roleU)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText("Thank you for choosing us!");

		} else if (role.equals(roleG)) {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
			greetingMsg.setText(
					fName.substring(0, 1).toUpperCase() + fName.substring(1) + ", " + role + ", happy to see you!");
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAllUnvisible();
	}

}
