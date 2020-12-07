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

public class paraAndRepController {

	ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");
	ObservableList<String> years = FXCollections.observableArrayList("2017", "2018", "2019", "2020");
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
	private RadioButton setMaxVisit;

	@FXML
	private RadioButton setVisitDur;

	@FXML
	private RadioButton setMaxOrder;

	@FXML
	private RadioButton setDiscount;

	@FXML
	private Label maxVisitorsLabel;

	@FXML
	private Label visitDurLabel;

	@FXML
	private Label maxOrderLabel;

	@FXML
	private Label discountLabel;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker untilDate;

	@FXML
	private TextField discountField;

	@FXML
	private TextField maxOrderField;

	@FXML
	private TextField visitDurField;

	@FXML
	private TextField maxVisitField;

	@FXML
	private Label untilLabel;

	@FXML
	private Label fromLabel;

	@FXML
	private Button conBtn;

	@FXML
	void backClicked(MouseEvent event) {
		// dont know what to do with it
	}

	@FXML
	void sendToDepMan(MouseEvent event) {
		// need to add department manager approval somehow
	}

	@FXML
	void setDiscount(MouseEvent event) {// setting the discount data visible and hiding the rest of the parameters
										// details
		setMaxVisit.setSelected(false);
		setMaxOrder.setSelected(false);
		setVisitDur.setSelected(false);
		maxVisitorsLabel.setVisible(false);
		maxVisitField.setVisible(false);
		visitDurLabel.setVisible(false);
		visitDurField.setVisible(false);
		maxOrderLabel.setVisible(false);
		maxOrderField.setVisible(false);
		discountLabel.setVisible(true);
		fromDate.setVisible(true);
		untilDate.setVisible(true);
		untilLabel.setVisible(true);
		fromLabel.setVisible(true);
		discountField.setVisible(true);
	}

	@FXML
	void setMaxOrder(MouseEvent event) {// setting the max order numbers data visible and hiding the rest of the
										// parameters details
		setMaxVisit.setSelected(false);
		setDiscount.setSelected(false);
		setVisitDur.setSelected(false);
		maxVisitorsLabel.setVisible(false);
		maxVisitField.setVisible(false);
		visitDurLabel.setVisible(false);
		visitDurField.setVisible(false);
		maxOrderLabel.setVisible(true);
		maxOrderField.setVisible(true);
		discountLabel.setVisible(false);
		fromDate.setVisible(false);
		untilDate.setVisible(false);
		untilLabel.setVisible(false);
		fromLabel.setVisible(false);
		discountField.setVisible(false);
	}

	@FXML
	void setMaxVisit(MouseEvent event) {// setting the max number of visitors in the park data visible and hiding the
										// rest of the parameters details
		setMaxOrder.setSelected(false);
		setDiscount.setSelected(false);
		setVisitDur.setSelected(false);
		maxVisitorsLabel.setVisible(true);
		maxVisitField.setVisible(true);
		visitDurLabel.setVisible(false);
		visitDurField.setVisible(false);
		maxOrderLabel.setVisible(false);
		maxOrderField.setVisible(false);
		fromDate.setVisible(false);
		untilDate.setVisible(false);
		untilLabel.setVisible(false);
		fromLabel.setVisible(false);
		discountField.setVisible(false);
		discountLabel.setVisible(false);
	}

	@FXML
	void setVisitDur(MouseEvent event) {// setting the visit duration data visible and hiding the rest of the parameters
										// details
		setMaxVisit.setSelected(false);
		setMaxOrder.setSelected(false);
		setDiscount.setSelected(false);
		maxVisitorsLabel.setVisible(false);
		maxVisitField.setVisible(false);
		visitDurLabel.setVisible(true);
		visitDurField.setVisible(true);
		maxOrderLabel.setVisible(false);
		maxOrderField.setVisible(false);
		fromDate.setVisible(false);
		untilDate.setVisible(false);
		untilLabel.setVisible(false);
		fromLabel.setVisible(false);
		discountField.setVisible(false);
		discountLabel.setVisible(false);
	}

	@FXML
	void showParaToUp(MouseEvent event) {// showing the parameters options and hiding the reports options
		setMaxVisit.setVisible(true);
		setVisitDur.setVisible(true);
		setMaxOrder.setVisible(true);
		setDiscount.setVisible(true);
		visitCountRep.setVisible(false);
		usageRep.setVisible(false);
		incomeRep.setVisible(false);
		repYearLabel.setVisible(false);
		repMonth.setVisible(false);
		repYear.setVisible(false);
		repMonthLabel.setVisible(false);
		visitCountRep.setVisible(false);
		showRep.setVisible(false);
		conBtn.setVisible(true);
	}

	@FXML
	void showReports(MouseEvent event) {// showing the reports options and hiding the parameters options
		setMaxVisit.setVisible(false);
		setVisitDur.setVisible(false);
		setMaxOrder.setVisible(false);
		setDiscount.setVisible(false);
		repYearLabel.setVisible(true);
		repMonth.setVisible(true);
		repYear.setVisible(true);
		repMonthLabel.setVisible(true);
		visitCountRep.setVisible(true);
		usageRep.setVisible(true);
		incomeRep.setVisible(true);
		conBtn.setVisible(false);
		showRep.setVisible(true);
		setVisFalse();
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

	void setVisFalse()// hiding all the parameters data
	{
		setMaxVisit.setSelected(false);
		setMaxOrder.setSelected(false);
		setDiscount.setSelected(false);
		maxVisitorsLabel.setVisible(false);
		maxVisitField.setVisible(false);
		visitDurLabel.setVisible(false);
		visitDurField.setVisible(false);
		maxOrderLabel.setVisible(false);
		maxOrderField.setVisible(false);
		fromDate.setVisible(false);
		untilDate.setVisible(false);
		untilLabel.setVisible(false);
		fromLabel.setVisible(false);
		discountField.setVisible(false);
		discountLabel.setVisible(false);

	}

	@FXML
	void showTheReport(MouseEvent event) {
		//shows the actual report
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

	}

}
