package clientTry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.management.openmbean.OpenDataException;

import javafx.application.Application;

//import com.mysql.cj.x.protobuf.MysqlxExpect.Open;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.NextStages;
import util.Role;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import util.INextStage;

/**
 * this controller is for log in you can enter to the system via id member
 * number or if you an employee you can enter by employee number and password
 *
 */
public class LoginController implements Initializable {
	@FXML
	private Label noSelected;

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
	private PasswordField EnterPsw;

	@FXML
	private Label txtErrAllFieldsReq;

	@FXML
	private Label logInBeforeLabel;

	@FXML
	private Label txtErrUserName;

	@FXML
	private Button helpBtn;

	@FXML
	private Label dontFindMemberShipIDLabel;

	@FXML
	private Label txtErrPassword;

	@FXML
	private Label memberNotNumbers;

	@FXML
	private Label IDError;

	int amountOfPeople = 0;
	String fName = null;
	String lName = null;
	String role = null;
	String userID = null;
	String park = null;
	MouseEvent m_event = null;

	INextStage nextStages;

	enum UserType {
		user, member, employee
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void changeIdentificationVisible(ActionEvent event) {
		loginSetVisibility(false);
		identificationSetVisibility(true);
		noSelected.setVisible(false);
	}

	@FXML
	void changeLoginVisible(ActionEvent event) {
		identificationSetVisibility(false);
		loginSetVisibility(true);
		noSelected.setVisible(false);
	}

	public Label getNoSelected() {
		return noSelected;
	}

	public void setNoSelected(Label noSelected) {
		this.noSelected = noSelected;
	}

	public RadioButton getEnterAsCoustumerRadioBtn() {
		return enterAsCoustumerRadioBtn;

	}

	public void setEnterAsCoustumerRadioBtn(boolean bool) {
		// this.enterAsCoustumerRadioBtn = enterAsCoustumerRadioBtn;
		this.enterAsCoustumerRadioBtn.setSelected(bool);
	}

	public Label getTxtErrAllFieldsReq1() {
		return txtErrAllFieldsReq1;
	}

	public void setTxtErrAllFieldsReq1(Label txtErrAllFieldsReq1) {
		this.txtErrAllFieldsReq1 = txtErrAllFieldsReq1;
	}

	public TextField getEnterUserName() {
		return enterUserName;
	}

	public void setEnterUserName(String value) {
		// this.enterUserName = enterUserName;
		this.enterUserName.setText(value);
	}

	public PasswordField getEnterPsw() {
		return EnterPsw;
	}

	public void setEnterPsw(String enterPsw) {
		this.EnterPsw.setText(enterPsw);
	}

	public Label getTxtErrAllFieldsReq() {
		return txtErrAllFieldsReq;
	}

	public void setTxtErrAllFieldsReq(Label txtErrAllFieldsReq) {
		this.txtErrAllFieldsReq = txtErrAllFieldsReq;
	}

	public Label getLogInBeforeLabel() {
		return logInBeforeLabel;
	}

	public void setLogInBeforeLabel(Label logInBeforeLabel) {
		this.logInBeforeLabel = logInBeforeLabel;
	}

	public Label getTxtErrUserName() {
		return txtErrUserName;
	}

	public void setTxtErrUserName(Label txtErrUserName) {
		this.txtErrUserName = txtErrUserName;
	}

	public Label getTxtErrPassword() {
		return txtErrPassword;
	}

	public void setTxtErrPassword(Label txtErrPassword) {
		this.txtErrPassword = txtErrPassword;
	}

	public Label getIDError() {
		return IDError;
	}

	public void setIDError(Label iDError) {
		IDError = iDError;
	}

