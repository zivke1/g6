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
import util.NextStages;

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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * 
 *  Controller  for Report Department Manager GUI 
 */
public class ReportDepartmentManagerController {
	
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
    private RadioButton durationBTN;
    
    @FXML
    private Label errorMsg;

    @FXML
    private Button helpBtn;


	private MouseEvent m_event;
    
	/**
	 * @author Idan
	 * @param event show event of click on back button
	 */    

    @FXML
    void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();


    }
    
    @FXML
    void durRepClicked(MouseEvent event) {
    	cancelBTN.setSelected(false);
    	visitBTN.setSelected(false);
    }

    @FXML
    void visitReportClick(MouseEvent event) {
    	cancelBTN.setSelected(false);
    	durationBTN.setSelected(false);
    	errorMsg.setText("");
    }

    @FXML
    void cancelReportClick(MouseEvent event) {
      	visitBTN.setSelected(false);
      	durationBTN.setSelected(false);
    	errorMsg.setText("");
    }

    @FXML
    void finishOrderClicked(MouseEvent event) {
    	if (cancelBTN.isSelected()){
    		NextStages nextStages = new NextStages("/fxmlFiles/CancelReport.fxml", "Cancellation Report", userIDH);
    		FXMLLoader loader = nextStages.goToNextStage(event);
    		CancelReportController Control = loader.getController();
    		Control.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
    		Control.setPreviousPage(event);
    	}
    	if(visitBTN.isSelected()) {
    		NextStages nextStages = new NextStages("/fxmlFiles/VisitorReportDeparmentManager.fxml", "Visitor Report", userIDH);
    		FXMLLoader loader = nextStages.goToNextStage(event);
    		VisitorReportDepartmentController Control = loader.getController();
    		Control.setDetails(fNameH, lNameH, roleH.toString(), userIDH, parkNameH);
    		Control.setPreviousPage(event);		
    	}
    	if(durationBTN.isSelected()) {
    		NextStages nextStages = new NextStages("/fxmlFiles/DurationReportDeparmentManager.fxml", "Duration Report", userIDH);
    		FXMLLoader loader = nextStages.goToNextStage(event);
    		DurReportDepartmentController Control = loader.getController();
    		Control.setDetails(fNameH, lNameH, roleH.toString(), userIDH, parkNameH);
    		Control.setPreviousPage(event);	
    		
    	}
    	if(!visitBTN.isSelected()&& !cancelBTN.isSelected()&&!durationBTN.isSelected())
    		errorMsg.setText("you must choose report first \n");
    }

    /**
     * @author Idan
     * @param event show event of click on contact us 
     */

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userIDH);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	Tooltip tt = new Tooltip();
		tt.setText("Please select the report you want to view \nand then click show");  // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; "
		    + "-fx-background-color: #F0F8FF; "
		    + "-fx-text-fill: black;");
		helpBtn.setTooltip(tt);
    }
    
	public void setDetails(String fName, String lName, String role, String userID, String parkName)
	{
		this.fNameH=fName;
		this.lNameH=lName;
		this.roleH=role;
		this.userIDH=userID;
		this.parkNameH=parkName;

	}
 
	public void setPreviousPage(MouseEvent event) {
		m_event=event;
	}
}
