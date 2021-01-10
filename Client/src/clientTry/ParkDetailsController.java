
package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NextStages;
import util.Role;
/**
 * in this page we can know the details of each park
 *
 *
 */
public class ParkDetailsController {

	ObservableList<String> ParksName = FXCollections.observableArrayList("Tal Park", "Carmel Park", "Jordan Park");
	@FXML
	private AnchorPane optOfManager;
	@FXML
	private ComboBox comboPark;
	@FXML
	private Label parkName;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Text activityHours;

	@FXML
	private Text ManagerName;

	@FXML
	private Text MaxCapacity;

	@FXML
	private Text AvgVisitTime;

	@FXML
	private Text gapVisitors;

	@FXML
	private Button QuestionBtn;
	
	@FXML
	private Label explanation;
	
	MouseEvent m_previousPage;
	private boolean i = true;
	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;

	@FXML
	void comboAction(ActionEvent event) {
		parkName.setText(comboPark.getValue().toString());
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("FetchParkDetails");
		arr.add(parkName.getText());
		try {
			ClientMain.chat.accept(arr);
			// activityHours.setText("8:00-16:00 Sunday to Thursday");
			activityHours.setText("8:00-16:00 Seven days a week");
			ManagerName.setText(ChatClient.dataInArrayList.get(3));// the number is according to the order of the
																	// insert
																	// in the mysqlconnection
			MaxCapacity.setText(ChatClient.dataInArrayList.get(0));
			AvgVisitTime.setText(ChatClient.dataInArrayList.get(1)+" hours");
			// MaxOrders.setText(ChatClient.dataInArrayList.get(2));
			gapVisitors.setText(ChatClient.dataInArrayList.get(4));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setDetails(String fName, String lName, String role, String userID, String parkNameToFetch) {
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		parkNameS = parkNameToFetch;
		
		
		if (!(role.equals(Role.DepartmentManager.toString()))) {	
			comboPark.setValue(parkNameS);
			optOfManager.setVisible(false);
		} else {
			optOfManager.setVisible(true);
			comboPark.setValue("Tal Park");
		}
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("FetchParkDetails");
			// ziv
			if (parkNameToFetch.equals("DepManager") == false) {
				arr.add(parkNameToFetch);
				parkName.setText(parkNameToFetch);
			}else {
				arr.add("Tal Park");
				parkName.setText("Tal Park"); //shani
			}
			//ziv end
			try {
				ClientMain.chat.accept(arr);
				activityHours.setText("8:00-16:00 Seven days a week");
				ManagerName.setText(ChatClient.dataInArrayList.get(3));// the number is according to the order of the
																		// insert
																		// in the mysqlconnection
				MaxCapacity.setText(ChatClient.dataInArrayList.get(0));
				AvgVisitTime.setText(ChatClient.dataInArrayList.get(1) + " hours");
				// MaxOrders.setText(ChatClient.dataInArrayList.get(2));
				gapVisitors.setText(ChatClient.dataInArrayList.get(4));
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_previousPage.getSource()).getScene().getWindow()).show();

	}
/*
	@FXML
	void finishOrderClicked(MouseEvent event) {

	}
*/
	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page show the park details ");  // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; "
		    + "-fx-background-color: #F0F8FF; "
		    + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);

	}

	@FXML
	void explainGapVisitors(MouseEvent event) {
		if (i == true) {
			explanation.setVisible(true);
			i = false;
		} else {
			explanation.setVisible(false);
			i = true;
		}
	}

	@FXML
	public void initialize() {// initializing the combo box
		comboPark.setValue("Tal Park");
		comboPark.setItems(ParksName);
		explanation.setVisible(false);
	}

	public void setPreviousPage(MouseEvent event) {
		m_previousPage= event;
		
	}

}
