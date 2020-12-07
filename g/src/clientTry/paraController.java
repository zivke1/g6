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

public class paraController {
	
	ObservableList<String> parksNames=FXCollections.observableArrayList("Jordan Park","Carmel Park","Tal Park");

	@FXML
	private ComboBox parkName;

	@FXML
	private Button backBtn;

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
	public void initialize() {// initializing the combo boxes
		parkName.setValue("Tal Park");
		parkName.setItems(parksNames);
	}
}
