package util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 * 
 * @author shani
 * class to show next stage and hide current stage
 * 
 * currently missing hiding current stage
 */
public class NextStages {
	private String path;
	private String stageTitle;
	
	public NextStages(String path, String stageTitle) {
		this.path = path;
		this.stageTitle = stageTitle;
	}

	public void goToNextStage() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		try {
			root = loader.load(getClass().getResource(this.getPath()).openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle(this.getStageTitle());
			stage.setScene(scene);
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getPath() {
		return path;
	}

	public String getStageTitle() {
		return stageTitle;
	}

}
