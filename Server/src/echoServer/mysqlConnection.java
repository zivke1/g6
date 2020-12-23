package echoServer;

import util.OrderToChange;
import util.OrderToView;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.Role;
import util.SimulationDetails;

public class mysqlConnection {
	static Connection conn;
	static HashSet<String> m_connectedID = new HashSet<String>();  

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

//	public static ArrayList<String> showTable(Object id) { ArrayList<String>
//	  dataFromDB = new ArrayList<>(); try {// inserting new row to the table String
//	  firstName = null, lastName = null, ID = null, email = null, phoneNum = null;
//	  Statement stmt = conn.createStatement(); String tmpId = ((ArrayList<String>)
//	  id).get(0); ResultSet rs =
//	  stmt.executeQuery("select * from visitor Where ID=" + tmpId); while
//	  (rs.next()) { firstName = rs.getString("FirstName"); lastName =
//	  rs.getString("LastName"); ID = rs.getString("ID"); email =
//	  rs.getString("Email"); phoneNum = rs.getString("PhoneNumber"); }
//	  dataFromDB.add(firstName); dataFromDB.add(lastName); dataFromDB.add(ID);
//	  dataFromDB.add(email); dataFromDB.add(phoneNum); } catch (SQLException e) {
//	  e.printStackTrace(); } return dataFromDB; }
//
//	public static String CheckID(Object id) {
//		if (id != null) {
//			ArrayList<String> arr = showTable(id);
//			if (((ArrayList<String>) id).get(0).equals(arr.get(2)))
//				return "True";
//		}
//		return "False";
//	}

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
			Date OrderDate = null;

			Statement stmt = conn.createStatement();
			String tmpId = (String) id;
			ResultSet rs = stmt.executeQuery("select * from orders Where UserID=" + tmpId);
			while (rs.next()) {
				UserID = rs.getString("UserID");
				OrderID = rs.getString("OrderID");
				OrderStatus = rs.getString("OrderStatus");
				OrderDate = rs.getDate("VisitDate");
				// cost = rs.getFloat("Payment");
				// orderType = rs.getString("TypeOfOrder");
				// orderEnterTime = rs.getTime("EnterTime");
				// email = rs.getString("Email");

				dataFromDB.add(new OrderToView(UserID, OrderID, OrderStatus, OrderDate));
				/*
				 * dataFromDB.add(email); dataFromDB.add(orderEnterTime.toString());
				 * dataFromDB.add(orderType); dataFromDB.add(cost + ""); dataFromDB.add(UserID);
				 * dataFromDB.add(OrderID); dataFromDB.add(OrderStatus);
				 * dataFromDB.add(OrderDate.toString());
				 */
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
	}

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

	public static ArrayList<String> checkIfIdConnectedWithId(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		stmt = conn.createStatement();
		ResultSet rs = null;

		toReturn.add(arr.get(0));
		if (m_connectedID.contains(arr.get(0))) {
			toReturn.add("connectedBefore");
			return toReturn;
		} else {
			m_connectedID.add(arr.get(0));
			rs = stmt.executeQuery("select * from members Where ID=" + arr.get(0));
			if (rs.next()) {
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String memberOrGuide = rs.getString("MemberOrGuide");
				toReturn.add(firstName);
				toReturn.add(lastName);
				toReturn.add(memberOrGuide);
			} else {
				toReturn.add("user");
			}
			return toReturn;
		}

	}

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

			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static ArrayList<String> checkIfIdConnectedWithMemberId(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		String id;
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
			id = rs.getString("ID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			String memberOrGuide = rs.getString("MemberOrGuide");
			toReturn.add(id);
			toReturn.add(firstName);
			toReturn.add(lastName);
			toReturn.add(memberOrGuide);
		} else {
			toReturn.add("notMember");
			return toReturn;
		}
		// check if the member already connected
		if (m_connectedID.contains(arr.get(0))) {
			toReturn.add("connectedBefore");
			return toReturn;
		} else {
			m_connectedID.add(arr.get(0));
		}
		return toReturn;

	}

