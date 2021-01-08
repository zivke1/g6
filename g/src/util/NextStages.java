package util;

import java.io.IOException;
import java.util.ArrayList;

import clientTry.ClientMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * 
 * class to show next stage with and without hiding current stage
 * @param path - of fxml to open
 * @param stageTitle - to set in form
 * 
 */
public class NextStages {
	private String path;
	private String stageTitle;
	private String userID;
	
	public NextStages(String path, String stageTitle, String userID) {
		this.path = path;
		this.stageTitle = stageTitle;
		this.userID = userID;
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
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
			stage.setTitle(this.getStageTitle());
			stage.setScene(scene);
			stage.setOnCloseRequest(evt -> {	// disconnect client if exit window
				if (ClientMain.chat.checkConnection()) {
					ArrayList<String> arr = new ArrayList<String>();
					arr.add("closeAndSetIdNull");
					arr.add(userID);
					arr.add("disconnect");
					ClientMain.chat.accept(arr);
					ClientMain.chat.stopConnection();
				}
			});
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
			scene.getStylesheets().add(getClass().getResource("/clientTry/application.css").toExternalForm());
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
