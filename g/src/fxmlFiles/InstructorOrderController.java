package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InstructorOrderController {

	public static Stage stage;
    @FXML
    private Button helpBtn;

    @FXML
    private ComboBox<?> parkName;

    @FXML
    private ComboBox<?> numVistor;

    @FXML
    private DatePicker dateVisit;

    @FXML
    private ComboBox<?> timeVisit;

    @FXML
    private Button checkOrder;

    @FXML
    private TextField email;

    @FXML
    private Button backToHomePage;

    @FXML
    private Button FOButton;

    @FXML
    void BackToHomePage(MouseEvent event) {//need to add home page
    	stage.close();
    }

    @FXML
    void CheckOrder(MouseEvent event) {//need to check in the DB

    }

    @FXML
    void OpenFaq(MouseEvent event) {// need to create faq page
    	stage.close();
    }
    
    @FXML
    void FinishOrder(MouseEvent event) {//What's the next page? and probably need to add send to DB or something
    	stage.close();
    	EndOrderController.stage.show();
    }

}
