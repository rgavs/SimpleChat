package common;

import ocsf.server.*;
import java.io.*;

/**
 *  This class handles a request from a client to login to the server.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class ServerLoginHandler extends ServerMessageHandler
{
  private String myId;

  public ServerLoginHandler(String str)
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
    System.out.println(myId + " has logged on");
    getClient().setInfo("id", myId);
    getServer().sendToAllClients("SERVER MSG> " + myId + " has joined");
  }

}