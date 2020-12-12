package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MembershipRegistrationSucccesCon {

    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Label memberNum;

    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
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
    	Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader
					.load(getClass().getResource("/fxmlFiles/ContactUsPopUp.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle("Contact Us");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }

}
