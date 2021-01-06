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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import util.DayToView;
import util.NextStages;

/**
 * Class present to department manager reports submitted from parks managers.
 * present one of the reports: usage report, income report, visitors amount report
 * according to his choice.
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
	private StackedBarChart<String, Number> chart;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private Label monthVisit;

	@FXML
	private Label yearVisit;

	@FXML
	private Label parkNameVisit;

	@FXML
	private Label totalAmountOfVisitors;

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

	XYChart.Series<String, Number> group, personal, member;

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
		Tooltip tt = new Tooltip();
		tt.setText("View submitted report by\npark managers."); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

	/**
	 * set usage report
	 * 
	 * @param parkName
	 * @param month
	 * @param year
	 * @param usagePerDay
	 */
	public void setUsageReport(String parkName, String month, String year, String day, ArrayList<String> usagePerHour) {
		headUsageRep.setVisible(true);
		crumUsageRep.setVisible(true);
		anchoreUsageReport.setVisible(true);
		parkNameUsage.setText(parkName);
		monthUsage.setText(month);
		yearUsage.setText(year);
//		ArrayList<DayToView> temp = new ArrayList<DayToView>();
//		for (int i = 0; i < 9; i++) {
//			if (usagePerDay.get(i) != null) {
//				float x = Float.valueOf(usagePerDay.get(i));
//				if (x < 100)
//					temp.add(new DayToView("0" + (i + 1), x + "%"));
//			}
//		}
//		for (int i = 9; i < 31; i++) {
//			if (usagePerDay.get(i) != null) {
//				float x = Float.valueOf(usagePerDay.get(i));
//				if (x < 100)
//					temp.add(new DayToView("" + (i + 1), x + "%"));
//			}
//		}
//		// day column
//		TableColumn<DayToView, String> dayColumn = new TableColumn<>("Day");
//		dayColumn.setMinWidth(150);
//		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
//
//		// usage Column
//		TableColumn<DayToView, String> usageColumn = new TableColumn<>("Usage");
//		usageColumn.setMinWidth(150);
//		usageColumn.setCellValueFactory(new PropertyValueFactory<>("usage"));
//
//		usageTable.setItems(getAmount(temp));
//		usageTable.getColumns().addAll(dayColumn, usageColumn);

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
	public void setVisitorsReport(String parkName, String month, String year, String totalVisitor, ArrayList<String> GroupDays, ArrayList<String> UserDays, ArrayList<String> MemberDays) {
		headVisitRep.setVisible(true);
		crumVisitRep.setVisible(true);
		anchorVisitorReport.setVisible(true);
		parkNameVisit.setText(parkName);
		yearVisit.setText(year);
		monthVisit.setText(month);
		totalAmountOfVisitors.setText(totalVisitor);
		
		chart.getData().clear();
		chart.setAnimated(false);
		xaxis.lookup(".axis-label").setStyle("-fx-label-padding: -5 0 -40 0;");
		xaxis.categorySpacingProperty().add(5);
		yaxis.setLowerBound(0);

		try {
			personal = new XYChart.Series();
			member = new XYChart.Series();
			group = new XYChart.Series();

			/**
			 * add for each day the number of groups
			 */

			group.setName("Group");
			group.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(GroupDays.get(0))));
			group.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(GroupDays.get(1))));
			group.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(GroupDays.get(2))));
			group.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(GroupDays.get(3))));
			group.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(GroupDays.get(4))));
			group.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(GroupDays.get(5))));
			group.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(GroupDays.get(6))));
	
			personal.setName("Personal");
			personal.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(UserDays.get(0))));
			personal.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(UserDays.get(1))));
			personal.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(UserDays.get(2))));
			personal.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(UserDays.get(3))));
			personal.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(UserDays.get(4))));
			personal.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(UserDays.get(5))));
			personal.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(UserDays.get(6))));

			member.setName("Member");
			member.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(MemberDays.get(0))));
			member.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(MemberDays.get(1))));
			member.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(MemberDays.get(2))));
			member.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(MemberDays.get(3))));
			member.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(MemberDays.get(4))));
			member.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(MemberDays.get(5))));
			member.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(MemberDays.get(6))));

			chart.getData().addAll(personal, group, member);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
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
