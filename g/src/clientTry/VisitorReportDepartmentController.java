package clientTry;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.Node;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.HourAmount;
import util.NextStages;
import util.TypeOfOrder;
/**
 * 
 * @author Idan
 *controller for visitor report of department manager with bar - chart 
 */
public class VisitorReportDepartmentController {

	private String fNameH,lNameH,roleH,userIDH,parkNameH;
	private MouseEvent m_event;
	
    @FXML
    private ImageView imgContactUs;

    @FXML
    private Button backBtn;

    @FXML
    private Button helpBtn;
    
    @FXML
    private StackedBarChart<String,Number> chart;

    @FXML
    private CategoryAxis xaxis;

    @FXML
    private NumberAxis yaxis;

	
    @FXML
    void backClicked(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	((Stage)((Node) m_event.getSource()).getScene().getWindow()).show();
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
	public void setDetails(String fName, String lName, String role, String userID, String parkName)
	{
		this.fNameH=fName;
		this.lNameH=lName;
		this.roleH=role;
		this.userIDH=userID;
		this.parkNameH=parkName;
		setChart();
		
		

	}
	@FXML
	private void setChart() {
		
		try{
		chart.setTitle("Visitors Report Chart");
		XYChart.Series<String,Number> personal = new XYChart.Series();
		XYChart.Series<String,Number> member = new XYChart.Series();
		XYChart.Series<String,Number> group = new XYChart.Series();
		personal.setName("Personal");
		group.setName("Group");
		member.setName("membership");
		
		ArrayList<String> arr =new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add(TypeOfOrder.user.toString());
		ClientMain.chat.accept(arr);
		ArrayList<HourAmount> answer= ChatClient.dataInArrayListHour;
	//	System.out.println(answer.get(0).getAmount()); 
		for (HourAmount a: answer)
			personal.getData().add(new XYChart.Data(a.getHour(),a.getAmount()));	
		
		arr.remove(1);
		arr.add(TypeOfOrder.member.toString());
		ClientMain.chat.accept(arr);
		answer= ChatClient.dataInArrayListHour;
		for (HourAmount a: answer)
			member.getData().add(new XYChart.Data(a.getHour(),a.getAmount()));
		
		arr.remove(1);
		arr.add(TypeOfOrder.group.toString());
		ClientMain.chat.accept(arr);
		answer= ChatClient.dataInArrayListHour;
		for (HourAmount a: answer)
			group.getData().add(new XYChart.Data(a.getHour(),a.getAmount()));
		
		chart.getData().add(personal);
		chart.getData().add(member);
		chart.getData().add(group);
		}catch (Exception e)
		{e.printStackTrace();}
	
	} 
	public void setPreviousPage(MouseEvent event) {
		m_event=event;
		}


	

}
