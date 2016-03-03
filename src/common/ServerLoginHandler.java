package common;

import java.io.*;
import SimpleChatServer.*;

/**
 * This class handles a request from a client to login to the server.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class ServerLoginHandler extends ServerMessageHandler
{
  private String myId;
  private String myPassword;

  public ServerLoginHandler(String id, String password)
  {
    myId = id;
    myPassword = password;
  }

  /**
   * This method logs the client in by saving its id String and
   * sends a message to all clients that the new client has logged in
   * If already logged in (id String has been set) a message is sent to the
   * client and no other action is taken.
   */
  public void handleMessage()
  {
	EchoServer1 server = (EchoServer1)getServer();
	if(!server.checkExistingAccount(myId) || !server.checkPassword(myId, myPassword)){
		try{
			getClient().sendToClient("Incorrect username/password.");
			getClient().close();
		}
		catch(IOException e){
			
		}
		
		return;
	}//end if

    getClient().setInfo("id", myId);
    
    try{
    	getClient().sendToClient("Connection to " + getClient().getInfo("id") + " opened.");
    }
    catch(IOException e){
    	
    }
    
    System.out.println(myId + " has logged on");
    getServer().sendToAllClients("SERVER MSG> " + myId + " has joined");
  }
}
