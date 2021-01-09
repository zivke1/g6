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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.NextStages;

/**
 * 		   this class is controller for the FXML ReportParkManager the
 *         controller isn't connect to the DB only call the right page report
 *         and send him the year month and park name.
 *
 */
public class ReportParkManagerController {

	ObservableList<String> months = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12");
	ObservableList<String> years = FXCollections.observableArrayList("2020","2021");
	ObservableList<String> parksNames = FXCollections.observableArrayList("Jordan Park", "Carmel Park", "Tal Park");

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

	private boolean flag = false;

	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS = "Tal Park";
	MouseEvent m_event;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("Please select the report you want to view \nand fill the date fields");  // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; "
		    + "-fx-background-color: #F0F8FF; "
		    + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.parkName.setText(parkName);
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS = parkName;
	}

	
	@FXML
	void showTheReport(MouseEvent event) {
		flag = false;
		if (visitCountRep.isSelected()) {		
			flag = true;
			NextStages nextStages = new NextStages("/fxmlFiles/VisitorAmountReport.fxml", "Visitor Amount Report", userID);
			FXMLLoader loader = nextStages.goToNextStage(event);
			VisitorAmountReportController visitRepControl = loader.getController();
			visitRepControl.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
					role, userID);
			visitRepControl.setPreviousPage(event);
			repMonth.setValue("01");
			repYear.setValue("2020");
			visitCountRep.setSelected(false);
			errSelectReport.setVisible(false);
		}
		if (usageRep.isSelected()) {
			flag = true;
			NextStages nextStages = new NextStages("/fxmlFiles/UsageReports.fxml", "Usage Reports", userID);
			FXMLLoader loader = nextStages.goToNextStage(event);
			UsageReportsController useRepControl = loader.getController();
			useRepControl.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
					role, userID);
			useRepControl.setPreviousPage(event);	
			repMonth.setValue("01");
			repYear.setValue("2020");
			usageRep.setSelected(false);
			errSelectReport.setVisible(false);

		}
		if (incomeRep.isSelected()) {
			flag = true;
			errSelectReport.setVisible(false);
			////ziv change
//			repMonth.setValue("01");
//			repYear.setValue("2020");
	///////ziv end
			incomeRep.setSelected(false);
			NextStages nextStages = new NextStages("/fxmlFiles/IncomeReport.fxml", "Income Report", userID);
			FXMLLoader loader = nextStages.goToNextStage(event);
			incomeReportController incomeControl = loader.getController();
			incomeControl.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
					role, userID);
			incomeControl.setPreviousPage(event);
			repMonth.setValue("01");
			repYear.setValue("2020");
		}
		if (!flag) {
			// if no one selected err
			errSelectReport.setVisible(true);
		}
	}

	@FXML
	public void initialize() {// initializing the combo boxes
		repMonth.setValue("01");
		repMonth.setItems(months);
		ObservableList<String> months = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12");
		
		ObservableList<String> years = FXCollections.observableArrayList("2020","2021");
		repYear.setValue("2020");
		repYear.setItems(years);
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}
