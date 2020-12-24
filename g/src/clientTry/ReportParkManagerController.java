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

/**
 * @author eliran this class is controller for the FXML ReportParkManager the
 *         controller isn't connect to the DB only call the right page report
 *         and send him the year month and park name.
 *
 */
public class ReportParkManagerController {

	ObservableList<String> months = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08",
			"09", "10", "11", "12");
	ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
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
		this.parkNameS = parkName;
	}

	@FXML
	void showTheReport(MouseEvent event) {
		if (visitCountRep.isSelected()) {
			flag = true;
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			try {
				root = loader.load(getClass().getResource("/fxmlFiles/VisitorAmountReport.fxml").openStream());
				VisitorAmountReportController v = loader.getController();
				v.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
						role, userID);
				Scene scene = new Scene(root);
				v.setPreviousPage(event);
				repMonth.setValue("01");
				repYear.setValue("2020");
				visitCountRep.setSelected(false);
				errSelectReport.setVisible(false);
				flag=false;
				scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
				stage.setTitle("Visitor Amount Report");
				stage.setScene(scene);
				stage.show();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		if (usageRep.isSelected()) {
			flag = true;
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			try {
				root = loader.load(getClass().getResource("/fxmlFiles/UsageReports.fxml").openStream());
				UsageReportsController v = loader.getController();
				v.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
						role, userID);
				v.setPreviousPage(event);
				repMonth.setValue("01");
				repYear.setValue("2020");
				usageRep.setSelected(false);
				errSelectReport.setVisible(false);
				flag=false;
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
				stage.setTitle("Usage Reports");
				stage.setScene(scene);
				stage.show();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		if (incomeRep.isSelected()) {
			flag = true;
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root;
			try {
				root = loader.load(getClass().getResource("/fxmlFiles/IncomeReport.fxml").openStream());
				incomeReportController v = loader.getController();
				v.setDetails(repYear.getValue().toString(), repMonth.getValue().toString(), parkNameS, fName, lName,
						role, userID);
				v.setPreviousPage(event);
				repMonth.setValue("01");
				repYear.setValue("2020");
				errSelectReport.setVisible(false);
				incomeRep.setSelected(false);
				flag=false;
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
				stage.setTitle("Income Report");
				stage.setScene(scene);
				stage.show();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
		ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
		repYear.setValue("2020");
		repYear.setItems(years);
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}
