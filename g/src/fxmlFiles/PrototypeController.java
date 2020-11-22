package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PrototypeController {

	public static Stage stage;
    @FXML
    private TextField id;

    @FXML
    void Login(MouseEvent event) {
    	stage.close();
    	//check DB
    	AfterLoginController.stage.show();
    }

    @FXML
    void OpenFaq(MouseEvent event) {//need to add faq page

    }

}
