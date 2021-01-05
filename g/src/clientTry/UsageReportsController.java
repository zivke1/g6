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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.DayToView;
import util.NextStages;

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
	@FXML
    private Label submitted;
	private boolean flag=false;
	private MouseEvent m_event;
	ArrayList<String> usage=new ArrayList<>();

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
		arr.add(parkName);
		try {
			ClientMain.chat.accept(arr);
			if(!ChatClient.dataInArrayList.get(0).equals("0"))
			{
				usageNumber=Integer.valueOf(ChatClient.dataInArrayList.get(0));
				ArrayList<DayToView> temp = new ArrayList<DayToView>();
				for(int i=1;i<10;i++)
				{
					float x=(Float.valueOf(ChatClient.dataInArrayList.get(i))/usageNumber)*100;//usageNumber is number that count to full capacity
					usage.add(x+"");
					if(x<100)
						temp.add(new DayToView("0"+i,x+"%"));
				}
				for(int i=10;i<ChatClient.dataInArrayList.size();i++)
				{
					float x=(Float.valueOf(ChatClient.dataInArrayList.get(i))/usageNumber)*100;//usageNumber is number that count to full capacity
					usage.add(x+"");
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
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {


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
    	if(flag==false)
    	{
    		ArrayList<String> arr = new ArrayList<>();
    		arr.add("SubmitUsageReport");
    		arr.add(year.getText());
    		arr.add(month.getText());
    		arr.add(parkName.getText());
    		if(usage.size()==28)
    		{
    			usage.add("100");
    			usage.add("100");
    			usage.add("100");			
    		}
    		if(usage.size()==30)
    		{
    			usage.add("100");		
    		}
    		arr.addAll(usage);
    		usage.clear();
    		ClientMain.chat.accept(arr);
    		flag=true;
    	    submitted.setVisible(true);
    	}
    }


}
