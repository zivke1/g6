package clientTry;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class myMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox vbox = null;
		controllerTry controller;
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("FxmlTry.fxml"));
			vbox = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
