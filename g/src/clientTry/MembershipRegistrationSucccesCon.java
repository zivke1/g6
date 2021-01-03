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
import util.NextStages;
/**
 *
 * the controller of page that come after we success to register new member
 *
 */
public class MembershipRegistrationSucccesCon {


    @FXML
    private ImageView imgContactUs;
 
    @FXML
    private Button backBtn;

    @FXML
    private Button finishOrderBtn;

    @FXML
    private Label memberNum;

	private String fNameH;

	private String lNameH;

	private String roleH;

	private String userIDH;

	private String parkNameH;

	MouseEvent m_MainPage;
	
    @FXML
    void backClicked(MouseEvent event) {
     	
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_MainPage.getSource()).getScene().getWindow()).show();
    	
    	//ziv change
//    	((Node) event.getSource()).getScene().getWindow().hide();
//    	Stage stage = new Stage();
//		FXMLLoader loader = new FXMLLoader();
//		Parent root;
//		try {
//			root = loader.load(getClass().getResource("/fxmlFiles/HomePageForEmployee.fxml").openStream());
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
//			HomePageForEmployeeController controller=loader.getController();
//			controller.setDetails(fNameH, lNameH, roleH, userIDH, parkNameH);
//			stage.setTitle("HomePage");
//			stage.setScene(scene);
//			stage.show();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {
    	//ziv change
//    	Stage stage = new Stage();
//		FXMLLoader loader = new FXMLLoader();
//		Parent root;
//		try {
//			root = loader
//					.load(getClass().getResource("/fxmlFiles/ContactUsPopUp.fxml").openStream());
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
//			stage.setTitle("Contact Us");
//			stage.setScene(scene);
//			stage.show();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
    	
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "View Customer's Order",userIDH );
		FXMLLoader loader = nextStages.openPopUp();
//		loader.getController();
    }
    public void updateMemberNum(String num)
    {
    	memberNum.setText(num);

    }

    public void setDetails(String fName, String lName, String role, String userID, String parkName)
	{
		this.fNameH=fName;
		this.lNameH=lName;
		this.roleH=role;
		this.userIDH=userID;
		this.parkNameH=parkName;
	}

	public void setMainPage(MouseEvent mainPage) {
		// TODO Auto-generated method stub
		m_MainPage=mainPage;
	}
}
