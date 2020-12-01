package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientMain extends Application {
	public static ClientConsole chat; //only one instance
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat=new ClientConsole("localhost", 5555);
		//this make the X btn to close the connection
		primaryStage.setOnCloseRequest(evt->{
			if (ClientMain.chat.checkConnection()) {

	    	ArrayList<String> arr = new ArrayList<String>();
			arr.add("close");
			ClientMain.chat.accept(arr);
	    	ClientMain.chat.stopConnection();
			}
		});
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
		primaryStage.setTitle("Enter ID");
		primaryStage.show();
	}

}