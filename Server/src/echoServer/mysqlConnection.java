
package echoServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import com.mysql.cj.jdbc.SuspendableXAConnection;

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

	public static ArrayList<String> showTable(Object id) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		try {// inserting new row to the table
			String firstName = null, lastName = null, ID = null, email = null, phoneNum = null;
			Statement stmt = conn.createStatement();
			String tmpId = ((ArrayList<String>) id).get(0);
			ResultSet rs = stmt.executeQuery("select * from visitor Where ID=" + tmpId);
			while (rs.next()) {
				firstName = rs.getString("FirstName");
				lastName = rs.getString("LastName");
				ID = rs.getString("ID");
				email = rs.getString("Email");
				phoneNum = rs.getString("PhoneNumber");
			}
			dataFromDB.add(firstName);
			dataFromDB.add(lastName);
			dataFromDB.add(ID);
			dataFromDB.add(email);
			dataFromDB.add(phoneNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dataFromDB;
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

	public static String CheckID(Object id) {
		if (id != null) {
			ArrayList<String> arr = showTable(id);
			if (((ArrayList<String>) id).get(0).equals(arr.get(2)))
				return "True";
		}
		return "False";
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
			boolean flagExists = true;
			while (flagExists) {
				try {

					memberID = rand.nextInt(899999);
					memberID += 100000;
					String ID = "";
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from visitor Where memberID=" + memberID);
					while (rs.next()) {
						ID = rs.getString("ID");
					}
				} catch (SQLException e) {
					flagExists = false;
					update.setString(8, "" + memberID);
				}

			}

			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Exists";
		}

		return memberID + "";
	}

	private static boolean insertToUsers(String id)// adding new user
	{
		try {
			PreparedStatement update = conn.prepareStatement("INSERT INTO useres (UserID,Connect) VALUES (?, ?)");
			update.setString(1, id);
			update.setString(2, null);
			update.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
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

	public static void closeAndSetIdNull(ArrayList<String> arr) throws SQLException {
		String id = arr.get(0);
		m_connectedID.remove(id);

	}

	public static ArrayList<String> visitorAmountReport(ArrayList<String> arr) {
		ArrayList<String> dataFromDB = new ArrayList<>();
		String year = arr.get(1), month = arr.get(2), amountOfVisitors = null, amountOfPersonal = null,
				amountOfGroup = null, amountOfMember = null;
		String parkName="'"+arr.get(3)+"'";
		try {
			ResultSet rs = null;
			Statement stmt = conn.createStatement();
			if (month.equals("02")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-28' AND OrderStatus='finished' AND ParkName="+parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-31' AND OrderStatus='finished' AND ParkName="+parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-30') AND OrderStatus='finished' AND ParkName="+parkName);
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
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-28' AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName="+parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-31' AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName="+parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-30') AND OrderStatus='finished' AND TypeOfOrder='user' AND ParkName="+parkName);
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
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-28' AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName="+parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-31' AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName="+parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-30') AND OrderStatus='finished' AND TypeOfOrder='member' AND ParkName="+parkName);
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
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-28' AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName="+parkName);
			}
			if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07")
					|| month.equals("08") || month.equals("10") || month.equals("12")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-31' AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName="+parkName);
			}
			if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
				rs = stmt.executeQuery("select SUM(VisitorsAmountActual) from orders" + " Where (VisitDate BETWEEN '" + year
						+ "-" + month + "-01' AND '" + year + "-" + month + "-30') AND OrderStatus='finished' AND TypeOfOrder='group' AND ParkName="+parkName);
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
}
