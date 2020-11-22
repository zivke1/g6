package fxmlFiles;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AfterLoginController {

	public static Stage stage;

	@FXML
	void ShowInfo(MouseEvent event) {                                                
		stage.close();
		
		ShowInfoController.stage.show();
		
	}

	@FXML
	void UpdateInfo(MouseEvent event) {
		stage.close();
	}

}
