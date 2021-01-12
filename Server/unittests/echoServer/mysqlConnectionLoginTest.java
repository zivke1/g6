package echoServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.internal.runners.statements.Fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;


class mysqlConnectionLoginTest {

	mysqlConnection mySql; 
	
	
	@Mock
	Connection con;
	
	@Mock
	Statement stmt;
	
	@Mock
	ResultSet rs;
	
	ArrayList<String> empUserIdAndPasStrings = new ArrayList<String>();
	ArrayList<String> checkForId = new ArrayList<String>();
	
	
	@BeforeEach
	void setUp() throws Exception {
		con = mock(Connection.class);
		stmt = mock(Statement.class);
		rs = mock(ResultSet.class);
		empUserIdAndPasStrings.add("123");
		empUserIdAndPasStrings.add("1234");
		checkForId.add("123456");
		mySql = mysqlConnection.getInstance();
		mySql.SetConnection(con);
		when(con.createStatement()).thenReturn(stmt);
		
	}

	/*employee success to login
	 * input 
	 *	 arr.add("123");
	 *	 arr.add("1234");
	 * 
	 * output arrayList
	 * 		expected.add("123");
	 *		expected.add("avi");
	 *		expected.add("ron");
	 *		expected.add("parkEmployee");
	 *		expected.add("Tal Park");
	 * */
	
	
	@Test
	void checkIfEmployeeSuccsessTest()  {
		
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("123");
		expected.add("avi");
		expected.add("ron");
		expected.add("parkEmployee");
		expected.add("Tal Park");
		
		try {
			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + empUserIdAndPasStrings.get(0).toString())).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when(rs.getString("UserID")).thenReturn("123");
			when(rs.getString("UserRole")).thenReturn("parkEmployee");
			when(rs.getString("UserPassword")).thenReturn("1234");
			when(rs.getString("Park")).thenReturn("Tal Park");
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			ret=mySql.checkIfEmployee(empUserIdAndPasStrings);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}

	
	/*
	 * get employee id that not exist in the DB
	 * input 
	 * 	 arr.add("123");
	 *	 arr.add("1234");
	 * 
	 * 
	 * output
	 * expected.add("employeeNotFound");
	 * */
	
	
	@Test
	void checkIfEmployeeemployeeNotFoundTest() {
		ArrayList<String> actual= new ArrayList<String>();
		ArrayList<String> expected= new ArrayList<String>();
		expected.add("employeeNotFound");
		
		try {
			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + empUserIdAndPasStrings.get(0).toString())).thenReturn(rs);
			when(rs.next()).thenReturn(false);
			actual = mySql.checkIfEmployee(empUserIdAndPasStrings);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		
		assertEquals(actual, expected);
			
	}
	
	
	/*
	 * get employee id that not exist in the DB
	 * input 
	 * 	 arr.add("123");
	 *	 arr.add("1234");
	 *
	 * output
	 * 		expected.add("connectedBefore");
	 * */
	
	@Test
	void checkIfEmployeeConncetedBeforeTest()  {
		
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("connectedBefore");

		
		try {
			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + empUserIdAndPasStrings.get(0).toString())).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when(rs.getString("UserID")).thenReturn("123");
			when(rs.getString("UserRole")).thenReturn("parkEmployee");
			when(rs.getString("UserPassword")).thenReturn("1234");
			when(rs.getString("Park")).thenReturn("Tal Park");
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			mySql.checkIfEmployee(empUserIdAndPasStrings);
			ret=mySql.checkIfEmployee(empUserIdAndPasStrings);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	/*employee success to login
	 * input 
	 *	 arr.add("123");
	 *	 arr.add("1234");
	 * 
	 * output arrayList
	 * 		expected.add("123");
	 *		expected.add("avi");
	 *		expected.add("ron");
	 *		expected.add("parkEmployee");
	 *		expected.add("Tal Park");
	 * */
	
	
	@Test
	void checkIfIncoorectPasswordTest()  {
		
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("PaswwordIncorrect");

		
		try {
			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + empUserIdAndPasStrings.get(0).toString())).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when(rs.getString("UserID")).thenReturn("123");
			when(rs.getString("UserRole")).thenReturn("parkEmployee");
			when(rs.getString("UserPassword")).thenReturn("12345");
			when(rs.getString("Park")).thenReturn("Tal Park");
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			ret=mySql.checkIfEmployee(empUserIdAndPasStrings);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	
	
	/*member success to login
	 * input 
	 *	checkForId.add("123456");
	 *
	 * output 
	 *  arrayList
	 *	expected.add("123456");
	 *	expected.add("avi");
	 *	expected.add("ron");
	 *	expected.add("member");
	 *	expected.add("10");
	 * */
	@Test
	void checkIfIdConnectedWithIdMemberTest()  {
		
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("123456");
		expected.add("avi");
		expected.add("ron");
		expected.add("member");
		expected.add("10");

		
		try {
			when(stmt.executeQuery("select * from members Where ID=" + checkForId.get(0))).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when( rs.getString("MemberOrGuide")).thenReturn("member");
			when(rs.getInt("numberOfPepole")).thenReturn(10);

			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			ret=mySql.checkIfIdConnectedWithId(checkForId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	/*user success to login
	 * input 
	 *		send.add("1234567");
	 *
	 * output 
	 *  arrayList
	 *	expected.add("1234567");
	 *	expected.add("user");
	 * */
	
	@Test
	void checkIfIdConnectedWithIdUserTest()  {
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("1234567");
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1234567");
		expected.add("user");


		
		try {
			when(stmt.executeQuery("select * from members Where ID=" + send.get(0))).thenReturn(rs);
			when(rs.next()).thenReturn(false);


			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			ret=mySql.checkIfIdConnectedWithId(send);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	
	/*login twice 
	 * input
	 * 		send.add("12345678");
	 * 
	 * output
	 * 		expected.add("connectedBefore");
	 * */
	@Test
	void checkIfIdConnectedWithIdConnectedBeforeTest()  {
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("12345678");
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("connectedBefore");



		
		try {
			when(stmt.executeQuery("select * from members Where ID=" + send.get(0))).thenReturn(rs);
			when(rs.next()).thenReturn(false);


			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			mySql.checkIfIdConnectedWithId(send);
			ret=mySql.checkIfIdConnectedWithId(send);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	

	
	/*checkIfIdConnectedWithMemberId
	 * input
	 * 		send.add("123456789");
	 * 
	 * output
	 * 		expected.add("notMember");
	 * */
	@Test
	void checkIfIdConnectedWithMemberIdNotMemberTest()  {
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("123456789");
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("notMember");



		
		try {
			when(stmt.executeQuery("select * from members Where memberID=" + send.get(0))).thenThrow(new SQLSyntaxErrorException());
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
		
			ret=mySql.checkIfIdConnectedWithMemberId(send);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	/*send member id and connect him
	 * 
	 * checkIfIdConnectedWithMemberId
	 * input
	 * 		send.add("111");
	 * 
	 * output
	 * 
	 * 		expected.add("111");
	 *		expected.add("avi");
	 *		expected.add("ron");
	 *		expected.add("member");
	 *		expected.add("10");
	 * */
	@Test
	void checkIfIdConnectedWithMemberIdTest()  {
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("111");
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("111");
		expected.add("avi");
		expected.add("ron");
		expected.add("member");
		expected.add("10");



		
		try {
			when(stmt.executeQuery("select * from members Where memberID=" + send.get(0))).thenReturn(rs);
			
			when(rs.next()).thenReturn(true);
			when(rs.getString("ID")).thenReturn("111");
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when( rs.getString("MemberOrGuide")).thenReturn("member");
			when(rs.getInt("numberOfPepole")).thenReturn(10);
			
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
		
			ret=mySql.checkIfIdConnectedWithMemberId(send);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	
	/*
	 * try to connect twice with the same member id
	 * 
	 * input
	 * 	send.add("222");
	 * 
	 * output
	 *	 expected.add("connectedBefore");
	 * 
	 * */
	@Test
	
	void checkIfIdConnectedWithMemberIdConnectedBeforeTest()  {
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("222");
		
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("connectedBefore");




		
		try {
			when(stmt.executeQuery("select * from members Where memberID=" + send.get(0))).thenReturn(rs);
			
			when(rs.next()).thenReturn(true);
			when(rs.getString("ID")).thenReturn("111");
			when(rs.getString("FirstName")).thenReturn("avi");
			when(rs.getString("LastName")).thenReturn("ron");
			when( rs.getString("MemberOrGuide")).thenReturn("member");
			when(rs.getInt("numberOfPepole")).thenReturn(10);
			
			
		} catch (SQLException e) {
			fail("exception throw");
		}
		
		try {
			mySql.checkIfIdConnectedWithMemberId(send);
			ret=mySql.checkIfIdConnectedWithMemberId(send);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
	
	

}
