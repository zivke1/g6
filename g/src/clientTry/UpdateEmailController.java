package clientTry;



import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateEmailController {

    @FXML
    private TextField newEmail;

    @FXML
    void UpdateEmail(MouseEvent event) {


    	ArrayList<String> arr=new ArrayList<String>();
    	arr.add("updateTable");
    	arr.add(newEmail.getText());
    	arr.add(EnterIDController.iD);
    	arr.add("Email");
    	((Node) event.getSource()).getScene().getWindow().hide();
    	ClientMain.chat.accept(arr);
    	UserInformationController cT = new UserInformationController();
    	try {
    		ArrayList<String> arrShowNewVals=new ArrayList<String>();
    		arrShowNewVals.add("showTable");
    		arrShowNewVals.add(EnterIDController.iD);
			cT.showDetails(arrShowNewVals);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    }
    @FXML
    void BackToID(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	Stage primaryStage = new Stage();
	    FXMLLoader loader=new FXMLLoader();
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
