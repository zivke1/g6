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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author eliran
 * this class is controller for the FXML incomeREport
 * the controller fetch from the DB the data for the income 
 * in a specific month
 *
 */

public class incomeReportController {

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Label parkName;

    @FXML
    private Label monthLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label totalIncome;
    private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;
    
	
    public void setDetails(String year, String month,String parkName,String fName, String lName, String role, String userID) {
    	this.parkName.setText(parkName);
    	yearLabel.setText(year);
    	monthLabel.setText(month);
    	this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS=parkName;
		ArrayList<String> arr = new ArrayList<>();
		arr.add("incomeReport");
		arr.add(year);
		arr.add(month);
		arr.add(parkName);
		try {
			ClientMain.chat.accept(arr);
			if(ChatClient.dataInArrayList.contains("incomeReport"))
			{
				totalIncome.setText(ChatClient.dataInArrayList.get(1)+"¤");
			}
			else
				System.out.println("Error in the data");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
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

}

