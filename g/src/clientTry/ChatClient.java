// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package clientTry;

import ocsf.client.*;
import clientTry.controllerTry;
import common.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
	ClientConsole m_ClientConsole;
	public static ArrayList<String> dataInArrayList = new ArrayList<String>();
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
 * @throws Exception 
   */
  public void handleMessageFromServer(Object msg)
  {
	String st;
    clientUI.display(msg.toString());
    ArrayList<String> dataFromDb=(ArrayList<String>)msg;
    if(dataFromDb.contains("showTable"))
    {
    	dataFromDb.remove("showTable");
    	dataInArrayList=dataFromDb;
    	System.out.println("niz egati1");
//    	try
//    	{
//    		controllerTry.c.setDetails(dataFromDb);
//    	}
//    	catch(Exception e)
//    	{
//    		e.printStackTrace();
//    	}
    	System.out.println("niz egati2");
    	//use setDetails in ShowInfoController
    	
    }
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param arr The message from the UI.    
   */
  public void handleMessageFromClientUI(ArrayList<String> arr,ClientConsole clientConsole )  
  {
    try
    {
    	sendToServer(arr);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
