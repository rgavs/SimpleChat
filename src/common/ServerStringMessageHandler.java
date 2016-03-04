package common;

import ocsf.server.*;
import java.io.*;

/**
 *  This class defines a message handler to simple request that a String be displayed.
 */
public class ServerStringMessageHandler extends ServerNonLoginHandler
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
    System.out.println((String)getClient().getInfo("id") + "> " + myString);
    getServer().sendToAllClients((String)getClient().getInfo("id") + "> " + myString);
  }

}
