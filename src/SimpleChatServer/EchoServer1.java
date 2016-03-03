package SimpleChatServer;

import java.io.*;
import java.util.ArrayList;

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
 * @author Dr Robert Laganiegravere
 * @author Franccedilois Beacutelanger
 * @author Paul Holden
 * @author Chris Nevison
 * @version February 2012
 */
public class EchoServer1 extends AbstractServer
{
  //Class variables *************************************************
	private boolean closed; 
	private ChatIF myServerUI;
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
    private ArrayList<Channel> channels;
  

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer1(int port, ChatIF serverUI) throws IOException
  {
    super(port);
    myServerUI = serverUI;
    closed = false;
    initializeChannels();
    try {
    	listen();
    }
    catch(IOException e) {
    	serverUI().display("ERROR - Could not listen for clients!");
    }
  }
  
  public ChatIF serverUI() {
  	return myServerUI;
  }
  
  private void initializeChannels() { 
	  channels = new ArrayList<Channel>();
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
    ServerMessageHandler handler = (ServerMessageHandler) msg;
    handler.setServer(this);
    handler.setConnectionToClient(client);
    handler.handleMessage();
  }
  
  public void addChannel(Channel chl) {
	  channels.add(chl);
  }
  
  
  private void sendToChannelClients(Object msg, String channel) {
	  for (int j = 0; j < channels.size(); j++) {
		  Channel chl = channels.get(j);
		  if (chl.getChannelName().equals(channel)) {
			  Object[] channelClients = chl.enumerateClients();
			  for (int i=0; i<channelClients.length; i++)
			    {
			      try
			      {
			        ((ConnectionToClient)channelClients[i]).sendToClient("Server MSG "+ chl.getChannelName()+"> "+msg);
			      }
			      catch (Exception e) {
			    	  serverUI().display("Error in sending message");
			      }
			    }
			  return;
		  }
		  else {
			  serverUI().display("Channel with the name " + channel + " does not exist.");
		  	  return;
		  }
	  }
	
  }
  
  public void handleMessageFromUser(String message){
	  if (message.charAt(0) == '@') {
		  sendToChannel(message.substring(1));
	  }
	  else if(message.charAt(0) != '#')
	    {
		  sendToAllClients("SERVER MSG>" + message);
	    }
	    else
	    {
	      message = message.substring(1);
	      createAndDoCommand(message);
	    }
	  
	  
  }//end handleMessageFromUser
  
  public void sendToChannel(String message) {
	  String channelName;
	  int indexBlank = message.indexOf(' ');
	  if(indexBlank == -1) {
		  channelName = message;
		  String msg = "";
		  sendToChannelClients(channelName, msg);
	  }
	  else {
		  channelName = message.substring(0, indexBlank);
		  String msg = message.substring(indexBlank + 1);
		  sendToChannelClients(msg, channelName);
	  }
  }
  
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
	     serverUI().display("\nNo such command " + commandStr + "\nNo action taken.");
	  }
	  
  }//end createAndDoCommand

  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
      closed = false;
      serverUI().display("Server listening for connections on port " + getPort());
      sendToAllClients("SERVER MSG> Server has started listening.");
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
	  closed = true;
	  serverUI().display("Server has stopped listening for connections.");
	  sendToAllClients("SERVER MSG> Server has stopped listening.");
  }
  
  protected Boolean isClosed() {
	  return closed;
  }

  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of
   * the server instance (there is no UI in this phase).
   *
   * param args[0] The port number to listen on.  Defaults to 5555
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
}