	public static boolean insertParaUpdate(Object arr) {
		try {// inserting new row to the table
			ArrayList<String> a = (ArrayList<String>) arr;
			PreparedStatement update = conn.prepareStatement(
					"INSERT INTO paraUpdate (ParkName, paraType, ParaVal, dateOfRequest, FromDate, UntilDate) VALUES (?, ?, ?, ?,?,?)");
			System.out.println("arr size " + a.size() + " arr val " + arr);
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
			System.out.println(dataFromDB);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;

	}

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
		dataFromDB.add(amountOfPersonal);
		dataFromDB.add(amountOfGroup);
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

	public static String CancelOrder(ArrayList<String> arr) {
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			update.setString(1, "cancelled");
			update.setString(2, arr.get(0));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "The Order Cancelled Successfully";
	}

	public static ArrayList<String> checkInvite(ArrayList<String> arr) throws SQLException {// arr=ID,parkName,time,date,numberOfVisitors,email,occasional,status=(user,member,guide)
		ArrayList<Integer> parkDetils = checkCapacityAndAvarageVisitTime(arr.get(1));
		ArrayList<String> toReturn = new ArrayList<String>();
		// parkDetils=Capacity,GapVisitors,TimeOfAvergeVisit
		int gap = parkDetils.get(1);
		int Capacity = parkDetils.get(0);
		int numberOfVisitorsInInvite = Integer.parseInt(arr.get(4));

		if (arr.get(6).equals("notOccasional")) {
			ArrayList<String> sendTocheckNumberOfVistorsInPark = new ArrayList<String>();
			sendTocheckNumberOfVistorsInPark.add(arr.get(1));
			sendTocheckNumberOfVistorsInPark.add(arr.get(3));
			sendTocheckNumberOfVistorsInPark.add(arr.get(2));
			sendTocheckNumberOfVistorsInPark.add(parkDetils.get(2).toString());// sendTocheckNumberOfVistorsInPark=
																				// parkName,date,time,TimeOfAvrageVisit

			// this only for not occasional visit

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
																												// visit
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
				numberOfVisitorsInInvite--;
			} // him
			int extraDiscount = getExtraDiscount(arr.get(1));
			float price = 100 * numberOfVisitorsInInvite;// TODO check about the price
			price = (float) (price * (100 - regularDiscount.get(0)) / 100.0);
			price = (float) (price * (100 - regularDiscount.get(1)) / 100.0);
			price = (float) (price * (100 - extraDiscount) / 100.0);
			if (arr.contains("payBefore")) {
				price = (float) (price * (100 - 12) / 100.0);
			}
			toReturn.add(String.valueOf(price));// confirmed or theParkIsFull then price
//			if (toReturn.contains("InviteConfirm")) {
//				String orderNumber = getOrderNumber();
//				addToOrdersTable(arr, price, orderNumber, "waitingToApprove");
//			}
			// TODO need to add the message to confirm
			//// end of not occasional visit
		} else {//occasional visit only
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
				float price = 100 * numberOfVisitorsInInvite;// TODO check about the price
				price = (float) (price * (100 - regularDiscount.get(0)) / 100.0);
				price = (float) (price * (100 - regularDiscount.get(1)) / 100.0);
				price = (float) (price * (100 - extraDiscount) / 100.0);
				toReturn.add("InviteConfirm");
				toReturn.add(String.valueOf(price));// confirmed or theParkIsFull then price
//				String orderNumber = getOrderNumber();
//
//				addToOrdersTable(arr, price, orderNumber, "active");
			}
		}//occasional visit only end
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

	private static int getExtraDiscount(String parkName) throws SQLException {
		Statement stmt = conn.createStatement();

		Date d = new Date();
		String dateToMySql = d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
		int discaount = 0;
		try {
			ResultSet rs = stmt.executeQuery("select * from extradiscount Where startDate<=" + dateToMySql
					+ " AND endDate >=" + dateToMySql + " parkName= " + parkName);
			while (rs.next()) {
				discaount = rs.getInt("percentage");
			}
		} catch (SQLException e) {

		}
		return discaount;
	}

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

