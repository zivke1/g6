package clientTry;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * 
 * main for client
 *
 */
public class ClientMain extends Application {
	final public static int OPEN_TIME_INT=8;
	final public static int CLOSE_TIME_INT=16;
	final public static int AVG_DUR_TIME_INT=4;
	public static ClientConsole chat; //only one instance
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat=new ClientConsole("localhost", 5555);
//		this make the X btn to close the connection
		primaryStage.setOnCloseRequest(evt->{
			if (ClientMain.chat.checkConnection()) {
	    	ArrayList<String> arr = new ArrayList<String>();
			arr.add("close");
			ClientMain.chat.accept(arr);
	    	ClientMain.chat.stopConnection();
			}
		});	
		
		BorderPane borderPane = null;
		//EnterIDController controller;
		try {
			FXMLLoader loader=new FXMLLoader();


			loader.setLocation(getClass().getResource("/fxmlFiles/LoginP.fxml"));
//			loader.setLocation(getClass().getResource("../fxmlFiles/CardReader.fxml"));

			 


			borderPane = loader.load();
			//controller = loader.getController();
		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
		/////////////////////////////////////////////////
		
		try {
			chat=new ClientConsole("localhost", 5555);
			Stage stage=new Stage();
			try {
				stage.setOnCloseRequest(evt->{
					if (ClientMain.chat.checkConnection()) {
				    	ArrayList<String> arr = new ArrayList<String>();
						arr.add("close");
						ClientMain.chat.accept(arr);
				    	ClientMain.chat.stopConnection();
						}
					});	
				BorderPane borderPane1 = null;
				try {
					FXMLLoader loader=new FXMLLoader();
					loader.setLocation(getClass().getResource("/fxmlFiles/CardReader.fxml"));
					borderPane1 = loader.load();
				} catch (IOException e) {
				e.printStackTrace();
				}	
				Scene scene1=new Scene(borderPane1);
				stage.setScene(scene1);
				stage.setTitle("Card Reader");
				stage.show();
			} catch (Exception ex) {
				System.out.println("Card reader didn't open");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}