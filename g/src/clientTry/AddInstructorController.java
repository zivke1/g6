package clientTry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AddInstructorController {

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Label ccLabel;

    @FXML
    private RadioButton yBtn;

    @FXML
    private RadioButton nBtn;

    @FXML
    private TextField ccField;

    @FXML
    void backClicked(MouseEvent event) {
    	
    }

    @FXML
    void moveToSuccess(MouseEvent event) {

    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }
    
    @FXML
    void ccNo(MouseEvent event) {
    	yBtn.setSelected(false);
    	ccLabel.setVisible(false);
    	ccField.setVisible(false);
    }

    @FXML
    void ccYes(MouseEvent event) {
    	nBtn.setSelected(false);
    	ccLabel.setVisible(true);
    	ccField.setVisible(true);
    }

}
