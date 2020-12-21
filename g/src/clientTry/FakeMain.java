package clientTry;



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
import util.Role;

public class FakeMain extends Application {
	public static ClientConsole chat; //only one instance
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat=new ClientConsole("localhost", 5555);
		BorderPane borderPane = null;
		//EnterIDController controller;
		try {
			FXMLLoader loader=new FXMLLoader();

			loader.setLocation(getClass().getResource("/fxmlFiles/ViewOrder.fxml"));

			borderPane = loader.load();
			ViewOrderController controller = loader.getController();
			controller.setDetails(null, null, Role.Member.toString(), null, null,11+"");


		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.show();
	}
}
