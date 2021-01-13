package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

public interface INextStage {

	/**
	 * open next stage with hiding current page
	 */
	FXMLLoader goToNextStage(MouseEvent event);

	/**
	 * open next stage without hiding current page - for popUps
	 */
	FXMLLoader openPopUp();

}