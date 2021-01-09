package clientTry;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * 
 * this controller is for page that user enter to waiting list and show 
 * him the order number that he can check his status later
 *
 */
public class EnterWaitingListPageSuccessController {
	MouseEvent m_eventMain;
	String m_orderNumber;
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button goToMainPageBtn;

    @FXML
    private Button helpBtn;
    

    @FXML
    private Label orderNumberLabel;

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void goToMainPageBtnClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_eventMain.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    	Tooltip tt = new Tooltip();
		tt.setText("You are now in the waiting list\nyou can contact us if you have any questions\nwe will infrom you if requested spot will open");  // add text to help filed 
		tt.setStyle("-fx-font: normal bold 15 Langdon; "
		    + "-fx-background-color: #F0F8FF; "
		    + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
    }
	public void setMainPage(MouseEvent event) {
	 m_eventMain = event;
	}

	public void setOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		m_orderNumber = orderNumber;
		orderNumberLabel.setText(m_orderNumber);
		
	}
}
