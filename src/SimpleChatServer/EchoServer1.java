package SimpleChatServer;

import java.io.*;

import client.ChatClient1;
import client.ClientCommand;
import ocsf.server.*;
import common.*;

/**
 *  This class modifies EchoServer to complete to begin Chat phase 2.
 *  It uses messages from Client to Server that are instances
 *  of command classes that extend the class ServerMessageHandler.
 *  This class delegates responsibility for handling a message to the
 *  message itself.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @author Chris Nevison
 * @version February 2012
 */
public class EchoServer1 extends AbstractServer
{
  //Class variables *************************************************
	private boolean closed; 
	private ServerConsole myconsole;
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer1(int port)
  {
    super(port);
    closed = false;
  }

  //Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received, an instance of a subclass of ServerMessageHandler
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  if (!isClosed()){
    ServerMessageHandler handler = (ServerMessageHandler) msg;
    handler.setServer(this);
    handler.setConnectionToClient(client);
    handler.handleMessage();
	  }
	  else
		  sendToAllClients("The server has closed the listening.");
  }
  
  public void handleMessageFromUser(String message){
	  if (!isClosed()){
	  if(message.charAt(0) != '#')
	    {
		  sendToAllClients("SERVER MSG>" + message);
	    }
	    else
	    {
	      message = message.substring(1);
	      createAndDoCommand(message);
	    }
	  }
	  else
		  getConsole().display("Connnection has closed. Unable to send messages.");
	  
  }//end handleMessageFromUser
  
  public void createAndDoCommand(String message){
	  String commandStr; 
	  int indexBlank = message.indexOf(' ');
	  if(indexBlank == -1)
	  {
      commandStr = "SimpleChatServer." + message;
      message = "";
	  }
	  else
	  {
		  commandStr = "SimpleChatServer." + message.substring(0, indexBlank);
	      message = message.substring(indexBlank+1);
	    }

	    try
	    {
	      Class[] param = {String.class, EchoServer1.class};
	      ServerCommand cmd = (ServerCommand)Class.forName(commandStr).getConstructor(param).newInstance(message, this);
	      cmd.doCommand();
	    }
	    catch(Exception ex)
	    {
	      getConsole().display("\nNo such command " + commandStr + "\nNo action taken.");
	    }
	  
  }//end createAndDoCommand

  public void setConsole(ServerConsole c){
	  myconsole = c;
	  
  }//end setConsole()
  
  public ServerConsole getConsole(){
	  
	  return myconsole;
	  
  }//end getConsole()
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
      closed = false;
	  System.out.println("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  this.stopListening();
	  closed = true;
	  System.out.println("Server has stopped listening for connections.");
  }
  
  protected Boolean isClosed() {
	  
	  return closed;
  }

  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555
   *          if no argument is entered.
   */
  
  //setClosed(true) means that the server has been closed
  //setClosed(false) means that the server has been open
  protected void setClosed(Boolean status){
	  closed = status;
  }//end setStatus
  
  protected synchronized void clientException(ConnectionToClient client, Throwable exception){
	  sendToAllClients("SERVER MSG>  " + client.getInfo("id") + " has disconnected.");
  }//end clientException
  
  protected synchronized void clientConnected(ConnectionToClient client){
	  sendToAllClients("SERVER MSG> A client has connected.");
	 
  }//end clientConnected
  
  protected synchronized void clientDisconnected(ConnectionToClient client){
	  sendToAllClients("SERVER MSG> A client has disconnected.");
		 
  }//end clientConnected
  
  public static void main(String[] args)
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }

    EchoServer1 sv = new EchoServer1(port);

    try
    {
      sv.listen(); //Start listening for connections
    }
    catch (Exception ex)
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }//end main
  
  
  
}

