package echoServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.mysql.cj.jdbc.SuspendableXAConnection;

public class mysqlConnection {
	static Connection conn;

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
		String id, firstName, lastName, role, connected, password;
		connected = "true";
		ArrayList<String> toReturn = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from employee Where UserID=" + arr.get(0));
		if (rs.next()) {// check if employee exist
			firstName = rs.getString("FirstName");
			lastName = rs.getString("LastName");
			id = rs.getString("UserID");
			role = rs.getString("UserRole");
			password = rs.getString("UserPassword");
		} else {
			toReturn.add("employeeNotFound");
			return toReturn;
		}
		if (password.equals(arr.get(1))) {// the password right
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from useres Where UserID=" + id);
			if (rs.next()) // check if employee exist
				connected = rs.getString("Connect");
			if (connected == null) {
				toReturn.add(id);
				toReturn.add(firstName);
				toReturn.add(lastName);
				toReturn.add(role);
				PreparedStatement update = conn.prepareStatement("UPDATE useres SET Connect = true WHERE UserID=?");
				update.setString(1, id);
				update.executeUpdate();
				return toReturn;

			}
		} else {// the user already connected
			toReturn.add("false");
			return toReturn;
		}
		return toReturn;

	}

	public static boolean RegisterMember(ArrayList<String> arr) {
		try {// inserting new row to the table
			Random rand = new Random();
			int memberID = 0;
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
			return false;
		}
		if (!insertToUsers(arr.get(2)))
			return false;

		return true;
	}

	private static boolean insertToUsers(String id)// adding new user
	{
		try {
			PreparedStatement update = conn.prepareStatement("INSERT INTO useres (UserID,Connect) VALUES (?, ?)");
			update.setString(1, id);
			update.setString(2, null);
			update.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
