package clientTry;

import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class OrderController implements Initializable {

	@FXML
	private Label txtCrumViaHomePage;

	@FXML
	private Label txtCrum;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private AnchorPane helpBtn;

	@FXML
	private Text guideWelcomeText;

	@FXML
	private ComboBox<String> parkNameCombo;

	@FXML
	private ComboBox<String> numberOfVistorsCombo;

	@FXML
	private DatePicker pickDatePicker;

	@FXML
	private ComboBox<String> hourCombo;

	@FXML
	private TextField emailTextFiled;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private Text notAllfieldFilledLabel;

	@FXML
	private CheckBox payTimeCheckBox;

	@FXML
	private Label emailNotVaild;

	String m_fName, m_lName, m_role, m_userID, m_parkName;
	String m_ownerUserID, m_status;
	boolean m_occasional;

	@FXML
	void backClicked(MouseEvent event) {

	}

	ArrayList<String> invite;

	@FXML
	void finishOrderClicked(MouseEvent event) {
		invite = new ArrayList<String>();
		invite.add("checkInvite");
		try {
			invite.add(m_ownerUserID);
			invite.add(parkNameCombo.getValue().toString());
			invite.add(hourCombo.getValue().toString());
			invite.add(pickDatePicker.getValue().toString());// check if the date is vaild TODO
			invite.add(numberOfVistorsCombo.getValue().toString());
			if (checkEmail(emailTextFiled.getText()) == false) {
				return;
			}
			emailNotVaild.setVisible(false);
			invite.add(emailTextFiled.getText());

			if (m_occasional == true) {
				invite.add("occasional");
			} else {
				invite.add("notOccasional");
			}
			invite.add(m_status);
		} catch (Exception e) {
			Platform.runLater(() -> {
				notAllfieldFilledLabel.setVisible(true);
			});
		}

		if (invite.contains("")) {
			notAllfieldFilledLabel.setVisible(true);
			return;
		} else {
			notAllfieldFilledLabel.setVisible(false);
			// TODO send and check if we have place
			ClientMain.chat.accept(invite);

			if (ChatClient.dataInArrayList.contains("TheParkIsFull")) {
				ChatClient.dataInArrayList.remove("TheParkIsFull");
				// TODO show the waiting list page
			} else if (ChatClient.dataInArrayList.contains("InviteConfirm")) {
				ChatClient.dataInArrayList.remove("InviteConfirm");
				// show successful page and message to confirm the message

			}
			System.out.println(invite);
		}

	}

	private boolean checkEmail(String emailS) {
		if (!((emailS.split("@").length == 2) && (emailS.indexOf("@") != 0) && (emailS.indexOf(".") != 0)
				&& (emailS.lastIndexOf(".") != emailS.length() - 1) && (emailS.length() >= 5 && emailS.length() <= 30)
				&& (emailS.indexOf("@") != emailS.indexOf(".") + 1 || emailS.indexOf("@") != (emailS.indexOf(".") + 1))
				&& emailS.contains(".") && emailS.lastIndexOf('.') > emailS.indexOf('@')) || emailS.length() == 0) {
			emailNotVaild.setVisible(true);
			return false;
		}
		return true;
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	ObservableList<String> list;

	// creating list of number of visitors
	private void setNumberOfVistors(int num) {
		ArrayList<String> al = new ArrayList<String>();
		for (int i = 1; i <= num; i++) {
			al.add(String.valueOf(i));
		}

		list = FXCollections.observableArrayList(al);
		numberOfVistorsCombo.setItems(list);
	}

	// we need to call this function when we know the park the costumer chose
	private void setHourCombo(Time fromTime, Time toTime) {
		ArrayList<String> al = new ArrayList<String>();
		while (fromTime.compareTo(toTime) < 0) {
			al.add(String.valueOf(fromTime.toString()));
			fromTime.setHours(fromTime.getHours() + 1);

		}
		list = FXCollections.observableArrayList(al);
		hourCombo.setItems(list);
	}

	private void setParkCombo(ArrayList<String> parkList) {
		// TODO we need in the initialize func to get the list of park name and send it
		// to here
		list = FXCollections.observableArrayList(parkList);
		parkNameCombo.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setHourCombo(new Time(8, 0, 0), new Time(16, 29, 0));
		ArrayList<String> tempArrayList = new ArrayList<String>();
		tempArrayList.add("Carmel Park");
		tempArrayList.add("Tal Park");
		tempArrayList.add("Jorden Park");
		setParkCombo(tempArrayList);
		setNumberOfVistors(15);
		
		setDetailsOfOwner("315766014", "member",false);
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		m_fName = fName;
		m_lName = lName;
		m_role = role;
		m_userID = userID;
		m_parkName = parkName;

	}

	public void setDetailsOfOwner(String ownerUserID, String status, boolean occasional) {// in status i want to know if
																							// the user owner is a
																							// member user or guide
		m_ownerUserID = ownerUserID;
		m_status = status;
		m_occasional = occasional;
		if (m_status.equals("guide") == false) {
			guideWelcomeText.setVisible(true);
			if (occasional == false) {
				payTimeCheckBox.setVisible(true);
			}
		}
	}

}
