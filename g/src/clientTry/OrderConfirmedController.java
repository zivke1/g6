

package clientTry;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * 
 * @author zivke
 *
 */
public class OrderConfirmedController {
	MouseEvent m_eventMain;
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Label employeeOrderCrums;

    @FXML
    private Label userOrderCrums;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Button helpBtn;

    @FXML
    void finishOrderClicked(MouseEvent event) {
     	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_eventMain.getSource()).getScene().getWindow()).show();
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
	public void setMainPage(MouseEvent eventMain) {
		// TODO Auto-generated method stub
		m_eventMain=eventMain;
	}

}
