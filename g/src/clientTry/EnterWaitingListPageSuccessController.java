package clientTry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EnterWaitingListPageSuccessController {
	MouseEvent m_eventMain;
	
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button goToMainPageBtn;

    @FXML
    private Button helpBtn;

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
}