	/**
	 * when we the next btn click
	 * 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void finishOrderClicked(MouseEvent event) throws Exception {

		m_event = event;
		ArrayList<String> toSend = new ArrayList<String>();
		clearAllErrorMessage();
		if (enterAsEmployee.isSelected()) {// check if employee
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
				} else {
					openHomePage(UserType.employee);
				}

			}
		} else if (enterAsCoustumerRadioBtn.isSelected()) {
			String idNumber = enterIDnumber.getText();
			String memberNumber = enterMemberID.getText();

			if (((idNumber.equals("")) && (memberNumber.equals("")))
					|| (((!idNumber.equals("")) && (!memberNumber.equals(""))))) {
				txtErrAllFieldsReq1.setVisible(true);
				return;
			} else {
				if (!idNumber.equals("")) {// if the user enter id number
					char[] chars = idNumber.toCharArray();
					for (char c : chars) {
						if (!Character.isDigit(c)) {
							IDError.setVisible(true);
							return;
						}
					}

					toSend.add("checkIfIdConnectedWithId");
					toSend.add(idNumber);

					ClientMain.chat.accept(toSend);
					if (ChatClient.dataInArrayList.contains("notValidUserID")) {
						IDError.setVisible(true);
					}

					statusToOpen();
				}

				if (!memberNumber.equals("")) {// if the user enter membership number
					char[] chars = memberNumber.toCharArray();
					for (char c : chars) {
						if (!Character.isDigit(c)) {
							memberNotNumbers.setVisible(true);
							return;
						}
					}

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
		} else
			noSelected.setVisible(true);
	}

	public Label getDontFindMemberShipIDLabel() {
		return dontFindMemberShipIDLabel;
	}

	public void setDontFindMemberShipIDLabel(Label dontFindMemberShipIDLabel) {
		this.dontFindMemberShipIDLabel = dontFindMemberShipIDLabel;
	}

	public Label getMemberNotNumbers() {
		return memberNotNumbers;
	}

	public void setMemberNotNumbers(Label memberNotNumbers) {
		this.memberNotNumbers = memberNotNumbers;
	}

	public TextField getEnterMemberID() {
		return enterMemberID;
	}

	public void setEnterMemberID(String enterMemberID) {
		this.enterMemberID.setText(enterMemberID);

	}

	public TextField getEnterIDnumber() {
		return enterIDnumber;
	}

	public void setEnterIDnumber(String enterIDnumber) {
		this.enterIDnumber.setText(enterIDnumber);
	}

	public RadioButton getEnterAsEmployee() {
		return enterAsEmployee;
	}

	public void setEnterAsEmployee(boolean bool) {
		// this.enterAsEmployee = enterAsEmployee;
		this.enterAsEmployee.setSelected(bool);
	}


	/**
	 * this function for clear all warning
	 */
	private void clearAllErrorMessage() {
		dontFindMemberShipIDLabel.setVisible(false);
		txtErrUserName.setVisible(false);
		txtErrAllFieldsReq1.setVisible(false);
		logInBeforeLabel.setVisible(false);
		txtErrPassword.setVisible(false);
		txtErrAllFieldsReq.setVisible(false);
		IDError.setVisible(false);
		memberNotNumbers.setVisible(false);

	}

	/**
	 * check which status we need to open
	 * 
	 * @throws Exception
	 */
	private void statusToOpen() throws Exception {
		if (ChatClient.dataInArrayList.contains("connectedBefore")) {
			logInBeforeLabel.setVisible(true);
			return;
		} else if (ChatClient.dataInArrayList.contains("member") || ChatClient.dataInArrayList.contains("guide")) {
			openHomePage(UserType.member);
		} else if (ChatClient.dataInArrayList.contains("user")) {
			openHomePage(UserType.user);
		}
	}

	/**
	 * this function open the next page it get which type of user the next page will
	 * get
	 * 
	 * @param userType
	 * @throws Exception
	 */
	private void openHomePage(UserType userType) throws Exception {
		// TODO

		switch (userType) {
		case member: {
			fName = ChatClient.dataInArrayList.get(1);
			lName = ChatClient.dataInArrayList.get(2);
			userID = ChatClient.dataInArrayList.get(0);
			role = ChatClient.dataInArrayList.get(3);
			if (role.equals(Role.Member.toString().toLowerCase()))
				amountOfPeople = Integer.parseInt(ChatClient.dataInArrayList.get(4));
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
			role = "user";
			break;
		}
		default:
			break;
		}

		FXMLLoader loader;
		// nextStages = new NextStages("/fxmlFiles/HomePageForEmployee.fxml", "Home
		// Page", userID);
		loader = nextStages.goToNextStage(m_event);

		if (nextStages instanceof NextStages) {
			HomePageForEmployeeController homePageForEmployeeController = loader.getController();
			homePageForEmployeeController.setDetails(fName, lName, role, userID, park);
			homePageForEmployeeController.setAmountForMember(amountOfPeople);
		}
	}

	public INextStage getNextStages() {
		return nextStages;
	}

	public void setNextStages(INextStage nextStages) {
		this.nextStages = nextStages;
	}

	public void identificationSetVisibility(boolean cond) {
		Identification.setVisible(cond);
	}

	public void loginSetVisibility(boolean cond) {
		login.setVisible(cond);
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText(
				"please choose which type of login you would like to do\n(employee/visitor)\nand fill the required details"); // add
																																// text
																																// to
																																// help
																																// filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nextStages = new NextStages("/fxmlFiles/HomePageForEmployee.fxml", "Home Page", userID);
	}

}
