package clientTry;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SimulationController {

    @FXML
    private Text phoneNumber;

    @FXML
    private Text emailAddress;

    @FXML
    void exit(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

   
}
