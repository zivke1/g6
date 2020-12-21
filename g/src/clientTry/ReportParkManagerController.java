package clientTry;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReportParkManagerController {

//	ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May",
//			"June", "July", "August", "September", "October", "November", "December");
	ObservableList<String> months = FXCollections.observableArrayList("01", "02", "03", "04", "05",
		"06", "07", "08", "09", "10", "11", "12");
	ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
	ObservableList<String> parksNames=FXCollections.observableArrayList("Jordan Park","Carmel Park","Tal Park");
	
    @FXML
    private Label parkName;
	@FXML
	private Button showRep;
    @FXML
    private ToggleGroup repoG;
    @FXML
    private Button helpBtn;
	@FXML
	private Button backBtn;

	@FXML
	private Label repYearLabel;

	@FXML
	private ComboBox repMonth;

	@FXML
	private ComboBox repYear;

	@FXML
	private Label repMonthLabel;

	@FXML
	private RadioButton visitCountRep;

	@FXML
	private RadioButton usageRep;

	@FXML
	private RadioButton incomeRep;

	@FXML
	private Button conBtn;
	@FXML
	private Label errSelectReport;
	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;

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

    }
    public void setDetails(String fName, String lName, String role, String userID, String parkName) {
    	this.parkName.setText(parkName);
    	this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS=parkName;
    }

	@FXML
	void showTheReport(MouseEvent event) {
		if(visitCountRep.isSelected())
		{
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			try {
				root = loader.load(getClass().getResource("/fxmlFiles/VisitorAmountReport.fxml").openStream());
				VisitorAmountReportController v=loader.getController();
				v.setDetails(repYear.getValue().toString(),repMonth.getValue().toString(),parkNameS,fName,lName, role, userID);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
				stage.setTitle("Visitor Amount Report");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		if(usageRep.isSelected())
		{
			
		}
		if(incomeRep.isSelected())
		{
			
		}
		//if no one selected err
		errSelectReport.setVisible(true);
	}

	@FXML
	public void initialize() {// initializing the combo boxes
		repMonth.setValue("01");
		repMonth.setItems(months);
		ObservableList<String> months = FXCollections.observableArrayList("01", "02", "03", "04", "05",
				"06", "07", "08", "09", "10", "11", "12");
		ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
		repYear.setValue("2020");
		repYear.setItems(years);
	}

}
