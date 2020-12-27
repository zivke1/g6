package clientTry;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author Idan
 *  Controller  for Report Department Manager GUI 
 */
public class ReportDeparmentManagerController {
	
	private String fNameH,lNameH,roleH,userIDH,parkNameH;


    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button showReportBtn;
    
    @FXML
    private RadioButton visitBTN;

    @FXML
    private RadioButton cancelBTN;
    
    @FXML
    private Label errorMsg;

    @FXML
    private Button helpBtn;
    
	/**
	 * @author Idan
	 * @param event show event of click on back button
	 */    

    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
			HomePageForEmployeeController controller=loader.getController();
			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("HomePage");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

    }
    @FXML
    void visitReportClick(MouseEvent event) {
    	cancelBTN.setSelected(false);
    	errorMsg.setText("");
    }

    @FXML
    void cancelReportClick(MouseEvent event) {
      	visitBTN.setSelected(false);
    	errorMsg.setText("");
    }

    @FXML
    void finishOrderClicked(MouseEvent event) {
    	if (cancelBTN.isSelected()){
    		((Node) event.getSource()).getScene().getWindow().hide();
    		Stage stage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Parent root;
    		try {
    			root = loader.load(getClass().getResource("/fxmlFiles/CancelReport.fxml").openStream());
    			CancelReportController controller=loader.getController();
    			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
    			Scene scene = new Scene(root);
    			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
    			stage.setTitle("Cancellation Report"); 
    			stage.setScene(scene);
    			stage.show();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    	}
    	if(visitBTN.isSelected()) {
    		((Node) event.getSource()).getScene().getWindow().hide();
    		Stage stage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Parent root;
    		try {
    			root = loader.load(getClass().getResource("/fxmlFiles/VisitorReportDeparmentManager.fxml").openStream());
    			CancelReportController controller=loader.getController();
    			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
    			Scene scene = new Scene(root);
    			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
    			stage.setTitle("Visitor Report"); 
    			stage.setScene(scene);
    			stage.show();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
    		
    	}
    	if(!visitBTN.isSelected()&& !cancelBTN.isSelected())
    		errorMsg.setText("you must chose report first \n");

    }

    /**
     * @author Idan
     * @param event show event of click on contact us 
     */

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
    
	public void setDetails(String fName, String lName, String role, String userID, String parkName)
	{
		this.fNameH=fName;
		this.lNameH=lName;
		this.roleH=role;
		this.userIDH=userID;
		this.parkNameH=parkName;

	}

}
