package clientTry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.input.MouseEvent;

class LoginControllerTest {
	LoginController loginController;
	MouseEvent event;
	
//	@Mock
	
	
	@BeforeEach
	void setUp() throws Exception {
		String userID = "111";
		String memberID = "222";
		String employeeUserName = "123";
		String employeePassword = "123";
		loginController = new LoginController();		
	}

	@Test
	void test() {
		try {
			loginController.finishOrderClicked(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
