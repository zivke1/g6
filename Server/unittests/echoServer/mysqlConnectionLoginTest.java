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
	
	@BeforeEach
	void setUp() throws Exception {
		con = mock(Connection.class);
		stmt = mock(Statement.class);
		rs = mock(ResultSet.class);
		
		
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
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("123");
		arr.add("1234");
		ArrayList<String> ret = null;
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("123");
		expected.add("avi");
		expected.add("ron");
		expected.add("parkEmployee");
		expected.add("Tal Park");
		
		try {
			when(stmt.executeQuery("select * from employee Where EmployeeNumber=" + arr.get(0).toString())).thenReturn(rs);
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
			ret=mySql.checkIfEmployee(arr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("exception throw");
		}
		assertEquals(ret, expected);	
	}
	
//	toReturn.add("employeeNotFound");
	@Test
	void checkIfEmployeeemployeeNotFoundTest()

}
