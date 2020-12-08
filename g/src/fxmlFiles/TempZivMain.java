package fxmlFiles;

import java.io.IOException;
import java.util.ArrayList;

import clientTry.ClientConsole;
import clientTry.ClientMain;
import clientTry.EnterIDController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TempZivMain extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane borderPane = null;
		//EnterIDController controller;
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("OrderNew.fxml"));
			borderPane = loader.load();
			//controller = loader.getController();
		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
	}
}