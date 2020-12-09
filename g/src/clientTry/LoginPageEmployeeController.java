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

public class LoginPageEmployeeController {

    @FXML
    private Button helpBtn;

    @FXML
    private TextField ID;

    @FXML
    private Label typeOfOr;

    @FXML
    private Label pName;

    @FXML
    private Label amOfVisit;

    @FXML
    private Label date;

    @FXML
    private Label hour;

    @FXML
    private Label email;

    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		VBox root = null;
		try {
			root = loader.load(getClass().getResource("/clientTry/HomePageForEmployee.fxml").openStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
		primaryStage.setTitle("HomePage");
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	
    }
    
    @FXML
    void viewOrder(MouseEvent event) {
    	String id=ID.getText();
    	if (id.length() > 0) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("CheckOrder");
			arr.add(id);
			ClientMain.chat.accept(arr);
			//need to return it this way
			pName.setText(ChatClient.dataInArrayList.get(0));
			typeOfOr.setText(ChatClient.dataInArrayList.get(1));
			amOfVisit.setText(ChatClient.dataInArrayList.get(2));
			date.setText(ChatClient.dataInArrayList.get(3));
			hour.setText(ChatClient.dataInArrayList.get(4));
			email.setText(ChatClient.dataInArrayList.get(5));
		}
    	pName.setVisible(true);
    	typeOfOr.setVisible(true);
    	amOfVisit.setVisible(true);
    	date.setVisible(true);
    	hour.setVisible(true);
    	email.setVisible(true);
    }


}
