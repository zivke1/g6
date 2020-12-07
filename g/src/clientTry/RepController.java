package clientTry;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RepController {

	ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");
	ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
	ObservableList<String> parksNames=FXCollections.observableArrayList("Jordan Park","Carmel Park","Tal Park");
	
	@FXML
	private ComboBox parkName;

	@FXML
	private Button showRep;

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
	void backClicked(MouseEvent event) {
		// dont know what to do with it
	}

	@FXML
	void usageRep(MouseEvent event) {// cleaning the unused buttons
		visitCountRep.setSelected(false);
		incomeRep.setSelected(false);
	}

	@FXML
	void visitCountRep(MouseEvent event) {// cleaning the unused buttons
		incomeRep.setSelected(false);
		usageRep.setSelected(false);

	}

	@FXML
	void incomeRep(MouseEvent event) {// cleaning the unused buttons
		usageRep.setSelected(false);
		visitCountRep.setSelected(false);
	}

	@FXML
	void showTheReport(MouseEvent event) {
		// shows the actual report
	}

	@FXML
	public void initialize() {// initializing the combo boxes
		repMonth.setValue("January");
		repMonth.setItems(months);
		ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December");
		ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
		repYear.setValue("2020");
		repYear.setItems(years);
		parkName.setValue("Tal Park"); 
		parkName.setItems(parksNames);

	}

}
