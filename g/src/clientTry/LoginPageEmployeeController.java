package clientTry;

import java.io.IOException;

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
		primaryStage.setTitle("HomePage Employee");
		primaryStage.setScene(scene);
		primaryStage.show();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	
    }
    
    @FXML
    void viewOrder(MouseEvent event) {
    	//if the id has order fill the details
    	pName.setVisible(true);
    	typeOfOr.setVisible(true);
    	amOfVisit.setVisible(true);
    	date.setVisible(true);
    	hour.setVisible(true);
    	email.setVisible(true);
    }


}
