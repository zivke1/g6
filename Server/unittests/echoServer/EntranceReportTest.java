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

class EntranceReportTest {

	private static final int OPEN_TIME_INT = 8;

	private static final int CLOSE_TIME_INT = 16;
	private Time t1, t2;

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
		t1 = new Time(OPEN_TIME_INT, 0, 0);
		t2 = new Time(t1.getHours(), 59, 59);

	}

	// check: success filling the arrayList according to the query
	// input: arrayList with park name, date, and type of order
	// expected: an hourAmount(String hour,int amount) arrayList full with all
	// the hours and 5 for the amount in every hour
	@Test
	void checkGetDataSuccsessTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Tal Park");
		Date d = new Date(121, 0, 10);
		arr.add(d.toString());
		arr.add(TypeOfOrder.user.toString());

		ArrayList<HourAmount> ret = null;
		ArrayList<HourAmount> expected = new ArrayList<>();
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++)
				expected.add(new HourAmount(i + "", 5));
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1.setHours(t1.getHours() + 1), t2
					.setHours(t2.getHours() + 1)) {
				when(stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';"))
								.thenReturn(rs);
				when(rs.next()).thenReturn(true);
				when(rs.getInt("sum(VisitorsAmountActual)")).thenReturn(5);
			}
		} catch (SQLException e) {
			fail();
		}

		try {
			ret = mySql.depManVisitRep(arr);
		} catch (SQLException e) {
			fail();
		}
		assertEquals(ret, expected);
	}

	// check: fail to fill the hourAmount array list cause the park name does not
	// exist
	// input: arrayList with wrong park name, date, and type of order
	// expected: the ret arrayList is still null
	@Test
	void checkGetDataParkNameDoesntExistTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Tal Prak");
		Date d = new Date(121, 0, 10);
		arr.add(d.toString());
		arr.add(TypeOfOrder.user.toString());
		ArrayList<HourAmount> ret = null;
		ArrayList<HourAmount> expected = new ArrayList<>();
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++)
				expected.add(new HourAmount(i + "", 5));
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1.setHours(t1.getHours() + 1), t2
					.setHours(t2.getHours() + 1)) {
				when(stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';"))
								.thenReturn(null);

			}
		} catch (SQLException e) {
			fail();
		}

		try {
			try {
				ret = mySql.depManVisitRep(arr);
				assertTrue(false);
			} catch (NullPointerException e) {
				assertTrue(true);
			}
		} catch (SQLException e) {
			fail();
		}
		assertEquals(ret, null);
	}

	// check: fail to fill the hourAmount array list cause the date does not exist
	// input: arrayList with park name, bad date, and type of order
	// expected: the ret arrayList is still null
	@Test
	void checkGetDataInvalidDateTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Tal Park");
		Date d = new Date(121, 0, 10);
		arr.add(d.toString() + "4112");
		arr.add(TypeOfOrder.user.toString());
		ArrayList<HourAmount> ret = null;
		ArrayList<HourAmount> expected = new ArrayList<>();
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++)
				expected.add(new HourAmount(i + "", 5));
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1.setHours(t1.getHours() + 1), t2
					.setHours(t2.getHours() + 1)) {
				when(stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';"))
								.thenReturn(null);

			}
		} catch (SQLException e) {
			fail();
		}

		try {
			try {
				ret = mySql.depManVisitRep(arr);
				assertTrue(false);
			} catch (NullPointerException e) {
				assertTrue(true);
			}
		} catch (SQLException e) {
			fail();
		}
		assertEquals(ret, null);
	}

	// check: fail to fill the hourAmount array list cause the type of order does
	// not exist
	// input: arrayList with park name, date, and wrong type of order
	// expected: the ret arrayList is still null
	@Test
	void checkGetDataInvalidTypeOfOrderTest() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("Tal Park");
		Date d = new Date(121, 0, 10);
		arr.add(d.toString());
		arr.add(TypeOfOrder.user.toString() + "aaa");
		ArrayList<HourAmount> ret = null;
		ArrayList<HourAmount> expected = new ArrayList<>();
		try {
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++)
				expected.add(new HourAmount(i + "", 5));
			for (int i = OPEN_TIME_INT; i <= CLOSE_TIME_INT; i++, t1.setHours(t1.getHours() + 1), t2
					.setHours(t2.getHours() + 1)) {
				when(stmt.executeQuery("select sum(VisitorsAmountActual) from orders Where EnterTime BETWEEN '"
						+ t1.toString() + "'" + "						 AND '" + t2.toString()
						+ "' AND OrderStatus='finished' AND TypeOfOrder='" + arr.get(2) + "' AND ParkName='"
						+ arr.get(0) + "'" + "						 AND VisitDate ='" + arr.get(1) + "';"))
								.thenReturn(null);

			}
		} catch (SQLException e) {
			fail();
		}

		try {
			try {
				ret = mySql.depManVisitRep(arr);
				assertTrue(false);
			} catch (NullPointerException e) {
				assertTrue(true);
			}
		} catch (SQLException e) {
			fail();
		}
		assertEquals(ret, null);
	}

}
