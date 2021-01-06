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
 * @author Eliran this class is controller for the FXML UsageReports the
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
			ArrayList<DayToView> temp;
			ClientMain.chat.accept(arr);
	 		temp=ChatClient.usageReportDayToView;
				
				// day column
				TableColumn<DayToView, String> dayColumn = new TableColumn<>("");
				dayColumn.setMinWidth(150);
				dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

				// h1 Column
				TableColumn<DayToView, String> h1Column = new TableColumn<>("8:00");
				h1Column.setMinWidth(150);
				h1Column.setCellValueFactory(new PropertyValueFactory<>("h1"));

				// h2 Column
				TableColumn<DayToView, String> h2Column = new TableColumn<>("9:00");
				h2Column.setMinWidth(150);
				h2Column.setCellValueFactory(new PropertyValueFactory<>("h2"));

				// h3 Column
				TableColumn<DayToView, String> h3Column = new TableColumn<>("10:00");
				h3Column.setMinWidth(150);
				h3Column.setCellValueFactory(new PropertyValueFactory<>("h3"));

				// h4 Column
				TableColumn<DayToView, String> h4Column = new TableColumn<>("11:00");
				h4Column.setMinWidth(150);
				h4Column.setCellValueFactory(new PropertyValueFactory<>("h4"));

				// h5 Column
				TableColumn<DayToView, String> h5Column = new TableColumn<>("12:00");
				h5Column.setMinWidth(150);
				h5Column.setCellValueFactory(new PropertyValueFactory<>("h5"));

				// h6 Column
				TableColumn<DayToView, String> h6Column = new TableColumn<>("13:00");
				h6Column.setMinWidth(150);
				h6Column.setCellValueFactory(new PropertyValueFactory<>("h6"));

				// h7 Column
				TableColumn<DayToView, String> h7Column = new TableColumn<>("14:00");
				h7Column.setMinWidth(150);
				h7Column.setCellValueFactory(new PropertyValueFactory<>("h7"));

				// h8 Column
				TableColumn<DayToView, String> h8Column = new TableColumn<>("15:00");
				h8Column.setMinWidth(150);
				h8Column.setCellValueFactory(new PropertyValueFactory<>("h8"));

				// h9 Column
				TableColumn<DayToView, String> h9Column = new TableColumn<>("16:00");
				h9Column.setMinWidth(150);
				h9Column.setCellValueFactory(new PropertyValueFactory<>("h9"));

				table.setItems(getOrders(temp));
				table.getColumns().addAll(dayColumn, h1Column, h2Column, h3Column, h4Column, h5Column, h6Column,
						h7Column, h8Column, h9Column);
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

	@FXML
	void submitUsageReport(ActionEvent event) {
		if (flag == false) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("SubmitUsageReport");
			arr.add(year.getText());
			arr.add(month.getText());
			arr.add(parkName.getText());
			ClientMain.chat.accept(arr);
			flag = true;
			submitted.setVisible(true);
		}
	}

//
// * package gui;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Locale;
//import java.util.Map;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.ScatterChart;
//import javafx.scene.chart.XYChart;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//
//import java.util.ResourceBundle;
//
//import java.util.List;
//
//import entity.Employee;
//import entity.EntityConstants;
//import entity.ParkCapacityReport;
//
//import javafx.fxml.Initializable;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.RowConstraints;
//import message.ClientMessage;
//import message.ClientMessageType;
///**
// * 
// * This class responsible for the control page of the park manager's capacity report
// *
// */
// 
//public class ParkManagerCapacityReportController implements Initializable {
//	GUIControl guiControl = GUIControl.getInstance();
//
//	@FXML
//	private Label month;
//
//	@FXML
//	private Label parkName;
//	
//
//	    @FXML
//	    private Label year2021;
//	    @FXML
//	    private ListView<String> list8;
//
//	    @FXML
//	    private ListView<String> list9;
//
//	    @FXML
//	    private ListView<String> list10;
//
//	    @FXML
//	    private ListView<String> list11;
//
//	    @FXML
//	    private ListView<String> list12;
//
//	    @FXML
//	    private ListView<String> list13;
//
//	    @FXML
//	    private ListView<String> list14;
//
//	    @FXML
//	    private ListView<String> list15;
//
//	    @FXML
//	    private ListView<String> list16;
//
//	    @FXML
//	    private ListView<String> list17;
//
//	    @FXML
//	    private ListView<String> listDate;
//	    /**
//	     * initialize all the Data report before the page uploaded and displayed to the user
//	     */
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//	
//
//		Calendar c = Calendar.getInstance();
//		
//		String park = ((Employee) (guiControl.getUser())).getParkName();
//		parkName.setText(park);
//		 year2021.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
//		month.setText(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
//		int intMonth=Calendar.MONTH-1;
//		int thisDay = c.get(Calendar.DAY_OF_MONTH);
//		guiControl.sendToServer(new ClientMessage(ClientMessageType.PARK_MNG_CAPACITY_REPORT, park));
//		Map<Integer, boolean[]> capacityMap = (Map<Integer, boolean[]> ) guiControl.getServerMsg().getMessage();
//		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//		
//		ObservableList list=FXCollections.observableArrayList();
//		int key=EntityConstants.PARK_OPEN;
//		
//		HashMap<Integer, ListView<String>> map = new LinkedHashMap<Integer, ListView<String>>();
//	map.put(key++,list8);
//	map.put(key++, list9);
//	map.put(key++,list10);	
//	map.put(key++,list11);
//	map.put(key++,list12);
//	map.put(key++,list13);
//	map.put(key++,list14);
//	map.put(key++,list15);
//	map.put(key++,list16);
//	map.put(key++,list17);
//	 key=EntityConstants.PARK_OPEN;
//	 ObservableList listDateOb=FXCollections.observableArrayList();
//		for(boolean[] b: capacityMap.values())
//		{ list.removeAll(list);
//		list.add(key>=10?key+":00":"0"+key+":00");
//			for(int i=1;i<=thisDay;i++)
//			{
//				if(b[i]==false)
//				{
//					list.add("Not Full");
//				}
//				else
//					list.add("");
//				
//			}
//			
//			map.get(key).getItems().addAll(list);
//			key++;
//			if(key>EntityConstants.PARK_CLOSED)
//				break;
//		}
//		listDateOb.add("");
//		for(int i=1;i<=thisDay;i++)   
//		listDateOb.add(i<10?"0"+i+"/"+(intMonth<10?"0"+intMonth:intMonth):i+"/"+(intMonth<10?"0"+intMonth:intMonth));
//		
//		listDate.getItems().addAll(listDateOb);
//		}
//	
//}
// */
}
