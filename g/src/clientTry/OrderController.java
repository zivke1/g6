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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

public class OrderController implements Initializable {

	@FXML
	private Button backBtn;

	@FXML
	private AnchorPane helpBtn;

	@FXML
	private ToggleButton PersonalBtn;

	@FXML
	private ToggleGroup typeOrder;

	@FXML
	private ToggleButton FamilyBtn;

	@FXML
	private ToggleButton GroupBtn;

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

	enum OrderType {
		Family, Group, Personal,Init
	}

	OrderType orderType=OrderType.Init;

	@FXML
	void FamilyBtnPress(MouseEvent event) {
		setNumberOfVistors(15);
		orderType = OrderType.Family;
	}

	@FXML
	void GroupBtnPress(MouseEvent event) {
		setNumberOfVistors(15);
		orderType = OrderType.Group;
	}

	@FXML
	void PersonalBtnPress(MouseEvent event) {
		setNumberOfVistors(1);
		orderType = OrderType.Personal;
	}

	@FXML
	void backClicked(MouseEvent event) {

	}

	@FXML
	void finishOrderClicked(MouseEvent event) {
		ArrayList<String> invite = new ArrayList<String>();
		invite.add("check invite");
		try {
			switch (orderType) {
			case Personal:
				invite.add("Personal");
				break;
			case Group:
				invite.add("Group");
				break;
			case Family:
				invite.add("Family");
				break;
			default:
				throw new Exception();
			
			}

			invite.add(parkNameCombo.getValue().toString());
			invite.add(numberOfVistorsCombo.getValue().toString());
			invite.add(pickDatePicker.getValue().toString());// problem
			invite.add(hourCombo.getValue().toString());
			invite.add(emailTextFiled.getText());
		} catch (Exception e) {
			Platform.runLater(() -> {
				notAllfieldFilledLabel.setVisible(true);
			});
		}

		if (invite.contains("")) {
			notAllfieldFilledLabel.setVisible(true);
		} else {
			notAllfieldFilledLabel.setVisible(false);
			// TODO send and check if we have place
			System.out.println(invite);
		}

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
			al.add(String.valueOf(fromTime.toString().substring(0, 5)));
			if (fromTime.getMinutes() == 30) {
				fromTime.setMinutes(0);
				fromTime.setHours(fromTime.getHours() + 1);
			} else {
				fromTime.setMinutes(30);
			}
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
		setHourCombo(new Time(0, 0, 0), new Time(23, 29, 0));
		ArrayList<String> tempArrayList = new ArrayList<String>();
		tempArrayList.add("ziv");
		tempArrayList.add("kenig");
		tempArrayList.add("chttlatar");
		setParkCombo(tempArrayList);
		setNumberOfVistors(1);
	}

}
