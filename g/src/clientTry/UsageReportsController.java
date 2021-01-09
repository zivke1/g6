package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.DayToView;
import util.NextStages;

/**
 *	 this class is controller for the FXML UsageReports the
 *         controller fetch from the DB the data for the report in a specific
 *         month and display table with the data. func calculate the usage of
 *         the numbers of visitor each day
 *
 */
public class UsageReportsController {

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private TableView<DayToView> table;

	@FXML
	private Label month;

	@FXML
	private Label year;

	@FXML
	private Button btnSubmit;
	
	@FXML
	private Label parkName;
	private int usageNumber;
	private ArrayList<DayToView> temp;
	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;
	@FXML
	private Label submitted;
	private boolean flag = false;
	private MouseEvent m_event;
	ArrayList<String> usage = new ArrayList<>();

	
	/**
	 * get data from the previous page and build the table of the usage report
	 * @param year
	 * @param month
	 * @param parkName
	 * @param fName
	 * @param lName
	 * @param role
	 * @param userID
	 */
	public void setDetails(String year, String month, String parkName, String fName, String lName, String role,
			String userID) {// the other page call to this method
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS = parkName;
		this.year.setText(year);
		this.month.setText(month);
		this.parkName.setText(parkName);
		ArrayList<String> arr = new ArrayList<>();
		arr.add("UsageReport");
		arr.add(parkName);
		arr.add(year);
		arr.add(month);
		try {
			ClientMain.chat.accept(arr);
	 		temp=ChatClient.dataInArrayListDayToView;
				
				// day column
				TableColumn<DayToView, String> dayColumn = new TableColumn<>("Date");
				dayColumn.setMinWidth(90);
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
				
				table.setItems(getOrders(temp));
				table.getColumns().addAll(dayColumn, h1Column, h2Column, h3Column, h4Column, h5Column, h6Column,
						h7Column, h8Column, h9Column);
				table.setFixedCellSize(35.0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		tt.setText("This page show report of usage\n"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
	}
/**
 * ObservableList for table
 * @param temp
 * @return ObservableList<DayToView>
 */
	public ObservableList<DayToView> getOrders(ArrayList<DayToView> temp) {

		ObservableList<DayToView> orders = FXCollections.observableArrayList();
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i) != null)
				orders.add(temp.get(i));
		}
		return orders;
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

	/**
	 * save reports in DB for department manager
	 * @param event
	 */
	@FXML
	void submitUsageReport(ActionEvent event) {
		if (flag == false) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("SubmitUsageReport");
			arr.add(year.getText());
			arr.add(month.getText());
			arr.add(parkName.getText());
			for(DayToView d: temp)
			{
				arr.add(d.getH1());
				arr.add(d.getH2());
				arr.add(d.getH3());
				arr.add(d.getH4());
				arr.add(d.getH5());
				arr.add(d.getH6());
				arr.add(d.getH7());
				arr.add(d.getH8());
				arr.add(d.getH9());
			}
			ClientMain.chat.accept(arr);
			flag = true;
			submitted.setVisible(true);
		}
	}

}
