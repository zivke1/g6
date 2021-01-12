package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.HourAmount;
import util.TypeOfOrder;

import clientTry.ClientMain;
import clientTry.IEntranceReport;
import clientTry.VisitorReportDepartmentController;

class VisitorReportDepartmentControllerTest {

	class stubEntrance implements IEntranceReport {
		@Override
		public void extractedChat(ArrayList<String> arr) {

		}

		@Override
		public ArrayList<HourAmount> extractedHourAmountArray() {
			if (flag)
				return fiveAmoutList();
			else
				return zeroAmoutList();
		}

		ArrayList<HourAmount> zeroAmoutList() {
			ArrayList<HourAmount> ret = new ArrayList<>();
			for (int i = ClientMain.OPEN_TIME_INT; i <= ClientMain.CLOSE_TIME_INT; i++)
				ret.add(new HourAmount(i + "", 0));
			return ret;
		}

		ArrayList<HourAmount> fiveAmoutList() {
			ret = new ArrayList<>();
			for (int i = ClientMain.OPEN_TIME_INT; i <= ClientMain.CLOSE_TIME_INT; i++)
				ret.add(new HourAmount(i + "", 5));
			return ret;
		}

//		@Override
//		public void logic(String parkName, String date, String type) {
//			controller.logic(parkName, date, type);
//			
//		}
	}

	boolean flag = false;

	VisitorReportDepartmentController controller;

	stubEntrance stub;

	ArrayList<HourAmount> ret;

	ArrayList<String> arr;

	String parkName = "Tal Park", date = "2021-01-01";

	@BeforeEach
	void setUp() throws Exception {
		stub = new stubEntrance();
		controller = new VisitorReportDepartmentController(stub);
	}

	/*
	 * check: got only user data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=true flag2=false flag3=false
	 */
	@Test
	void onlyUserTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		assertTrue(controller.isFlag1() && !controller.isFlag2() && !controller.isFlag3());
	}

	/*
	 *  check: got only member data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=false flag2=true flag3=false
	 */
	@Test
	void onlyMemberTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		assertTrue(!controller.isFlag1() && controller.isFlag2() && !controller.isFlag3());
	}

	/* 
	 * check: got only group data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=false flag2=false flag3=true
	 */
	@Test
	void onlyGroupTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		assertTrue(!controller.isFlag1() && !controller.isFlag2() && controller.isFlag3());
	}

	/*
	 *  check: got user and member data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=true flag2=trueflag3=false
	 */
	@Test
	void UserAndMemberTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		assertTrue(controller.isFlag1() && controller.isFlag2() && !controller.isFlag3());
	}

	/*
	 * check: got user and group data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=true flag2=false flag3=true
	 */
	@Test
	void UserAndGroupTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		assertTrue(controller.isFlag1() && !controller.isFlag2() && controller.isFlag3());
	}

	/*
	 * check: got group and member data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=false flag2=true flag3=true
	 */
	@Test
	void MemberAndGroupTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		assertTrue(!controller.isFlag1() && controller.isFlag2() && controller.isFlag3());
	}

	/*
	 * check: got user and member and group data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=true flag2=true flag3=true
	 */
	@Test
	void UserAndMemberAndGroupTest() {
		flag = true;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		assertTrue(controller.isFlag1() && controller.isFlag2() && controller.isFlag3());
	}

	/*
	 * check: got no data
	 * 
	 * input: parkName="Tal Park" date="2021-01-01"
	 * 
	 * 
	 * output: flag1=false flag2=false flag3=false
	 */
	@Test
	void NotAnyTypeTest() {
		flag = false;
		controller.logic(parkName, date, TypeOfOrder.user.toString());
		controller.logic(parkName, date, TypeOfOrder.member.toString());
		controller.logic(parkName, date, TypeOfOrder.group.toString().toLowerCase());
		assertTrue(!controller.isFlag1() && !controller.isFlag2() && !controller.isFlag3());
	}
}
