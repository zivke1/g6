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

public class UserInformationController {
//	static public controllerTry c = new controllerTry();
	@FXML
	private Label firstName;

	@FXML
	private Label lastName;

	@FXML
	private Label id;

	@FXML
	private Label email;//ziv update

	@FXML
	private Label phoneNum;

	public void setDetails(ArrayList<String> a) {
		firstName.setText(a.get(0));
		lastName.setText(a.get(1));
		id.setText(a.get(2));
		email.setText(a.get(3));
		phoneNum.setText(a.get(4));
	}

	public void showDetails(ArrayList<String> arr) throws Exception
	  {
			myMain.chat.accept(arr);
		    Stage primaryStage = new Stage();
		    FXMLLoader loader=new FXMLLoader();
			VBox root = loader.load(getClass().getResource("/clientTry/UserInformationGui.fxml").openStream());
			UserInformationController ct = loader.getController();
			try {
				ct.setDetails(ChatClient.dataInArrayList);
			}
			catch(Exception e)
			{
				e.getStackTrace();
			}
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			primaryStage.setTitle("Details");
			primaryStage.setScene(scene);
			primaryStage.show();
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
				e1.printStackTrace();
			}
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			primaryStage.setTitle("Enter ID");
			primaryStage.setScene(scene);
			primaryStage.show();
	    }
	
}