package clientTry;

import java.io.IOException;

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

	private String fName, lName, role,userID;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button helpBtn;

	@FXML
	private Button btnAddMember;

	@FXML
	private Button btnAddInstructor;

	@FXML
	private Button btnParkDetails;

	@FXML
	private Button btnNewOrderFotC;

	@FXML
	private Button btnViewOrder;

	@FXML
	private Button btnAvailabilityCheck;

	@FXML
	private Button btnUpdateP;

	@FXML
	private Button btnGenReport;

	@FXML
	private Button btnApproveP;

	@FXML
	private Button backBtn;

	@FXML
	void backClicked(MouseEvent event) {

	}

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
	void goToGenerateReport(MouseEvent event) {

	}

	@FXML
	void goToInstructorRegistretion(MouseEvent event) {

	}

	@FXML
	void goToMakeOrderForCustomer(MouseEvent event) {

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
			
			ClientMain.chat.accept(arr);
			//ParkDetailsController.setParkDetails(String namePark);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Park Details");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void goToUpdateP(MouseEvent event) {

	}

	@FXML
	void goToViewExistOrder(MouseEvent event) {

	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	public void setDetails(String fName, String lName, String role,String userID) {
		this.fName = fName;
		this.lName = lName;
		this.userID=userID;
		this.role = role;
		switch (role) {
		case "Park Manager": {
			btnGenReport.setVisible(true);
			btnUpdateP.setVisible(true);
			btnParkDetails.setVisible(true);
		}
		case "Department Manager": {
			btnApproveP.setVisible(true);
		}
		case "Gate Work": {
			btnAvailabilityCheck.setVisible(true);
			btnNewOrderFotC.setVisible(true);
			btnViewOrder.setVisible(true);
			btnParkDetails.setVisible(true);
		}
		case "Representative": {
			btnAddInstructor.setVisible(true);
			btnAddMember.setVisible(true);

		}
		}
	}

}
