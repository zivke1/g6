package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateEmailController {

	@FXML
	private TextField newEmail;

	@FXML
	private Label errorMsg;

	@FXML
	void UpdateEmail(MouseEvent event) {
		if (ClientMain.chat.checkConnection()) {

			String email = newEmail.getText();
			if ((email.split("@").length == 2) && (email.indexOf("@") != 0) && (email.indexOf(".") != 0)
					&& (email.lastIndexOf(".") != email.length() - 1) && (email.length() >= 5 && email.length() <= 30)
					&& (email.indexOf("@") != email.indexOf(".") + 1 || email.indexOf("@") != (email.indexOf(".") + 1))
					&& email.contains(".") && email.lastIndexOf('.') > email.indexOf('@')) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add("updateTable");
				arr.add(newEmail.getText());
				arr.add(EnterIDController.iD);
				arr.add("Email");
				((Node) event.getSource()).getScene().getWindow().hide();
				ClientMain.chat.accept(arr);
				UserInformationController cT = new UserInformationController();
				try {
					ArrayList<String> arrShowNewVals = new ArrayList<String>();
					arrShowNewVals.add("showTable");
					arrShowNewVals.add(EnterIDController.iD);
					cT.showDetails(arrShowNewVals);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				errorMsg.setText("Error: Please enter a valid email");
		}
		 else
				errorMsg.setText("Error: The server is offline.\nPlease try again later.");

	}

	@FXML
	void BackToID(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();

		// this make the X btn to close the connection 
		primaryStage.setOnCloseRequest(evt -> {
			if (ClientMain.chat.checkConnection()) {
			ArrayList<String> closeArrayList = new ArrayList<String>();
			closeArrayList.add("close");
			ClientMain.chat.accept(closeArrayList);
			ClientMain.chat.stopConnection();
			}
		});

		FXMLLoader loader = new FXMLLoader();
		VBox root = null;
		try {
			root = loader.load(getClass().getResource("/clientTry/EnterID.fxml").openStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
		primaryStage.setTitle("Enter ID");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
