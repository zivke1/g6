package echoServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import ocsf.server.iConnector;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class LoginTest {
	ArrayList<String> ret = null;
	
	class MockConnectionToClient implements iConnector {

	

		@Override
		public void sendToClient(Object msg) throws IOException {
			// TODO Auto-generated method stub
			ret = (ArrayList<String>) msg;
		}

	}

	@Mock
	mysqlConnection connection;

	EchoServer server;

	@BeforeEach
	void setUp() throws Exception {
		connection = mock(mysqlConnection.class);
		server = new EchoServer(5555);
		server.setConnection(connection);

	}

	@Test
	void LoginEmployeeSuccsessTest() throws SQLException {

		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfEmployee");
		arr.add("1");
		arr.add("1");

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("ziv");
		expected.add("kenig");
		expected.add("ParkEmployee");
		expected.add("Tal Park");
		when(connection.checkIfEmployee(arr)).thenReturn(expected);

		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
		ret=null;
	}
	
	
	
	
	
	@Test
	void LoginEmployeeFailTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfEmployee");
		arr.add("1");
		arr.add("1");

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("2");
		expected.add("ziv");
		expected.add("kenig");
		expected.add("ParkEmployee");
		expected.add("Tal Park");
		when(connection.checkIfEmployee(arr)).thenReturn(expected);
		
		ArrayList<String> send = new ArrayList<String>();
		send.add("checkIfEmployee");
		send.add("2");
		send.add("2");
		
		server.handleMessageFromClient(send, new MockConnectionToClient());
		assertNotEquals(expected, ret);
	}
	
	
	@Test
	void checkIfIdConnectedWithMemberIdConnectedBeforeTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfIdConnectedWithMemberId");
		arr.add("1");

		ArrayList<String> expected = new ArrayList<String>();
		expected.add("connectedBefore");
		
		when(connection.checkIfIdConnectedWithMemberId(arr)).thenReturn(expected);

		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
	}
	
	@Test
	void checkIfIdConnectedWithMemberIdNotMemberTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfIdConnectedWithMemberId");
		arr.add("1");
		ArrayList<String> arrayToMoc = new ArrayList<String>();
		arrayToMoc.add("1");
		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("notMember");
		
		when(connection.checkIfIdConnectedWithMemberId(arrayToMoc)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
	}
	
	
	@Test
	void checkIfIdConnectedWithMemberIdSuccsessTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfIdConnectedWithMemberId");
		arr.add("1");

		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("Avi");
		expected.add("Ron");
		expected.add("Member");
		expected.add("10");
		
		when(connection.checkIfIdConnectedWithMemberId(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
	}
	
	
	
	@Test
	void checkIfIdConnectedWithIdConnectedBeforeTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfIdConnectedWithId");
		arr.add("1");

		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("connectedBefore");

		when(connection.checkIfIdConnectedWithId(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
	}
	
	@Test
	void checkIfIdConnectedWithIdSuccsessTest() throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("checkIfIdConnectedWithId");
		arr.add("1");

		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("Avi");
		expected.add("Ron");
		expected.add("Member");
		expected.add("10");

		when(connection.checkIfIdConnectedWithId(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertEquals(expected, ret);
	}
	
	

}
