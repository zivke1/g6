package echoServer;

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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/visitorschema?serverTimezone=IST","root","Aa123456");
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
	
	public static ArrayList<String> showTable(Object id){
		ArrayList<String> dataFromDB=new ArrayList<>();
		try {
			String firstName = null,lastName = null,ID = null,email = null,phoneNum = null;
			Statement stmt=conn.createStatement();
			String tmpId=((ArrayList<String>)id).get(0);
			ResultSet rs=stmt.executeQuery("select * from visitor Where ID="+tmpId);
			while(rs.next())
			{
				 firstName=rs.getString("FirstName");
				 lastName=rs.getString("LastName");
				 ID=rs.getString("ID");
				 email=rs.getString("Email");
				 phoneNum=rs.getString("PhoneNumber");
			}
			dataFromDB.add(firstName);
			dataFromDB.add(lastName);
			dataFromDB.add(ID);
			dataFromDB.add(email);
			dataFromDB.add(phoneNum);
		} catch (SQLException e) {	e.printStackTrace();}
		return dataFromDB;		
	}
	
	public static void updateTable(Object arr)//arr={the new value u want,its ID,the column we want to change}
	{
		try {
			ArrayList<String> aL=(ArrayList<String>)arr;
			PreparedStatement update = conn.prepareStatement("UPDATE visitor SET "+aL.get(2)+"=? WHERE ID=?");
			for(int i=0;i<aL.size()-1;i++)//we do -1 because we dont relate to the column we want to change
				update.setString(i+1,aL.get(i));
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
	
	public static String CheckID(Object id)
	{
		if(id!=null)
		{
			ArrayList<String> arr=showTable(id);
			if(((ArrayList<String>)id).get(0).equals(arr.get(2)))
				return "True";
		}
		return "False";
	}

	public static ArrayList<String> FetchParkDetails(ArrayList<String> arr) {
		ArrayList<String> dataFromDB=new ArrayList<>();
		try {
			String Capacity= null, TimeOfAvergeVisit = null,MaxAmountOfOrders = null, ManagerName = null, GapVisitors= null;
			Statement stmt=conn.createStatement();
			String parkName="'"+arr.get(0)+"'";
			System.out.println(parkName);
			System.out.println("3");
			
			ResultSet rs=stmt.executeQuery("select * from park Where ParkName="+parkName);
			System.out.println("4");
			while(rs.next())
			{
				Capacity=rs.getString("Capacity");
				TimeOfAvergeVisit=rs.getString("TimeOfAverageVisit");
				MaxAmountOfOrders=rs.getString("MaxAmountOfOrders");
				ManagerName=rs.getString("ManagerName");
				GapVisitors=rs.getString("GapVisitors");
			}
			System.out.println("5");
			dataFromDB.add("FetchParkDetails");
			dataFromDB.add(Capacity);
			dataFromDB.add(TimeOfAvergeVisit);
			dataFromDB.add(MaxAmountOfOrders);
			dataFromDB.add(ManagerName);
			dataFromDB.add(GapVisitors);
			System.out.println(dataFromDB);
		} catch (SQLException e) {	e.printStackTrace();}
		return dataFromDB;
		
	}
			
}


