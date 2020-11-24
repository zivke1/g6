package clientTry;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import client.*;
import common.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  public static ClientConsole chat= new ClientConsole("localhost", DEFAULT_PORT);
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
//  public void accept() 
//  {
//    try
//    {
//      BufferedReader fromConsole = 
//        new BufferedReader(new InputStreamReader(System.in));
//      String message;
//      ArrayList<String> arr=new ArrayList<>();
//      int i=0;
//      while (true)
//      {
//    	if(i%4==0)
//    	System.out.println("enter UserName:");
//    	if(i%4==1)
//    	System.out.println("enter ID:");
//    	if(i%4==2)
//    	System.out.println("enter Department:");
//    	if(i%4==3)
//    	{
//    		System.out.println("enter Tel:");
//    		message = fromConsole.readLine();
//    		arr.add(message);
//    		System.out.println("enter send");
//    	}
//    	
//        message = fromConsole.readLine();
//        
//        if(message.equals("send"))
//        {
//        	client.handleMessageFromClientUI(arr);
//        }
//        arr.add(message); 
//        i++;
//      }
//    } 
//    catch (Exception ex) 
//    {
//      System.out.println
//        ("Unexpected error while reading from console!");
//    }
//  }
  
  public void accept(ArrayList<String> arr) 
  {
    try
    {
        	client.handleMessageFromClientUI(arr);
    } 
    catch (Exception ex) 
    {
      System.out.println("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  public void showDetails() throws Exception
  {
	    Stage primaryStage = new Stage();
	    FXMLLoader loader=new FXMLLoader();
		VBox root = loader.load(getClass().getResource("/try1/FxmlTry.fxml").openStream());
		controllerTry ct = loader.getController();
		ct.setDetails(ChatClient.dataInArrayList);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/try1/application.css").toExternalForm());
		primaryStage.setTitle("eliran yoyo");
		primaryStage.setScene(scene);
		primaryStage.show();
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
 * @throws Exception 
   */
  public static void main(String[] args) throws Exception 
  {
//    String host = "";
//    int port = 0;  //The port number
//
//    try
//    {
//      host = args[0];
//    }
//    catch(ArrayIndexOutOfBoundsException e)
//    {
//      host = "localhost";
//    }
    
    ArrayList<String> arr=new ArrayList<>();
      arr.add("showTable");
//    arr.add("eliran");
//    arr.add("bendodshel niz");
      arr.add("316222");
//    arr.add("eli@halid");
//    arr.add("052656");
    chat.accept(arr);  //Wait for console data
    chat.showDetails();
  }
}
//End of ConsoleChat class
