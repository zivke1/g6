package echoServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import echoServer.LoginTest.MockConnectionToClient;
import util.HourAmount;
import util.TypeOfOrder;
import ocsf.server.*;
class EnteranceReportTest {
	mysqlConnection connection;
	IEnteranceReport stubEnteranceRep;
	iConnector conn;
	ArrayList<HourAmount> ret = null;
	
	EchoServer server;
	class MockConnectionToClient implements iConnector {

		

		@Override
		public void sendToClient(Object msg) throws IOException {
			// TODO Auto-generated method stub
			ret = (ArrayList<HourAmount>) msg;
		}

	}
	class StubEnteranceReport implements IEnteranceReport {

		@Override
		public ArrayList<HourAmount> depManVisitRep(ArrayList<String> arr) {
			ArrayList<HourAmount> ret = new ArrayList<>();
			ret.add(new HourAmount("10", 5));
			if (arr.size() > 0)
				return ret;
			else
				return null;
		}

	}

	@BeforeEach
	void setUp() throws Exception {
		stubEnteranceRep = new StubEnteranceReport();
		server=new EchoServer(5555);
		connection=mock(mysqlConnection.class);
		server.setConnection(connection);
	}
	//wallak sharmuta
	@Test
	void retSuccessTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add("Tal Park");
		arr.add("2021-01-10");
		arr.add(TypeOfOrder.user.toString());
		
		
		ArrayList<HourAmount> expected =new ArrayList<>();
		HourAmount testing = new HourAmount("10", 5);
		expected.add(testing);
		when(connection.depManVisitRep(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertTrue(expected.equals(ret));
		ret=null;
	}

	@Test
	void retFailTest() {
		ArrayList<String> arr = new ArrayList<>();
		assertTrue(stubEnteranceRep.depManVisitRep(arr) == null);
	}
	
	@Test
	void retParkDoenstExistTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add("Tal prak");
		arr.add("2021-01-10");
		arr.add(TypeOfOrder.user.toString());
		
		ArrayList<HourAmount> expected =new ArrayList<>();
		when(connection.depManVisitRep(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertTrue(expected.equals(ret));
		ret=null;
	}
	
	@Test
	void retInValidDateTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add("Tal Park");
		arr.add("2021-01");
		arr.add(TypeOfOrder.user.toString());
		
		ArrayList<HourAmount> expected =new ArrayList<>();
		when(connection.depManVisitRep(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertTrue(expected.equals(ret));
		ret=null;
	}
	
	@Test
	void retTypeOfOrderDoenstExistTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("depManVisitRep");
		arr.add("Tal Park");
		arr.add("2021-01-10");
		arr.add("urser");
		
		ArrayList<HourAmount> expected =new ArrayList<>();
		when(connection.depManVisitRep(arr)).thenReturn(expected);
		
		server.handleMessageFromClient(arr, new MockConnectionToClient());
		assertTrue(expected.equals(ret));
		ret=null;
	}
}
