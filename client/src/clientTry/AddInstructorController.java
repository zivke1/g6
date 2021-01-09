package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.NextStages;

/**
 * adding new guide to the DB with all the required information
 *
 */
public class AddInstructorController {

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private TextField fName;

	@FXML
	private TextField lName;

	@FXML
	private TextField id;

	@FXML
	private TextField phoneNum;

	@FXML
	private TextField email;

	@FXML
	private TextField enterCardNumber;

	@FXML
	private ComboBox numVisitor;

	@FXML
	private RadioButton cash;

	@FXML
	private RadioButton creditC;

	@FXML
	private Label ccLabel;

	@FXML
	private Button helpBtn;

	@FXML
	private Label errorMsg;

	private String fNameHM;

	private String lNameHM;

	private String userIDHM;

	private String roleHM;

	private String parkNameHM;

	MouseEvent m_previousPage;
	MouseEvent m_MainPage;

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("Fill all the following details\nin order to register a new instructor."); // add text to help 
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_previousPage.getSource()).getScene().getWindow()).show();
	}

	/**
	 * checking if all the information is filled correctly and if so adding him to
	 * the DB unless their ID is already in the DB
	 * 
	 * @param event
	 */
	@FXML
	void checkRegistration(MouseEvent event) {
		boolean flag = true;// checking if all the fields are filed correctly
		errorMsg.setText("");// resetting the message
		char[] chars = fName.getText().toCharArray();
		if (chars.length == 0) {
			errorMsg.setText("Please enter a valid first name\n");
			flag = false;
		} else {
			for (char c : chars) {
				if (!Character.isLetter(c)) {
					errorMsg.setText("Please enter a valid first name\n");
					flag = false;
					break;
				}
			}
		}

		chars = lName.getText().toCharArray();
		if (chars.length == 0) {
			errorMsg.setText(errorMsg.getText() + "Please enter a valid last name\n");
			flag = false;
		}
		for (char c : chars) {
			if (!Character.isLetter(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid last name\n");
				flag = false;
				break;
			}
		}

		chars = id.getText().toCharArray();
		if (chars.length == 0) {
			errorMsg.setText(errorMsg.getText() + "Please enter a valid ID\n");
			flag = false;
		}
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid ID\n");
				flag = false;
				break;
			}
		}

		chars = phoneNum.getText().toCharArray();
		if (chars.length == 0) {
			errorMsg.setText(errorMsg.getText() + "Please enter a valid phone number\n");
			flag = false;
		}
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid phone number\n");
				flag = false;
				break;
			}
		}

		String emailS = email.getText();
		if (!((emailS.split("@").length == 2) && (emailS.indexOf("@") != 0) && (emailS.indexOf(".") != 0)
				&& (emailS.lastIndexOf(".") != emailS.length() - 1) && (emailS.length() >= 5 && emailS.length() <= 30)
				&& (emailS.indexOf("@") != emailS.indexOf(".") + 1 || emailS.indexOf("@") != (emailS.indexOf(".") + 1))
				&& emailS.contains(".") && emailS.lastIndexOf('.') > emailS.indexOf('@')) || emailS.length() == 0) {
			errorMsg.setText(errorMsg.getText() + "Please enter a valid email\n");
			flag = false;
		}

		if (!creditC.isSelected() && !cash.isSelected()) {
			errorMsg.setText(errorMsg.getText() + "Please select a payment method\n");
			flag = false;
		}

		boolean flagCC = false;

		if (creditC.isSelected()) {
			chars = enterCardNumber.getText().toCharArray();
			if (chars.length == 0) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid credit card number\n");
				flag = false;
			}
			for (char c : chars) {
				if (!Character.isDigit(c)) {
					errorMsg.setText(errorMsg.getText() + "Please enter a valid credit card number\n");
					flag = false;
					break;
				}
			}
			flagCC = true;
		}

		if (flag) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("RegisterMember");
			arr.add(fName.getText());
			arr.add(lName.getText());
			arr.add(id.getText());
			arr.add(email.getText());
			arr.add(phoneNum.getText());
			arr.add("15");// number of visitors
			if (flagCC)
				arr.add(enterCardNumber.getText());
			else
				arr.add("cash");
			arr.add("guide");
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.contains("Exists")) {
				ChatClient.dataInArrayList.clear();
				errorMsg.setText("This ID already exists in our system");
			}
			if (ChatClient.dataInArrayList.contains("Success")) {
				ChatClient.dataInArrayList.remove("Success");

				NextStages nextStages = new NextStages("/fxmlFiles/MembershipRegistrationSuccess.fxml",
						"Membership Registration Success", userIDHM);
				FXMLLoader loader = nextStages.goToNextStage(event);
				MembershipRegistrationSucccesCon controller = loader.getController();
				controller.setMainPage(m_MainPage);
				controller.updateMemberNum(ChatClient.dataInArrayList.get(0));
			}
		}
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", userIDHM);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void saveCC(MouseEvent event) {
		cash.setSelected(false);
		ccLabel.setVisible(true);
		enterCardNumber.setVisible(true);
	}

	@FXML
	void saveCash(MouseEvent event) {
		creditC.setSelected(false);
		ccLabel.setVisible(false);
		enterCardNumber.setVisible(false);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameHM = fName;
		this.lNameHM = lName;
		this.userIDHM = userID;
		this.roleHM = role;
		this.parkNameHM = parkName;
	}

	public void setPreviousPage(MouseEvent event) {
		m_previousPage = event;
	}

	public void setMainPage(MouseEvent event) {
		m_MainPage = event;
	}

}
