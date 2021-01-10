package echoServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import echoServer.LoginTest.MockConnectionToClient;
import util.HourAmount;
import util.TypeOfOrder;
import ocsf.server.*;
class EnteranceReportTest {
	
	private static final int OPEN_TIME_INT = 8;

	private static final int CLOSE_TIME_INT = 16;
	private Time t1,t2;

	mysqlConnection mySql; 
	
	@Mock
	Connection con;
	
	@Mock
	Statement stmt;
	
	@Mock
	ResultSet rs;
	
	@BeforeEach
	void setUp() throws Exception {
		con = mock(Connection.class);
		stmt = mock(Statement.class);
		rs = mock(ResultSet.class);
		
		mySql = mysqlConnection.getInstance();
		mySql.SetConnection(con);
		when(con.createStatement()).thenReturn(stmt);
		t1=new Time (OPEN_TIME_INT,0,0);
		t2=new Time (OPEN_TIME_INT +1,0,0);
		
	}
	
	@Test
	void checkSuccsessTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add("Tal Park");
		arr.add("2021-01-10");
		arr.add(TypeOfOrder.user.toString());
		ArrayList<HourAmount> ret = null;
		ArrayList<HourAmount> expected = new ArrayList<>();
		try {
		for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++) 
			expected.add(new HourAmount(i+"", 5));
//		for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++,t1.setHours(t1.getHours()+1),t2.setHours(t2.getHours()+1) )
		 
			when(stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
					+ t1.toString() + "'" + "						 AND '" + t2.toString()
					+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
					+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';")).thenReturn(rs);
		
		when(rs.next()).thenReturn(true);
		when(rs.getInt("sum(VisitorsAmountActual)")).thenReturn(5);
		
		}catch (SQLException e) {
			fail();
		}
		
		try {
			ret=mySql.depManVisitRep(arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail();
		}
		assertEquals(ret, expected);
	}		
//		expected.add("123");
//		expected.add("avi");
//		expected.add("ron");
//		expected.add("parkEmployee");
//		expected.add("Tal Park");
		
//		try {
//			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + arr.get(0).toString())).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getString("FirstName")).thenReturn("avi");
//			when(rs.getString("LastName")).thenReturn("ron");
//			when(rs.getString("UserID")).thenReturn("123");
//			when(rs.getString("UserRole")).thenReturn("parkEmployee");
//			when(rs.getString("UserPassword")).thenReturn("1234");
//			when(rs.getString("Park")).thenReturn("Tal Park");
//			
//		} catch (SQLException e) {
//			fail("exception throw");
//		}
//		
//		try {
//			ret=mySql.checkIfEmployee(arr);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			fail("exception throw");
//		}
//		assertEquals(ret, expected);	
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	mysqlConnection connection;
//	IEnteranceReport stubEnteranceRep;
//	iConnector conn;
//	ArrayList<HourAmount> ret = null;
//	
//	EchoServer server;
//	class MockConnectionToClient implements iConnector {
//
//		
//
//		@Override
//		public void sendToClient(Object msg) throws IOException {
//			// TODO Auto-generated method stub
//			ret = (ArrayList<HourAmount>) msg;
//		}
//
//	}
//	class StubEnteranceReport implements IEnteranceReport {
//
//		@Override
//		public ArrayList<HourAmount> depManVisitRep(ArrayList<String> arr) {
//			ArrayList<HourAmount> ret = new ArrayList<>();
//			ret.add(new HourAmount("10", 5));
//			if (arr.size() > 0)
//				return ret;
//			else
//				return null;
//		}
//
//	}
//
//	@BeforeEach
//	void setUp() throws Exception {
//		stubEnteranceRep = new StubEnteranceReport();
//		server=new EchoServer(5555);
//		connection=mock(mysqlConnection.class);
//		server.setConnection(connection);
//	}
//	//wallak sharmuta
//	@Test
//	void retSuccessTest() {
//		ArrayList<String> arr = new ArrayList<>();
//		arr.add("depManVisitRep");
//		arr.add("Tal Park");
//		arr.add("2021-01-10");
//		arr.add(TypeOfOrder.user.toString());
//		
//		
//		ArrayList<HourAmount> expected =new ArrayList<>();
//		HourAmount testing = new HourAmount("10", 5);
//		expected.add(testing);
//		when(connection.depManVisitRep(arr)).thenReturn(expected);
//		
//		server.handleMessageFromClient(arr, new MockConnectionToClient());
//		assertTrue(expected.equals(ret));
//		ret=null;
//	}
//
//	@Test
//	void retFailTest() {
//		ArrayList<String> arr = new ArrayList<>();
//		assertTrue(stubEnteranceRep.depManVisitRep(arr) == null);
//	}
//	
//	@Test
//	void retParkDoenstExistTest() {
//		ArrayList<String> arr = new ArrayList<>();
//		arr.add("depManVisitRep");
//		arr.add("Tal prak");
//		arr.add("2021-01-10");
//		arr.add(TypeOfOrder.user.toString());
//		
//		ArrayList<HourAmount> expected =new ArrayList<>();
//		when(connection.depManVisitRep(arr)).thenReturn(expected);
//		
//		server.handleMessageFromClient(arr, new MockConnectionToClient());
//		assertTrue(expected.equals(ret));
//		ret=null;
//	}
//	
//	@Test
//	void retInValidDateTest() {
//		ArrayList<String> arr = new ArrayList<>();
//		arr.add("depManVisitRep");
//		arr.add("Tal Park");
//		arr.add("2021-01");
//		arr.add(TypeOfOrder.user.toString());
//		
//		ArrayList<HourAmount> expected =new ArrayList<>();
//		when(connection.depManVisitRep(arr)).thenReturn(expected);
//		
//		server.handleMessageFromClient(arr, new MockConnectionToClient());
//		assertTrue(expected.equals(ret));
//		ret=null;
//	}
//	
//	@Test
//	void retTypeOfOrderDoenstExistTest() {
//		ArrayList<String> arr = new ArrayList<>();
//		arr.add("depManVisitRep");
//		arr.add("Tal Park");
//		arr.add("2021-01-10");
//		arr.add("urser");
//		
//		ArrayList<HourAmount> expected =new ArrayList<>();
//		when(connection.depManVisitRep(arr)).thenReturn(expected);
//		
//		server.handleMessageFromClient(arr, new MockConnectionToClient());
//		assertTrue(expected.equals(ret));
//		ret=null;
//	}
	
}
