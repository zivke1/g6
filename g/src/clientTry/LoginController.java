package clientTry;

import java.util.ArrayList;

//import com.mysql.cj.x.protobuf.MysqlxExpect.Open;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LoginController {

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button btnNextPage;

	@FXML
	private RadioButton enterAsCoustumerRadioBtn;

	@FXML
	private ToggleGroup radio;

	@FXML
	private RadioButton enterAsEmployee;

	@FXML
	private AnchorPane Identification;

	@FXML
	private TextField enterIDnumber;

	@FXML
	private TextField enterMemberID;

	@FXML
	private Label txtErrAllFieldsReq1;

	@FXML
	private AnchorPane login;

	@FXML
	private TextField enterUserName;

	@FXML
	private TextField EnterPsw;

	@FXML
	private Label txtErrAllFieldsReq;
	
    @FXML
    private Label logInBeforeLabel;

	@FXML
	private Label txtErrUserName;

    @FXML
    private Label dontFindMemberShipIDLabel;

	@FXML
	private Label txtErrPassword;

	String fName = null;
	String lName = null;
	String role = null;
	String userID = null;
	String park = null;
	MouseEvent m_event = null;

	enum UserType {
		user, member, employee
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	void changeIdentificationVisible(ActionEvent event) {
		loginSetVisibility(false);
		identificationSetVisibility(true);

	}

	@FXML
	void changeLoginVisible(ActionEvent event) {
		identificationSetVisibility(false);
		loginSetVisibility(true);

	}

	@FXML
	void finishOrderClicked(MouseEvent event) throws Exception {
		m_event= event;
		ArrayList<String> toSend = new ArrayList<String>();
		if (enterAsEmployee.isSelected()) {//check if employee
			
			String empNumber = enterUserName.getText();
			String password = EnterPsw.getText();
			if (empNumber.equals("") || password.equals("")) {
				txtErrAllFieldsReq.setVisible(true);
				return;
			} else {
				toSend.add("checkIfEmployee");
				toSend.add(empNumber);
				toSend.add(password);
				ClientMain.chat.accept(toSend);
				if (ChatClient.dataInArrayList.contains("PaswwordIncorrect")) {// i put only Password incorrect
					txtErrPassword.setVisible(true);
					return;
				} else if (ChatClient.dataInArrayList.contains("connectedBefore")) {
					logInBeforeLabel.setVisible(true);
					return;
				} else if (ChatClient.dataInArrayList.contains("employeeNotFound")) {
					txtErrUserName.setVisible(true);
					return;
				} 
				else {
					openHomePage(UserType.employee);
				}

			}
		} else if (enterAsCoustumerRadioBtn.isSelected()) {
			String idNumber = enterIDnumber.getText();
			String memberNumber = enterMemberID.getText();

			if (((idNumber.equals("")) && (memberNumber.equals(""))) || (((!idNumber.equals("")) && (!memberNumber.equals(""))))) {
				txtErrAllFieldsReq1.setVisible(true);
				return;
			} else {
				if (!idNumber.equals("")) {//if the user enter id number
					toSend.add("checkIfIdConnectedWithId");
					toSend.add(idNumber);
					ClientMain.chat.accept(toSend);
					statusToOpen();

				}
				if (!memberNumber.equals("")) {//if the user enter membership number
					toSend.add("checkIfIdConnectedWithMemberId");
					toSend.add(memberNumber);
					ClientMain.chat.accept(toSend);
					if (ChatClient.dataInArrayList.contains("notMember")) {
						dontFindMemberShipIDLabel.setVisible(true);
						return;
					}
					statusToOpen();
				}
			}
		}
	}

	private void statusToOpen() throws Exception {
		if (ChatClient.dataInArrayList.contains("connectedBefore")) {
			logInBeforeLabel.setVisible(true);
			return;
		} else if (ChatClient.dataInArrayList.contains("member")) {
			openHomePage(UserType.member);
		} else if (ChatClient.dataInArrayList.contains("user")){
			openHomePage(UserType.user);
		}
	}

	private void openHomePage(UserType userType) throws Exception {
		// TODO
		FXMLLoader loader = new FXMLLoader();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
		HomePageForEmployeeController homePageForEmployeeController = loader.getController();

		switch (userType) {
		case member: {
			fName = ChatClient.dataInArrayList.get(1);
			lName = ChatClient.dataInArrayList.get(2);
			userID = ChatClient.dataInArrayList.get(0);
			role="member";
			break;
		}
		case employee: {
			fName = ChatClient.dataInArrayList.get(1);
			lName = ChatClient.dataInArrayList.get(2);
			userID = ChatClient.dataInArrayList.get(0);
			role = ChatClient.dataInArrayList.get(3);
			park = ChatClient.dataInArrayList.get(4);
			break;
		}
		case user: {
			userID = ChatClient.dataInArrayList.get(0);
			break;
		}
		default:
			break;
		}
		homePageForEmployeeController.setDetails(fName, lName, role, userID , park);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home Page");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(evt->{
			if (ClientMain.chat.checkConnection()) {
	    	ArrayList<String> arr = new ArrayList<String>();
			arr.add("closeAndSetIdNull");
			arr.add(userID);
			ClientMain.chat.accept(arr);
	    	ClientMain.chat.stopConnection();
			}
		});
		((Node) m_event.getSource()).getScene().getWindow().hide();
		primaryStage.show();
	}

	public void identificationSetVisibility(boolean cond) {
		Identification.setVisible(cond);
	}

	public void loginSetVisibility(boolean cond) {
		login.setVisible(cond);
	}

}
