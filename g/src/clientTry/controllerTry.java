package clientTry;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class controllerTry {
//	static public controllerTry c = new controllerTry();
	@FXML
	private Label firstName;

	@FXML
	private Label lastName;

	@FXML
	private Label id;

	@FXML
	private Label email;

	@FXML
	private Label phoneNum;

	public void setDetails(ArrayList<String> a) {
		firstName.setText(a.get(0));
		lastName.setText(a.get(1));
		id.setText(a.get(2));
		email.setText(a.get(3));
		phoneNum.setText(a.get(4));
	}

//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		// TODO Auto-generated method stub
//		firstName = new Label();
//		lastName = new Label();
//		id = new Label();
//		email = new Label();
//		phoneNum = new Label();
//	}

}
//public void setDetails() {
//		firstName.setText("eliraaaaaaaaaaaaaaaaaan");
//		lastName.setText("dam");
//		id.setText("316275");
//		email.setText("eliran@niz");
//		phoneNum.setText("05234");
//	}
