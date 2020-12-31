

package clientTry;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.NextStages;
/**
 * 
 * 
 *
 */
public class OrderConfirmedController {
	MouseEvent m_eventMain;
	boolean m_occasional;
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Label txtCrumViaHomePageLabel;

    @FXML
    private Label txtCrumLabel;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Button helpBtn;
    
    @FXML
    private Label orderNumberLabel;
   String  m_orderNumber;
    
    @FXML
    void finishOrderClicked(MouseEvent event) {
     	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_eventMain.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order", "aa");
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
	public void setOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		m_orderNumber=orderNumber;
		orderNumberLabel.setText(m_orderNumber);
		
	}    
    
	public void setMainPage(MouseEvent eventMain) {
		// TODO Auto-generated method stub
		m_eventMain=eventMain;
	}

	public void setOccasional(boolean occasional) {
		// TODO Auto-generated method stub
		m_occasional = occasional;
		if(m_occasional) {
			txtCrumLabel.setVisible(true);
		}else {
			txtCrumViaHomePageLabel.setVisible(true);
		}
	}

}