	private static void addToOrdersTable(ArrayList<String> arr, String orderNumber, String orderStatus)
			throws SQLException {
		PreparedStatement update = conn.prepareStatement(
				"INSERT INTO orders (UserID, OrderID, ParkName, ExpectedEnterTime, VisitDate, VisitorsAmount,TypeOfOrder,OrderStatus,EnterTime,ExitTime,Occasional,VisitorsAmountActual,Payment,Email) VALUES (?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)");
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
		update.setFloat(13, Float.parseFloat(arr.get(8)));
		update.setString(14, arr.get(5));
		update.executeUpdate();

	}

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
	private static int checkNumberOfVistorsInParkNext(ArrayList<String> sendTocheckNumberOfVistorsInPark)
			throws SQLException {
		Statement stmt = conn.createStatement();
		String visitTime = sendTocheckNumberOfVistorsInPark.get(2);
		String hour = visitTime.substring(0, 2);
		int hourAfterGap = Integer.parseInt(hour);
		hourAfterGap = hourAfterGap + Integer.parseInt(sendTocheckNumberOfVistorsInPark.get(3));
		Time fromThisTime = new Time(hourAfterGap, 0, 0);
		int numberOfVisitors = 0;
		ResultSet rs = stmt.executeQuery("select SUM(VisitorsAmount) from orders "
				+ "Where OrderStatus= 'waitingToVisit' OR OrderStatus='waitingToApprove' " + "AND VisitDate = '"
				+ sendTocheckNumberOfVistorsInPark.get(1) + "' AND ParkName = '"
				+ sendTocheckNumberOfVistorsInPark.get(1) + "' AND  EnterTime>='"
				+ sendTocheckNumberOfVistorsInPark.get(2) + "'AND EnterTime<'" + fromThisTime + "'");
		while (rs.next()) {
			numberOfVisitors = rs.getInt("SUM(VisitorsAmount)");
		}
		return numberOfVisitors;
	}

// i check waitingToAprove and Approve
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
				+ "Where OrderStatus= 'waitingToVisit' OR OrderStatus='waitingToApprove' " + "AND VisitDate = '"
				+ sendTocheckNumberOfVistorsInParkBack.get(1) + "' AND ParkName = '"
				+ sendTocheckNumberOfVistorsInParkBack.get(1) + "' AND  EnterTime<='"
				+ sendTocheckNumberOfVistorsInParkBack.get(2) + "'AND EnterTime>'" + fromThisTime + "'");
		while (rs.next()) {
			numberOfVisitors = rs.getInt("SUM(VisitorsAmount)");
		}
		return numberOfVisitors;
	}

	private static ArrayList<Integer> checkCapacityAndAvarageVisitTime(String parkName) throws SQLException {
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

	public static ArrayList<String> cancelReport() {
		ArrayList<String> arr = new ArrayList<>();
		Integer cancelNum = 0, expiredNum = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders Where OrderStatus=6");// 6=cancelled
			while (rs.next()) {
				cancelNum++;
				System.out.println(cancelNum.toString() + "cancel");
			}
			ResultSet rs2 = stmt.executeQuery("select * from orders Where OrderStatus=2");// 2= expired
			while (rs2.next()) {
				expiredNum++;
				System.out.println(expiredNum.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arr.add(cancelNum.toString());
		arr.add(expiredNum.toString());
		return arr;

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
					if (rs.getString("TypeOfOrder").equals("'user'"))
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
	public static void setOrderExpired(String orderID) {
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
			update.setString(1, "expired");
			update.setString(2, orderID);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * checking if any of the existing orders' status should be changed to expire
	 * and changes it if needed
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
				dateDb = rs.getDate("VisitDate");
				if (dateDb.getYear() < dateToday.getYear()
						|| (dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() < dateToday.getMonth())
						|| ((dateDb.getYear() == dateToday.getYear() && dateDb.getMonth() == dateDb.getMonth()
								&& dateDb.getDate() < dateToday.getDate()))) {
					if (!rs.getString("OrderStatus").equals("finished")
							&& !rs.getString("OrderStatus").equals("expired")
							&& !rs.getString("OrderStatus").equals("cancelled")
							&& !rs.getString("OrderStatus").equals("active")) {
						PreparedStatement update = conn
								.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
						update.setString(2, rs.getString("OrderID"));
						update.setString(1, "expired");
						update.executeUpdate();
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
	public static ArrayList<OrderToChange> checkCancelledOrder() {
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
			update.setString(1, "waitingToVisit");
			update.setString(2, arr.get(0));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Thank You For Your Confrimation";
	}

	/**
	 * checking if any of the waiting list orders can fit these time slots
	 * 
	 * @param arr
	 * @return
	 */
	public static ArrayList<OrderToChange> checkWaitingList(ArrayList<OrderToChange> arr) {
		ArrayList<OrderToChange> arrWaitingL = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select * from orders where EnterWaitingListDate is not null and OrderStatus='waitingList'");
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
		ArrayList<OrderToChange> alertWaiting = mysqlConnection.waitingFitsCancelledOrder(arr, arrWaitingL);
		alertWaiting = outOfWaitingList(alertWaiting);
		return addPhoneToOrder(alertWaiting);
	}

	/**
	 * checking if any of the orders in the waiting list can fit the cancelled
	 * orders time slot and if there are any conflicts (2 or more orders fit the
	 * same time slot) we take the order that waited the longest time
	 * 
	 * @param arr
	 * @param arrWaitingL
	 * @return
	 */
	private static ArrayList<OrderToChange> waitingFitsCancelledOrder(ArrayList<OrderToChange> arr,
			ArrayList<OrderToChange> arrWaitingL) {
		ArrayList<OrderToChange> alertWaiting = new ArrayList<>();
		for (OrderToChange cancelledOrder : arr) {
			TreeSet<OrderToChange> conflicts = new TreeSet<>();
			for (OrderToChange waiting : arrWaitingL) {
				if (cancelledOrder.getAmount() >= waiting.getAmount()
						&& cancelledOrder.getVisitDate().equals(waiting.getVisitDate())
						&& cancelledOrder.getExpectedEnterTime().equals(waiting.getExpectedEnterTime()))
					conflicts.add(waiting);

			}
			switch (conflicts.size()) {
			case 0:
				break;
			default:
				alertWaiting.add(conflicts.first());// taking the order with the longest waiting time for this time slot
				break;

			}
		}
		return alertWaiting;
	}

	/**
	 * changing order's status from waiting list to waiting to approve
	 * 
	 * @param arr
	 * @return
	 */
	private static ArrayList<OrderToChange> outOfWaitingList(ArrayList<OrderToChange> arr) {
		for (OrderToChange order : arr) {
			try {
				PreparedStatement update = conn.prepareStatement("UPDATE orders SET OrderStatus=? WHERE OrderID=?");
				update.setString(1, "waitingToApprove");
				update.setString(2, order.getOrderID());
				update.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arr;
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
		ArrayList<String> toReturn= new ArrayList<String>();
		String orderNumber = getOrderNumber();
		addToOrdersTable(arr, orderNumber, "active");
		toReturn.add(orderNumber);
		return toReturn;

	}
}
