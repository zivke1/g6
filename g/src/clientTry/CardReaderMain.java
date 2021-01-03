package clientTry;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CardReaderMain extends Application {
	public static ClientConsole chat; //only one instance
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		chat=new ClientConsole("localhost", 5555);
//		this make the X btn to close the connection
		primaryStage.setOnCloseRequest(evt->{
			if (CardReaderMain.chat.checkConnection()) {
	    	ArrayList<String> arr = new ArrayList<String>();
			arr.add("close");
			CardReaderMain.chat.accept(arr);
	    	CardReaderMain.chat.stopConnection();
			}
		});	
		
		BorderPane borderPane = null;
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("../fxmlFiles/CardReader.fxml"));
			borderPane = loader.load();
		} catch (IOException e) {
		e.printStackTrace();
		}	
		Scene scene=new Scene(borderPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Card Reader");
		primaryStage.show();
		/////////////////////////////////////////////////
	}
}
