package common;


import ocsf.server.*;
import java.io.*;

/**
 *  This class specifies requests from a client that should come only after login.
 *  If the client has not yet logged in, then the client's connection is closed.
 *  The actual request from the client is handled by the abstract method handleMess
 *  to be specified in concrete subclasses of this class.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public abstract class ServerNonLoginHandler extends ServerMessageHandler
{
  /**
   *  This method checks if the user has logged in (by checking for an id)
   *  If logged in, the abstract method handleMess is called to handle the request
   *  If not logged in, the client is informed and disconnected.
   */
  public void handleMessage()
  {
    if(getClient().getInfo("id") != null)
    {
      handleMess();
    }
    else
    {
      try
      {
        System.out.println("Error: attempt to send command to server before logging in. \nConnection closed.");
        getClient().sendToClient("Error: attempt to send command to server before logging in. \nConnection closed.");
        getClient().close();
      }
      catch(IOException ex)
      {
        System.out.println("IOException " + ex + " when trying to send to client or close client. \n No other action taken.");
      }
    }
  }

  /**
   * This method provides the slot that any command from the client sent to the server must fill by
   * implementing this method in the subclass that defines the command. This method will not be
   * called if the client is not logged in to the server.
   */
  public abstract void handleMess();

}
