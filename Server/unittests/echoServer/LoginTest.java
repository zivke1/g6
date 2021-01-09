package echoServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;



import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;

class LoginTest {

	class MockConnectionToClient implements IConnectionToClient{

		@Override
		public boolean isValid(String fileName) {
			return condition;
		}
		
	}
	
	@Mock
	mysqlConnection connection;
	
	EchoServer server;
	
	
	@BeforeEach
	void setUp() throws Exception {
		connection = mock(mysqlConnection.class);
		connection.connectDB();
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
		
		server.handleMessageFromClient(arr,null);
		
		
	}

}
