package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * 
 * @author Idan
 *controller for cancel report of department manager 
 */
public class CancelReportController {
	private String fNameH, lNameH, roleH, userIDH, parkNameH;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Label numCancelOrders;

	@FXML
	private Label numExpiredOrders;

	@FXML
	private ImageView imgContactUs;

	@FXML
	void backClicked(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/ReportDepartmentManagerPage.fxml").openStream());
			ReportDeparmentManagerController controller = loader.getController();
			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Report Deparment Manager");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		//all the func work from here 
		ArrayList<String> arr= new ArrayList<>();
		arr.add("cancel report");
		//ClientMain
		ClientMain.chat.accept(arr);
		numCancelOrders.setText(ChatClient.dataInArrayList.get(0));
		numExpiredOrders.setText(ChatClient.dataInArrayList.get(1));
	}
}