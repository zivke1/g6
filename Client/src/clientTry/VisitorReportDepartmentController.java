package clientTry;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.HourAmount;
import util.NextStages;
import util.TypeOfOrder;

/**
 * 
 * 		   controller for visitor report of department manager with bar -
 *         chart
 */
public class VisitorReportDepartmentController {

	private String fNameH, lNameH, roleH, userIDH, parkNameH;
	private MouseEvent m_event;
	ObservableList<String> parkNames = FXCollections.observableArrayList("Tal Park", "Carmel Park", "Jordan Park");

	XYChart.Series<String, Number> personal, member, group;
	@FXML
	private Label errorMsg;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private StackedBarChart<String, Number> chart;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private ComboBox<String> selectPark;

	@FXML
	private DatePicker selectDate;
 
	@FXML
	private Button showBtn;
	

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
		Tooltip tt = new Tooltip();
		tt.setText("This page show report of visitor amount\n"); // add text to help filed
		tt.setStyle("-fx-font: normal bold 15 Langdon; " + "-fx-background-color: #F0F8FF; " + "-fx-text-fill: black;");

		helpBtn.setTooltip(tt);
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
		chart.setVisible(false);
	}

	
	@FXML
	void showReport(MouseEvent event) {
		chart.getData().clear();
		chart.setAnimated(false);
		xaxis.lookup(".axis-label").setStyle("-fx-label-padding: -5 0 -40 0;");
		xaxis.categorySpacingProperty().add(5);
		yaxis.setLowerBound(0);
		//yaxis.setTickUnit(5);
		yaxis.setAutoRanging(false);
		try {
			boolean flag1 = false, flag2 = false, flag3 = false;
			chart.setTitle("Visitors Report Chart");
			personal = new XYChart.Series();
			member = new XYChart.Series();
			group = new XYChart.Series();
			personal.setName("Personal");
			group.setName("Group");
			member.setName("membership");
			ArrayList<String> arr = new ArrayList<>();
			arr.add("depManVisitRep");
			arr.add(selectPark.getValue());
			java.sql.Date date = new Date(selectDate.getValue().getYear() - 1900,
					selectDate.getValue().getMonthValue() - 1, selectDate.getValue().getDayOfMonth());
			arr.add(date.toString());
			arr.add(TypeOfOrder.user.toString());
			ClientMain.chat.accept(arr);
			ArrayList<HourAmount> answer = ChatClient.dataInArrayListHour;
			for (HourAmount h : ChatClient.dataInArrayListHour)
				if (h.getAmount() > 0)
					flag1 = true;
			int max[] = new int[24];
//			for (int i = 0; i < 24; i++) {
//				max[i] = 0;
//				personal.getData().add(new XYChart.Data(i + "", 0));
//				member.getData().add(new XYChart.Data(i + "", 0));
//				group.getData().add(new XYChart.Data(i + "", 0));
//			}
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				personal.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			}
			chart.getData().add(personal);
			arr.remove(TypeOfOrder.user.toString());
			arr.add(3, TypeOfOrder.member.toString());
			ChatClient.dataInArrayListHour.clear();
			ClientMain.chat.accept(arr);
			answer = ChatClient.dataInArrayListHour;
			for (HourAmount h : ChatClient.dataInArrayListHour)
				if (h.getAmount() > 0)
					flag2 = true;
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				member.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			}
			chart.getData().add(member);
			arr.remove(TypeOfOrder.member.toString());
			arr.add(3, TypeOfOrder.group.toString().toLowerCase());
			ChatClient.dataInArrayListHour.clear();
			ClientMain.chat.accept(arr);
		
			answer = ChatClient.dataInArrayListHour;
			for (HourAmount h : ChatClient.dataInArrayListHour)
				if (h.getAmount() > 0)
					flag3 = true;
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				group.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			} 
			chart.getData().add(group);
			int maxRes = max[0];
			for (int i : max)
			{
				if (i > maxRes)
					maxRes = i;
			}
			yaxis.setUpperBound(maxRes);
 
			if (!flag1 && !flag2 && !flag3) {
				chart.setVisible(false);
				errorMsg.setVisible(true);
			}
			else
			{
				chart.setVisible(true);
				errorMsg.setVisible(false);
			}
			ChatClient.dataInArrayListHour.clear();
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.setVisible(true);
		}

	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}
