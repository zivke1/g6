package echoServer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.HourAmount;
import util.TypeOfOrder;

class EnteranceReportTest {

	IEnteranceReport stubEnteranceRep;

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
	}

	@Test
	void retSuccessTest() {
		HourAmount excpected = new HourAmount("10", 5);
		ArrayList<String> arr = new ArrayList<>();
		arr.add("test");
		HourAmount res=(stubEnteranceRep.depManVisitRep(arr)).get(0);
		assertTrue(excpected.getHour().equals(res.getHour()));
		assertTrue(excpected.getAmount()==res.getAmount());
	}

	@Test
	void retFailTest() {
		ArrayList<String> arr = new ArrayList<>();
		assertTrue(stubEnteranceRep.depManVisitRep(arr) == null);
	}

}
