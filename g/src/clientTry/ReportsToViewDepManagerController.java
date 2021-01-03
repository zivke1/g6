package clientTry;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import util.DayToView;
import util.NextStages;

/**
 * Class present to department manager reports submitted from parks managers
 * The reports: usage report, income report, visitors amount report 
 * @author shani
 *
 */

public class ReportsToViewDepManagerController {

	@FXML
	private Text headIncomeRep;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Label crumIncomeRep;

	@FXML
	private Text headUsageRep;

	@FXML
	private Text headVisitRep;

	@FXML
	private Label crumUsageRep;

	@FXML
	private Label crumVisitRep;

	@FXML
	private Button exitReport;

	@FXML
	private Button helpBtn;

	@FXML
	private AnchorPane anchorVisitorReport;

	@FXML
	private AnchorPane anchorIncomeReport;

	@FXML
	private Label parkNameIncome;

	@FXML
	private Label monthIncome;

	@FXML
	private Label yearIncome;

	@FXML
	private Label totalIncomeLabel;

	@FXML
	private AnchorPane anchoreUsageReport;

	@FXML
	private TableView<DayToView> usageTable;

	@FXML
	private Label monthUsage;

	@FXML
	private Label yearUsage;

	@FXML
	private Label parkNameUsage;

	@FXML
	void exitWindow(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", "");
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	/**
	 * set usage report
	 * 
	 * @param parkName
	 * @param month
	 * @param year
	 * @param usagePerDay
	 */
	public void setUsageReport(String parkName, String month, String year, ArrayList<String> usagePerDay) {
		headUsageRep.setVisible(true);
		crumUsageRep.setVisible(true);
		anchoreUsageReport.setVisible(true);
		parkNameUsage.setText(parkName);
		monthUsage.setText(month);
		yearUsage.setText(year);
		ArrayList<DayToView> temp = new ArrayList<DayToView>();
		for (int i = 0; i < 9; i++) {
			if (usagePerDay.get(i) != null) {
				float x = Float.valueOf(usagePerDay.get(i));
				if (x < 100)
					temp.add(new DayToView("0" + (i+1), x + "%"));
			}
		}
		for (int i = 9; i < 31; i++) {
			if (usagePerDay.get(i) != null) {
				float x = Float.valueOf(usagePerDay.get(i));
				if (x < 100)
					temp.add(new DayToView("" + (i+1), x + "%"));
			}
		}
		// day column
		TableColumn<DayToView, String> dayColumn = new TableColumn<>("Day");
		dayColumn.setMinWidth(150);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

		// usage Column
		TableColumn<DayToView, String> usageColumn = new TableColumn<>("Usage");
		usageColumn.setMinWidth(150);
		usageColumn.setCellValueFactory(new PropertyValueFactory<>("usage"));

		usageTable.setItems(getAmount(temp));
		usageTable.getColumns().addAll(dayColumn, usageColumn);

	}

	public ObservableList<DayToView> getAmount(ArrayList<DayToView> temp) {

		ObservableList<DayToView> usageAmount = FXCollections.observableArrayList();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) != null)
				usageAmount.add(temp.get(i));
		}
		return usageAmount;
	}

	/**
	 * method set visitors amount report for department manager
	 */
	public void setVisitorsReport() {
		headVisitRep.setVisible(true);
		crumVisitRep.setVisible(true);
		anchorVisitorReport.setVisible(true);
	}

	/**
	 * set income report for department manager
	 * 
	 * @param parkName
	 * @param month
	 * @param year
	 * @param income
	 */
	public void setIncomeReport(String parkName, String month, String year, String income) {
		headIncomeRep.setVisible(true);
		crumIncomeRep.setVisible(true);
		anchorIncomeReport.setVisible(true);
		parkNameIncome.setText(parkName);
		monthIncome.setText(month);
		yearIncome.setText(year);
		totalIncomeLabel.setText(income);
	}
}
