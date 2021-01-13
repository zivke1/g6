package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clientTry.ChatClient;
import clientTry.ClientMain;
import clientTry.IClientConsole;
import clientTry.LoginController;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.INextStage;

class LoginControllerTest {
	
	private class StubClient implements IClientConsole{
		@Override
		public void accept(ArrayList<String> arr) {
			ChatClient.dataInArrayList = dataInArrayListTest;
		}
		
		@Override
		public void display(String message) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void stopConnection() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public boolean checkConnection() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private class StubNextStage implements INextStage{

		@Override
		public FXMLLoader goToNextStage(MouseEvent event) {
			check = true;
			return null;
		}

		@Override
		public FXMLLoader openPopUp() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	LoginController loginController;
	//private ArrayList<String> returnFromAccept;
	private static ArrayList<String> dataInArrayListTest;
	StubClient stub;
	JFXPanel panel;
	boolean check;
	
	@BeforeEach
	void setUp() throws Exception {
		check = false;
		panel = new JFXPanel();
		dataInArrayListTest = new ArrayList<String>(); 
		stub = new StubClient();
		ClientMain.chat= stub;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LoginController.class.getResource("../fxmlFiles/LoginP.fxml"));
		loader.load();
		loginController = loader.getController();
	}

	
	// check: employee exist, password incorrect
	// input: employee number and password 
	// expected: label txtErrPassword is visible 
	@Test
	void EmployeePasswordInCorrectTest() {
		loginController.setEnterAsEmployee(true);
		loginController.setEnterPsw("123");
		loginController.setEnterUserName("123");
		
		dataInArrayListTest.add("PaswwordIncorrect");
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getTxtErrPassword();
		assertEquals(s.isVisible(),true);
	}

	// check: success login of member 
	// input: memberID
	// expected: open next page 
	@Test
	void MemberLoginSuccessTest() {
		loginController.setEnterAsCoustumerRadioBtn(true);
		loginController.setEnterIDnumber("123");
		loginController.setNextStages(new StubNextStage());
		
		dataInArrayListTest.add("123");
		dataInArrayListTest.add("Eli");
		dataInArrayListTest.add("Ran");
		dataInArrayListTest.add("member");
		dataInArrayListTest.add("6");
		
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		
		assertTrue(check);
	}
	

	// check: employee enter - error fields empty
	// input: employee number and password empty 
	// expected: label TxtErrAllFieldsReq is visible 
	@Test
	void FieldsEmptyTest() {
		loginController.setEnterAsEmployee(true);
		loginController.setEnterPsw("");
		loginController.setEnterUserName("");
		
		
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getTxtErrAllFieldsReq();
		assertEquals(s.isVisible(),true);
	}
	
	// check: employee already connected
	// input: employee number and password 
	// expected: label LogInBeforeLabel is visible 
	@Test
	void EmployeeConnectedTest() {
		loginController.setEnterAsEmployee(true);
		loginController.setEnterPsw("111");
		loginController.setEnterUserName("111");
		
		dataInArrayListTest.add("connectedBefore");
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		
		Label s = loginController.getLogInBeforeLabel();
		assertEquals(s.isVisible(),true);
	}
	
	// check: employee do not exist
	// input: employee number and password 
	// expected: label TxtErrUserName is visible 
	@Test
	void EmployeeDoNotExistTest() {
		loginController.setEnterAsEmployee(true);
		loginController.setEnterPsw("111");
		loginController.setEnterUserName("111");
		
		dataInArrayListTest.add("employeeNotFound");
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		
		Label s = loginController.getTxtErrUserName();
		assertEquals(s.isVisible(),true);
	}
	

	// check: success login of employee 
	// input: employee number and password
	// expected: open home page -> check is true
	@Test
	void EmployeeLoginSuccessTest() {
		loginController.setEnterAsEmployee(true);
		loginController.setEnterPsw("111");
		loginController.setEnterUserName("111");
		loginController.setNextStages(new StubNextStage());
		
		dataInArrayListTest.add("123");
		dataInArrayListTest.add("Eli");
		dataInArrayListTest.add("Ran");
		dataInArrayListTest.add("ParkManager");
		dataInArrayListTest.add("Tal Park");
		
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		
		assertTrue(check);
	}
	
	// check: customer didn't enter ID 
	// input: empty fields
	// expected: label TxtErrUserName1 is visible 
	@Test
	void CustomerEmptyFieldTest() {
		loginController.setEnterAsCoustumerRadioBtn(true);
		loginController.setEnterIDnumber("");
		loginController.setEnterMemberID("");
				
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getTxtErrAllFieldsReq1();
		assertEquals(s.isVisible(),true);
	}
	
	// check: radio button wasn't selected
	// input: radio button isn't selected
	// expected: label NoSelected is visible 
	@Test
	void RadioBtnNotSelectedTest() {
		loginController.setEnterAsCoustumerRadioBtn(false);
		loginController.setEnterAsEmployee(false);
				
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getNoSelected();
		assertEquals(s.isVisible(),true);
	}
	
	// check: customer already logged in
	// input: userID
	// expected: label IDError is visible 
	@Test
	void CustomerAlreadyLoggedInTest() {
		loginController.setEnterAsCoustumerRadioBtn(true);
		loginController.setEnterIDnumber("111");
		
		dataInArrayListTest.add("notValidUserID");
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getIDError();
		assertEquals(s.isVisible(),true);
	}
	
	// check: member Id isn't valid
	// input: memberID
	// expected: label IDError is visible 
	@Test
	void MemberIDNotValidTest() {
		loginController.setEnterAsCoustumerRadioBtn(true);
		loginController.setEnterMemberID("1aa1");
		
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		
		Label s = loginController.getMemberNotNumbers();
		assertEquals(s.isVisible(),true);
	}	
	
	// check: member Id do not exist in DB
	// input: memberID
	// expected: label DontFindMemberShipIDLabel is visible 
	@Test
	void MemberIdDoNotExistTest() {
		loginController.setEnterAsCoustumerRadioBtn(true);
		loginController.setEnterMemberID("111");
		
		dataInArrayListTest.add("notMember");
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			fail("Exception");
		}
		Label s = loginController.getDontFindMemberShipIDLabel();
		assertEquals(s.isVisible(),true);
	}	


}

