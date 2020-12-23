package clientTry;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ApproveParametersController {

	private String fNameH, lNameH, roleH, userIDH, parkNameH;
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Label errorMsg;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> parkName;

    @FXML
    private TableColumn<?, ?> parameter;

    @FXML
    private TableColumn<?, ?> newValue;

    @FXML
    private TableColumn<?, ?> fromDate;

    @FXML
    private TableColumn<?, ?> toDate;

    @FXML
    private TableColumn<?, ?> reject;

    @FXML
    private TableColumn<?, ?> approve;

    @FXML
    void backClicked(MouseEvent event) {

    }

    @FXML
    void closeClicked(MouseEvent event) {

    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {
    }
    public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		
		

    }

}
