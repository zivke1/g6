package echoServer;

import util.OrderToChange;
import util.DurationOrder;
import util.FreePlaceInPark;
import util.ViewReports;
import util.OrderToView;
import util.ParameterToView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import util.HourAmount;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import util.Role;

import util.TypeOfOrder;
import util.SimulationDetails;
import util.VisitorsInDate;

public class mysqlConnection {
	static Connection conn;
	static HashSet<String> m_connectedID = new HashSet<String>();

	static EchoServer server;

	public static void SetServer(EchoServer server1) {
		server = server1;
	}

	final static int DAYS_IN_WEEK = 7;
	final static int HOURS_IN_DAY = 24;
	final static Time OPEN_TIME = new Time(8, 0, 0);
	final static Time CLOSE_TIME = new Time(16, 0, 0);
	final static int OPEN_TIME_INT = 8;
	final static int CLOSE_TIME_INT = 16;
	final static int ENTER_PRICE = 100;
	static String calculatePrice;

	public static void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/visitorschema?serverTimezone=CAT", "root",
					"Aa123456");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static void insertTable(Object arr) {
		try {// inserting new row to the table
			PreparedStatement update = conn.prepareStatement(
					"INSERT INTO visitor (FirstName,LastName,ID,Email,PhoneNumber) VALUES (?, ?, ?, ?,?)");
			for (int i = 0; i < ((ArrayList<String>) arr).size(); i++)
				update.setString(i + 1, ((ArrayList<String>) arr).get(i));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateTable(Object arr)// arr={the new value u want,its ID,the column we want to change}
	{
		try {
			ArrayList<String> aL = (ArrayList<String>) arr;
			PreparedStatement update = conn.prepareStatement("UPDATE visitor SET " + aL.get(2) + "=? WHERE ID=?");
			for (int i = 0; i < aL.size() - 1; i++)// we do -1 because we dont relate to the column we want to change
				update.setString(i + 1, aL.get(i));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteRowTable(String id) {// deleting a row in the table
		try {
			PreparedStatement update = conn.prepareStatement("DELETE FROM visitor WHERE ID=" + id);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// need to go over existing table given its name and find the id
	// or search id in a given table- maybe not show it

	public static ArrayList<OrderToView> ReturnUserIDInTableOrders(Object arr) {
		if (arr instanceof ArrayList) {
			ArrayList<String> array = (ArrayList<String>) arr;
			if (array != null && array.get(0) != null) {// index 0 = userID
				ArrayList<OrderToView> ar = showTableOrders(array.get(0));
				return ar;
			}
		}
		return null;
	}

	public static String CheckUserIDInTable(Object arr) {
		if (arr instanceof ArrayList) {
			ArrayList<String> array = (ArrayList<String>) arr;

			if (array.get(0) != null) { // index 0 = userID
				ArrayList<OrderToView> ar = showTableOrders(array.get(0));
				if (ar.size() > 0) {
					return "True";
				}
			}
		}
		return "False";
	}

	/**
	 * 
	 * @param id
	 * @return arrayList with order details of a given UserID (row form orders
	 *         table)
	 */
	public static ArrayList<OrderToView> showTableOrders(Object id) {
		ArrayList<OrderToView> dataFromDB = new ArrayList<>();
		try {// inserting new row to the table
			String UserID = null, OrderID = null, OrderStatus = null;
			java.sql.Date OrderDate = null;

			Statement stmt = conn.createStatement();
			String tmpId = (String) id;
			ResultSet rs = stmt.executeQuery("select * from orders Where UserID=" + tmpId);
			while (rs.next()) {
				UserID = rs.getString("UserID");
				OrderID = rs.getString("OrderID");
				OrderStatus = rs.getString("OrderStatus");
				OrderDate = rs.getDate("VisitDate");
				dataFromDB.add(new OrderToView(UserID, OrderID, OrderStatus, OrderDate));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
	}
/**
 * check if this is employee id and password valid
 * @param arr
 * @return
 * @throws SQLException
 */
	public static ArrayList<String> checkIfEmployee(ArrayList<String> arr) throws SQLException {

		String id, firstName, lastName, role, connected, password, park;
		connected = "true";
		ArrayList<String> toReturn = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("select * from employee Where EmployeeNumber=" + arr.get(0).toString());
		} catch (SQLSyntaxErrorException e) {
			toReturn.add("employeeNotFound");
			return toReturn;
		}
		if (rs.next()) {// check if employee exist
			firstName = rs.getString("FirstName");
			lastName = rs.getString("LastName");
			id = rs.getString("UserID");
			role = rs.getString("UserRole");
			password = rs.getString("UserPassword");
			park = rs.getString("Park");
		} else {
			toReturn.add("employeeNotFound");
			return toReturn;
		}
		if (password.equals(arr.get(1))) {// the password right

			if (m_connectedID.contains(id)) {
				toReturn.add("connectedBefore");
				return toReturn;
			} else {
				toReturn.add(id);
				toReturn.add(firstName);
				toReturn.add(lastName);
				toReturn.add(role);
				toReturn.add(park);
				m_connectedID.add(id);
				return toReturn;
			}

		} else {
			toReturn.add("PaswwordIncorrect");
			return toReturn;
		}
	}

	// check if member ID is in member table
	
	/**
	 * get id and check if this is a member or user and if he connected before
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> checkIfIdConnectedWithId(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		// toReturn.add(arr.get(0));
		if (m_connectedID.contains(arr.get(0))) {
			toReturn.add("connectedBefore");
			return toReturn;
		} else {
			m_connectedID.add(arr.get(0));
			toReturn = checkIdInMember(arr);
			return toReturn;
		}
	}
/**
 * check if the id this function get is a member
 * @param arr
 * @return
 * @throws SQLException
 */
	public static ArrayList<String> checkIdInMember(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		toReturn.add(arr.get(0));
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select * from members Where ID=" + arr.get(0));
		if (rs.next()) {
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			String memberOrGuide = rs.getString("MemberOrGuide");
			int numberOfPeople = rs.getInt("numberOfPepole");
			toReturn.add(firstName);
			toReturn.add(lastName);
			toReturn.add(memberOrGuide);
			toReturn.add(numberOfPeople + "");
		} else {
			toReturn.add("user");
		}
		return toReturn;
	}
/**
 * this function add to members table new member
 * @param arr
 * @return
 */
	public static String RegisterMember(ArrayList<String> arr) {
		int memberID = 0;
		if (!insertToUsers(arr.get(2)))
			return "Exists";
		try {// inserting new row to the table
			Random rand = new Random();
			PreparedStatement update = conn.prepareStatement(
					"INSERT INTO members (FirstName,LastName,ID,Email,PhoneNumber,numberOfPepole,creditCard,memberID,MemberOrGuide) "
							+ "VALUES (?, ?, ?, ?,?,?,?,?,?)");
			for (int i = 0; i < ((ArrayList<String>) arr).size(); i++)
				if (i == 7)
					update.setString(i + 2, ((ArrayList<String>) arr).get(i));
				else
					update.setString(i + 1, ((ArrayList<String>) arr).get(i));

			do {
				memberID = rand.nextInt(899999);
				memberID += 100000;
			} while (!checkMemberIDExistsInMembership("" + memberID));
			update.setString(8, String.valueOf(memberID));
			update.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			return "Exists";
		}

		return memberID + "";
	}

	private static boolean insertToUsers(String id)// adding new user
	{

		try {// inserting new row to the table
			String firstName = null, lastName = null, ID = null, email = null, phoneNum = null;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from members Where memberID=" + id);
			int count = 0;
			while (rs.next()) {
				ID = rs.getString("memberID");
				count++;
			}
			if (count == 0)
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean checkMemberIDExistsInMembership(String memberID)// adding new user
	{
		try {// inserting new row to the table
			String firstName = null, lastName = null, ID = null, email = null, phoneNum = null;
			Statement stmt = conn.createStatement();
			;
			ResultSet rs = stmt.executeQuery("select * from members Where memberID=" + memberID);
			int count = 0;
			while (rs.next()) {
				ID = rs.getString("memberID");
				count++;
			}
			if (count == 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// check if member ID is in member table
	public static ArrayList<String> checkIfIdConnectedWithMemberId(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();

		// check if the member already connected
		if (m_connectedID.contains(arr.get(0))) {
			toReturn.add("connectedBefore");
			return toReturn;
		} else {
			m_connectedID.add(arr.get(0));
			toReturn = checkMemberIDInMembers(arr);
		}
		return toReturn;

	}
/**
 * check if this member id in the member table
 * @param arr
 * @return
 * @throws SQLException
 */
	public static ArrayList<String> checkMemberIDInMembers(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		ResultSet rs;
		Statement stmt = conn.createStatement();
		stmt = conn.createStatement();
		try {
			rs = stmt.executeQuery("select * from members Where memberID=" + arr.get(0));
		} catch (SQLSyntaxErrorException e) {
			toReturn.add("notMember");
			return toReturn;
		}
		if (rs.next()) {
			String id = rs.getString("ID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			String memberOrGuide = rs.getString("MemberOrGuide");
			int numberOfPeople = rs.getInt("numberOfPepole");
			toReturn.add(id);
			toReturn.add(firstName);
			toReturn.add(lastName);
			toReturn.add(memberOrGuide);
			toReturn.add(numberOfPeople + "");
		} else {
			toReturn.add("notMember");
			return toReturn;
		}
		return toReturn;
	}
/**
 * this function add to paraUpdate table
 * @param arr
 * @return
 */
	public static boolean insertParaUpdate(Object arr) {
		try {// inserting new row to the table
			ArrayList<String> a = (ArrayList<String>) arr;
			PreparedStatement update = conn.prepareStatement(
					"INSERT INTO paraUpdate (ParkName, paraType, ParaVal, dateOfRequest, FromDate, UntilDate) VALUES (?, ?, ?, ?,?,?)");
			for (int i = 0; i < ((ArrayList<String>) arr).size(); i++)
				update.setString(i + 1, ((ArrayList<String>) arr).get(i));
			update.executeUpdate();  
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public static ArrayList<String> FetchParkDetails(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		try {
			String Capacity = null, TimeOfAvergeVisit = null, MaxAmountOfOrders = null, ManagerName = null,
					GapVisitors = null;
			Statement stmt = conn.createStatement();
			String parkName = "'" + arr.get(0) + "'";
			ResultSet rs = stmt.executeQuery("select * from park Where ParkName=" + parkName);
			while (rs.next()) {
				Capacity = rs.getString("Capacity");
				TimeOfAvergeVisit = rs.getString("TimeOfAverageVisit");
				MaxAmountOfOrders = rs.getString("MaxAmountOfOrders");
				ManagerName = rs.getString("ManagerName");
				GapVisitors = rs.getString("GapVisitors");
			}
			dataFromDB.add("FetchParkDetails");
			dataFromDB.add(Capacity);
			dataFromDB.add(TimeOfAvergeVisit);
			dataFromDB.add(MaxAmountOfOrders);
			dataFromDB.add(ManagerName);
			dataFromDB.add(GapVisitors);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;

	}
/**
 *  remove id from the set of the id that connected
 * @param arr
 * @return
 * @throws SQLException
 */
	public static String closeAndSetIdNull(ArrayList<String> arr) throws SQLException {
		String id = arr.get(0);
		m_connectedID.remove(id);
		return "true";

	}

	public static ArrayList<String> visitorAmountReport(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2), amountOfVisitors = null, amountOfPersonal = null,
				amountOfGroup = null, amountOfMember = null;
		String parkName = "'" + arr.get(3) + "'";
		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-28' AND OrderStatus='finished' AND ParkName=" + parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-31' AND OrderStatus='finished' AND ParkName=" + parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-30') AND OrderStatus='finished' AND ParkName=" + parkName);
			}

			while (rs.next()) {
				amountOfVisitors = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-28' AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName=" + parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-31' AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName=" + parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-30') AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName=" + parkName);
			}
			while (rs.next()) {
				amountOfPersonal = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-28' AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName=" + parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-31' AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName=" + parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-30') AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName=" + parkName);
			}
			while (rs.next()) {
				amountOfMember = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-28' AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName=" + parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-31' AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName=" + parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '"
						+ year + "-" + month + "-01' AND '" + year + "-" + month
						+ "-30') AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName=" + parkName);
			}
			while (rs.next()) {
				amountOfGroup = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		dataFromDB.add(amountOfVisitors);
		if (amountOfPersonal == null)
			dataFromDB.add("0");
		else
			dataFromDB.add(amountOfPersonal);
		if (amountOfGroup == null)
			dataFromDB.add("0");
		else
			dataFromDB.add(amountOfGroup);
		if (amountOfMember == null)
			dataFromDB.add("0");
		else
			dataFromDB.add(amountOfMember);
		dataFromDB.add("VisitorAmountReport");
		return dataFromDB;
	}

	public static ArrayList<String> ViewOrders(ArrayList<String> arr) {

		ArrayList<String> dataFromDB = new ArrayList<>();
		try {
			String parkName = "", ExpectedEnterTime = "", TypeOfOrder = "", OrderStatus = "", Email = "";
			Date VisitDate = null;
			Integer VisitorsAmount = 0;
			Float Payment = 0f;
			Statement stmt = conn.createStatement();
			String orderID = arr.get(0);
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderID=" + orderID);
			while (rs.next()) {
				parkName = rs.getString("ParkName");
				ExpectedEnterTime = rs.getString("ExpectedEnterTime");
				VisitDate = rs.getDate("VisitDate");
				VisitorsAmount = rs.getInt("VisitorsAmount");
				TypeOfOrder = rs.getString("TypeOfOrder");
				OrderStatus = rs.getString("OrderStatus");
				Payment = rs.getFloat("Payment");
				Email = rs.getString("Email");
			}
			dataFromDB.add(orderID);
			dataFromDB.add(parkName);
			dataFromDB.add(ExpectedEnterTime);
			dataFromDB.add(VisitDate.toString());
			dataFromDB.add(VisitorsAmount.toString());
			dataFromDB.add(TypeOfOrder);
			dataFromDB.add(OrderStatus);
			dataFromDB.add(Payment.toString());
			dataFromDB.add(Email);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
	}
/**
 * set
 * @param arr
 * @return
 */
	public static String CancelOrder(ArrayList<String> arr) {
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			update.setString(1, "cancelled");
			update.setString(2, arr.get(0));
			update.executeUpdate();
			Thread t = new Thread(server.new OrderOpenSpot(arr.get(0)));// orderID
			t.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "The Order Cancelled Successfully";
	}

	/**
	 * check if the order is valid it get array that tell if the order is occasional
	 * or not the number of the visitors
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> checkInvite(ArrayList<String> arr) throws SQLException {// arr=ID,parkName,time,date,numberOfVisitors,email,occasional,status=(user,member,guide)

		ArrayList<Integer> parkDetils = checkCapacityAndAvarageVisitTime(arr.get(1));
		ArrayList<String> toReturn = new ArrayList<String>();
		// parkDetils=Capacity,GapVisitors,TimeOfAvergeVisit
		int gap = parkDetils.get(1);
		int Capacity = parkDetils.get(0);
		int numberOfVisitorsInInvite = Integer.parseInt(arr.get(4));
		calculatePrice = "Full tickect price: 100$\n";

		if (arr.get(6).equals("notOccasional")) {

			ArrayList<String> sendTocheckNumberOfVistorsInPark = new ArrayList<String>();
			sendTocheckNumberOfVistorsInPark.add(arr.get(1));
			sendTocheckNumberOfVistorsInPark.add(arr.get(3));
			sendTocheckNumberOfVistorsInPark.add(arr.get(2));
			sendTocheckNumberOfVistorsInPark.add(parkDetils.get(2).toString());// sendTocheckNumberOfVistorsInPark=
																				// parkName,date,time,TimeOfAvrageVisit

			// this only for not occasional visit
///TODO check 
			int numberOfVisitorsInTimeBefore = checkNumberOfVistorsInParkBack(sendTocheckNumberOfVistorsInPark);// this
																												// check
																												// for
																												// visitors
																												// amount
																												// before
																												// but
																												// it
																												// check
																												// for
																												// not
																												// occasinal
			// TODO check // visit
			int numberOfVisitorsInTimeAfter = checkNumberOfVistorsInParkNext(sendTocheckNumberOfVistorsInPark);
			if ((Capacity - gap < numberOfVisitorsInTimeBefore + numberOfVisitorsInInvite)
					|| (Capacity - gap < numberOfVisitorsInTimeAfter + numberOfVisitorsInInvite)) {
				toReturn.add("TheParkIsFull");

			} else {
				toReturn.add("InviteConfirm");
			}
			ArrayList<String> toDiscount = new ArrayList<String>();// status=(user,member,guide),occaional
			toDiscount.add(arr.get(7));
			toDiscount.add(arr.get(6));
			ArrayList<Integer> regularDiscount = getDiscount(toDiscount);
			if (arr.get(7).equals("guide")) {
				calculatePrice = calculatePrice + "Group visit, guide doesn't pay.\n";

				numberOfVisitorsInInvite--;
			} // him
			int extraDiscount = getExtraDiscount(arr.get(1));
			float price = ENTER_PRICE * numberOfVisitorsInInvite;// TODO check about the price

			price = (float) (price * (100 - regularDiscount.get(0)) / 100.0);
			if (regularDiscount.get(0) != 0) {
				calculatePrice = calculatePrice + "Regular discount " + regularDiscount.get(0) + "%\n";
			}
			if (arr.get(7).equals("member")) {
				price = (float) (price * (100 - regularDiscount.get(1)) / 100.0);
				if (regularDiscount.get(1) != 0) {
					calculatePrice = calculatePrice + "Member discount " + regularDiscount.get(1) + "%\n";
				}
			}
			price = (float) (price * (100 - extraDiscount) / 100.0);
			if (extraDiscount != 0) {
				calculatePrice = calculatePrice + "Extra discount " + extraDiscount + "%\n";
			}
			if (arr.contains("payBefore")) {
				price = (float) (price * (100 - 12) / 100.0);
				calculatePrice = calculatePrice + "Prepayment discount 12%\n";
			}
			toReturn.add(String.valueOf(price));// confirmed or theParkIsFull then price
//			if (toReturn.contains("InviteConfirm")) {
//				String orderNumber = getOrderNumber();
//				addToOrdersTable(arr, price, orderNumber, "waitingToApprove");
//			}
			// TODO need to add the message to confirm
			//// end of not occasional visit
		} else {// occasional visit only
			// parkDetils=Capacity,GapVisitors,TimeOfAvergeVisit
			int visitosAmount = checkNumberOfVisitorsNow(arr.get(1));
			if (visitosAmount + numberOfVisitorsInInvite > Capacity) {
				toReturn.add("TheParkIsFull");
				return toReturn;// TODO if there is not occaional invite i retrun only TheParkIsFull
			} else {
				ArrayList<String> toDiscount = new ArrayList<String>();// status=(user,member,guide),occaional
				toDiscount.add(arr.get(7));
				toDiscount.add(arr.get(6));
				ArrayList<Integer> regularDiscount = getDiscount(toDiscount);
				int extraDiscount = getExtraDiscount(arr.get(1));
				float price = ENTER_PRICE * numberOfVisitorsInInvite;// TODO check about the price
				price = (float) (price * (100 - regularDiscount.get(0)) / 100.0);
				if (regularDiscount.get(0) != 0) {
					calculatePrice = calculatePrice + "Regular discount " + regularDiscount.get(0) + "%\n";
				}
				if (arr.get(7).equals("member")) {
					price = (float) (price * (100 - regularDiscount.get(1)) / 100.0);
					if (regularDiscount.get(1) != 0) {
						calculatePrice = calculatePrice + "Member discount " + regularDiscount.get(1) + "%\n";
					}
				}
				price = (float) (price * (100 - extraDiscount) / 100.0);
				if (extraDiscount != 0) {
					calculatePrice = calculatePrice + "Extra discount " + extraDiscount + "%\n";
				}
				toReturn.add("InviteConfirm");
				toReturn.add(String.valueOf(price));// confirmed or theParkIsFull then price
//				String orderNumber = getOrderNumber();
//
//				addToOrdersTable(arr, price, orderNumber, "active");
			}
		} // occasional visit only end
		toReturn.add(calculatePrice);
		return toReturn;

	}

	private static int checkNumberOfVisitorsNow(String string) throws SQLException {
		Statement stmt = conn.createStatement();
		int visitorsNow = 0;

		Date d = new Date();
		String dateToMySql = d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
		try {
			ResultSet rs = stmt
					.executeQuery("select SUM(VisitorsAmountActual) from orders Where OrderStatus ='active'");
			while (rs.next()) {
				visitorsNow = rs.getInt("SUM(VisitorsAmountActual)");
			}
		} catch (SQLException e) {

		}
		return visitorsNow;
	}

	/**
	 * take from the extraDiscount table if there is an extra discount
	 * 
	 * @param parkName
	 * @return
	 * @throws SQLException
	 */
	private static int getExtraDiscount(String parkName) throws SQLException {
		Statement stmt = conn.createStatement();

//		Date d = new Date();//old ziv change
//		String dateToMySql = d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
//		int discaount = 0;
//		try {
//			ResultSet rs = stmt.executeQuery("select * from extradiscount Where startDate<=" + dateToMySql
//					+ " AND endDate >=" + dateToMySql + " AND parkName= " + parkName);
//			while (rs.next()) {
//				discaount = rs.getInt("percentage");
//			}
//		} catch (SQLException e) {
//
//		}
//		return discaount;

		Date d = new Date();
		String dateToMySql = d.getYear() + 1900 + "-" + d.getMonth() + 1 + "-" + d.getDate();
		int discaount = 0;
		try {
			ResultSet rs = stmt.executeQuery("select MAX(percentage) from extradiscount Where startDate<='" + dateToMySql
					+ "' AND endDate >='" + dateToMySql + "' AND parkName= '" + parkName+ "';");
			while (rs.next()) {
				discaount = rs.getInt("MAX(percentage)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return discaount;
	}

	/**
	 * give you valid order number random
	 * 
	 * @return
	 */
	private static String getOrderNumber() {
		boolean flagExists = true;
		Random rand = new Random();
		int orderID = 0;
		while (flagExists) {
			try {

				orderID = rand.nextInt(899999);
				orderID += 100000;
				String ID = "";
				Statement stmt = conn.createStatement();// select count(*) from foo where id
				ResultSet rs = stmt.executeQuery("select count(*) from orders Where OrderID=" + orderID);
				while (rs.next()) {
					ID = rs.getString("count(*)");
				}
				if (ID.equals("0")) {
					break;
				}
			} catch (SQLException e) {
				flagExists = false;

			}

		}
		return String.valueOf(orderID);
	}

	/**
	 * add to order table
	 * 
	 * @param arr
	 * @param orderNumber
	 * @param orderStatus
	 * @throws SQLException
	 */
	private static void addToOrdersTable(ArrayList<String> arr, String orderNumber, String orderStatus)
			throws SQLException {
		Date date = new Date();
		PreparedStatement update = conn.prepareStatement(
				"INSERT INTO orders (UserID, OrderID, ParkName, ExpectedEnterTime, VisitDate, VisitorsAmount,TypeOfOrder,OrderStatus,EnterTime,ExitTime,Occasional,VisitorsAmountActual,Payment,Email,EnterWaitingListDate) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?)");
		update.setString(1, arr.get(0));// INSERT INTO `visitorschema`.`orders` (`UserID`, `OrderID`, `ParkName`,
										// `ExpectedEnterTime`, `VisitDate`, `VisitorsAmount`, `TypeOfOrder`,
										// `OrderStatus`, `EnterTime`, `ExitTime`, `Occasional`, `VisitorsAmountActual`,
										// `Payment`, `Email`) VALUES ('a', 'q', '1', '2', '1', '1', '1', '2', '3', '4',
										// '5', '4', '4', '4');
		update.setString(2, orderNumber);
		update.setString(3, arr.get(1));
		update.setString(4, arr.get(2));
		update.setString(5, arr.get(3));
		update.setString(6, arr.get(4));
		if (!(arr.get(7).equals("guide"))) {
			update.setString(7, arr.get(7));
		} else {
			update.setString(7, "group");
		}
		update.setString(8, orderStatus);
		update.setString(9, null);
		update.setString(10, null);
		update.setBoolean(11, arr.get(6).equals("occasional"));
		update.setString(12, null);
		if (arr.get(8).equals("payBefore") == false) {
			update.setFloat(13, Float.parseFloat(arr.get(8)));
		} else {
			update.setFloat(13, Float.parseFloat(arr.get(9)));
		}
		update.setString(14, arr.get(5));
		if (orderStatus.equals("waitingList")) {
			update.setTimestamp(15, new Timestamp(date.getTime()));
		} else {
			update.setTimestamp(15, null);
		}

		update.executeUpdate();

	}

	/**
	 * get the discount that the invite can get
	 * 
	 * @param toDiscount
	 * @return
	 * @throws SQLException
	 */
	private static ArrayList<Integer> getDiscount(ArrayList<String> toDiscount) throws SQLException {// status=(user,member,guide),ocasional
		String TypeOfOrder = toDiscount.get(0);
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		if (TypeOfOrder.equals("guide")) {
			TypeOfOrder = "group";
		} else {
			TypeOfOrder = "personalFamily";
		}
		boolean ocasional = toDiscount.get(1).equals("occasional");

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(
				"select * from discounts Where TypeOfOrder='" + TypeOfOrder + "' AND ocasional='" + ocasional + "'");
		while (rs.next()) {
			toReturn.add(rs.getInt("percentage"));
			toReturn.add(rs.getInt("percentageForMembers"));
		}
		return toReturn;
	}

	// need to unit back and next

	/**
	 * check the number of the visitors in the park in the next few hours according
	 * to the average visit
	 * 
	 * @param sendTocheckNumberOfVistorsInPark
	 * @return
	 * @throws SQLException
	 */
	private static int checkNumberOfVistorsInParkNext(ArrayList<String> sendTocheckNumberOfVistorsInPark)
			throws SQLException {// this not include the hour
		Statement stmt = conn.createStatement();
		String visitTime = sendTocheckNumberOfVistorsInPark.get(2);
		String hour = visitTime.substring(0, 2);
		int hourAfterGap = Integer.parseInt(hour);
		hourAfterGap = hourAfterGap + Integer.parseInt(sendTocheckNumberOfVistorsInPark.get(3));
		Time fromThisTime = new Time(hourAfterGap, 0, 0);
		int numberOfVisitors = 0;
		ResultSet rs = stmt.executeQuery("select SUM(VisitorsAmount) from orders "
				+ "Where (OrderStatus= 'waitingToVisit' OR OrderStatus='waitingToApprove') " + "AND VisitDate = '"
				+ sendTocheckNumberOfVistorsInPark.get(1) + "' AND ParkName = '"
				+ sendTocheckNumberOfVistorsInPark.get(0) + "' AND  ExpectedEnterTime>='"
				+ sendTocheckNumberOfVistorsInPark.get(2) + "'AND ExpectedEnterTime<'" + fromThisTime + "'");
		while (rs.next()) {
			numberOfVisitors = rs.getInt("SUM(VisitorsAmount)");
		}
		return numberOfVisitors;
	}

// i check waitingToAprove and Approve
	/**
	 * check the number of the visitors in the park before take care about what in
	 * the previous hours
	 * 
	 * @param sendTocheckNumberOfVistorsInParkBack
	 * @return
	 * @throws SQLException
	 */
	private static int checkNumberOfVistorsInParkBack(ArrayList<String> sendTocheckNumberOfVistorsInParkBack)
			throws SQLException {
		Statement stmt = conn.createStatement();
		String visitTime = sendTocheckNumberOfVistorsInParkBack.get(2);
		String hour = visitTime.substring(0, 2);
		int hourAfterGap = Integer.parseInt(hour);
		hourAfterGap = hourAfterGap - Integer.parseInt(sendTocheckNumberOfVistorsInParkBack.get(3));
		Time fromThisTime = new Time(hourAfterGap, 0, 0);
		int numberOfVisitors = 0;
		ResultSet rs = stmt.executeQuery("select SUM(VisitorsAmount) from orders "
				+ " Where (OrderStatus= 'waitingToVisit' OR OrderStatus='waitingToApprove' ) AND VisitDate = '"
				+ sendTocheckNumberOfVistorsInParkBack.get(1) + "' AND ParkName = '"
				+ sendTocheckNumberOfVistorsInParkBack.get(0) + "' AND  ExpectedEnterTime<='"
				+ sendTocheckNumberOfVistorsInParkBack.get(2) + "'AND ExpectedEnterTime>'" + fromThisTime + "'");
		while (rs.next()) {
			numberOfVisitors = rs.getInt("SUM(VisitorsAmount)");
		}
		return numberOfVisitors;
	}

	/**
	 * get from the park table the details of one park return get parkName return
	 * arrayList<int> Capacity,TimeOfAverageVisit,GapVisitors
	 * 
	 * @throws SQLException
	 */
	public static ArrayList<Integer> checkCapacityAndAvarageVisitTime(String parkName) throws SQLException {
		ArrayList<Integer> parkDetilsNumbers = new ArrayList<Integer>();

		int TimeOfAvergeVisit = 0, GapVisitors = 0, Capacity = 0;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from park Where ParkName='" + parkName + "'");

		while (rs.next()) {
			Capacity = rs.getInt("Capacity");
			TimeOfAvergeVisit = rs.getInt("TimeOfAverageVisit");
			GapVisitors = rs.getInt("GapVisitors");
		}
		parkDetilsNumbers.add(Capacity);
		parkDetilsNumbers.add(GapVisitors);
		parkDetilsNumbers.add(TimeOfAvergeVisit);
		return parkDetilsNumbers;

	}

	public static ArrayList<String> cheakCapacity(ArrayList<String> arr) {
		Integer capacity = 0;
		try {
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from park Where ParkName='" + arr.get(0) + "'");
			if (rs.next())
				capacity = rs.getInt("Capacity");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> answer = new ArrayList<String>();
		answer.add(capacity.toString());
		return answer;

	}

	public static ArrayList<String> cancelReport(ArrayList<String> arr2) {
		ArrayList<String> arr = new ArrayList<>();
		Integer cancelNum = 0, expiredNum = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderStatus=6 AND ParkName='" + arr2.get(0)
					+ "'AND MONTH(VisitDate) ='" + arr2.get(1) + "' AND YEAR(VisitDate)='" + arr2.get(2) + "'");// 6=cancelled
			while (rs.next()) {
				cancelNum++;
			}
			ResultSet rs2 = stmt.executeQuery("select * from orders Where OrderStatus=2 AND ParkName='" + arr2.get(0)
					+ "'AND MONTH(VisitDate) ='" + arr2.get(1) + "' AND YEAR(VisitDate)='" + arr2.get(2) + "'");// 2=
																												// expired
			while (rs2.next()) {
				expiredNum++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arr.add(cancelNum.toString());
		arr.add(expiredNum.toString());
		return arr;

	}

	public static ArrayList<HourAmount> depManVisitRep(TypeOfOrder type, ArrayList<String> arr) {
		ArrayList<HourAmount> dataFromDB = new ArrayList<>();
		ResultSet rs = null;
		Time t1, t2;
		t1 = new Time(OPEN_TIME_INT, 0, 0);
		t2 = new Time(t1.getHours() + 1, 0, 0);
		int openTime = CLOSE_TIME_INT - OPEN_TIME_INT + 1;
		int[] sum = new int[24];// sum for each hour
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1 = new Time(t1.getHours(), 0,
					0), t2 = new Time(t2.getHours() + 1, 0, 0)) {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN ('"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "') AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';");
//				rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where (EnterTime BETWEEN '" + t1
//						+ "' AND '" + t2 + "') AND OrderStatus='finished' AND TypeOfOrder='" + type + "' AND ParkName='"
//						+ arr.get(0) + "' AND VisitDate ='" + arr.get(1) + "'");
				int x = Integer.parseInt(t1.toString().substring(0, 2));

				if (rs.next() && sum[x] == 0) {
					sum[x] = rs.getInt("sum(VisitorsAmountActual)");
					Integer temp = t1.getHours();
					dataFromDB.add(new HourAmount(temp.toString(), sum[x]));
				}
				// System.out.println("sum["+x+"]="+sum[x]);
//				while (rs.next())
//				{
//					sum[x] += rs.getInt("VisitorsAmountActual");
//
//				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataFromDB;
	}

	public static ArrayList<ParameterToView> paraToUpdateTable() {
		ArrayList<ParameterToView> dataFromDB = new ArrayList<>();
		ResultSet rs;
		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("Select * From paraupdate ");
			while (rs.next()) {
				ParameterToView temp = new ParameterToView();
				temp.setParkName(rs.getString("parkName"));
				temp.setParameter(rs.getString("paraType"));
				temp.setNewValue(rs.getInt("ParaVal"));
				temp.setRequest(rs.getTimestamp("dateOfRequest"));
				temp.setFrom(rs.getDate("FromDate"));
				temp.setTo(rs.getDate("UntilDate"));
				// temp.setApproveButton(new Button("Accept"));
				// temp.setRejectButton(new Button("Deny"));
				dataFromDB.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataFromDB;

	}

	/**
	 * getting phone number, email, order ID for an order to the day after
	 * 
	 * @return
	 */
	public static ArrayList<SimulationDetails> dayBeforeVisit() {
		ArrayList<SimulationDetails> arr = new ArrayList<>();
		Date d = new Date();
		java.sql.Date d1 = new java.sql.Date(d.getYear(), d.getMonth(), d.getDate() + 1), dDb;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderStatus='waitingToApprove'");
			while (rs.next()) {
				dDb = rs.getDate("VisitDate");
				if (d1.getYear() == dDb.getYear() && d1.getMonth() == dDb.getMonth() && d1.getDate() == dDb.getDate()) {
					if (rs.getString("TypeOfOrder").equals("user"))
						arr.add(new SimulationDetails(rs.getString("Email"), null, rs.getString("OrderID")));
					else {
						String id = rs.getString("UserID");
						Statement stmt2 = conn.createStatement();
						ResultSet rsMember = stmt2.executeQuery("select * from members Where ID=" + id);
						if (rsMember.next())
							arr.add(new SimulationDetails(rs.getString("Email"), rsMember.getString("PhoneNumber"),
									rs.getString("OrderID")));
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * checking if the status of the order is "waitingFor"
	 * 
	 * @param orderID
	 * @param waitingFor
	 * @return
	 */
	public static boolean checkWaiting(String orderID, String waitingFor) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderID='" + orderID + "'");
			if (rs.next())
				if (!rs.getString("OrderStatus").equals(waitingFor))
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * changing the order's status to expired
	 * 
	 * @param orderID
	 */
	public static void setOrderExpired(String orderID, String setStatus) {
		String orderStatus;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders where orderID=" + orderID);
			if (rs.next())
				orderStatus = rs.getString("OrderStatus");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			update.setString(1, setStatus);
			update.setString(2, orderID);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * checking if any of the existing orders' status should be changed to expire
	 * and changes it if needed
	 * or delete if it's still in the waiting list
	 * 
	 */
	public static void checkOrdersStatus() {
		try {
			String status = "";
			Date d = new Date();
			java.sql.Date dateDb, dateToday = new java.sql.Date(d.getYear(), d.getMonth(), d.getDate());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders");
			while (rs.next()) {
				Time t = new Time(d.getHours() - 4, d.getMinutes(), d.getSeconds());//allowing 4 hours late
				dateDb = rs.getDate("VisitDate");
				if (dateDb.getYear() < dateToday.getYear()
						|| (dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() < dateToday.getMonth())
						|| (dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() == dateDb.getMonth()
								&& dateDb.getDate() < dateToday.getDate())
						|| (dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() == dateDb.getMonth()
								&& dateDb.getDate() == dateToday.getDate()
								&& rs.getTime("ExpectedEnterTime").compareTo(t) < 0)) {
					if (!rs.getString("OrderStatus").equals("finished")
							&& !rs.getString("OrderStatus").equals("expired")
							&& !rs.getString("OrderStatus").equals("cancelled")
							&& !rs.getString("OrderStatus").equals("active")) {
						if(rs.getString("OrderStatus").equals("waitingList"))
						{
							PreparedStatement update = conn
									.prepareStatement("delete from orders where OrderID=?");
							update.setString(1, rs.getString("OrderID"));
							update.executeUpdate();
						}
						else {
						PreparedStatement update = conn
								.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
						update.setString(2, rs.getString("OrderID"));
						update.setString(1, "expired");
						update.executeUpdate();
						}
					}

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * adding all the cancelled orders in the future to array list and returning it
	 * 
	 * @return
	 */
	public static synchronized ArrayList<OrderToChange> checkCancelledOrder() {
		Date d = new Date();
		ArrayList<OrderToChange> arr = new ArrayList<>();
		try {
			java.sql.Date dateDb, dateToday = new java.sql.Date(d.getYear(), d.getMonth(), d.getDate());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders where OrderStatus='cancelled'");
			while (rs.next()) {
				dateDb = rs.getDate("VisitDate");
				if (dateDb.getYear() > dateToday.getYear()
						|| (dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() > dateToday.getMonth())
						|| ((dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() == dateDb.getMonth()
								&& dateDb.getDate() > dateToday.getDate()))) {
					String userID = rs.getString("userID"), orderID = rs.getString("orderID"),
							pName = rs.getString("ParkName"), type = rs.getString("TypeOfOrder"),
							status = rs.getString("OrderStatus"), email = rs.getString("Email");
					Time expectedEnterTime = rs.getTime("ExpectedEnterTime");
					java.sql.Date VisitDate = rs.getDate("VisitDate");
					int amount = rs.getInt("VisitorsAmount");
					boolean Occasional = false;
					float payment = rs.getFloat("Payment");
					arr.add(new OrderToChange(userID, orderID, pName, type, status, email, expectedEnterTime, VisitDate,
							amount, Occasional, payment, null, null, null));// no waiting list enter time, phone number
																			// and message
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	/**
	 * updating an order's status to 'waitingToVisit'
	 * 
	 * @param arr
	 * @return
	 */
	public static String WaitingForVisitOrder(ArrayList<String> arr) {
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			if(!checkDateWatingList(arr.get(0)))
			update.setString(1, "waitingToVisit");
			else update.setString(1, "waitingToApprove");
			update.setString(2, arr.get(0));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Thank You For Your Confrimation";
	}
	/**
	 * checks if this order needs a day before visit simulation
	 * @param orderID
	 * @return
	 */
	public static boolean checkDateWatingList(String orderID)
	{
		
		Statement stmt;
		Date d=new Date();
		java.sql.Date orderD=null,today=new java.sql.Date(d.getYear(), d.getMonth(), d.getDate()+1);
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders where orderID=" + orderID);
			if(rs.next())
				orderD=rs.getDate("VisitDate");
			if(today.getYear()==orderD.getYear()&&today.getMonth()==orderD.getMonth()&&today.getDate()==today.getDate())
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * checking if any of the waiting list orders can fit these time slots
	 * 
	 * 
	 * @param orderIDC
	 * @return
	 */
	public static ArrayList<OrderToChange> checkWaitingList(String orderIDC) {
		ArrayList<OrderToChange> arrWaitingL = new ArrayList<>();
		Statement stmt2;
		OrderToChange order = null;
		try {
			stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("select * from orders where orderID=" + orderIDC);// taking the cancelled
																									// order
			while (rs2.next()) {
				String userID = rs2.getString("userID"), orderID = rs2.getString("orderID"),
						pName = rs2.getString("ParkName"), type = rs2.getString("TypeOfOrder"),
						status = rs2.getString("OrderStatus"), email = rs2.getString("Email");
				Time expectedEnterTime = rs2.getTime("ExpectedEnterTime");
				java.sql.Date VisitDate = rs2.getDate("VisitDate");
				int amount = rs2.getInt("VisitorsAmount");
				boolean Occasional = false;
				float payment = rs2.getFloat("Payment");
				Timestamp enteredWaiting = rs2.getTimestamp("EnterWaitingListDate");
				order = new OrderToChange(userID, orderID, pName, type, status, email, expectedEnterTime, VisitDate,
						amount, Occasional, payment, enteredWaiting, null, null);// no phone number and
																					// message
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select * from orders where EnterWaitingListDate is not null and OrderStatus='waitingList' and ParkName='"
							+ order.getpName() + "' and VisitDate='" + order.getVisitDate() + "'");
			while (rs.next()) {
				String userID = rs.getString("userID"), orderID = rs.getString("orderID"),
						pName = rs.getString("ParkName"), type = rs.getString("TypeOfOrder"),
						status = rs.getString("OrderStatus"), email = rs.getString("Email");
				Time expectedEnterTime = rs.getTime("ExpectedEnterTime");
				java.sql.Date VisitDate = rs.getDate("VisitDate");
				int amount = rs.getInt("VisitorsAmount");
				boolean Occasional = false;
				float payment = rs.getFloat("Payment");
				Timestamp enteredWaiting = rs.getTimestamp("EnterWaitingListDate");
				arrWaitingL.add(new OrderToChange(userID, orderID, pName, type, status, email, expectedEnterTime,
						VisitDate, amount, Occasional, payment, enteredWaiting, null, null));// no phone number and
																								// message
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ArrayList<OrderToChange> alertWaiting = mysqlConnection.waitingFitsCancelledOrder(order, arrWaitingL);
		// alertWaiting = outOfWaitingList(alertWaiting);
		return addPhoneToOrder(alertWaiting);
	}

	/**
	 * checking if any of the orders in the waiting list can fit the cancelled
	 * orders time slot and if there are any conflicts (2 or more orders fit the
	 * same time slot) we take the order that waited the longest time
	 * 
	 * @param order
	 * @param arrWaitingL
	 * @param amount
	 * @return
	 */
	private static ArrayList<OrderToChange> waitingFitsCancelledOrder(OrderToChange order,
			ArrayList<OrderToChange> arrWaitingL) {
		ArrayList<OrderToChange> alertWaiting = new ArrayList<>();
		int[] amount = checkActualCap(order);
		TreeSet<OrderToChange> conflicts = new TreeSet<>();
		for (OrderToChange waiting : arrWaitingL) {
			int i = Integer.parseInt(waiting.getExpectedEnterTime().toString().substring(0, 2));
			if (amount[i] >= waiting.getAmount() && order.getVisitDate().equals(waiting.getVisitDate())) {
				conflicts.add(waiting);
			}
		}
		boolean flag = true;
		while (flag && conflicts.size() > 0) {

			int i = Integer.parseInt(conflicts.first().getExpectedEnterTime().toString().substring(0, 2));
			if (amount[i] >= conflicts.first().getAmount()) {
				alertWaiting.add(outOfWaitingList(conflicts.first()));// taking the order with the longest waiting time
																		// for this time slot
				// and changing it's status to waiting to approve
				conflicts.remove(conflicts.first());
				amount = checkActualCap(order);
			} else
				flag = false;
		}
		return alertWaiting;
	}

	/**
	 * getting the amount of free space in each hour in a certain day(according to
	 * the order's date)
	 * 
	 * @param order
	 * @return
	 */
	public static int[] checkActualCap(OrderToChange order) {
		int res[] = new int[24];
		try {
			ArrayList<Integer> arr = checkCapacityAndAvarageVisitTime(order.getpName());
			ArrayList<String> arrS = new ArrayList<>();
			arrS.add(order.getpName());
			arrS.add(order.getVisitDate().toString());
			arrS.add(order.getExpectedEnterTime().toString());
			arrS.add(arr.get(2).toString());
			int amount = checkNumberOfVistorsInParkBack(arrS);

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT VisitDate,ExpectedEnterTime, SUM(VisitorsAmount) FROM orders "
					+ "WHERE (OrderStatus = 'waitingToApprove' OR OrderStatus='waitingToVisit') AND ExpectedEnterTime>='"
					+ OPEN_TIME.toString() + "' AND ExpectedEnterTime<='" + CLOSE_TIME.toString() + "' AND ParkName = '"
					+ order.getpName() + "' AND VisitDate='" + order.getVisitDate() + "' GROUP BY ExpectedEnterTime "
					+ "Order BY ExpectedEnterTime ;");

			int[] visitorsPerDay = new int[24];
			Time time;
			while (rs.next()) {
				visitorsPerDay = new int[24];
				time = rs.getTime("ExpectedEnterTime");
				amount = rs.getInt("SUM(VisitorsAmount)");
				visitorsPerDay[Integer.valueOf(time.toString().substring(0, 2))] = amount;

			}

			int sum = 0;
			int timeOfAvarageVisit = arr.get(2);
			for (int j = OPEN_TIME_INT; j < CLOSE_TIME_INT + timeOfAvarageVisit; j++) {// set the loop according to the
																						// // open hours
				sum += visitorsPerDay[j];
				if (j >= timeOfAvarageVisit * 2 - 1) {
					sum -= visitorsPerDay[j - timeOfAvarageVisit * 2 + 1];
				}
				res[j] = (arr.get(0) - arr.get(2) - sum);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * changing order's status from waiting list to waiting to approve
	 * 
	 * @param arr
	 * @return
	 */
	private static OrderToChange outOfWaitingList(OrderToChange order) {
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			update.setString(1, "waitingToApprove");
			update.setString(2, order.getOrderID());
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return order;
	}

	/**
	 * adding the phone number of the owner of each order
	 * 
	 * @param arr
	 * @return
	 */
	private static ArrayList<OrderToChange> addPhoneToOrder(ArrayList<OrderToChange> arr) {
		for (OrderToChange order : arr) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from members where ID='" + order.getUserID() + "'");
				while (rs.next())
					order.setPhoneNum(rs.getString("PhoneNumber"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arr;
	}

	public static ArrayList<String> setInvite(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		String orderNumber = getOrderNumber();
		if (arr.contains("occasional")) {
			addToOrdersTable(arr, orderNumber, "waitingToVisit");
		} else {
			addToOrdersTable(arr, orderNumber, "waitingToApprove");
		}
		toReturn.add(orderNumber);
		return toReturn;

	}

	public static void setPara(ArrayList<String> arr) {
		try {
			PreparedStatement update = conn
					.prepareStatement("DELETE FROM paraupdate WHERE parkName = ?  and paraType = ? "
							+ "and ParaVal = ? and dateOfRequest = ?");
			update.setString(1, arr.get(1));
			update.setString(2, arr.get(2));
			update.setString(3, arr.get(3));
			update.setString(4, arr.get(4));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (arr.contains("y")) {
			if (arr.contains("discount")) {
				try {
					PreparedStatement update = conn
							.prepareStatement("INSERT INTO extradiscount  (percentage,startDate,endDate,parkName) values (?,?,?,?)");
					update.setString(1, arr.get(3));
					update.setString(2, arr.get(5));
					update.setString(3, arr.get(6));
					update.setString(4, arr.get(1));
					update.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				try {
					PreparedStatement update = conn
							.prepareStatement("UPDATE park SET " + arr.get(2) + "=? WHERE parkName=?");
					update.setInt(1, Integer.parseInt(arr.get(3)));
					update.setString(2, arr.get(1));
					update.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static ArrayList<String> incomeReport(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2), income = null;
		String parkName = "'" + arr.get(3) + "'";
		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(Payment) from orders" + " Where VisitDate BETWEEN '" + year + "-"
						+ month + "-01' AND '" + year + "-" + month + "-28' AND OrderStatus='finished' AND ParkName="
						+ parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(Payment) from orders" + " Where VisitDate BETWEEN '" + year + "-"
						+ month + "-01' AND '" + year + "-" + month + "-31' AND OrderStatus='finished' AND ParkName="
						+ parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(Payment) from orders" + " Where (VisitDate BETWEEN '" + year + "-"
						+ month + "-01' AND '" + year + "-" + month + "-30') AND OrderStatus='finished' AND ParkName="
						+ parkName);
			}
			while (rs.next()) {
				income = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataFromDB.add("incomeReport");
		if (income == null)
			dataFromDB.add("0");
		else
			dataFromDB.add(income);
		return dataFromDB;
	}

	public static ArrayList<String> UsageReports(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		ArrayList<String> tmpDataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2);
		String parkName = "'" + arr.get(3) + "'";
		int capacity = 0, TimeOfAverageVisit = 0, parkOpenINHours = 8, usageNumber = 0;// usageNumber number of
																						// excellent
																						// usage day(above 100%)

		try {// first query fetch the capacity and the TimeOfAverageVisit from DB
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select Capacity,TimeOfAverageVisit from park where ParkName=" + parkName);
			while (rs.next()) {
				capacity = rs.getInt(1);
				TimeOfAverageVisit = rs.getInt(2);
			}
			usageNumber = capacity * (parkOpenINHours / TimeOfAverageVisit);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {// second query fetch the number of visitors in all days at specific month and
				// year
			if (month.equals("02")) {
				for (int i = 1; i <= 28; i++) {
					ResultSet rs = null;
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders where VisitDate='" + year
							+ "-02-" + i + "' and OrderStatus='finished' AND ParkName=" + parkName);
					while (rs.next()) {
						tmpDataFromDB.add(rs.getString(1));
					}
				}
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				for (int i = 1; i <= 31; i++) {
					ResultSet rs = null;
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders where VisitDate='" + year + "-"
							+ month + "-" + i + "' and OrderStatus='finished' AND ParkName=" + parkName);
					while (rs.next()) {
						tmpDataFromDB.add(rs.getString(1));
					}
				}

			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				for (int i = 1; i <= 30; i++) {
					ResultSet rs = null;
					Statement stmt = conn.createStatement();
					rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders where VisitDate='" + year + "-"
							+ month + "-" + i + "' and OrderStatus='finished' AND ParkName=" + parkName);
					while (rs.next()) {
						tmpDataFromDB.add(rs.getString(1));
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataFromDB.add(usageNumber + "");
		for (int i = 0; i < tmpDataFromDB.size(); i++) {
			if (tmpDataFromDB.get(i) != null)
				dataFromDB.add(tmpDataFromDB.get(i));
			else
				dataFromDB.add("0");
		}
		return dataFromDB;
		/**
		 * i need to think how i transfer the data from her to the client i have picture
		 * of how the arrays look like
		 */

	}

	/**
	 * @author elira simulate card reader that return random user id
	 */

	public static ArrayList<String> simulationCardReader() {
		ArrayList<String> arr = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select UserID from orders");
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
			Random rand = new Random();
			String userId = arr.get(rand.nextInt(arr.size()));
			arr.clear();
			arr.add(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public static ArrayList<OrderToView> ReturnUserIDInTableOrdersForCardReader(ArrayList<String> arr) {
		if (arr instanceof ArrayList) {
			ArrayList<String> array = (ArrayList<String>) arr;
			if (array != null && array.get(0) != null) {// index 0 = userID
				ArrayList<OrderToView> ar = showTableOrdersCardReader(array.get(0));
				return ar;
			}
		}
		return null;
	}

	public static ArrayList<OrderToView> showTableOrdersCardReader(Object id) {
		ArrayList<OrderToView> dataFromDB = new ArrayList<>();
		try {
			String UserID = null, OrderID = null, OrderStatus = null;
			Date OrderDate = null;
			LocalDate date = LocalDate.now(); // Gets the current date
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Statement stmt = conn.createStatement();
			String tmpId = (String) id;
			ResultSet rs = stmt.executeQuery("select * from orders Where UserID='" + tmpId + "' "
					+ "AND (OrderStatus='waitingToVisit' OR OrderStatus='active') AND VisitDate='"
					+ date.format(formatter) + "'");
			while (rs.next()) {
				UserID = rs.getString("UserID");
				OrderID = rs.getString("OrderID");
				OrderStatus = rs.getString("OrderStatus");
				OrderDate = rs.getDate("VisitDate");
				dataFromDB.add(new OrderToView(UserID, OrderID, OrderStatus, OrderDate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
	}

	public static void updateToFinished(ArrayList<String> arr) {
		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			PreparedStatement update = conn.prepareStatement(
					"UPDATE orders SET OrderStatus=?,ExitTime='" + sdf.format(cal.getTime()) + "' WHERE OrderID=?");
			update.setString(1, "finished");
			update.setString(2, arr.get(1));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateToActive(Object arr) {
		try {
			ArrayList<String> aL = (ArrayList<String>) arr;
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=?,EnterTime='"
					+ sdf.format(cal.getTime()) + "' ,VisitorsAmountActual='" + aL.get(2) + "' WHERE OrderID=?");
			update.setString(1, "active");
			update.setString(2, aL.get(1));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get an invite and return arrayList of times and dates that the park can get
	 * more visitors
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<FreePlaceInPark> getFreePlace(ArrayList<String> arr) throws SQLException {
		ArrayList<FreePlaceInPark> toReturn = new ArrayList<FreePlaceInPark>();
		String parkName = arr.get(1);
		ArrayList<Integer> temp = checkCapacityAndAvarageVisitTime(parkName);
		int maxVisitors = temp.get(0);
		int gap = temp.get(1);
		int timeOfAvarageVisit = temp.get(2);
		String numberOfPepole = arr.get(4);
		int numberOfPepoleInInvite = Integer.valueOf(arr.get(4));
		ArrayList<String> sendToCheck = new ArrayList<String>();
		sendToCheck.add(parkName);
		sendToCheck.add(arr.get(3));
		sendToCheck.add(arr.get(2));
		sendToCheck.add(String.valueOf(timeOfAvarageVisit));

		int dalta = 0;
		int year = Integer.valueOf(arr.get(3).substring(0, 4));
		int month = Integer.valueOf(arr.get(3).substring(5, 7));
		int day = Integer.valueOf(arr.get(3).substring(8, 10));
		month--;
		java.sql.Date toDate, visitDate = new java.sql.Date(year - 1900, month, day - 3);
//		Date visitDate = new Date(2020,month,day-3);
//		year = visitDate.getYear();
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());

		while (visitDate.compareTo(today) < 0) {
			dalta++;
			visitDate = new java.sql.Date(year - 1900, month, day - 3 + dalta);

		}

//		String fromThisDate = String.valueOf(year)+visitDate.toString().substring(4);
		String fromThisDate = visitDate.toString();
		toDate = new java.sql.Date(year - 1900, month, day + 3 + dalta);
		String toThisDate = toDate.toString();

//		year = visitDate.getYear();
//		String toThisDate = String.valueOf(year)+visitDate.toString().substring(4);
//		@SuppressWarnings("deprecation")
//		Time openTime = new Time(OPEN_TIME.getHours(),OPEN_TIME.getMinutes(),OPEN_TIME.getSeconds());

//		String tString = OPEN_TIME.toString();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT VisitDate,ExpectedEnterTime, SUM(VisitorsAmount) FROM orders "
				+ "WHERE (OrderStatus = 'waitingToApprove' OR OrderStatus='waitingToVisit') AND ExpectedEnterTime>='"
				+ OPEN_TIME.toString() + "' AND ExpectedEnterTime<='" + CLOSE_TIME.toString() + "' AND ParkName = '"
				+ parkName + "' AND VisitDate>='" + fromThisDate + "' AND VisitDate<='" + toThisDate
				+ "'GROUP BY ExpectedEnterTime ,VisitDate " + "Order BY VisitDate,ExpectedEnterTime ;");
		java.sql.Date date;
		Time time;
		int amount;
		// java.sql.Date week[] = new java.sql.Date[7];
		VisitorsInDate allWeek[] = new VisitorsInDate[7];
		int i;
		for (i = 0; i < 7; i++) {
			allWeek[i] = new VisitorsInDate(visitDate);
			visitDate = new java.sql.Date(year - 1900, month, day - 3 + dalta + i + 1);
		}
		i = 0;
		int[] visitorsPerDay = new int[24];

		while (rs.next()) {
			date = rs.getDate("VisitDate");

			while (date.equals(allWeek[i].getDay()) == false) {
				i++;
				visitorsPerDay = new int[24];
			}
			time = rs.getTime("ExpectedEnterTime");
			amount = rs.getInt("SUM(VisitorsAmount)");
			visitorsPerDay[Integer.valueOf(time.toString().substring(0, 2))] = amount;

			allWeek[i].setVisitors(visitorsPerDay);
		}

		for (i = 0; i < DAYS_IN_WEEK; i++) {
			int sum = 0;
			visitorsPerDay = allWeek[i].getVisitors();
			for (int j = OPEN_TIME_INT; j < CLOSE_TIME_INT + timeOfAvarageVisit; j++) {// set the loop according to the
																						// open hours
				sum += visitorsPerDay[j];
				if (j >= timeOfAvarageVisit * 2 - 1) {
					sum -= visitorsPerDay[j - timeOfAvarageVisit * 2 + 1];
				}

				if ((j >= (timeOfAvarageVisit - 1) + OPEN_TIME_INT)
						&& (j <= (timeOfAvarageVisit - 1) + CLOSE_TIME_INT)) {
					int checkNow = j - timeOfAvarageVisit + 1;
					if (sum + numberOfPepoleInInvite < maxVisitors - gap) {
						// TODO vaild time
						toReturn.add(new FreePlaceInPark(new Time(checkNow, 0, 0).toString(),
								allWeek[i].getDay().toString()));

					}

				}
			}

		}
		return toReturn;

	}

	/**
	 * add to orders table in waiting list status
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<String> setInWaitingList(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		String orderNumber = getOrderNumber();
		addToOrdersTable(arr, orderNumber, "waitingList");
		toReturn.add(orderNumber);
		return toReturn;
	}

	public static ArrayList<String> countActiveOrders(String parkName) {
		String countOrders = null;
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"Select SUM(VisitorsAmount) From orders O Where O.OrderStatus = 'active' AND O.ParkName =" + "'"
							+ parkName + "'");
			while (rs.next()) {
				countOrders = rs.getString(1);
			}
			// ziv add
			if (countOrders == null) {
				countOrders = "0";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		toReturn.add(countOrders);
		return toReturn;
	}

	
	public static ArrayList<ViewReports> reportsToView() {
		ArrayList<ViewReports> toReturn = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			// first go over income report table
			ResultSet rs = stmt.executeQuery("Select * From incomereport");
			while (rs.next()) {
				String year = rs.getString("year");
				String month = rs.getString("month");
				String parkName = rs.getString("parkName");
				String totalIncome = rs.getString("totalIncome");
				ViewReports tmp = new ViewReports(year, month, parkName, "Income Report"); 
				tmp.setDataIncomeReport(totalIncome);
				toReturn.add(tmp);
			}
			// second go over usage report table
			rs = stmt.executeQuery("Select * From usagereport");
			while (rs.next()) {
				String year = rs.getString("year");
				String month = rs.getString("month");
				String parkName = rs.getString("parkName");
				ArrayList<String> dayUsage = new ArrayList<>();
				for (int i = 1; i <= 31; i++) {
					dayUsage.add("day" + i); //what if day empty, short month
				}
				ViewReports tmp = new ViewReports(year, month, parkName, "Usage Report"); 
				tmp.setDataUsageReport(dayUsage);
				toReturn.add(tmp);
			}
			// third go over visitor amount report table
			////

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return toReturn;
	}
	

	public static ArrayList<String> cheakGap(ArrayList<String> arr) {
		Integer gap = 0;
		try {
			ResultSet rs;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from park Where ParkName='" + arr.get(0) + "'");
			if (rs.next())
				gap = rs.getInt("GapVisitors");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> answer = new ArrayList<String>();
		answer.add(gap.toString());
		return answer;

	}

	public static ArrayList<DurationOrder> depManDuration(ArrayList<String> arr) {
//		//arr=[park name;date]
//		Integer countvisitor=0;//need to check if we need it
//		try {
//			Statement stmt= conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders Where OrderStatus ='finished' AND "
//					+ "VisitDate='"+arr.get(1) +"'AND ParkName='"+arr.get(0)+"'");
//			if (rs.next()) {
//				countvisitor= rs.getInt("SUM(VisitorsAmountActual)");
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		ArrayList<DurationOrder> arrD = new ArrayList<DurationOrder>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderStatus ='finished' AND " + "VisitDate='"
					+ arr.get(1) + "' AND ParkName='" + arr.get(0) + "' ");
			while (rs.next()) {
				String type = rs.getString("TypeOfOrder");
				int amount = rs.getInt("VisitorsAmountActual");
				int duration = Integer.parseInt(rs.getTime("ExitTime").toString().substring(0, 2))
						- Integer.parseInt(rs.getTime("EnterTime").toString().substring(0, 2));
				if (duration >= Integer.parseInt(arr.get(2)) && duration <= Integer.parseInt(arr.get(3))) {
					boolean flag = false;
					for (DurationOrder d : arrD)
						if (d.getType().equals(type)) {
							d.setAmount(d.getAmount() + amount);
							flag = true;
						}
					if (!flag)
						arrD.add(new DurationOrder(type, duration, amount));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrD;
	}

}
