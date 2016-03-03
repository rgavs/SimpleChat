package SimpleChatServer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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
	private ChatIF myServerUI;
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  private ArrayList<Channel> channels;
  private HashMap<String, String> accounts;//added by Shouheng
  

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
    
    accounts = new HashMap<String, String>();//added by Shouheng
    accounts.put("guest", "123");//added by Shouheng.
    
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
	  
    //if this message needs to be sent to monitor
	  if (msg.toString().contains("##")){
	    sendToMonitor(msg);  
	}
	else{
	    ServerMessageHandler handler = (ServerMessageHandler) msg;
	    handler.setServer(this);
	    handler.setConnectionToClient(client);
	    handler.handleMessage();
	}
	 
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
			      catch (Exception ex) {
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
  
  /**
   * send message only to the monitor
   * send feedback to the client if the person who will monitor is not connected
   * 
   * @param msg
   */
 public void sendToMonitor(Object msg){
	  String[] list = msg.toString().split("##");
	  String name = list[1];
	  String id = list[2];
	//some method to find the connection to client using the id name
	  ConnectionToClient monitor;
	  monitor = getConnection(name, getClientConnections());
	  if (monitor != null){
	  try{
		 monitor.sendToClient(list[0]);
	  }
	  catch (Exception ex){}
	  }
	  else {
		  ConnectionToClient client = getConnection(id, getClientConnections());
		  try {
			  client.sendToClient("The person you asked for monitoring is not connected. Try another one.");
		  }
		  catch (Exception ex){}
	  }
  }

/**
   * Returns the connection to the person who will monitor the message
   * 
   * @param id
   * @param allClients
   * @return Connection to the monitor
   */
  final public ConnectionToClient getConnection(String id, Thread[] allClients){
	  for (int i =0; i < allClients.length; i++) {
			ConnectionToClient client = (ConnectionToClient) allClients[i];
			String username = (String) client.getInfo("id");
			if (username.equals(id)) 
				return client; 
	  }
	  return null;
  }

  
//Written by Shouheng Wu
  //This method checks whether the given ID is already an existing user
  public boolean checkExistingAccount (String id){
	  if (accounts.containsKey(id)){
		  return true;
	  }
	  else{
		  return false;
	  }
  }//end checkExistingAccount
  
  //Written by Shouheng Wu
  //This method creates a user account by adding a id/password combination to the hashmap accounts
  public void setNewAccount(String id, String password){
	  accounts.put(id, password);
  }//end class
  
  //Written by Shouheng Wu
  //This method returns true if the provided password is correct
  public boolean checkPassword(String id, String password){
	  if(accounts.get(id).equals(password)){
		  return true;
	  }
	  else{
		  return false;
	  }
	  
  }//end checkPassword
  
  public void handleMessageFromUser(String message){
	
	  if(message.charAt(0) != '#')
	    {
		  sendToAllClients("SERVER MSG>" + message);
	    }
	    else
	    {
	      message = message.substring(1);
	      createAndDoCommand(message);
	    }
	  
	  
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
	  //this.stopListening();
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
  
  
  
}

