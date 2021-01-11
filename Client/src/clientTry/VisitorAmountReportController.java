package clientTry;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.HourAmount;
import util.NextStages;
import util.TypeOfOrder;


/**
 * 		    this class is controller for the FXML VisitorAmountReport the
 *         controller fetch from the DB the data for the report in a specific
 *         month and display chart with the data.
 *
 */
public class VisitorAmountReportController implements Initializable {

	XYChart.Series<String, Number> group, personal, member;
	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private Label year;

	@FXML
	private Label month;

	@FXML
	private Label totalAmountOfVisitors;

	@FXML
	private Label noReportToPresent;

	@FXML
	private Label parkName;

	@FXML
	private CategoryAxis xaxis;

	@FXML
	private NumberAxis yaxis;

	@FXML
	private Label submitted;
	@FXML
	private Button btnSubmit;
	@FXML
	private StackedBarChart<String, Number> chart;
	private boolean flag = false;

	private String fName;
	private String lName;
	private String role;
	private String userID;
	private String parkNameS;
	private MouseEvent m_event;

	@FXML
	void backClicked(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		((Stage) ((Node) m_event.getSource()).getScene().getWindow()).show();

	}

	public void setDetails(String year, String month, String parkName, String fName, String lName, String role,
			String userID) {// the other page call to this method
		ArrayList<String> arr = new ArrayList<>();
		this.fName = fName;
		this.lName = lName;
		this.userID = userID;
		this.role = role;
		this.parkNameS = parkName;
		this.year.setText(year);
		this.month.setText(month);
		this.parkName.setText(parkName);
		chart.getData().clear();
		chart.setAnimated(false);
		xaxis.lookup(".axis-label").setStyle("-fx-label-padding: -5 0 -40 0;");
		xaxis.categorySpacingProperty().add(5);
		yaxis.setLowerBound(0);

		try {
			personal = new XYChart.Series();
			member = new XYChart.Series();
			group = new XYChart.Series();
			arr.add("VisitorAmountReport");
			arr.add(year);
			arr.add(month);
			arr.add(parkName);
			ClientMain.chat.accept(arr);

			if (ChatClient.dataInArrayList.get(0).equals("0")) {
				totalAmountOfVisitors.setText("0");
				noReportToPresent.setVisible(true);
				chart.setVisible(false);
				btnSubmit.setVisible(false);
			} else {
				arr = ChatClient.dataInArrayList;
				totalAmountOfVisitors.setText(arr.get(0));
				arr.remove(0);
				/**
				 * add for each day the number of groups
				 */

				group.setName("Group");
				group.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(arr.get(0))));
				group.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(arr.get(1))));
				group.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(arr.get(2))));
				group.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(arr.get(3))));
				group.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(arr.get(4))));
				group.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(arr.get(5))));
				group.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(arr.get(6))));
				for (int i = 0; i < 7; i++)
					arr.remove(0);
				personal.setName("Users");
				personal.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(arr.get(0))));
				personal.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(arr.get(1))));
				personal.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(arr.get(2))));
				personal.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(arr.get(3))));
				personal.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(arr.get(4))));
				personal.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(arr.get(5))));
				personal.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(arr.get(6))));
				for (int i = 0; i < 7; i++)
					arr.remove(0);
				member.setName("Member");
				member.getData().add(new XYChart.Data<>("Sun", Integer.valueOf(arr.get(0))));
				member.getData().add(new XYChart.Data<>("Mon", Integer.valueOf(arr.get(1))));
				member.getData().add(new XYChart.Data<>("Tues", Integer.valueOf(arr.get(2))));
				member.getData().add(new XYChart.Data<>("Wed", Integer.valueOf(arr.get(3))));
				member.getData().add(new XYChart.Data<>("Thurs", Integer.valueOf(arr.get(4))));
				member.getData().add(new XYChart.Data<>("Fri", Integer.valueOf(arr.get(5))));
				member.getData().add(new XYChart.Data<>("Sat", Integer.valueOf(arr.get(6))));

				chart.getData().addAll(personal, group, member);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@FXML
	void goToContactUsPopUp(MouseEvent event) {
		NextStages nextStages = new NextStages("/fxmlFiles/ContactUsPopUp.fxml", "Contact Us", userID);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setPreviousPage(MouseEvent event) {
		m_event = event;
	}
/**
 * Submit the report
 * @param event
 */
	@FXML
	void submitVisitorAmount(ActionEvent event) {
		if (flag == false) {
			ArrayList<String> arr = new ArrayList<>();
			arr.add("SubmitVisitorAmountReport");
			arr.add(year.getText());
			arr.add(month.getText());
			arr.add(parkName.getText());
			ClientMain.chat.accept(arr);
			flag = true;
			submitted.setVisible(true);
		}

	}

}
