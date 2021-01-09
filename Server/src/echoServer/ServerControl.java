package echoServer;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.NextStages;
/**
 * 
 * gui for server
 *
 */
public class ServerControl {

	@FXML
	private GridPane statusLabel;

	@FXML
	private Text ipLabel;

	@FXML
	private Text hostLabel;

	@FXML
	private TextField statusState;

	@FXML
	private TextField ipState;

	@FXML
	private TextField hostState;

	EchoServer echoServer;

	@FXML
	void startServer(MouseEvent event) {
//		try {
//			echoServer = new EchoServer(5555, this);
//			Stage stage=new Stage();
//			try {
//				echoServer.listen(); // Start listening for connections
//				//mysqlConnection.insertOrders();
//				
//				BorderPane borderPane = null;
//				try {
//					FXMLLoader loader=new FXMLLoader();
//					loader.setLocation(getClass().getResource("/echoServer/CardReader.fxml"));
//					borderPane = loader.load();
//				} catch (IOException e) {
//				e.printStackTrace();
//				}	
//				Scene scene=new Scene(borderPane);
//				stage.setScene(scene);
//				stage.setTitle("Card Reader");
//				stage.show();
//			} catch (Exception ex) {
//				System.out.println("ERROR - Could not listen for clients!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			echoServer = new EchoServer(5555, this);
			try {
				echoServer.listen(); // Start listening for connections
				//mysqlConnection.insertOrders();
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void setParameters(String ip, String host, String status) {
		statusState.setText(status);
		ipState.setText(ip);
		hostState.setText(host);
	}

	@FXML
	public void stopServer() throws IOException {
		if (echoServer != null)
			echoServer.close();
	}

}
