package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GroupOrderController {

	public static Stage stage;
    @FXML
    private Button helpBtn;

    @FXML
    private Button backToOrderBtn;

    @FXML
    void BackToOrder(MouseEvent event) {//need to create order controller
    	stage.close();
    	//OrderController.stage.show();
    }

    @FXML
    void OpenFaq(MouseEvent event) {//need to create faq page

    }

}
