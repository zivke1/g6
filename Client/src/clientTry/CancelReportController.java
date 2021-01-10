package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import util.NextStages;

/**
 * controller for cancel report of department manager
 */
public class CancelReportController {
	private String fNameH, lNameH, roleH, userIDH, parkNameH;
	ObservableList<String> parkNames = FXCollections.observableArrayList("Tal Park", "Carmel Park", "Jordan Park");
	ObservableList<String> months = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12");
	ObservableList<String> years = FXCollections.observableArrayList("2020", "2021");

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;
    
	@FXML
	private Button helpBtn; 

	@FXML
	private Label numCancelOrders;

	@FXML
	private Label numExpiredOrders;

	@FXML
	private Label errMsg;

	@FXML
	private ComboBox<String> selectPark;

	@FXML
	private ComboBox<String> selectYear;

	@FXML
	private ComboBox<String> selectMonth;

	@FXML
	private Button showBtn;

	private MouseEvent previousPageEvent;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) previousPageEvent.getSource()).getScene().getWindow()).show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userIDH);
		nextStages.openPopUp();
		}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("This page presents the number of cancelled and expired orders \nfor the selected park and date"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		selectPark.setItems(parkNames);
		selectMonth.setItems(months);
		selectYear.setItems(years);
		selectPark.setValue("Tal Park");
		selectMonth.setValue("1");
		selectYear.setValue("2021");
		

			}
	


	@FXML
	void showReport(MouseEvent event) {
		errMsg.setText("");
		String y, p, m;
		y = selectYear.getValue();
		p = selectPark.getValue();
		m = selectMonth.getValue();
		if (!m.isEmpty() && !y.isEmpty() && !p.isEmpty()) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("cancel report");
			arr.add(p);
			arr.add(m);
			arr.add(y);
			// ClientMain
			ClientMain.chat.accept(arr);
			numCancelOrders.setText(ChatClient.dataInArrayList.get(0));
			numExpiredOrders.setText(ChatClient.dataInArrayList.get(1));
		} else {
			errMsg.setText("Please fill all fileds");
		}
	}

	public void setPreviousPage(MouseEvent event) {
		previousPageEvent = event;

	}
}