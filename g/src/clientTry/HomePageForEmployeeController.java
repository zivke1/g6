package clientTry;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePageForEmployeeController {

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

	}

	@FXML
	void goToAvailbilityCheck(MouseEvent event) {

	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	void goToGenerateReportDepManager(MouseEvent event) {

	}

	@FXML
	void goToGenerateReportParkManager(MouseEvent event) {

	}

	@FXML
	void goToInstructorRegistretion(MouseEvent event) {

	}

	@FXML
	void goToLoginP(MouseEvent event) {

	}

	@FXML
	void goToMakeOrderForCustomer(MouseEvent event) {

	}

	@FXML
	void goToNewOrderCustomer(MouseEvent event) {

	}

	@FXML
	void goToUpdateP(MouseEvent event) {

	}

	@FXML
	void goToViewExistOrder(MouseEvent event) {

	}

	@FXML
	void goToViewOrderCustomer(MouseEvent event) {

	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	@FXML
	void goToMemberRegistration(MouseEvent event) {

		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/MembershipRegistration.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Membership Registration");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void goToParkDetail(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/ParkDetails.fxml").openStream());

			// ClientMain.chat.accept(arr);
			// ParkDetailsController.setParkDetails(String namePark);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Park Details");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		switch (role) {
		case "Park Manager": {
			employeeCrums.setVisible(true);
			anchorParkManager.setVisible(true);
			/////////להוסיף מחר את הודעת הברכה המתאימה
		}
		case "Department Manager": {
			employeeCrums.setVisible(true);
			anchorDepManager.setVisible(true);
		}
		case "Park Employee": {
			employeeCrums.setVisible(true);
			anchorParkEmployee.setVisible(true);
		}
		case "Representative": {
			employeeCrums.setVisible(true);
			anchorCustomerrepResentativeEmp.setVisible(true);
		}
		case "Member": {
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
		}
		case "User":{
			customerCrums.setVisible(true);
			anchorCustomer.setVisible(true);
		}
		}
	}

}
