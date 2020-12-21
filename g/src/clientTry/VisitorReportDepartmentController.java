package clientTry;

import java.sql.Time;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class VisitorReportDepartmentController {

	private String fNameH, lNameH, roleH, userIDH, parkNameH;

	@FXML
	private ImageView imgContactUs;

	@FXML
	private Button backBtn;

	@FXML
	private Button helpBtn;

	@FXML
	private StackedBarChart<String, Number> chart;

	@FXML
	void backClicked(MouseEvent event) {

	}

	@FXML
	void goToContactUsPopUp(MouseEvent event) {

	}

	@FXML
	void helpBtnPressed(MouseEvent event) {

	}

	public void setDetails(String fName, String lName, String role, String userID, String parkName) {
		this.fNameH = fName;
		this.lNameH = lName;
		this.roleH = role;
		this.userIDH = userID;
		this.parkNameH = parkName;

	

		// Configuring StackedBarChart
		//chart = new StackedBarChart<String, Number>(xaxis, yaxis);
		chart.setTitle("Popularity of Programming languages");
		// Configuring series for java
		XYChart.Series<String, Number> java = new XYChart.Series<>();
		java.setName("java");
		java.getData().add(new XYChart.Data<>("Jan", 10000));
		java.getData().add(new XYChart.Data<>("Jan", 130000));
		java.getData().add(new XYChart.Data<>("Feb", 50000));
		java.getData().add(new XYChart.Data<>("Mar", 60300));
		java.getData().add(new XYChart.Data<>("Apr", 105600));
		java.getData().add(new XYChart.Data<>("May", 50600));
		java.getData().add(new XYChart.Data<>("Jun", 103000));
		java.getData().add(new XYChart.Data<>("Jul", 104500));
		java.getData().add(new XYChart.Data<>("Aug", 203000));
		java.getData().add(new XYChart.Data<>("Sep", 103400));
		java.getData().add(new XYChart.Data<>("Oct", 105600));
		java.getData().add(new XYChart.Data<>("Nov", 102400));
		java.getData().add(new XYChart.Data<>("Dec", 200000));

		// Adding series java to the stackedbarchart
		chart.getData().add(java);

		// Configuring series python
		XYChart.Series<String, Number> python = new XYChart.Series<>();
		python.setName("python");
		python.getData().add(new XYChart.Data<>("Jan", 50000));
		python.getData().add(new XYChart.Data<>("Jan", 14300));
		python.getData().add(new XYChart.Data<>("Feb", 50400));
		python.getData().add(new XYChart.Data<>("Mar", 100500));
		python.getData().add(new XYChart.Data<>("Apr", 104000));
		python.getData().add(new XYChart.Data<>("May", 134000));
		python.getData().add(new XYChart.Data<>("Jun", 60000));
		python.getData().add(new XYChart.Data<>("Jul", 78000));
		python.getData().add(new XYChart.Data<>("Aug", 89000));
		python.getData().add(new XYChart.Data<>("Sep", 150000));
		python.getData().add(new XYChart.Data<>("Oct", 120000));
		python.getData().add(new XYChart.Data<>("Nov", 109450));
		python.getData().add(new XYChart.Data<>("Dec", 50450));

		// adding python series to the stackedbarchart
		chart.getData().add(python);

	}

	@FXML
	private void setChart() {
		/*
		 * try{ CategoryAxis xAxis = new CategoryAxis();
		 * 
		 * 
		 * xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList
		 * ("8:00", "9:00", "10:00", "11:00",
		 * "12:00","13:00","14:00","15:00","16:00"))); xAxis.setLabel("Enterance Time");
		 * 
		 * //Defining the y axis NumberAxis yAxis = new NumberAxis(10,20,30);
		 * yAxis.setLabel("Amount Of Visitors"); chart= new StackedBarChart<>(xAxis,
		 * yAxis); chart.setTitle("Halid Chart");
		 * 
		 * XYChart.Series<String,Number> dataSeries1 = new XYChart.Series();
		 * dataSeries1.getData().add( new XYChart.Data("2014", 567));
		 * chart.getData().add(dataSeries1); }catch (Exception e) {e.printStackTrace();}
		 * 
		 * 
		 * }
		 */

	}

}
