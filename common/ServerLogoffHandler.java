package common;

import ocsf.server.*;

import java.io.*;

public class ServerLogoffHandler extends ServerMessageHandler{
	private String myId;

	  public ServerLogoffHandler(String str)
	  {
	    myId = str;
	  }

	  /**
	   * This method logs the client in by saving its id String and
	   * sends a message to all clients that the new client has logged in
	   * If already logged in (id String has been set) a message is sent to the
	   * client and no other action is taken.
	   */
	  public void handleMessage()
	  {
	    System.out.println(myId + " has logged off");
	    getClient().setInfo("id", myId);
	    getServer().sendToAllClients("SERVER MSG> " + myId + " has left");
	  }
}
