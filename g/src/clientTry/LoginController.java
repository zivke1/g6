package fxmlFiles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private Button backBtn;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private RadioButton makeOrderR;

    @FXML
    private ToggleGroup radio;

    @FXML
    private RadioButton enterAsEmployee;

    @FXML
    private AnchorPane Identification;

    @FXML
    private TextField enterIdentification;

    @FXML
    private AnchorPane login;

    @FXML
    private TextField enterUserName;

    @FXML
    private TextField EnterPsw;

    private boolean cond = false;
    
    @FXML
    void backClicked(MouseEvent event) {

    }

    @FXML
    void changeIdentificationVisible(ActionEvent event) {
    	loginSetVisibility(false);
    	identificationSetVisibility(true);
    	
    }

    @FXML
    void changeLoginVisible(ActionEvent event) {
    	identificationSetVisibility(false);
    	loginSetVisibility(true);

    }

    @FXML
    void finishOrderClicked(MouseEvent event) {

    }

    public void identificationSetVisibility(boolean cond) {
    	Identification.setVisible(cond);
    }
    
    public void loginSetVisibility(boolean cond) {
    	login.setVisible(cond);
    }

}
