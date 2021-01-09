package echoServer;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * 
 * main for server
 *
 */
public class ServerMain extends Application{

	 BorderPane root;
	
	public void start(Stage stage) {
		Scene scene;
		ServerControl controller;
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("/echoServer/Server.fxml"));
			
			root = loader.load();
		
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		//this make the X btn to close the connection
		stage.setOnCloseRequest(evt->{
	    	try {
				controller.stopServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		scene=new Scene(root);
		stage.setScene(scene);

		stage.setTitle("server");
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
