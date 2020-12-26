package echoServer;

import util.OrderToView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import com.mysql.cj.jdbc.SuspendableXAConnection;

import util.Role;

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
			conn = DriverManager.getConnection("jdbc:mysql://localhost/visitorschema?serverTimezone=IST", "root",
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
	public static ArrayList<String> checkIfIdConnectedWithId(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
	//	toReturn.add(arr.get(0));
		if (m_connectedID.contains(arr.get(0))) {
			toReturn.add("connectedBefore");
			return toReturn;
		} else {
			m_connectedID.add(arr.get(0));
			toReturn = checkIdInMember(arr);
			return toReturn;
		}
	}

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
		} // occasional visit only end
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

	public static ArrayList<String> setInvite(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		String orderNumber = getOrderNumber();
		addToOrdersTable(arr, orderNumber, "active");
		toReturn.add(orderNumber);
		return toReturn;
	}

	public static ArrayList<String> countActiveOrders(String parkName) {
		String countOrders = null;
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select COUNT(OrderID) From orders O Where O.OrderStatus = 'active' AND O.ParkName =" + "'"+parkName+"'"); 																									
			while (rs.next()) {
				countOrders = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		toReturn.add(countOrders);
		return toReturn;
	}
}
