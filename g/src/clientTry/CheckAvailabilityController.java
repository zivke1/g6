package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CheckAvailabilityController implements Initializable {
String m_FirstName,m_id,m_lastName,m_role,m_park;

	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button helpBtn;

    @FXML
    private Text parkNameText;

    @FXML
    private Text numberToFullCapacitiy;

    @FXML
    void backClicked(MouseEvent event) {

    }

    @FXML
    void goToContactUsPopUp(MouseEvent event) {

    }

    @FXML
    void helpBtnPressed(MouseEvent event) {

    }


    void setDetails(String fName ,String lName,String role,String userID ,String park) {
		m_FirstName =fName;
		m_lastName = lName;
		m_role = role;
		m_id = userID;
    	m_park=park;
    	CheckAvailabilityForThisPark(m_park);
	}

	private void CheckAvailabilityForThisPark(String Park) {
		ArrayList<String> toSend = new ArrayList<String>();
		toSend.add("CheckAvailabilityForThisPark");
		toSend.add(m_park);
		ClientMain.chat.accept(toSend);
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		m_park = "Carmel Park";
		CheckAvailabilityForThisPark("aaa");
	}
	

}
