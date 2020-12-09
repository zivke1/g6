package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MembershipRegisertrationController {

	ObservableList<String> amountOfVisitors = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12", "13", "14", "15");

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
	private TextField ccField;

	@FXML
	private ComboBox numVisitor;

	@FXML
	private RadioButton cash;

	@FXML
	private RadioButton creditC;

	@FXML
	private Label ccLabel;

	@FXML
	private Label errorMsg;

	@FXML
	void backClicked(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("HomePage");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void checkRegistration(MouseEvent event) {
		boolean flag = true;// checking if all the fields are filed correctly
		char[] chars = fName.getText().toCharArray();
		for (char c : chars) {
			if (!Character.isLetter(c)) {
				errorMsg.setText("Please enter a valid first name\n");
				flag = false;
				break;
			}
		}

		chars = lName.getText().toCharArray();
		for (char c : chars) {
			if (!Character.isLetter(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid last name\n");
				flag = false;
				break;
			}
		}

		chars = id.getText().toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				errorMsg.setText(errorMsg.getText() + "Please enter a valid ID\n");
				flag = false;
				break;
			}
		}

		chars = phoneNum.getText().toCharArray();
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
				&& emailS.contains(".") && emailS.lastIndexOf('.') > emailS.indexOf('@'))) {
			errorMsg.setText(errorMsg.getText() + "Please enter a valid email\n");
			flag = false;
		}

		if (!creditC.isSelected() && !cash.isSelected()) {
			errorMsg.setText(errorMsg.getText() + "Please select a payment method\n");
			flag = false;
		}
		boolean flagCC = false;
		if (creditC.isSelected()) {
			chars = ccField.getText().toCharArray();
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
			arr.add("members");
			arr.add("users");
			arr.add(fName.getText());
			arr.add(lName.getText());
			arr.add(id.getText());
			arr.add(phoneNum.getText());
			arr.add(email.getText());
			arr.add(numVisitor.getValue().toString());
			arr.add(id.getText());
			if (flagCC)
				arr.add(ccField.getText());
			else
				arr.add("cash");
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.contains("Exists"))
			{
				ChatClient.dataInArrayList.clear();
				errorMsg.setText("This ID already exists in our system");
			}
			if (ChatClient.dataInArrayList.contains("Success")) {
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Parent root;
				try {
					root = loader.load(getClass().getResource("/fxmlFiles/MembershipRegistrationSuccess.fxml").openStream());
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
					stage.setTitle("Membership Registration Success");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	public void initialize() {// initializing the combo box
		numVisitor.setValue("Tal Park");
		numVisitor.setItems(amountOfVisitors);
	}
}
