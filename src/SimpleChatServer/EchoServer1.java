package SimpleChatServer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    accounts = new HashMap<>();//added by Shouheng
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
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
      ServerStringMessageHandler handler = (ServerStringMessageHandler) msg;
      handler.setMessage(msg.toString());
      handler.setServer(this);
      handler.setConnectionToClient(client);
      handler.handleMessage();
  }

  public void addChannel(Channel chl) {
	  channels.add(chl);
  }

  private void sendToChannelClients(Object msg, String channel) {
	  for (Channel chan : channels) {
		  if (chan.getChannelName().equals(channel)) {
			  Object[] channelClients = chan.enumerateClients();
			  for (int i=0; i<channelClients.length; i++)
              {
                  try {
			        ((ConnectionToClient)channelClients[i]).sendToClient("Server MSG "+ chan.getChannelName()+"> "+msg);
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

  //Written by Shouheng Wu
  //This method checks whether the given ID is already an existing user
  public boolean checkExistingAccount (String id){
      return accounts.containsKey(id);
  }//end checkExistingAccount

  //Written by Shouheng Wu
  //This method creates a user account by adding a id/password combination to the HashMap accounts
  public void setNewAccount(String id, String password){
	  accounts.put(id, password);
  }//end class

  //Written by Shouheng Wu
  //This method returns true if the provided password is correct
  public boolean checkPassword(String id, String password){
      return accounts.get(id).equals(password);
  }//end checkPassword

  public void handleMessageFromUser(String message){
	  if (message.charAt(0) == '@') {
		  sendToChannel(message.substring(1));
	  } else if(message.charAt(0) != '#') {
		  sendToAllClients("SERVER MSG>" + message);
      } else {
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
      try {
          Class[] param = {String.class, EchoServer1.class};
          ServerCommand cmd = (ServerCommand) Class.forName(commandStr).getConstructor(param).newInstance(message, this);
          cmd.doCommand();
      } catch (Exception ex) {
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
