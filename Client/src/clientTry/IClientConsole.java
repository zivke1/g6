package clientTry;

import java.util.ArrayList;

//interface to break dependency from server
public interface IClientConsole {
	
	public void accept(ArrayList<String> arr);

	public void display(String message);

	public void stopConnection();

	public boolean checkConnection();

}
