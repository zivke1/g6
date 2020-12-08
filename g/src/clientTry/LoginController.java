package clientTry;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LoginController {
	
	  @FXML
	    private ImageView imgContactUs;

	    @FXML
	    private Button btnNextPage;

	    @FXML
	    private RadioButton makeOrderR;

	    @FXML
	    private ToggleGroup radio;

	    @FXML
	    private RadioButton enterAsEmployee;

	    @FXML
	    private RadioButton viewOrder;

	    @FXML
	    private AnchorPane Identification;

	    @FXML
	    private TextField enterIDnumber;

	    @FXML
	    private TextField enterMemberID;

	    @FXML
	    private Label txtErrAllFieldsReq1;

	    @FXML
	    private AnchorPane login;

	    @FXML
	    private TextField enterUserName;

	    @FXML
	    private TextField EnterPsw;

	    @FXML
	    private Label txtErrAllFieldsReq;

	    @FXML
	    private Label txtErrUserName;

	    @FXML
	    private Label txtErrPassword;

    
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
