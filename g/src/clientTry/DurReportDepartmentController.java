package clientTry;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.DurationOrder;
import util.NextStages;

public class DurReportDepartmentController {

	ObservableList<String> parkNames = FXCollections.observableArrayList("Tal Park", "Carmel Park", "Jordan Park");
	ObservableList<String> dur=FXCollections.observableArrayList();

	private float a = 33, b = 33, c = 34;

	@FXML
	private Label errorMsg;

	@FXML
	private ComboBox<String> durBox;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private ComboBox<String> selectPark;

	@FXML
	private DatePicker selectDate;

	@FXML
	private Button showBtn;

	@FXML
	private PieChart chart;

	private MouseEvent m_event;

	private String fNameH;

	private String lNameH;

	private String roleH;

	private String userIDH;

	private String parkNameH;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();
	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userIDH);
		FXMLLoader loader = nextStages.openPopUp();
		loader.getController();
	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	@FXML
	void showReport(MouseEvent event) {
		chart.getData().clear();
		errorMsg.setText("");
		ArrayList<String> arr = new ArrayList<>();
		arr.add("DurRep");
		arr.add(selectPark.getValue());
		arr.add(selectDate.getValue().toString());
		arr.add(durBox.getValue().substring(0, 1));
		arr.add(durBox.getValue().substring(2, 3));
		System.out.println(durBox.getValue().substring(0, 1) + " " + durBox.getValue().substring(2, 3));
		ClientMain.chat.accept(arr);
		ArrayList<DurationOrder> arrD = ChatClient.dataInArrDur;
		if (arrD.size() == 0) {
			errorMsg.setText("No Data For This Time Slot\nPlease Try A Different One");
			chart.setVisible(false);
		} else {
			chart.setVisible(true);
			int total = 0;
			for (DurationOrder d : arrD)
				total += d.getAmount();
			for (DurationOrder d : arrD) {
				if (d.getType().equals("user"))
					a = 100 * ((float) d.getAmount() / total);
				else if (d.getType().equals("member"))
					b = 100 * ((float) d.getAmount() / total);
				else
					c = 100 * ((float) d.getAmount() / total);
			}
			ObservableList<PieChart.Data> pieChartDatas = FXCollections.observableArrayList(
					new PieChart.Data("user", a), new PieChart.Data("member", b), new PieChart.Data("group", c));

			chart.setData(pieChartDatas);
		}
		ChatClient.dataInArrDur.clear();
	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;
		selectPark.setItems(parkNames);
		selectPark.setValue("Tal Park");
		selectDate.setValue(LocalDate.now());
		int test=0;
		for(int i=0;i<ClientMain.CLOSE_TIME_INT+ClientMain.AVG_DUR_TIME_INT;i+=2)
		{
			dur.add(i+"-"+(i+2));
			test=i;
		}
		if((ClientMain.CLOSE_TIME_INT+ClientMain.AVG_DUR_TIME_INT)%2==1)
		{
			dur.remove(test/2);
			dur.add(test+"-"+(test+1));
		}
		durBox.setItems(dur);
		durBox.setValue("0-2");
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}
