package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * 
 * @author shani
 * class to show next stage with and without hiding current stage
 * @param path - of fxml to open
 * @param stageTitle - to set in form
 * 
 */
public class NextStages {
	private String path;
	private String stageTitle;
	
	public NextStages(String path, String stageTitle) {
		this.path = path;
		this.stageTitle = stageTitle;
		
	}

	/**
	 * open next stage with hiding current page
	 */
	public FXMLLoader goToNextStage(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			root = loader.load(getClass().getResource(this.getPath()).openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/echoServer/application.css").toExternalForm());
			stage.setTitle(this.getStageTitle());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return loader;
	}

	/**
	 * open next stage without hiding current page - for popUps
	 */
	public FXMLLoader openPopUp() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource(this.getPath()).openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/echoServer/application.css").toExternalForm());
			stage.setTitle(this.getStageTitle());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return loader;
	}
	
	public String getPath() {
		return path;
	}

	public String getStageTitle() {
		return stageTitle;
	}

}
