package clientTry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

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
