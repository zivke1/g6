package echoServer;

import util.OrderToChange;
import util.DayToView;
import util.DurationOrder;
import util.FreePlaceInPark;
import util.Func;
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
import java.time.YearMonth;
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
import util.OrderForUsage;
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
/**
 * 
 * get data from our DB using queries
 * singleton
 */
public class mysqlConnection {
	static Connection conn;
	static HashSet<String> m_connectedID = new HashSet<String>();
	private static  mysqlConnection instance=null;
	static EchoServer server;

	public void SetServer(EchoServer server1) {
		server = server1;
	}
	
	private mysqlConnection() {
		
	} 
	
	public static mysqlConnection getInstance() 
    { 
        if (instance == null) 
        	instance = new mysqlConnection(); 
  
        return instance; 
    } 
	
	final static int DAYS_IN_WEEK = 7;
	final static int HOURS_IN_DAY = 24;
	final static Time OPEN_TIME = new Time(8, 0, 0);
	final static Time CLOSE_TIME = new Time(16, 0, 0);
	final static int OPEN_TIME_INT = 8;
	final static int CLOSE_TIME_INT = 16;
	final static int ENTER_PRICE = 100;
	static String calculatePrice;

	public void connectDB() {
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

	public void insertTable(Object arr) {
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

	public void updateTable(Object arr)// arr={the new value u want,its ID,the column we want to change}
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

	public void deleteRowTable(String id) {// deleting a row in the table
		try {
			PreparedStatement update = conn.prepareStatement("DELETE FROM visitor WHERE ID=" + id);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// need to go over existing table given its name and find the id
	// or search id in a given table- maybe not show it

	public ArrayList<OrderToView> ReturnUserIDInTableOrders(Object arr) {
		if (arr instanceof ArrayList) {
			ArrayList<String> array = (ArrayList<String>) arr;
			if (array != null && array.get(0) != null) {// index 0 = userID
				ArrayList<OrderToView> ar = showTableOrders(array.get(0));
				return ar;
			}
		}
		return null;
	}

	public String CheckUserIDInTable(Object arr) {
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
	public ArrayList<OrderToView> showTableOrders(Object id) {
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
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> checkIfEmployee(ArrayList<String> arr) throws SQLException {

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
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> checkIfIdConnectedWithId(ArrayList<String> arr) throws SQLException {
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
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> checkIdInMember(ArrayList<String> arr) throws SQLException {
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
	 * 
	 * @param arr
	 * @return
	 */
	public String RegisterMember(ArrayList<String> arr) {
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

	private boolean insertToUsers(String id)// adding new user
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

	private boolean checkMemberIDExistsInMembership(String memberID)// adding new user
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
	public ArrayList<String> checkIfIdConnectedWithMemberId(ArrayList<String> arr) throws SQLException {
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
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> checkMemberIDInMembers(ArrayList<String> arr) throws SQLException {
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
	 * 
	 * @param arr
	 * @return
	 */
	public boolean insertParaUpdate(Object arr) {
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

	public ArrayList<String> FetchParkDetails(ArrayList<String> arr) {
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
	 * remove id from the set of the id that connected
	 * 
	 * @param arr
	 * @return
	 * @throws SQLException
	 */
	public String closeAndSetIdNull(ArrayList<String> arr) throws SQLException {
		String id = arr.get(0);
		m_connectedID.remove(id);
		return "true";

	}

	public String amountOfVisitors(String year, String month, String parkName) {
		String amountOfVisitors = null;
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
		if (amountOfVisitors == null)
			return "" + 0;
		return amountOfVisitors;
	}

	public int returnTypeOfVisitorsEachDay(String type, String year, String month, String day, String parkName) {
		String numberOfTypeInSomeDate = null;
		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate ='" + year + "-"
					+ month + "-" + day + "' AND OrderStatus='finished' AND TypeOfOrder='" + type + "' AND ParkName="
					+ parkName);
			while (rs.next()) {
				numberOfTypeInSomeDate = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (numberOfTypeInSomeDate == null)
			return 0;
		return Integer.valueOf(numberOfTypeInSomeDate);// need to check if there in no visitor if return 0
	}

	public ArrayList<String> visitorAmountReport(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2), amountOfVisitors = null, amountOfPersonal = null,
				amountOfGroup = null, amountOfMember = null;
		String parkName = "'" + arr.get(3) + "'";
		int tmpGroup = 0, groupDay0 = 0, groupDay1 = 0, groupDay2 = 0, groupDay3 = 0, groupDay4 = 0, groupDay5 = 0,
				groupDay6 = 0;
		int tmpUser = 0, userDay0 = 0, userDay1 = 0, userDay2 = 0, userDay3 = 0, userDay4 = 0, userDay5 = 0,
				userDay6 = 0;
		int tmpMember = 0, memberDay0 = 0, memberDay1 = 0, memberDay2 = 0, memberDay3 = 0, memberDay4 = 0,
				memberDay5 = 0, memberDay6 = 0;

		amountOfVisitors = amountOfVisitors(year, month, parkName);
		/**
		 * if there is no visitors in this month go home
		 */
		if (amountOfVisitors.equals("0")) {
			dataFromDB.add("0");
			return dataFromDB;
		}

		if (month.equals("02")) {
			for (int i = 1; i <= 28; i++) {
				tmpGroup = returnTypeOfVisitorsEachDay("group", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpUser = returnTypeOfVisitorsEachDay("user", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpMember = returnTypeOfVisitorsEachDay("member", year, month, i + "", parkName);// get the number of
																									// some type in some
																									// park in some date

				java.sql.Date day = new java.sql.Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1, i);
				if (day.getDay() == 0)// add the number to the right day
				{
					groupDay0 += tmpGroup;
					userDay0 += tmpUser;
					memberDay0 += tmpMember;
				}
				if (day.getDay() == 1) {
					groupDay1 += tmpGroup;
					userDay1 += tmpUser;
					memberDay1 += tmpMember;
				}
				if (day.getDay() == 2) {
					groupDay2 += tmpGroup;
					userDay2 += tmpUser;
					memberDay2 += tmpMember;
				}
				if (day.getDay() == 3) {
					groupDay3 += tmpGroup;
					userDay3 += tmpUser;
					memberDay3 += tmpMember;
				}
				if (day.getDay() == 4) {
					groupDay4 += tmpGroup;
					userDay4 += tmpUser;
					memberDay4 += tmpMember;
				}
				if (day.getDay() == 5) {
					groupDay5 += tmpGroup;
					userDay5 += tmpUser;
					memberDay5 += tmpMember;
				}
				if (day.getDay() == 6) {
					groupDay6 += tmpGroup;
					userDay6 += tmpUser;
					memberDay6 += tmpMember;
				}
			}
		}
		if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08")
				|| month.equals("10") || month.equals("12")) {
			for (int i = 1; i <= 31; i++) {
				tmpGroup = returnTypeOfVisitorsEachDay("group", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpUser = returnTypeOfVisitorsEachDay("user", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpMember = returnTypeOfVisitorsEachDay("member", year, month, i + "", parkName);// get the number of
																									// some type in some
																									// park in some date

				java.sql.Date day = new java.sql.Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1, i);
				if (day.getDay() == 0)// add the number to the right day
				{
					groupDay0 += tmpGroup;
					userDay0 += tmpUser;
					memberDay0 += tmpMember;
				}
				if (day.getDay() == 1) {
					groupDay1 += tmpGroup;
					userDay1 += tmpUser;
					memberDay1 += tmpMember;
				}
				if (day.getDay() == 2) {
					groupDay2 += tmpGroup;
					userDay2 += tmpUser;
					memberDay2 += tmpMember;
				}
				if (day.getDay() == 3) {
					groupDay3 += tmpGroup;
					userDay3 += tmpUser;
					memberDay3 += tmpMember;
				}
				if (day.getDay() == 4) {
					groupDay4 += tmpGroup;
					userDay4 += tmpUser;
					memberDay4 += tmpMember;
				}
				if (day.getDay() == 5) {
					groupDay5 += tmpGroup;
					userDay5 += tmpUser;
					memberDay5 += tmpMember;
				}
				if (day.getDay() == 6) {
					groupDay6 += tmpGroup;
					userDay6 += tmpUser;
					memberDay6 += tmpMember;
				}
			}

		}
		if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
			for (int i = 1; i <= 30; i++) {
				tmpGroup = returnTypeOfVisitorsEachDay("group", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpUser = returnTypeOfVisitorsEachDay("user", year, month, i + "", parkName);// get the number of some
																								// type in some park in
																								// some date
				tmpMember = returnTypeOfVisitorsEachDay("member", year, month, i + "", parkName);// get the number of
																									// some type in some
																									// park in some date

				java.sql.Date day = new java.sql.Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1, i);
				if (day.getDay() == 0)// add the number to the right day
				{
					groupDay0 += tmpGroup;
					userDay0 += tmpUser;
					memberDay0 += tmpMember;
				}
				if (day.getDay() == 1) {
					groupDay1 += tmpGroup;
					userDay1 += tmpUser;
					memberDay1 += tmpMember;
				}
				if (day.getDay() == 2) {
					groupDay2 += tmpGroup;
					userDay2 += tmpUser;
					memberDay2 += tmpMember;
				}
				if (day.getDay() == 3) {
					groupDay3 += tmpGroup;
					userDay3 += tmpUser;
					memberDay3 += tmpMember;
				}
				if (day.getDay() == 4) {
					groupDay4 += tmpGroup;
					userDay4 += tmpUser;
					memberDay4 += tmpMember;
				}
				if (day.getDay() == 5) {
					groupDay5 += tmpGroup;
					userDay5 += tmpUser;
					memberDay5 += tmpMember;
				}
				if (day.getDay() == 6) {
					groupDay6 += tmpGroup;
					userDay6 += tmpUser;
					memberDay6 += tmpMember;
				}
			}
		}

		dataFromDB.add(amountOfVisitors);
		dataFromDB.add(groupDay0 + "");
		dataFromDB.add(groupDay1 + "");
		dataFromDB.add(groupDay2 + "");
		dataFromDB.add(groupDay3 + "");
		dataFromDB.add(groupDay4 + "");
		dataFromDB.add(groupDay5 + "");
		dataFromDB.add(groupDay6 + "");
		dataFromDB.add(userDay0 + "");
		dataFromDB.add(userDay1 + "");
		dataFromDB.add(userDay2 + "");
		dataFromDB.add(userDay3 + "");
		dataFromDB.add(userDay4 + "");
		dataFromDB.add(userDay5 + "");
		dataFromDB.add(userDay6 + "");
		dataFromDB.add(memberDay0 + "");
		dataFromDB.add(memberDay1 + "");
		dataFromDB.add(memberDay2 + "");
		dataFromDB.add(memberDay3 + "");
		dataFromDB.add(memberDay4 + "");
		dataFromDB.add(memberDay5 + "");
		dataFromDB.add(memberDay6 + "");
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
	 *update the order's status to cancelled and start the waiting list thread check
	 * 
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
	public ArrayList<String> checkInvite(ArrayList<String> arr) throws SQLException {// arr=ID,parkName,time,date,numberOfVisitors,email,occasional,status=(user,member,guide)

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

	private int checkNumberOfVisitorsNow(String string) throws SQLException {
		Statement stmt = conn.createStatement();
		int visitorsNow = 0;

		Date d = new Date();
//		String dateToMySql = d.getYear() + "-" + d.getMonth() + "-" + d.getDay();
		try {
			ResultSet rs = stmt
					.executeQuery("select SUM(VisitorsAmountActual) from orders Where OrderStatus ='active' AND ParkName ='"+string+"'");
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
	private int getExtraDiscount(String parkName) throws SQLException {
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
			ResultSet rs = stmt.executeQuery("select MAX(percentage) from extradiscount Where startDate<='"
					+ dateToMySql + "' AND endDate >='" + dateToMySql + "' AND parkName= '" + parkName + "';");
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
	private String getOrderNumber() {
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
	private void addToOrdersTable(ArrayList<String> arr, String orderNumber, String orderStatus)
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
	private ArrayList<Integer> getDiscount(ArrayList<String> toDiscount) throws SQLException {// status=(user,member,guide),ocasional
		String TypeOfOrder = toDiscount.get(0);
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		if (TypeOfOrder.equals("guide")) {
			TypeOfOrder = "group";
		} else {
			TypeOfOrder = "personalFamily";
		}
		boolean ocasional = toDiscount.get(1).equals("occasional");
		int toTableOcasional = 0;
		if (ocasional) {
			toTableOcasional = 1;
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from discounts Where TypeOfOrder='" + TypeOfOrder
				+ "' AND ocasional='" + toTableOcasional + "'");
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
	private int checkNumberOfVistorsInParkNext(ArrayList<String> sendTocheckNumberOfVistorsInPark)
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

	public ArrayList<String> cheakCapacity(ArrayList<String> arr) {
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

	public ArrayList<String> cancelReport(ArrayList<String> arr2) {
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

	public ArrayList<HourAmount> depManVisitRep(TypeOfOrder type, ArrayList<String> arr) {
		ArrayList<HourAmount> dataFromDB = new ArrayList<>();
		for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++)
			dataFromDB.add(new HourAmount(i + "", 0));
		ResultSet rs = null;
		Time t1, t2;
		t1 = new Time(OPEN_TIME_INT, 0, 0);
		t2 = new Time(t1.getHours() , 59, 59);
		int openTime = CLOSE_TIME_INT - OPEN_TIME_INT + 1;
		int[] sum = new int[24];// sum for each hour
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1 = new Time(t1.getHours() + 1, 0,0), t2 = new Time(t2.getHours() + 1, 59, 59)) {
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';");
//				rs = stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where (EnterTime BETWEEN '" + t1
//						+ "' AND '" + t2 + "') AND OrderStatus='finished' AND TypeOfOrder='" + type + "' AND ParkName='"
//						+ arr.get(0) + "' AND VisitDate ='" + arr.get(1) + "'");
				Integer x = Integer.parseInt(t1.toString().substring(0, 2));

				if (rs.next()) {
					sum[x] = rs.getInt("sum(VisitorsAmountActual)");
					// Integer temp = t1.getHours();
					for (HourAmount h : dataFromDB)
						if (h.getHour().equals(x.toString()))
							h.setAmount(h.getAmount() + sum[x]);
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

	public ArrayList<ParameterToView> paraToUpdateTable() {
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
	public ArrayList<SimulationDetails> dayBeforeVisit() {
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
	public boolean checkWaiting(String orderID, String waitingFor) {
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
	public void setOrderExpired(String orderID, String setStatus) {
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
	 * and changes it if needed or delete if it's still in the waiting list
	 * 
	 */
	public void checkOrdersStatus() {
		try {
			String status = "";
			Date d = new Date();
			java.sql.Date dateDb, dateToday = new java.sql.Date(d.getYear(), d.getMonth(), d.getDate());
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders");
			while (rs.next()) {
				Time t = new Time(d.getHours() - 4, d.getMinutes(), d.getSeconds());// allowing 4 hours late
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
						if (rs.getString("OrderStatus").equals("waitingList")) {
							PreparedStatement update = conn.prepareStatement("delete from orders where OrderID=?");
							update.setString(1, rs.getString("OrderID"));
							update.executeUpdate();
						} else {
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
			if (!checkDateWatingList(arr.get(0)))
				update.setString(1, "waitingToVisit");
			else
				update.setString(1, "waitingToApprove");
			update.setString(2, arr.get(0));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Thank You For Your Confrimation";
	}

	/**
	 * checks if this order needs a day before visit simulation
	 * 
	 * @param orderID
	 * @return
	 */
	public static boolean checkDateWatingList(String orderID) {

		Statement stmt;
		Date d = new Date();
		java.sql.Date orderD = null, today = new java.sql.Date(d.getYear(), d.getMonth(), d.getDate() + 1);
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from orders where orderID=" + orderID);
			if (rs.next())
				orderD = rs.getDate("VisitDate");
			if (today.getYear() == orderD.getYear() && today.getMonth() == orderD.getMonth()
					&& today.getDate() == orderD.getDate())

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
	public ArrayList<OrderToChange> checkWaitingList(String orderIDC) {
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
	private ArrayList<OrderToChange> addPhoneToOrder(ArrayList<OrderToChange> arr) {
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

	public ArrayList<String> setInvite(ArrayList<String> arr) throws SQLException {
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

	public void setPara(ArrayList<String> arr) {
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
					PreparedStatement update = conn.prepareStatement(
							"INSERT INTO extradiscount  (percentage,startDate,endDate,parkName) values (?,?,?,?)");
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

	public ArrayList<String> incomeReport(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2), income = null;
		float incomeFloat=0;
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
				incomeFloat = rs.getFloat(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataFromDB.add("incomeReport");
		if (incomeFloat == 0)
			dataFromDB.add("0");
		else
			dataFromDB.add(String.format("%.2f",incomeFloat));
		return dataFromDB;
	}

	public ArrayList<String> UsageReports(ArrayList<String> arr) {
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
	}

	/**
	 * @author eliran simulate card reader that return random user id
	 */

	public ArrayList<String> simulationCardReader() {
		ArrayList<String> arr = new ArrayList<>();
		try {
			LocalDate date = LocalDate.now(); // Gets the current date
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select UserID from orders where (OrderStatus='waitingToVisit' OR OrderStatus='active') and"
					+ " VisitDate='"+date.format(formatter)+"'");
			while (rs.next()) {
				arr.add(rs.getString(1));
			}
			Random rand = new Random();
			if (arr.size() > 0) {
				String userId = arr.get(rand.nextInt(arr.size()));
				arr.clear();
				arr.add(userId);
			}
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public ArrayList<OrderToView> ReturnUserIDInTableOrdersForCardReader(ArrayList<String> arr) {
		if (arr instanceof ArrayList) {
			ArrayList<String> array = (ArrayList<String>) arr;
			if (array != null && array.get(0) != null) {// index 0 = userID
				ArrayList<OrderToView> ar = showTableOrdersCardReader(array.get(0));
				return ar;
			}
		}
		return null;
	}

	public ArrayList<OrderToView> showTableOrdersCardReader(Object id) {
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

	public void updateToFinished(ArrayList<String> arr) {
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

	public void updateToActive(Object arr) {
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
	public ArrayList<FreePlaceInPark> getFreePlace(ArrayList<String> arr) throws SQLException {
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
	public ArrayList<String> setInWaitingList(ArrayList<String> arr) throws SQLException {
		ArrayList<String> toReturn = new ArrayList<String>();
		String orderNumber = getOrderNumber();
		addToOrdersTable(arr, orderNumber, "waitingList");
		toReturn.add(orderNumber);
		return toReturn;
	}

	public ArrayList<String> countActiveOrders(String parkName) {
		String countOrders = null;
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"Select SUM(VisitorsAmountActual) From orders O Where O.OrderStatus = 'active' AND O.ParkName =" + "'"
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

	public void SubmitIncomeReport(ArrayList<String> arr) {
		arr.remove("SubmitIncomeReport");
		if (existInDBReport(arr.get(0), arr.get(1), arr.get(2), "incomereport"))
			return;
		try {// inserting new row to the table
			PreparedStatement update = conn
					.prepareStatement("INSERT INTO incomereport (year,month,parkName,totalIncome) VALUES ( ?, ?, ?,?)");
			for (int i = 0; i < ((ArrayList<String>) arr).size(); i++)
				update.setString(i + 1, ((ArrayList<String>) arr).get(i));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void SubmitUsageReport(ArrayList<String> arr) {
		arr.remove("SubmitUsageReport");
		if (existInDBReport(arr.get(0), arr.get(1), arr.get(2), "usagereport"))// arr[year,month,parkName]
			return;
		String year = arr.get(0), month = arr.get(1), parkName = arr.get(2);
		for (int i = 0; i < 3; i++)
			arr.remove(0);
		int size = arr.size() / 9;
		for (int i = 0; i < size; i++) {
			try {// inserting new row to the table
				PreparedStatement update = conn.prepareStatement(
						"INSERT INTO usagereport (year,month,day,parkName,h8,h9,h10,h11,h12,h13,h14,h15,h16)"
								+ " VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?)");
				// update.setString(i + 1, ((ArrayList<String>) arr).get(i));
				update.setString(1, year);
				update.setString(2, month);
				update.setInt(3, i + 1);
				update.setString(4, parkName);
				for (int j = 0; j < 9; j++) {
					update.setString(j + 5, arr.get(j));
				}
				for (int j = 0; j < 9; j++) {
					arr.remove(0);
				}
				update.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public boolean existInDBReport(String year, String month, String parkName, String reportName) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select * from " + reportName + " Where year=" + year + " AND month=" + month
					+ " AND parkName='" + parkName + "'");
			if (rs.next()) {// check if employee exist
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * return an array list of reports made for department manager
	 * and saved in DB for his use.
	 * 
	 * @return ArrayList<ViewReports>
	 */
	public ArrayList<ViewReports> reportsToView() {
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
			setExistUsageReports();
			rs = stmt.executeQuery("select * from existusagereports");
			while (rs.next()) {
				String year = rs.getString("year");
				String month = rs.getString("month");
				String parkName = rs.getString("parkName");
				
				PreparedStatement update = conn.prepareStatement(
						"select * from usagereport \n" + 
						"where parkName = ? and year = ? and month = ? \n" + 
						"order by 'day'");
				update.setString(1, parkName);
				update.setString(2, year);
				update.setString(3, month);
				
				ResultSet rsInQuery = update.executeQuery();
				int index = 0;				
				ArrayList<ArrayList<String>> usagePerHour = new ArrayList<ArrayList<String>>();
				ArrayList<Integer> day = new ArrayList<>();
				while (rsInQuery.next()) {
					//String year1 = rsInQuery.getString("year");
					//String month1 = rsInQuery.getString("month");
					day.add(rsInQuery.getInt("day"));	
					//String parkName1 = rsInQuery.getString("parkName");
					usagePerHour.add(new ArrayList<String>());
					for (int i = 8; i <= 16; i++) {
						usagePerHour.get(index).add(rsInQuery.getString("h" + i));
					}
					index++;
				}
				ViewReports tmp = new ViewReports(year, month, parkName, "Usage Report");
				tmp.setDataUsageReport(usagePerHour, day);
				toReturn.add(tmp);
			}
			// third go over visitor amount report table
			rs = stmt.executeQuery("Select * From visitorsreport");
			while (rs.next()) {
				String year = rs.getString("year");
				String month = rs.getString("month");
				String parkName = rs.getString("parkName");
				String totalVisitors = rs.getString("TotalVisitors");
				ArrayList<String> groupDays = new ArrayList<>();
				for (int i = 0; i < 7; i++) {
					groupDays.add(rs.getString("groupday" + i));
				}
				ArrayList<String> userDays = new ArrayList<>();
				for (int i = 0; i < 7; i++) {
					userDays.add(rs.getString("userday" + i));
				}
				ArrayList<String> memberDays = new ArrayList<>();
				for (int i = 0; i < 7; i++) {
					memberDays.add(rs.getString("memberday" + i));
				}
				ViewReports tmp = new ViewReports(year, month, parkName, "Visitors Amount Report");
				tmp.setDataVisitReport(totalVisitors, groupDays, userDays, memberDays);
				toReturn.add(tmp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return toReturn;
	}


	/**
	 * set table Exist Usage Reports- according to Usage Reports table
	 */
	public void setExistUsageReports() {
		try {
			PreparedStatement update = conn.prepareStatement("delete FROM visitorschema.existusagereports;");
			PreparedStatement update1 = conn.prepareStatement("insert into visitorschema.existusagereports(year, month, parkName)  \r\n" + 
					"select distinct year, month, parkName from visitorschema.usagereport;");
			update.execute();
			update1.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> cheakGap(ArrayList<String> arr) {

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

	public ArrayList<DurationOrder> depManDuration(ArrayList<String> arr) {
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
//				int duration = Integer.parseInt(rs.getTime("ExitTime").toString().substring(0, 2))
//						- Integer.parseInt(rs.getTime("EnterTime").toString().substring(0, 2));
				Time enterTime = rs.getTime("EnterTime");
				Time exitTime = rs.getTime("ExitTime");
				long TotalTime = (exitTime.getTime()-enterTime.getTime())/3600000;

				if (TotalTime >= Integer.parseInt(arr.get(2)) && TotalTime <= Integer.parseInt(arr.get(3))) {
					boolean flag = false;
					for (DurationOrder d : arrD)
						if (d.getType().equals(type)) {
							d.setAmount(d.getAmount() + amount);
							flag = true;
						}
			
					if (!flag)
						arrD.add(new DurationOrder(type, (int)TotalTime, amount));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arrD;
	}

	public void SubmitVisitorAmountReport(ArrayList<String> arr) {
		ArrayList<String> reportData = visitorAmountReport(arr);

		if (existInDBReport(arr.get(1), arr.get(2), arr.get(3), "visitorsreport"))
			return;
		try {// inserting new row to the table
			PreparedStatement update = conn.prepareStatement(
					"INSERT INTO visitorsreport (year,month,parkName,TotalVisitors,groupday0,groupday1,groupday2,groupday3,groupday4,groupday5,groupday6,userday0,userday1,userday2,userday3,userday4,userday5,userday6,memberday0,memberday1,memberday2,memberday3,memberday4,memberday5,memberday6)"
							+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			update.setString(1, arr.get(1));
			update.setString(2, arr.get(2));
			update.setString(3, arr.get(3));
			for (int i = 4; i < ((ArrayList<String>) reportData).size() + 4; i++)
				update.setString(i, ((ArrayList<String>) reportData).get(i - 4));
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<DayToView> checkForUsage(ArrayList<String> arr) throws SQLException {

		// ArrayList<String> parkDetails = new ArrayList<String>();
		ArrayList<Integer> parkDetails = checkCapacityAndAvarageVisitTime(arr.get(0));
		int capacity = parkDetails.get(0);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from orders Where OrderStatus='finished' AND month(visitDate) ='"
				+ arr.get(2) + "' AND year(visitDate)='" + arr.get(1) + "' AND ParkName ='" + arr.get(0)
				+ "' order by VisitDate ");
		ArrayList<OrderForUsage> orders = new ArrayList<OrderForUsage>();
		while (rs.next()) {
			java.sql.Date visitDate = rs.getDate("visitDate");
			Time enterTime = rs.getTime("EnterTime");
			Time exitTime = rs.getTime("ExitTime");
			int visitorsAmount = rs.getInt("VisitorsAmountActual");
			orders.add(new OrderForUsage(visitDate, enterTime, exitTime, visitorsAmount));
		}

		YearMonth ym = YearMonth.of(Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)));
		int lastDay = ym.lengthOfMonth();
		int amountInThisTime = 0;
		ArrayList<DayToView> toReturn = new ArrayList<DayToView>();

		for (int day = 1; day <= lastDay; day++) {
//		java.sql.Date checkDateNow =new java.sql.Date(,),)  

			LocalDate dt = ym.atDay(day);
			java.sql.Date checkDateNow = new java.sql.Date(dt.getYear() - 1900, dt.getMonth().ordinal(), day);
//			System.out.println(checkDateNow.toString());
			DayToView nowDayToView = new DayToView();
			nowDayToView.setDay(Func.fixDateString(checkDateNow.toString()));
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++) {
				Time checkTimeNow = new Time(i, 0, 0);
				for (OrderForUsage o : orders) {

					if (o.getVisitDate().equals(checkDateNow) && (o.getEnterTime().compareTo(checkTimeNow) <= 0)
							&& (o.getExitTime().compareTo(checkTimeNow) >= 0)) {// this order was in the park in this
						amountInThisTime += o.getVisitorsAmount(); // time

					}

				}

				enterToDayToView(capacity, amountInThisTime, nowDayToView, i);
				amountInThisTime = 0;

			}
			toReturn.add(nowDayToView);

		}

		return toReturn;
	}


	private void enterToDayToView(int capacity, int amountInThisTime, DayToView nowDayToView, int i) {
	
		String temp = (float)amountInThisTime*100.0/capacity>=100?"Full":String.format("%.2f",amountInThisTime*100.0/capacity)+"%";
		
		switch (i) {
		case 8:
			nowDayToView.setH1(temp);
			break;

		case 9:
			nowDayToView.setH2(temp);
			break;
		case 10:
			nowDayToView.setH3(temp);
			break;
		case 11:
			nowDayToView.setH4(temp);
			break;
		case 12:
			nowDayToView.setH5(temp);
			break;
		case 13:
			nowDayToView.setH6(temp);
			break;
		case 14:
			nowDayToView.setH7(temp);
			break;
		case 15:
			nowDayToView.setH8(temp);
			break;
		case 16:
			nowDayToView.setH9(temp);
			break;
		default:
			break;
		}
	}

	public static void insertOrders() {

		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 5; i++) {
				try {
					Random rand = new Random(System.currentTimeMillis());
					int amount = rand.nextInt(100000);
					PreparedStatement update = conn.prepareStatement(
							"INSERT INTO orders (UserID,OrderID,parkName,ExpectedEnterTime,VisitDate,VisitorsAmount,TypeOfOrder,OrderStatus,EnterTime,ExitTime,Occasional,VisitorsAmountActual,Payment,Email,EnterWaitingListDate)"
									+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					update.setString(1, (1000000 + i) + "");
					update.setString(2, (2000 + i + 10 * j) + "");
					if ((amount) % 7 <= 2)
						update.setString(3, "Tal Park");
					if ((amount) % 7 >= 3 && (amount) % 7 <= 5)
						update.setString(3, "Carmel Park");
					if ((amount) % 7 > 5)
						update.setString(3, "Jordan Park");
					if ((amount % 3) == 0) {
//						update.setTime(4, new Time(8 + (i + j) % 4 + i % 5 + 1, 0, 0));
//						update.setTime(9, new Time(8 + (i + j) % 4 + i % 5 + 1, 0, 0));
						update.setString(7, "user");
					}
					if ((amount % 3) == 1) {
						// update.setTime(4, new Time(8 + (amount) % 9, 0, 0));
						// update.setTime(9, new Time(8 + (amount) % 9, 0, 0));
						update.setString(7, "member");
					}
					// if (i % 3 == 2&&j%3==1) {
					if ((amount % 3) == 2) {
//						update.setTime(4, new Time(8 + (i + j) % 5 + ((i + j)%4 + 1) % 5, 0, 0));
//						update.setTime(9, new Time(8 + (i + j) % 5 + ((i + j)%4 + 1) % 5, 0, 0));
						update.setString(7, "group");
					}
					update.setDate(5, new java.sql.Date(121, 0, 6));
					update.setTime(4, new Time(8 + amount % 4 + amount % 5 + 1, 0, 0));
					update.setTime(9, new Time(8 + amount % 4 + amount % 5 + 1, 0, 0));
					update.setInt(6, amount % 16);
					update.setString(8, "finished");

					
					update.setTime(10, new Time(8 + amount % 4 + amount % 5 + 1 + 4, 0, 0));
					if(amount%8==0)
						update.setBoolean(11, true);
					else
						update.setBoolean(11, false);
					
					update.setInt(12, amount % 16);
					update.setFloat(13, 150.6f); 
					update.setString(14, "abc" + i + "@abc.com");
					update.setString(15, null);
					update.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
