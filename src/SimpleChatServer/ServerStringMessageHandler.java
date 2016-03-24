package common;

import ocsf.server.*;
import java.io.*;

import SimpleChatServer.EchoServer1;

/**
 *  This class defines a message handler to simple request that a String be displayed.
 */
public class ServerStringMessageHandler extends ServerMessageHandler//extends ServerNonLoginHandler
{
  private String myString;

  public ServerStringMessageHandler(String str)
  {
    myString = str;
    
  }

  /**
   *  This method has the message String displayed on the server console and
   *  sent to all clients.
   */
  public void handleMess()
  {
	//int channelNum = parseChannel();
    System.out.println((String)getClient().getInfo("id") + "> " + myString);
    getServer().sendToAllClients((String)getClient().getInfo("id") + "> " + myString);
  }
  
  public void handleMessage() {

		//String clientId = (String) getClient().getInfo("id");
		if(myString.charAt(0) == '@')
	    {
			
			ConnectionToClient sender = getClient();//(String) getClient().getInfo("id");
			((EchoServer1)getServer()).sendToChannel(myString.substring(1),sender);
		  
		  //getServer().sendToAllClients(clientId + " MSG>" + myString);
	    }
		
		else if(myString.charAt(0) != '#') {
			handleMess();
		}
		
	    else
	    {
	      if (checkLogin())
	    	  doLogin();
	      else {
	      String message = myString.substring(1);
	      ((EchoServer1)getServer()).createAndDoCommand(message);
	      }
	    }
  }
  
  private boolean checkLogin() {
	  int indexBlank = myString.indexOf(" ");
	  String str = myString.substring(1,indexBlank);
	  if (str.equals("login"))
		  return true;
	  return false;
  }
  
  private void doLogin() {
	  int indexBlank = myString.indexOf(" ");
	  String id = myString.substring(indexBlank +1);
	  getClient().setInfo("id",id);
	  System.out.println(id + " has joined");
	  getServer().sendToAllClients("SERVER MSG> " + id + " has joined");
  }
  
  /**
   * Searches string at index 0 for $ which indicates that the channel
   * number is at index 1. If the index 1 is an int, the function returns
   * that int. Else return -1.
   * @return int
   */
  //public int parseChannel() {
	//  if (myString.charAt(0) == '$') {
		//  try{
		 // int channelNum = (int)myString.charAt(1);
		  //return channelNum;
		 // }
		  //catch (ClassCastException e) {
		  //}
	  //}
		//return -1;	  
  //}
}