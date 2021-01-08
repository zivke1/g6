package echoServer;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.xdevapi.Client;

import echoServer.ServerControl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import util.HourAmount;
import util.TypeOfOrder;
import util.ViewReports;
import util.OrderToChange;
import util.DayToView;
import util.DurationOrder;
import util.FreePlaceInPark;

import util.OrderToView;
import util.ParameterToView;
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
	SimulationController simControl=null;
	mysqlConnection Connection;
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
		Connection = mysqlConnection.getInstance();
		Connection.connectDB();
		Connection.SetServer(this);
	}

	public EchoServer(int port, ServerControl control) {
		super(port);
		Connection = mysqlConnection.getInstance();
		Connection.connectDB();
		m_ServerControl = control;
		Connection.SetServer(this);
	}

	public EchoServer instance() {
		return this;
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

			
			if(arr.contains("SubmitVisitorAmountReport"))
			{
				Connection.SubmitVisitorAmountReport(arr);
				client.sendToClient(msg);
				return;
			}
			if(arr.contains("SubmitUsageReport"))
			{
				Connection.SubmitUsageReport(arr);
				client.sendToClient(msg);
				return;
			}
			if(arr.contains("SubmitIncomeReport"))
			{
				Connection.SubmitIncomeReport(arr);
				client.sendToClient(msg);
				return;
			}

			if (arr.contains("DurRep")) {
				arr.remove("DurRep");
				ArrayList<DurationOrder> ret = Connection.depManDuration(arr);
				client.sendToClient(ret);
			}
			if (arr.contains("updateToActive")) {
				Connection.updateToActive(arr);
				client.sendToClient(msg);
				return;
			}
			if (arr.contains("updateToFinished")) {
				Connection.updateToFinished(arr);
				client.sendToClient(msg);
				return;
			}
			if (arr.contains("ReturnUserIDInTableOrdersForCardReader")) {
				ArrayList<OrderToView> ar = Connection.ReturnUserIDInTableOrdersForCardReader(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("simulationCardReader")) {
				dataFromDb = Connection.simulationCardReader();
				client.sendToClient(dataFromDb);
				return;
			}
			if (arr.contains("UsageReports")) {
				dataFromDb = Connection.UsageReports(arr);
				client.sendToClient(dataFromDb);
				return;
			}
			if (arr.contains("incomeReport")) {
				dataFromDb = Connection.incomeReport(arr);
				client.sendToClient(dataFromDb);
				return;
			}
			if (arr.contains("VisitorAmountReport")) {
				dataFromDb = Connection.visitorAmountReport(arr);
				client.sendToClient(dataFromDb);
				return;
			}
			if (arr.contains("FetchParkDetails")) {
				arr.remove("FetchParkDetails");
				dataFromDb = Connection.FetchParkDetails(arr);
				client.sendToClient(dataFromDb);
				return;
			}
			if (arr.contains("updateTable")) {
				arr.remove("updateTable");
				Connection.updateTable(msg);
			}
			if (arr.contains("insertTable")) {
				arr.remove("insertTable");
				Connection.insertTable(msg);
				arr.add("insertTable");
			}
			/*
			 * if (arr.contains("showTable")) { arr.remove("showTable");
			 * ArrayList<OrderToView> ar = mysqlConnection.showTableOrders(msg);
			 * dataFromDb.add("showTable"); this.sendToAllClients(dataFromDb); return; }
			 */

			if (arr.contains("close")) {
				arr.remove("close");
				ArrayList<String> tmp = new ArrayList<>();
				client.sendToClient(tmp);
				clientDisconnected(null);
				return;
			}
			/*
			 * // check if id exist in visitor table if (arr.contains("CheckID")) {
			 * arr.remove("CheckID"); arr.add(mysqlConnection.CheckID(arr));
			 * arr.add("CheckID"); this.sendToAllClients(arr); return; }
			 */
			if (arr.contains("checkIfEmployee")) {
				arr.remove("checkIfEmployee");
				arr = Connection.checkIfEmployee(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("approveParaTable")) {
				arr.remove("approveParaTable");
				ArrayList<ParameterToView> answer;
				answer = Connection.paraToUpdateTable();
				client.sendToClient(answer);

				return;
			}

			if (arr.contains("sendToDeparmentManager")) {
				arr.remove("sendToDeparmentManager");
				ArrayList<String> a = new ArrayList<>();
				if (Connection.insertParaUpdate(arr))
					a.add("True");
				else
					a.add("False");
				// a.add("sendToDeparmentManager");
				client.sendToClient(a);
				return;
			}
			if (arr.contains("cancel report")) {
				arr.remove("cancel report");
				ArrayList<String> answer = Connection.cancelReport(arr);
				client.sendToClient(answer);
				return;
			}
			if (arr.contains("takeCapacity")) {
				arr.remove("takeCapacity");
				ArrayList<String> answer = Connection.cheakCapacity(arr);
				client.sendToClient(answer);
				return;
			}
			if (arr.contains("takeGap")) {
				arr.remove("takeGap");
				ArrayList<String> answer = Connection.cheakGap(arr);
				client.sendToClient(answer);
				return;
			}

			if (arr.contains("ViewOrder")) {
				arr.remove("ViewOrder");
				ArrayList<String> returnArr = new ArrayList<>();
				returnArr = Connection.ViewOrders(arr);
				client.sendToClient(returnArr);
				return;
			}
			if (arr.contains("CancelOrder")) {
				arr.remove("CancelOrder");
				ArrayList<String> returnArr = new ArrayList<>();
				String ret = Connection.CancelOrder(arr);
				returnArr.add(ret);
				client.sendToClient(returnArr);
			}
			if (arr.contains("RegisterMember")) {
				ArrayList<String> returnArr = new ArrayList<>();
				arr.remove("RegisterMember");
				String str = Connection.RegisterMember(arr);
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
				arr = Connection.checkIfIdConnectedWithId(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("checkIfIdConnectedWithMemberId")) {
				arr.remove("checkIfIdConnectedWithMemberId");
				arr = Connection.checkIfIdConnectedWithMemberId(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("closeAndSetIdNull")) {
				arr.remove("closeAndSetIdNull");
				ArrayList<String> tmp = new ArrayList<>();
				tmp.add(Connection.closeAndSetIdNull(arr));
				client.sendToClient(tmp);
				if (arr.contains("disconnect")) {
					arr.remove("disconnect");
					clientDisconnected(null);
				}
				return;
			}
			if (arr.contains("ReturnUserIDInTableOrders")) {
				arr.remove("ReturnUserIDInTableOrders");
				ArrayList<OrderToView> ar = Connection.ReturnUserIDInTableOrders(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("CheckUserIDInTable")) {
				arr.remove("CheckUserIDInTable");
				arr = Connection.checkIfEmployee(arr);
				this.sendToAllClients(arr);
				return;
			}
			if (arr.contains("checkInvite")) {
				arr.remove("checkInvite");
				arr = Connection.checkInvite(arr);
				client.sendToClient(arr);
				return;
			}

			if (arr.contains("depManVisitRep")) {
				arr.remove("depManVisitRep");
				TypeOfOrder type = null;
				switch (arr.get(2)) {
				case "member":
					type = TypeOfOrder.member;
					break;
				case "user":
					type = TypeOfOrder.user;
					break;
				case "group":
					type = TypeOfOrder.group;
					break;
				}
				ArrayList<HourAmount> answer;
				answer = Connection.depManVisitRep(type, arr);
				client.sendToClient(answer);

				return;
			}

			if (arr.contains("setInvite")) {
				arr.remove("setInvite");
				arr = Connection.setInvite(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("getFreePlace")) {
				arr.remove("getFreePlace");
				ArrayList<FreePlaceInPark> ar = Connection.getFreePlace(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("setInWaitingList")) {
				arr.remove("setInWaitingList");
				arr = Connection.setInWaitingList(arr);
				client.sendToClient(arr);
				return;
			}

			if (arr.contains("SetPara")) {
				arr.remove("SetPara");
				Connection.setPara(arr);
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("countActiveOrders")) {
				arr.remove("countActiveOrders");
				arr = Connection.countActiveOrders(arr.get(0));
				client.sendToClient(arr);
				return;
			}
			if (arr.contains("checkCapacityAndAvarageVisitTime")) {
				arr.remove("checkCapacityAndAvarageVisitTime");
				ArrayList<Integer> ar = Connection.checkCapacityAndAvarageVisitTime(arr.get(0));
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("checkMemberIDInMembers")) {
				arr.remove("checkMemberIDInMembers");
				ArrayList<String> ar = Connection.checkMemberIDInMembers(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("checkIdInMember")) {
				arr.remove("checkIdInMember");
				ArrayList<String> ar = Connection.checkIdInMember(arr);
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("reportsToView")) {
				arr.remove("reportsToView");
				ArrayList<ViewReports> ar = Connection.reportsToView();
				client.sendToClient(ar);
				return;
			}
			if (arr.contains("UsageReport")) {
				arr.remove("UsageReport");
				ArrayList<DayToView> ar = Connection.checkForUsage(arr);
				client.sendToClient(ar);
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
		// Thread orderOpenSpot = new Thread(new EchoServer.OrderOpenSpot());
		tDayBefore.start();
		checkOldOrders.start();// changing old orders status to expired
		// orderOpenSpot.start();// checking if any of the cancelled orders in the
		// future can fit anyone in the
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
				arr = Connection.dayBeforeVisit();
				if (arr.size() > 0) {

					for (SimulationDetails s : arr) {
						Platform.runLater((new EchoServer.HoursCheck(s.getPhoneNum(), s.getEmail(), s.getOrderID(),
								"1 Day Before Visit Day Confirmation")));
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
				Thread.sleep(sleepT);// sleepT
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (s != null) { 
				// if (!mysqlConnection.checkWaiting(s.getOrderID(), "waitingToVisit"))
				if (Connection.checkWaiting(s.getOrderID(), "waitingToVisit"))
				{
					Connection.setOrderExpired(s.getOrderID(), "expired");
					while(simControl==null)
						;
					simControl.hideAll();
				}
			} else if (Connection.checkWaiting(order.getOrderID(), "waitingToVisit")
					&& Connection.checkDateWatingList(order.getOrderID())) {
				ArrayList<String> a=new ArrayList<>();
				a.add(order.getOrderID());
				Connection.CancelOrder(a);
				//Connection.setOrderExpired(order.getOrderID(), "cancelled");
				while(simControl==null)
					;
				simControl.hideAll();
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
				simControl=c;
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

		private String orderID;

		public OrderOpenSpot(String orderID) {
			this.orderID = orderID;
		}

		@Override
		public void run() {

			// ArrayList<OrderToChange> arr;
			// arr = mysqlConnection.checkCancelledOrder();
			// if (arr != null && arr.size() > 0) {arr =
			ArrayList<OrderToChange> arr = Connection.checkWaitingList(orderID);
			if (arr.size() > 0) {
				for (OrderToChange order : arr) {
					Platform.runLater(new EchoServer.HoursCheck(order.getPhoneNum(), order.getEmail(),
							order.getOrderID(), "Your Requested Visit Time Is Now Available"));
					Thread t = new Thread(new EchoServer.CheckWaiting(order, null, 1000 * 60 * 60));
					t.start();
//						if (!mysqlConnection.checkWaiting(order.getOrderID(), "waitingToApprove")) {
//							mysqlConnection.setOrderExpired(order.getOrderID());
//						}
					// }
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
				Connection.checkOrdersStatus();

				try {
					Thread.sleep(1000 * 60 * 60);// sleep 1 hour
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
