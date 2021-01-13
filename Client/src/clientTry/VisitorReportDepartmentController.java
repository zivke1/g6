package clientTry;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.application.Platform;
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
 * controller for visitor report of department manager with bar - chart
 */
public class VisitorReportDepartmentController implements IEntranceReport {

	private String fNameH, lNameH, roleH, userIDH, parkNameH;
	private MouseEvent m_event;
	ObservableList<String> parkNames = FXCollections.observableArrayList("Tal Park", "Carmel Park", "Jordan Park");

	java.sql.Date date;

	String userT, memberT, groupT;

	boolean flag1 = false, flag2 = false, flag3 = false;

	IEntranceReport con = null;

	public boolean isFlag1() {
		return flag1;
	}

	public IEntranceReport getCon() {
		return con;
	}

	public void setCon(IEntranceReport con) {
		this.con = con;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	ArrayList<HourAmount> userArray, memberArray, groupArray;
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
	public void showReport(MouseEvent event) {
		flag1 = false;
		flag2 = false;
		flag3 = false;
		chart.getData().clear();
		chart.setAnimated(false);
		xaxis.lookup(".axis-label").setStyle("-fx-label-padding: -5 0 -40 0;");
		xaxis.categorySpacingProperty().add(5);
		yaxis.setLowerBound(0);
		yaxis.setAutoRanging(false);
		try {

			chart.setTitle("Visitors Report Chart");
			personal = new XYChart.Series();
			member = new XYChart.Series();
			group = new XYChart.Series();
			personal.setName("Personal");
			group.setName("Group");
			member.setName("membership");
//			ArrayList<String> arr = new ArrayList<>();
//			arr.add("depManVisitRep");
//			arr.add(selectPark.getValue());
			date = new Date(selectDate.getValue().getYear() - 1900, selectDate.getValue().getMonthValue() - 1,
					selectDate.getValue().getDayOfMonth());
//			arr.add(date.toString());
//			userT = TypeOfOrder.user.toString();
//			arr.add(userT);
//			extractedChat(arr);
//			ArrayList<HourAmount> answer = extractedHourAmountArray();
//			for (HourAmount h : answer)
//				if (h.getAmount() > 0)
//					flag1 = true;

			logic(selectPark.getValue(), date.toString(), TypeOfOrder.user.toString());

			int max[] = new int[24];
			ArrayList<HourAmount> answer = extractedHourAmountArray();
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				personal.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			}
			chart.getData().add(personal);

			logic(selectPark.getValue(), date.toString(), TypeOfOrder.member.toString());

			answer = extractedHourAmountArray();
//			for (HourAmount h : extractedHourAmountArray())
//				if (h.getAmount() > 0)
//					flag2 = true;
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				member.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			}
			chart.getData().add(member);
//			arr.remove(memberT);
//			groupT = TypeOfOrder.group.toString().toLowerCase();
//			arr.add(3, groupT);
//			extractedHourAmountArray().clear();
//			extractedChat(arr);

			logic(selectPark.getValue(), date.toString(), TypeOfOrder.group.toString().toLowerCase());

			answer = extractedHourAmountArray();
//			for (HourAmount h : extractedHourAmountArray())
//				if (h.getAmount() > 0)
//					flag3 = true;
			for (HourAmount a : answer) {
				max[Integer.parseInt(a.getHour())] += a.getAmount();
				group.getData().add(new XYChart.Data(a.getHour(), a.getAmount()));
			}
			chart.getData().add(group);
			int maxRes = max[0];
			for (int i : max) {
				if (i > maxRes)
					maxRes = i;
			}
			yaxis.setUpperBound(maxRes);

			if (!flag1 && !flag2 && !flag3) {
				chart.setVisible(false);
				errorMsg.setVisible(true);
			} else {
				chart.setVisible(true);
				errorMsg.setVisible(false);
			}
			extractedHourAmountArray().clear();
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg.setVisible(true);
		}

	}

	public void logic(String parkName, String date, String type) {
		ArrayList<HourAmount> answer;
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add(parkName);

		arr.add(date);
		if (type.equals(TypeOfOrder.user.toString())) {

			arr.add(type);
			if (con != null) {
				con.extractedHourAmountArray().clear();
				con.extractedChat(arr);
				answer = con.extractedHourAmountArray();
			} else {
				extractedHourAmountArray().clear();
				extractedChat(arr);
				answer = extractedHourAmountArray();
			}

			for (HourAmount h : answer)
				if (h.getAmount() > 0) {
					flag1 = true;
					break;
				}
		}

		if (type.equals(TypeOfOrder.member.toString())) {

			arr.add(type);
			if (con != null) {
				con.extractedHourAmountArray().clear();
				con.extractedChat(arr);
				answer = con.extractedHourAmountArray();
			} else {
				extractedHourAmountArray().clear();
				extractedChat(arr);
				answer = extractedHourAmountArray();
			}
			for (HourAmount h : answer)
				if (h.getAmount() > 0) {
					flag2 = true;
					break;
				}
		}

		if (type.equals(TypeOfOrder.group.toString().toLowerCase())) {

			arr.add(type);
			if (con != null) {
				con.extractedHourAmountArray().clear();
				con.extractedChat(arr);
				answer = con.extractedHourAmountArray();
			} else {
				extractedHourAmountArray().clear();
				extractedChat(arr);
				answer = extractedHourAmountArray();
			}
			for (HourAmount h : answer)
				if (h.getAmount() > 0) {
					flag3 = true;
					break;
				}

		}

	}

	@Override
	public void extractedChat(ArrayList<String> arr) {
		ClientMain.chat.accept(arr);
	}

	@Override
	public ArrayList<HourAmount> extractedHourAmountArray() {
		return ChatClient.dataInArrayListHour;
	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}

}

//public String getUserT() {
//return userT;
//}
//
//public void setUserT(String userT) {
//this.userT = userT;
//}
//
//public String getMemberT() {
//return memberT;
//}
//
//public void setMemberT(String memberT) {
//this.memberT = memberT;
//}
//
//public String getGroupT() {
//return groupT;
//}
//
//public void setGroupT(String groupT) {
//this.groupT = groupT;
//}