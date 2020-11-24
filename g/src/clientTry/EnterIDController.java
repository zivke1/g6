package clientTry;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

		
public class EnterIDController {

    @FXML
    private TextField id;

    @FXML
    void ShowDetails(MouseEvent event) {
    	
    	ArrayList<String> arr=new ArrayList<String>();
    	arr.add("showTable");
    	arr.add(id.getText());
    	((Node) event.getSource()).getScene().getWindow().hide();
    	
    	controllerTry cT = new controllerTry();
    	try {
			cT.showDetails(arr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    }
   
    
//    System.out.println("Student ID Found");
//	((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//	Stage primaryStage = new Stage();
//	Pane root = loader.load(getClass().getResource("/gui/StudentForm.fxml").openStream());
//	StudentFormController studentFormController = loader.getController();
//	studentFormController.loadStudent(ChatClient.s1);
//
//	Scene scene = new Scene(root);
//	scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
//	primaryStage.setTitle("Student Managment Tool");
//
//	primaryStage.setScene(scene);
//	primaryStage.show();


}
