package clientTry;

import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxExpect.Open;

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
	private Label txtErrUserName;

	@FXML
	private Label txtErrPassword;

	String fName = null;
	String lName = null;
	String role = null;
	String userID = null;
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
		if (enterAsEmployee.isSelected()) {
			// TODO need to take the fileds and check if it is an employee
			String user = enterUserName.getText();
			String password = EnterPsw.getText();
			if (user == "" || password == "") {
				txtErrAllFieldsReq.setVisible(true);
				return;
			} else {
				toSend.add("checkIfEmployee");
				toSend.add(user);
				toSend.add(password);
				ClientMain.chat.accept(toSend);
				if (ChatClient.dataInArrayList.contains("false")) {// i put only Password incorrect
					txtErrPassword.setVisible(true);
					return;
				} else if (ChatClient.dataInArrayList.contains("connectedBefore")) {
					// TODO
				} else {
					openHomePage(UserType.employee);
				}

			}
		} else if (enterAsCoustumerRadioBtn.isSelected()) {
			String idNumber = enterIDnumber.getText();
			String memberNumber = enterMemberID.getText();
			if ((idNumber == "" && memberNumber == "") || (idNumber != "" && memberNumber != "")) {
				txtErrAllFieldsReq1.setVisible(true);
				return;
			} else {
				if (idNumber != "") {
					toSend.add("checkIfIdConnectedWithId");
					toSend.add(idNumber);
					ClientMain.chat.accept(toSend);
					statusToOpen();

				}
				if (memberNumber != "") {
					toSend.add("checkIfIdConnectedWithMemberId");
					toSend.add(memberNumber);
					ClientMain.chat.accept(toSend);
					if (ChatClient.dataInArrayList.contains("notMember")) {
						// TODO
						return;
					}
					statusToOpen();
				}
			}
		}
	}

	private void statusToOpen() throws Exception {
		if (ChatClient.dataInArrayList.contains("connectedBefore")) {
			// TODO
		} else if (ChatClient.dataInArrayList.contains("member")) {
			openHomePage(UserType.member);
		} else {
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
			break;
		}
		case employee: {
			fName = ChatClient.dataInArrayList.get(1);
			lName = ChatClient.dataInArrayList.get(2);
			userID = ChatClient.dataInArrayList.get(0);
			role = ChatClient.dataInArrayList.get(3);
			break;
		}
		case user: {
			userID = ChatClient.dataInArrayList.get(0);
			break;
		}
		default:
			break;
		}
		homePageForEmployeeController.setDetails(fName, lName, role, userID);
		Scene scene = new Scene(root);
		primaryStage.setTitle("Home Page");
		primaryStage.setScene(scene);
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
