package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class controllerTry {
//	static public controllerTry c = new controllerTry();
	@FXML
	private Label firstName;

	@FXML
	private Label lastName;

	@FXML
	private Label id;

	@FXML
	private Label email;

	@FXML
	private Label phoneNum;

	public void setDetails(ArrayList<String> a) {
		firstName.setText(a.get(0));
		lastName.setText(a.get(1));
		id.setText(a.get(2));
		email.setText(a.get(3));
		phoneNum.setText(a.get(4));
	}
//	public void start(Stage primaryStage) throws Exception
//	{
//		ArrayList<String> arr=new ArrayList<>();
//		arr.add("showTable");
//		arr.add("316222");
//		myMain.chat.accept(arr);
//		showDetails();
//	}

	public void showDetails(ArrayList<String> arr) throws Exception
	  {
			myMain.chat.accept(arr);
		    Stage primaryStage = new Stage();
		    FXMLLoader loader=new FXMLLoader();
			VBox root = loader.load(getClass().getResource("/clientTry/FxmlTry.fxml").openStream());
			controllerTry ct = loader.getController();
			try {
				ct.setDetails(ChatClient.dataInArrayList);
			}
			catch(Exception e)
			{
				e.getStackTrace();
			}
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			primaryStage.setTitle("eliran yoyo");
			primaryStage.setScene(scene);
			primaryStage.show();
	  }
	
	
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		// TODO Auto-generated method stub
//		firstName = new Label();
//		lastName = new Label();
//		id = new Label();
//		email = new Label();
//		phoneNum = new Label();
//	}

}
//public void setDetails() {
//		firstName.setText("eliraaaaaaaaaaaaaaaaaan");
//		lastName.setText("dam");
//		id.setText("316275");
//		email.setText("eliran@niz");
//		phoneNum.setText("05234");
//	}
