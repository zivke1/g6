package client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clientTry.ChatClient;
import clientTry.IClientMain;
import clientTry.LoginController;

class LoginControllerTest {
	
	LoginController loginController;
	private String userID;
	private String memberID;
	private String EmployeeNumberID;
	private String password;
	private ArrayList<String> returnFromAccept;
	private static ArrayList<String> dataInArrayListTest;
	
	@BeforeEach
	void setUp() throws Exception {
		
		userID = "111";
		memberID = "222";
		EmployeeNumberID = "123";
		password = "123";
	}

	private class StubClientMain implements IClientMain{

		@Override
		public void accept(ArrayList<String> arr) {
			returnFromAccept = arr;
		}
		
	}
	
	// check: memberID do not exist in DB
	// input: 
	// expected: 
	@Test
	void test() {
	//	loginController = new LoginController(new StubClientMain());
//		if (returnFromAccept.contains("checkIfIdConnectedWithMemberId")) {
//			assertFalse(returnFromAccept.get(1).equals(memberID));
//		}
	}

}

