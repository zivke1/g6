package echoServer;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import com.mysql.cj.MysqlConnection;

import echoServer.ServerControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import util.OrderToChange;
import util.OrderToView;
import util.SimulationDetails;

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
		m_ServerControl.setParameters(client.getInetAddress().getHostName(), client.getInetAddress().getHostAddress(),
				"connected");
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
	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
//TODO we need to change it to switch case or even if else
		try {

			ArrayList<String> dataFromDb;
			ArrayList<String> arr = (ArrayList<String>) msg;

			if (arr.contains("VisitorAmountReport")) {
				dataFromDb = mysqlConnection.visitorAmountReport(arr);
				this.sendToAllClients(dataFromDb);
				return;
			}
			if (arr.contains("FetchParkDetails")) {
				arr.remove("FetchParkDetails");
				dataFromDb = mysqlConnection.FetchParkDetails(arr);
				this.sendToAllClients(dataFromDb);
				return;
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
			/*
			 * if (arr.contains("showTable")) { arr.remove("showTable");
			 * ArrayList<OrderToView> ar = mysqlConnection.showTableOrders(msg);
			 * dataFromDb.add("showTable"); this.sendToAllClients(dataFromDb); return; }
			 */

			if (arr.contains("close")) {
				arr.remove("close");
				clientDisconnected(null);
			}
			/*
			 * // check if id exist in visitor table if (arr.contains("CheckID")) {
			 * arr.remove("CheckID"); arr.add(mysqlConnection.CheckID(arr));
			 * arr.add("CheckID"); this.sendToAllClients(arr); return; }
			 */
			if (arr.contains("checkIfEmployee")) {
				arr.remove("checkIfEmployee");
				arr = mysqlConnection.checkIfEmployee(arr);
				client.sendToClient(arr);
				return;
			}

			if (arr.contains("sendToDeparmentManager")) {
				arr.remove("sendToDeparmentManager");
				ArrayList<String> a = new ArrayList<>();
				if (mysqlConnection.insertParaUpdate(arr))
					a.add("True");
				else
					a.add("False");
				// a.add("sendToDeparmentManager");
				client.sendToClient(a);
				return;
			}
			if (arr.contains("cancel report")) {
				arr.remove("cancel report");
				ArrayList<String> answer = mysqlConnection.cancelReport();
				client.sendToClient(answer);
				return;
			}

			if (arr.contains("ViewOrder")) {
				arr.remove("ViewOrder");
				ArrayList<String> returnArr = new ArrayList<>();
				returnArr = mysqlConnection.ViewOrders(arr);
				client.sendToClient(returnArr);
				return;
			}
			if (arr.contains("CancelOrder")) {
				arr.remove("CancelOrder");
				ArrayList<String> returnArr = new ArrayList<>();
				String ret = mysqlConnection.CancelOrder(arr);
				returnArr.add(ret);
				client.sendToClient(returnArr);
			}
			if (arr.contains("RegisterMember")) {
				ArrayList<String> returnArr = new ArrayList<>();
				arr.remove("RegisterMember");
				String str = mysqlConnection.RegisterMember(arr);
				if (!str.equals("Exists")) {
					returnArr.add("Success");
					returnArr.add(str);
				} else
					returnArr.add(str);
				returnArr.add("RegisterMember");
				client.sendToClient(returnArr);
				return;
			}

			if (arr.contains("checkIfIdConnectedWithId")) {
				arr.remove("checkIfIdConnectedWithId");
				arr = mysqlConnection.checkIfIdConnectedWithId(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("checkIfIdConnectedWithMemberId")) {
				arr.remove("checkIfIdConnectedWithMemberId");
				arr = mysqlConnection.checkIfIdConnectedWithMemberId(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("closeAndSetIdNull")) {
				arr.remove("closeAndSetIdNull");
				String tmp = "";
				tmp = mysqlConnection.closeAndSetIdNull(arr);
				client.sendToClient(tmp);
				if (arr.contains("disconnect")) {
					arr.remove("disconnect");
					clientDisconnected(null);
				}
				return;
			}
			if (arr.contains("ReturnUserIDInTableOrders")) {
				arr.remove("ReturnUserIDInTableOrders");
				ArrayList<OrderToView> ar = mysqlConnection.ReturnUserIDInTableOrders(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("CheckUserIDInTable")) {
				arr.remove("CheckUserIDInTable");
				arr = mysqlConnection.checkIfEmployee(arr);
				this.sendToAllClients(arr);
				return;
			}
			if (arr.contains("checkInvite")) {
				arr.remove("checkInvite");
				arr = mysqlConnection.checkInvite(arr);
				client.sendToClient(arr);
				return;
			}

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
		Thread tDayBefore = new Thread(new EchoServer.ThreadDayBeforeVisit());
		Thread checkOldOrders = new Thread(new EchoServer.OldOrder());
		//Thread orderOpenSpot = new Thread(new EchoServer.OrderOpenSpot());
		tDayBefore.start();
		checkOldOrders.start();// changing old orders status to expired
		//orderOpenSpot.start();// checking if any of the cancelled orders in the future can fit anyone in the
								// waiting list
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	/**
	 * 
	 * @author Nitzan checking if the order approved one day before the visiting day
	 */
	class ThreadDayBeforeVisit implements Runnable {

		@Override
		public void run() {
			ArrayList<SimulationDetails> arr;
			while (true) {
				arr = mysqlConnection.dayBeforeVisit();
				if (arr.size() > 0) {

					for (SimulationDetails s : arr) {
						Platform.runLater((new EchoServer.HoursCheck(s.getPhoneNum(), s.getEmail(), s.getOrderID(),
								"Day Before Visit Day Confirmation")));
						Thread t = new Thread(new EchoServer.CheckWaiting(null, s, 1000 * 60 * 60 * 2));
						t.start();
					}
				}
				try {
					Thread.sleep(1000 * 60 * 60 * 24);// sleep 1 day
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * checking
	 * 
	 * @author Nitzan
	 *
	 */
	class CheckWaiting implements Runnable {
		SimulationDetails s;
		OrderToChange order;
		long sleepT;

		CheckWaiting(OrderToChange order, SimulationDetails s, long sleepT) {
			this.s = s;
			this.sleepT = sleepT;
			this.order = order;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(sleepT);// sleep 2 hours
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (s != null)
				if (!mysqlConnection.checkWaiting(s.getOrderID(), "waitingToVisit")) {
					mysqlConnection.setOrderExpired(s.getOrderID());
				} else if (!mysqlConnection.checkWaiting(order.getOrderID(), "waitingToVisit")) {
					mysqlConnection.setOrderExpired(order.getOrderID());
				}

		}

	}

	/**
	 * 
	 * @author Nitzan if the order wasn't approved after two hours it's status will
	 *         be changed to expired
	 */

	class HoursCheck implements Runnable {

		String orderID, email, phoneNum, msg;

		HoursCheck(String phoneNum, String email, String orderID, String msg) {// constructor
			this.orderID = orderID;
			this.email = email;
			this.phoneNum = phoneNum;
			this.msg = msg;
		}

		@Override
		public void run() {

			try {
				Parent root;
				Stage stage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				root = loader.load(getClass().getResource("/echoServer/MessageToConfirmOrder.fxml").openStream());
				SimulationController c = loader.getController();
				c.setDetails(orderID, phoneNum, email, msg);
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/echoServer/application.css").toExternalForm());
				stage.setTitle("Simulation");
				stage.setScene(scene);
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * checking if any of the cancelled orders time slot can fit someone in the
	 * waiting list
	 * 
	 * @author Nitzan
	 *
	 */
	class OrderOpenSpot implements Runnable {

		@Override
		public void run() {
			while (true) {
				ArrayList<OrderToChange> arr;
				arr = mysqlConnection.checkCancelledOrder();
				if (arr != null && arr.size() > 0) {
					arr = mysqlConnection.checkWaitingList(arr);
					if (arr.size() > 0) {
						for (OrderToChange order : arr) {
							Platform.runLater(new EchoServer.HoursCheck(order.getPhoneNum(), order.getEmail(),
									order.getOrderID(), "Your Requested Visit Time Is Now Available"));
							Thread t = new Thread(new EchoServer.CheckWaiting(order, null, 1000 * 60 * 60));
							if (!mysqlConnection.checkWaiting(order.getOrderID(), "waitingToApprove")) {
								mysqlConnection.setOrderExpired(order.getOrderID());
							}
						}
					}
				}
				try {
					Thread.sleep(1000 * 60 * 60);// sleep 1 hour
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * checking if any of the waiting orders is expired
	 * 
	 * @author Nitzan
	 */
	class OldOrder implements Runnable {

		@Override
		public void run() {
			while (true) {
				mysqlConnection.checkOrdersStatus();

				try {
					Thread.sleep(1000 * 60 * 60 * 24);// sleep 1 day
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

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
