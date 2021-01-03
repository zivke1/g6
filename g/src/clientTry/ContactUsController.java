package clientTry;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * giving contact information 
 * 
 *
 */
public class ContactUsController {

    @FXML
    private Button btnExitPopUp;

    @FXML
    private Text phoneNumber;
 
    @FXML
    private Text emailAddress;

    @FXML
    void ExitPopUp(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }
}
