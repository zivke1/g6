package clientTry;



import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UpdateEmailController {

	@FXML
    private TextField id;

    @FXML
    private TextField newEmail;

    @FXML
    void UpdateEmail(MouseEvent event) {


    	ArrayList<String> arr=new ArrayList<String>();
    	arr.add("updateTable");
    	arr.add(newEmail.getText());
    	arr.add(id.getText());
    	arr.add("Email");
    	((Node) event.getSource()).getScene().getWindow().hide();
    	myMain.chat.accept(arr);
    	controllerTry cT = new controllerTry();
    	try {
    		ArrayList<String> arrShowNewVals=new ArrayList<String>();
    		arrShowNewVals.add("showTable");
    		arrShowNewVals.add(id.getText());
			cT.showDetails(arrShowNewVals);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	
    }

}
