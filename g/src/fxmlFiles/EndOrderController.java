package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EndOrderController {

	public static Stage stage;

	@FXML
	private Button btn;

	@FXML
	void Close(MouseEvent event) {
		stage.close();

	}

	public static void main(String[] args) {

	}

}
