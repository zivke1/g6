package clientTry;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ParkDetailsController {

	@FXML
	private Label parkName;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button finishOrderBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Text activityHours;

	@FXML
	private Text ManagerName;

	@FXML
	private Text MaxCapacity;

	@FXML
	private Text AvgVisitTime;

	@FXML
	private Text MaxOrders;

	@FXML
	private Text gapVisitors;

	@FXML
	private Button QuestionBtn;
	@FXML
	private Label explanation;
	private boolean i = true;

	void ChooseParkBringDetails(String parkNameToFetch) {
		parkName.setText(parkNameToFetch);
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("FetchParkDetails");
		arr.add(parkNameToFetch);
		System.out.println("1");
		try {
			ClientMain.chat.accept(arr);
			System.out.println("8");
			activityHours.setText("8:00-16:00 Sunday to Thursday");
			ManagerName.setText(ChatClient.dataInArrayList.get(3));// the number is according to the order of the insert
																	// in the mysqlconnection
			MaxCapacity.setText(ChatClient.dataInArrayList.get(0));
			AvgVisitTime.setText(ChatClient.dataInArrayList.get(1));
			MaxOrders.setText(ChatClient.dataInArrayList.get(2));
			gapVisitors.setText(ChatClient.dataInArrayList.get(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void backClicked(MouseEvent event) {

	}

	@FXML
	void finishOrderClicked(MouseEvent event) {

	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	void helpBtnPressed(MouseEvent event) {
		ChooseParkBringDetails("Tal Park");
	}

	@FXML
	void explainGapVisitors(MouseEvent event) {
		if (i == true) {
			explanation.setVisible(true);
			i = false;
		} else {
			explanation.setVisible(false);
			i = true;
		}
	}

//	@FXML
//	public void initialize() {
//		
//	}

}
