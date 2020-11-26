package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EnterIDController {
	public static String iD;
	@FXML
	private TextField id;

	@FXML
	void ShowDetails(MouseEvent event) {

		ArrayList<String> arr = new ArrayList<String>();
		arr.add("showTable");
		arr.add(id.getText());
		((Node) event.getSource()).getScene().getWindow().hide();

		controllerTry cT = new controllerTry();
		try {
			cT.showDetails(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void UpdateEmail(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
	    FXMLLoader loader=new FXMLLoader();
		VBox root = null;
		try {
			root = loader.load(getClass().getResource("/clientTry/UpdateEmail.fxml").openStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		iD=id.getText();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
		primaryStage.setTitle("Update Email");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}