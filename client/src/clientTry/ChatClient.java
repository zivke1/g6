// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package clientTry;

import ocsf.client.*;
import util.HourAmount;
import util.DayToView;
import util.DurationOrder;
import util.FreePlaceInPark;

import util.OrderToView;
import util.ParameterToView;
import util.ViewReports;
import clientTry.UserInformationController;

import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
//	ClientConsole m_ClientConsole;
//	public static ArrayList<String> dataInArrayList = new ArrayList<String>();

	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public static ArrayList<Integer> dataInArrayListInteger = new ArrayList<>();
	
	public static ArrayList<String> dataInArrayList = new ArrayList<String>();

	public static ArrayList<HourAmount> dataInArrayListHour = new ArrayList<HourAmount>();

	public static ArrayList<OrderToView> dataInArrayListObject = new ArrayList<OrderToView>();
	
	public static ArrayList<ViewReports> dataInArrayListReport = new ArrayList<>();
	
	public static ArrayList<ParameterToView> dataInArrayListParameter = new ArrayList<ParameterToView>();

	public static ArrayList<FreePlaceInPark> dataInArrayListFreePlaceInParks = new ArrayList<>();

	public static ArrayList<DurationOrder> dataInArrDur=new ArrayList<>();
	
	public static ArrayList<DayToView> dataInArrayListDayToView = new ArrayList<DayToView>();
	
	public static boolean awaitResponse = false;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 * @throws Exception
	 */

	public void handleMessageFromServer(Object msg) // we need to modified this code to all the query not only showtable
	{
		String st;

		//awaitResponse = false;
		try {
			ArrayList<DurationOrder> dataFromDbCheck = (ArrayList<DurationOrder>) msg;
			dataInArrayListObject.clear();
			if (dataFromDbCheck != null)
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof DurationOrder) {
					dataInArrDur = dataFromDbCheck;
					awaitResponse = false;
					return;
				}
		} catch (ClassCastException e) {
		}
		
		try {
			ArrayList<ViewReports> dataFromDbCheck = (ArrayList<ViewReports>) msg;
			dataInArrayListReport.clear();
			if (dataFromDbCheck != null)
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof ViewReports) {
					dataInArrayListReport = dataFromDbCheck;
					awaitResponse = false;
					return;
				}
		}catch(ClassCastException e) {
		}
		try {
			ArrayList<OrderToView> dataFromDbCheck = (ArrayList<OrderToView>) msg;
			dataInArrayListObject.clear();
			if (dataFromDbCheck != null)
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof OrderToView) {
					dataInArrayListObject = dataFromDbCheck;
					awaitResponse = false;
					return;
				}
		} catch (ClassCastException e) {
		}
// maybe not use
		try {
			ArrayList<Integer> dataFromDbCheck = (ArrayList<Integer>) msg;
			dataInArrayListInteger.clear();
			if (dataFromDbCheck != null)
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof Integer) {
					dataInArrayListInteger = dataFromDbCheck;
					awaitResponse = false;
					return;
				}

		} catch (ClassCastException e) {
		}
		try {
			ArrayList<ParameterToView> dataFromDbCheck2 = (ArrayList<ParameterToView>) msg;
			dataInArrayListParameter.clear();
			if (dataFromDbCheck2 != null)
				if (!dataFromDbCheck2.isEmpty() && dataFromDbCheck2.get(0) instanceof ParameterToView) {
					dataInArrayListParameter = dataFromDbCheck2;
					awaitResponse = false;
					return;
				}
		} catch (ClassCastException e) {}
		

		try {
			ArrayList<HourAmount> dataFromDbCheck = (ArrayList<HourAmount>) msg;
			dataInArrayListHour.clear();
			if (dataFromDbCheck != null)
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof HourAmount) {
					dataInArrayListHour = dataFromDbCheck;
					awaitResponse = false;
			        return;
				}
		} catch (ClassCastException e) {
		}

		try {
			ArrayList<FreePlaceInPark> dataFromDbCheck = (ArrayList<FreePlaceInPark>) msg;
			dataInArrayListFreePlaceInParks.clear();
			if (dataFromDbCheck != null) {
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof FreePlaceInPark) {
					dataInArrayListFreePlaceInParks = (ArrayList<FreePlaceInPark>) msg;
					awaitResponse = false;
			        return;
				}
			}
		} catch (ClassCastException e) {
		}
		try {
			ArrayList<DayToView> dataFromDbCheck = (ArrayList<DayToView>) msg;
			dataInArrayListDayToView.clear();
			if (dataFromDbCheck != null) {
				if (!dataFromDbCheck.isEmpty() && dataFromDbCheck.get(0) instanceof DayToView) {
					dataInArrayListDayToView = (ArrayList<DayToView>) msg;
					awaitResponse = false;
			        return;
				}
			}
		} catch (ClassCastException e) {
		}


		ArrayList<String> dataFromDb = (ArrayList<String>) msg;

		if (dataFromDb.contains("showTable")) {
			dataFromDb.remove("showTable");
			dataInArrayList = dataFromDb;

		}
		if (dataFromDb.contains("CheckID")) {
			dataFromDb.remove("CheckID");
			dataInArrayList = dataFromDb;

		}
		if (dataFromDb.contains("CheckUserIDInTable")) {
			dataFromDb.remove("CheckUserIDInTable");
			dataInArrayList = dataFromDb;
		}

		if (dataFromDb.contains("FetchParkDetails")) {
			dataFromDb.remove("FetchParkDetails");
			dataInArrayList = dataFromDb;
		}
		if (dataFromDb.contains("RegisterMember")) {
			dataFromDb.remove("RegisterMember");
		}
		if (dataFromDb.contains("sendToDeparmentManager")) {
			dataFromDb.remove("sendToDeparmentManager");
		}
		dataInArrayList = dataFromDb;
		awaitResponse = false;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param arr The message from the UI.
	 */

	public void handleMessageFromClientUI(ArrayList<String> arr)// copy from tirgul
	{
		try {
//	    	openConnection();//in order to send more than one message
			awaitResponse = true;
			sendToServer(arr);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
