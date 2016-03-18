package common;

import java.io.*;

import SimpleChatServer.EchoServer1;

/**
 * Written by Shouheng Wu
 * February 28, 2016
 * This class handles a request from a client to create a new account.
 *
 * 
 */
public class ServerNewAccountHandler extends ServerMessageHandler
{
  private String myId;
  private String myPassword;

  public ServerNewAccountHandler(String id, String password)
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
	if(server.checkExistingAccount(myId)){
		try{
			getClient().sendToClient("The user " + myId + "already exists.");
		}
		catch(IOException e){	
		}
		
		return;
		
	}
	
	server.setNewAccount(myId, myPassword);
    getServer().sendToAllClients("A new account named " + myId + " has been created");
  }

}
