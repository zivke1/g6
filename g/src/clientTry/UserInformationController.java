package clientTry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * 
 *Get the information of the user with this id
 *
 */
public class UserInformationController {

	@FXML
	private Label firstName;

	@FXML
	private Label lastName;

	@FXML
	private Label errorMsg;

	@FXML
	private Label id;

	@FXML
	private Label email;

	@FXML
	private Label phoneNum;

	public void setDetails(ArrayList<String> a) {
		if (a.get(0) != null) {
			firstName.setText(a.get(0));
			lastName.setText(a.get(1));
			id.setText(a.get(2));
			email.setText(a.get(3));
			phoneNum.setText(a.get(4));
		} else
			firstName.setText("We didn't find this ID in the DB\n please press back and try again");

	}

	public void showDetails(ArrayList<String> arr) throws Exception {
		if (ClientMain.chat.checkConnection()) {
			ClientMain.chat.accept(arr);
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
			VBox root = loader.load(getClass().getResource("/clientTry/UserInformationGui.fxml").openStream());
			UserInformationController ct = loader.getController();
			try {
				ct.setDetails(ChatClient.dataInArrayList);
			} catch (Exception e) {
				e.getStackTrace();
			}

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			primaryStage.setTitle("Details");
			primaryStage.setScene(scene);
			primaryStage.show();
		} else
			errorMsg.setText("Error: The server is offline.\nPlease try again later.");
	}

	@FXML
	void BackToID(MouseEvent event) {
		if (ClientMain.chat.checkConnection()) {
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
				e1.printStackTrace();
			}
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			primaryStage.setTitle("Enter ID");
			primaryStage.setScene(scene);
			primaryStage.show();
		} else
			errorMsg.setText("Error: The server is offline.\nPlease try again later.");
	}

	public boolean CheckID(String idFromController) {
		if (idFromController.length() > 0) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("CheckID");
			arr.add(idFromController);
			ClientMain.chat.accept(arr);
			if (ChatClient.dataInArrayList.contains("True"))
				return true;
		}
		return false;
	}

}