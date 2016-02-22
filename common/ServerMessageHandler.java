package common;

import ocsf.server.*;
import java.io.*;

/**
 *  This abstract class specifies requests that can be sent from a client to
 *  the server. Rather than Strings being sent, a request object, an instance of a
 *  subclass of this class, is sent.
 *  Any request from client to server, including just echoing a String
 *  must extend this class.
 *
 * @author Chris Nevison
 * @version February 2012
 */

public abstract class ServerMessageHandler implements Serializable
{
  private AbstractServer myServer;
  private ConnectionToClient myClient;

  /**
   * Allows the server to provide access to itself
   *
   * @param server Should be the server receiving this command
   */
  public void setServer(AbstractServer server)
  {
    myServer = server;
  }

  /**
   * Allows the server to provide access to the ConnectionToClient
   *
   * @param client Should be the connection ToClient for the client sending this command
   */
  public void setConnectionToClient(ConnectionToClient client)
  {
    myClient = client;
  }

  /**
   * Allows subclasses access to the server
   *
   * @return the server
   */
  protected AbstractServer getServer()
  {
    return myServer;
  }

  /**
   * Allows subclasses access to the ConnectionToClient
   *
   * @return the ConnectionToClient for this client
   */
  protected ConnectionToClient getClient()
  {
    return myClient;
  }

  /**
   * This method provides the slot that any command from the client sent to the server must fill by
   * implementing this method in the subclass that defines the command.
   */
  public abstract void handleMessage();

}
