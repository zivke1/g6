package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewOrderEmployeeController {

    @FXML
    private Button helpBtn;

    @FXML
    private TextField ID;

    @FXML
    private Label errorMsg;
    
    @FXML
    void MoveToOrder(MouseEvent event) {
    	if (ClientMain.chat.checkConnection()) { 
			UserInformationController cT = new UserInformationController();
			if (cT.CheckID(ID.getText())) {
				ArrayList<String> arr = new ArrayList<String>();
				arr.add("Order");
				arr.add(ID.getText());
				((Node) event.getSource()).getScene().getWindow().hide();
				try {
					cT.showDetails(arr);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else
				errorMsg.setText("Error: This ID does not exist in the database. \nPlease enter a valid ID");
		} else
			errorMsg.setText("Error: The server is offline.\nPlease try again later.");

    }

    @FXML
    void backClicked(MouseEvent event) {
    	Stage primaryStage = new Stage();
    	FXMLLoader loader = new FXMLLoader();
		VBox root;
		try {
			root = loader.load(getClass().getResource("/clientTry/HomePageForEmployee.fxml").openStream());
		UserInformationController ct = loader.getController();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
		primaryStage.setTitle("HomePage");
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	
    }

}
