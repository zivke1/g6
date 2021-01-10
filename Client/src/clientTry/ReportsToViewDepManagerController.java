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
		nextStages.openPopUp();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		Tooltip tt = new Tooltip();
		tt.setText("View submitted report by\npark managers."); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}

/**
 * set and present usage report for DB
 * @param parkName
 * @param month
 * @param year
 * @param day
 * @param usagePerHour
 */
	public void setUsageReport(String parkName, String month, String year, ArrayList<Integer> day, ArrayList<ArrayList<String>> usagePerHour) {
		headUsageRep.setVisible(true);
		crumUsageRep.setVisible(true);
		anchoreUsageReport.setVisible(true);
		parkNameUsage.setText(parkName);
		monthUsage.setText(month);
		yearUsage.setText(year);

		ArrayList<DayToView> temp = new ArrayList<>();
		int i = 0;
		for(ArrayList<String> arr : usagePerHour) {
			String h1 = arr.get(0);
			String h2 = arr.get(1);
			String h3 = arr.get(2);
			String h4 = arr.get(3);
			String h5 = arr.get(4);
			String h6 = arr.get(5);
			String h7 = arr.get(6);
			String h8 = arr.get(7);
			String h9 = arr.get(8);
			temp.add(new DayToView(day.get(i++).toString(), h1, h2, h3, h4, h5, h6, h7, h8, h9));
		}
		
		// day column
		TableColumn<DayToView, String> dayColumn = new TableColumn<>("Date");
		dayColumn.setMinWidth(80);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		dayColumn.setStyle( "-fx-alignment: CENTER;");
		
		// h1 Column
		TableColumn<DayToView, String> h1Column = new TableColumn<>("8:00");
		h1Column.setMinWidth(80);
		h1Column.setCellValueFactory(new PropertyValueFactory<>("h1"));
		h1Column.setStyle( "-fx-alignment: CENTER;");
		
		// h2 Column
		TableColumn<DayToView, String> h2Column = new TableColumn<>("9:00");
		h2Column.setMinWidth(80);
		h2Column.setCellValueFactory(new PropertyValueFactory<>("h2"));
		h2Column.setStyle( "-fx-alignment: CENTER;");
		
		// h3 Column
		TableColumn<DayToView, String> h3Column = new TableColumn<>("10:00");
		h3Column.setMinWidth(80);
		h3Column.setCellValueFactory(new PropertyValueFactory<>("h3"));
		h3Column.setStyle( "-fx-alignment: CENTER;");
		
		// h4 Column
		TableColumn<DayToView, String> h4Column = new TableColumn<>("11:00");
		h4Column.setMinWidth(80);
		h4Column.setCellValueFactory(new PropertyValueFactory<>("h4"));
		h4Column.setStyle( "-fx-alignment: CENTER;");
		
		// h5 Column
		TableColumn<DayToView, String> h5Column = new TableColumn<>("12:00");
		h5Column.setMinWidth(80);
		h5Column.setCellValueFactory(new PropertyValueFactory<>("h5"));
		h5Column.setStyle( "-fx-alignment: CENTER;");
		
		// h6 Column
		TableColumn<DayToView, String> h6Column = new TableColumn<>("13:00");
		h6Column.setMinWidth(80);
		h6Column.setCellValueFactory(new PropertyValueFactory<>("h6"));
		h6Column.setStyle( "-fx-alignment: CENTER;");
		
		// h7 Column
		TableColumn<DayToView, String> h7Column = new TableColumn<>("14:00");
		h7Column.setMinWidth(80);
		h7Column.setCellValueFactory(new PropertyValueFactory<>("h7"));
		h7Column.setStyle( "-fx-alignment: CENTER;");
		
		// h8 Column
		TableColumn<DayToView, String> h8Column = new TableColumn<>("15:00");
		h8Column.setMinWidth(80);
		h8Column.setCellValueFactory(new PropertyValueFactory<>("h8"));
		h8Column.setStyle( "-fx-alignment: CENTER;");
		
		// h9 Column
		TableColumn<DayToView, String> h9Column = new TableColumn<>("16:00");
		h9Column.setMinWidth(80);
		h9Column.setCellValueFactory(new PropertyValueFactory<>("h9"));
		h9Column.setStyle( "-fx-alignment: CENTER;");
		
		usageTable.setItems(getAmount(temp));
		usageTable.getColumns().addAll(dayColumn, h1Column, h2Column, h3Column, h4Column, h5Column, h6Column,
				h7Column, h8Column, h9Column);
		usageTable.setFixedCellSize(35.0);

	}
/**
 * create ObservableList for table
 * @param temp
 * @return ObservableList<DayToView>
 */
	public ObservableList<DayToView> getAmount(ArrayList<DayToView> temp) {
		ObservableList<DayToView> usageAmount = FXCollections.observableArrayList();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) != null)
				usageAmount.add(temp.get(i));
		}
		return usageAmount;
	}

/**
 * set and present visitors report for DB
 * @param parkName
 * @param month
 * @param year
 * @param totalVisitor
 * @param GroupDays
 * @param UserDays
 * @param MemberDays
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
	
			personal.setName("Users");
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
 * set and present income report from DB
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