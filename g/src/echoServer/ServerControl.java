package echoServer;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

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
		try {
			echoServer = new EchoServer(5555, this);
			try {
				echoServer.listen(); // Start listening for connections
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
