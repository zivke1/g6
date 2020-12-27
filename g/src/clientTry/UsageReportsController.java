package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.DayToView;

/**
 * @author Eliran
 * this class is controller for the FXML UsageReports
 * the controller fetch from the DB the data for the report 
 * in a specific month
 * and display table with the data.
 * func calculate the usage of the numbers of visitor each day
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
	private Label parkName;
	private int usageNumber;

	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;

	private MouseEvent m_event;

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
		arr.add("UsageReports");
		arr.add(year);
		arr.add(month);
		// arr.add(parkName);<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		arr.add("Tal Park");
		try {
			ClientMain.chat.accept(arr);
			if(!ChatClient.dataInArrayList.get(0).equals("0"))
			{
				usageNumber=Integer.valueOf(ChatClient.dataInArrayList.get(0));
				ArrayList<DayToView> temp = new ArrayList<DayToView>();
				for(int i=1;i<10;i++)
				{
					float x=(Float.valueOf(ChatClient.dataInArrayList.get(i))/usageNumber)*100;//usageNumber is number that count to full capacity
					if(x<100)
						temp.add(new DayToView("0"+i,x+"%"));
				}
				for(int i=10;i<ChatClient.dataInArrayList.size();i++)
				{
					float x=(Float.valueOf(ChatClient.dataInArrayList.get(i))/usageNumber)*100;//usageNumber is number that count to full capacity
					if(x<100)
						temp.add(new DayToView(""+i,x+"%"));
				}
				// day column
				TableColumn<DayToView, String> dayColumn = new TableColumn<>("Day");
				dayColumn.setMinWidth(150);
				dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

				// usage Column
				TableColumn<DayToView, String> usageColumn = new TableColumn<>("Usage");
				usageColumn.setMinWidth(150);
				usageColumn.setCellValueFactory(new PropertyValueFactory<>("usage"));
				
				table.setItems(getOrders(temp));

				table.getColumns().addAll(dayColumn, usageColumn);
			} else
				System.out.println("Error in the data");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_event.getSource()).getScene().getWindow()).show();
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
//		ArrayList<DayToView> temp = new ArrayList<DayToView>();
//		temp.add(new DayToView("01", "15%"));
//		temp.add(new DayToView("02", "2%"));
//		temp.add(new DayToView("03", "38%"));
//		temp.add(new DayToView("04", "56%"));
//		temp.add(new DayToView("07", "80%"));
//		table.setItems(getOrders(temp));
//
//		table.getColumns().addAll(dayColumn, usageColumn);

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

}
