package common;

import ocsf.server.*;
import java.io.*;

import SimpleChatServer.EchoServer1;

/**
 *  This class defines a message handler to simple request that a String be displayed.
 */
public class ServerPrivateMessageHandler extends ServerMessageHandler
{
  private String myString;

  public ServerPrivateMessageHandler(String str)
  {
    myString = str;
  }


  public void handleMessage()
  {
    EchoServer1 server = (EchoServer1)getServer();
    server.handleMessageFromUser(myString);
	  
  }
  
}