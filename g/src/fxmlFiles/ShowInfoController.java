package fxmlFiles;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowInfoController {

	public static Stage stage;
    @FXML
    private Text firstName;

    @FXML
    private Text lastName;

    @FXML
    private Text id;

    @FXML
    private Text email;

    @FXML
    private Text phoneNum;

    public void setDetails(ArrayList<String> a)
    {
    	firstName.setText(a.get(0));
    	lastName.setText(a.get(1));
    	id.setText(a.get(2));
    	email.setText(a.get(3));
    	phoneNum.setText(a.get(4));
    }
}
