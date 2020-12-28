package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.NextStages;
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

	private MouseEvent previousPageEvent;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) previousPageEvent.getSource()).getScene().getWindow()).show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userIDH);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
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

	public void setPreviousPage(MouseEvent event) {
		previousPageEvent = event;
		
	}
}