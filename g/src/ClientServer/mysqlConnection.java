package ClientServer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.jdbc.SuspendableXAConnection;


public class mysqlConnection {
	static  Connection conn;
	public static void connectDB() 
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/visitorSchema?serverTimezone=IST","root","Aa123456");
            System.out.println("SQL connection succeed");
//            updateFlight(conn);
//            updateParisFlights(conn);
//            manually_update(conn);
//            count_delayed_flights(conn);
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	public static void insertTable(Object arr){
		try {//inserting new row to the table
			PreparedStatement update = conn.prepareStatement("INSERT INTO visitor (FirstName,LastName,ID,Email,PhoneNumber) VALUES (?, ?, ?, ?,?)");
			for(int i=0;i<((ArrayList<String>)arr).size();i++)
				update.setString(i+1,((ArrayList<String>)arr).get(i));
			update.executeUpdate();
		} catch (SQLException e) {	e.printStackTrace();}
	}
	
	public static void updateTable(Object arr,String col)//arr={the new value u want,its ID},col=the column we want to change
	{
		try {
			PreparedStatement update = conn.prepareStatement("UPDATE visitor SET "+col+"=? WHERE ID=?");
			for(int i=0;i<((ArrayList<String>)arr).size();i++)
				update.setString(i+1,((ArrayList<String>)arr).get(i));
			update.executeUpdate();
		} catch (SQLException e) {	e.printStackTrace();}
	}
	
	public static void deleteRowTable(String id)
	{//deleting a row in the table
		try {
			PreparedStatement update = conn.prepareStatement("DELETE FROM visitor WHERE ID="+id);
			update.executeUpdate();
		} catch (SQLException e) {	e.printStackTrace();}
	}
			
//	public static void createTableflights(Connection conn){
//		Statement stmt;
//		try {
//			stmt = conn.createStatement();
//			stmt.executeUpdate("CREATE TABLE flights(Scheduled TIME,Flight VARCHAR(6), FromWhere VARCHAR(20), Delay  VARCHAR(20),Terminal INT)");
//			stmt.executeUpdate("load data local infile \"arrived_flights.txt\" into table flights");
//		} catch (SQLException e) {	e.printStackTrace();}
//	}
//	
//	public static void updateFlight(Connection conn){
//		Statement stmt;
//		try {
//			stmt = conn.createStatement();
//			stmt.executeUpdate("UPDATE flights SET delay=\"Expected 18:00\" WHERE flight=\"KU101\";");
//			//stmt.execute("SELECT * FROM text.flights WHERE Flight ='KU101' ;");
//			
//		} catch (SQLException e) {	e.printStackTrace();}
//	}
//	
//	public static void updateParisFlights(Connection conn){
//		Statement stmt;
//		try {
//			stmt = conn.createStatement();
//			stmt.executeUpdate("UPDATE flights SET scheduled = \"00:00:00\" WHERE flights.fromWhere = \"Paris\" AND flights.scheduled < \"15:00:00\";");
//		} catch (SQLException e) {	e.printStackTrace();}
//	}
//	
//	public static void manually_update(Connection conn)
//	{
//		Scanner s= new Scanner(System.in);
//		try
//		{
//			PreparedStatement flightUpdate = conn.prepareStatement("UPDATE flights SET scheduled=? WHERE flight=?");
//			System.out.println("Enter The Flight You Want To Update:\n");
//			String select_flight = s.nextLine();
//			System.out.println("Enter Arrival Time:\n");
//			String arival_time = s.nextLine();
//			flightUpdate.setString(1,arival_time );
//			flightUpdate.setString(2, select_flight);
//			flightUpdate.executeUpdate();
//			System.out.println("success");
//		}
//		catch (SQLException e) {e.printStackTrace();}
//	}
//	
//	public static void count_delayed_flights(Connection conn)
//	{
//		Scanner s= new Scanner(System.in);
//		try
//		{
//			PreparedStatement delay_count = conn.prepareStatement("SELECT COUNT(*) FROM flights WHERE TIMESTAMPDIFF(MINUTE, scheduled, TIME(RIGHT(flights.delay, 5))) >? AND delay != \"Cancelled\";");
//			System.out.println("Enter The Delay:\n");
//			String delay = s.nextLine();
//			delay_count.setString(1,delay);
//			ResultSet rs = 	delay_count.executeQuery();
//	 		while(rs.next())
//	 		{
//				 System.out.println(rs.getString(1));
//			} 
//			rs.close();
//			System.out.println("The change was made successfully");
//		}
//		catch (SQLException e) {e.printStackTrace();}
//	}
}


