package clientTry;

import java.io.IOException;

import client.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class myMain extends Application {
	public static ClientConsole chat; //only one instance
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat=new ClientConsole("localhost", 5555);
		//controllerTry aFrame=new controllerTry();
		//aFrame.start(primaryStage);
		VBox vbox = null;
		EnterIDController controller;
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("EnterID.fxml"));
			vbox = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Details");
		primaryStage.show();
	}

}
//		VBox vbox = null;
//		controllerTry controller;
//		try {
//			FXMLLoader loader=new FXMLLoader();
//			loader.setLocation(getClass().getResource("FxmlTry.fxml"));
//			vbox = loader.load();
//			controller = loader.getController();
//		} catch (IOException e) {
//		e.printStackTrace();
//		}	
//		Scene scene=new Scene(vbox);
//		primaryStage.setScene(scene);
//		primaryStage.show();