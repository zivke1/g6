package clientTry;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class VisitorAmountReportController implements Initializable {

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private PieChart pieChart;

	@FXML
	private Label year;

	@FXML
	private Label month;

	@FXML
	private Label amountOfUsers;

	@FXML
	private Label amountOfMembers;

	@FXML
	private Label amountOfGroug;

	@FXML
	private Label totalAmountOfVisitors;
	@FXML
	private Label parkName;
	@FXML
    private Label noReportToPresent;
	
	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;

	private float x = 33, y = 33, z = 34;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/ReportParkManager.fxml").openStream());
			ReportParkManagerController v=loader.getController();
			v.setDetails(fName,lName,role,userID,parkNameS);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Home Page For Employee");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setDetails(String year, String month, String parkName, String fName, String lName, String role, String userID) {// the other page call to this method
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS=parkName;
		this.year.setText(year);
		this.month.setText(month);
		this.parkName.setText(parkName);
		ArrayList<String> arr = new ArrayList<>();
		arr.add("VisitorAmountReport");
		arr.add(year);
		arr.add(month);
		//arr.add(parkName);<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		arr.add("Tal Park");
		try {
			ClientMain.chat.accept(arr);
			if(ChatClient.dataInArrayList.contains("VisitorAmountReport"))
			{
				if(ChatClient.dataInArrayList.get(0)!=null) {
				totalAmountOfVisitors.setText(ChatClient.dataInArrayList.get(0));
				amountOfUsers.setText(ChatClient.dataInArrayList.get(1));
				amountOfGroug.setText(ChatClient.dataInArrayList.get(2));
				amountOfMembers.setText(ChatClient.dataInArrayList.get(3));
				System.out.println(totalAmountOfVisitors.getText()+amountOfUsers.getText()+amountOfGroug.getText()+amountOfMembers.getText());
				z=100*Float.valueOf(amountOfGroug.getText())/Float.valueOf(totalAmountOfVisitors.getText());
				y=100*Float.valueOf(amountOfMembers.getText())/Float.valueOf(totalAmountOfVisitors.getText());
				x=100*Float.valueOf(amountOfUsers.getText())/Float.valueOf(totalAmountOfVisitors.getText());
				ObservableList<PieChart.Data> pieChartDatas = FXCollections.observableArrayList(new PieChart.Data("Users", x),
						new PieChart.Data("Members", y), new PieChart.Data("Group", z));
				pieChart.setData(pieChartDatas);
				}
				else {
					totalAmountOfVisitors.setText("0");
					amountOfUsers.setText("0");
					amountOfGroug.setText("0");
					amountOfMembers.setText("0");
					noReportToPresent.setVisible(true);
					pieChart.setVisible(false);
					
				}
			}
			else
				System.out.println("Error in the data");
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<PieChart.Data> pieChartDatas = FXCollections.observableArrayList(new PieChart.Data("Users", x),
				new PieChart.Data("Members", y), new PieChart.Data("Group", z));
		pieChart.setData(pieChartDatas);
	}

}