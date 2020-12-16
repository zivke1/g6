
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ParkDetailsController {

	ObservableList<String> ParksName = FXCollections.observableArrayList("Tal Park","Carmel Park","Jordan Park");
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
	private Text MaxOrders;

	@FXML
	private Text gapVisitors;

	@FXML
	private Button QuestionBtn;
	@FXML
	private Label explanation;
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
			activityHours.setText("8:00-16:00 Sunday to Thursday");
			ManagerName.setText(ChatClient.dataInArrayList.get(3));// the number is according to the order of the
																	// insert
																	// in the mysqlconnection
			MaxCapacity.setText(ChatClient.dataInArrayList.get(0));
			AvgVisitTime.setText(ChatClient.dataInArrayList.get(1));
			MaxOrders.setText(ChatClient.dataInArrayList.get(2));
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
		parkNameS=parkNameToFetch;
		if (!role.equals("Department Manager")) {
			parkName.setText(parkNameToFetch);
			ArrayList<String> arr = new ArrayList<String>();
			arr.add("FetchParkDetails");
			arr.add(parkNameToFetch);
			try {
				ClientMain.chat.accept(arr);
				activityHours.setText("8:00-16:00 Sunday to Thursday");
				ManagerName.setText(ChatClient.dataInArrayList.get(3));// the number is according to the order of the
																		// insert
																		// in the mysqlconnection
				MaxCapacity.setText(ChatClient.dataInArrayList.get(0));
				AvgVisitTime.setText(ChatClient.dataInArrayList.get(1));
				MaxOrders.setText(ChatClient.dataInArrayList.get(2));
				gapVisitors.setText(ChatClient.dataInArrayList.get(4));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			optOfManager.setVisible(true);	
	}

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
			HomePageForEmployeeController v=loader.getController();
			v.setDetails(fName,lName,role,userID,parkNameS);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Home Page For Employee");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void finishOrderClicked(MouseEvent event) {

	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/ContactUsPopUp.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Contact Us");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		if(i==true)
			setDetails("","", "", "", "Tal Park");
		else
			setDetails("","Tal Park", "Department Manager", "", "");
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
		comboPark.setValue("Choose");
		comboPark.setItems(ParksName);
	}

}

