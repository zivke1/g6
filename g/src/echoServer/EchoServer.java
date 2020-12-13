package echoServer;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import echoServer.ServerControl;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * 
 */
public class EchoServer extends AbstractServer {
	// Class variables *************************************************
	ServerControl m_ServerControl;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 */
	public EchoServer(int port) {
		super(port);
		mysqlConnection.connectDB();
	}

	public EchoServer(int port, ServerControl control) {
		super(port);
		mysqlConnection.connectDB();
		m_ServerControl = control;
	}

	protected void clientConnected(ConnectionToClient client) {
		
		String ipAndHost = client.toString();
		String[] ipAndHostArray = ipAndHost.split(" ");
		//m_ServerControl.setParameters(client.getInetAddress().getHostName(), client.getInetAddress().getHostAddress(),"connected");
	}
	/**
	 * Hook method called each time a client disconnects. The default implementation
	 * does nothing. The method may be overridden by subclasses but should remains
	 * synchronized.
	 *
	 * @param client the connection with the client.
	 */

	synchronized protected void clientDisconnected(ConnectionToClient client) {
		m_ServerControl.setParameters(" ", " ", "not connected");
	}
	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ArrayList<String> dataFromDb;
		try {
			System.out.println("1.2");
			ArrayList<String> arr = (ArrayList<String>) msg;
			if(arr.contains("FetchParkDetails"))
			{
				System.out.println("2");
				arr.remove("FetchParkDetails");
				dataFromDb=mysqlConnection.FetchParkDetails(arr);
				System.out.println("6");
				this.sendToAllClients(dataFromDb);
			}
			if (arr.contains("updateTable")) {
				arr.remove("updateTable");
				mysqlConnection.updateTable(msg);
			}
			if (arr.contains("insertTable")) {
				arr.remove("insertTable");
				mysqlConnection.insertTable(msg);
				arr.add("insertTable");
			}
			if (arr.contains("showTable")) {
				arr.remove("showTable");
				dataFromDb = mysqlConnection.showTable(msg);
				dataFromDb.add("showTable");
				this.sendToAllClients(dataFromDb);
				return;
			}
			if (arr.contains("close")) {
				arr.remove("close");
				clientDisconnected(null);

			}
			if (arr.contains("CheckID")) {
				arr.remove("CheckID");
				arr.add(mysqlConnection.CheckID(arr));
				arr.add("CheckID");
				this.sendToAllClients(arr);
				return;
			}
			this.sendToAllClients(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {

		System.out.println("Server listening for connections on port " + getPort());

	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *                is entered.
	 */
	public static void main(String[] args) {
		int port = 0; // Port to listen on

		try {
			port = Integer.parseInt(args[0]); // Get port from command line
		} catch (Throwable t) {
			port = DEFAULT_PORT; // Set port to 5555
		}

		EchoServer sv = new EchoServer(port);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
		}
	}
}
//End of EchoServer class
