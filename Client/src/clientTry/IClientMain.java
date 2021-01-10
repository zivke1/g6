package clientTry;

import java.util.ArrayList;

//interface to break dependency from server
public interface IClientMain {
	
	//method to simulate send and receive data from server
	void accept(ArrayList<String> arr);
}
