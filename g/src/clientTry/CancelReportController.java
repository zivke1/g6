package clientTry;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CancelReportController {
	private String fNameH,lNameH,roleH,userIDH,parkNameH;

    @FXML
    private Button backBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Label numCancelOrders;

    @FXML
    private Label numExpiredOrders;

    @FXML
    private ImageView imgContactUs;

    @FXML
    void backClicked(MouseEvent event) {
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/ReportDepartmentManagerPage.fxml").openStream());
			HomePageForEmployeeController controller=loader.getController();
			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("HomePage");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

    

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }

}
public void setDetails(String fName, String lName, String role, String userID, String parkName)
{
	this.fNameH=fName;
	this.lNameH=lName;
	this.roleH=role;
	this.userIDH=userID;
	this.parkNameH=parkName;

}
