package client;

import java.io.IOException;

import common.ServerQuitHandler;

/**
 *  Implements client command to quit, after first closing any connection to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class quit extends ClientCommand
{
  public quit(String str, ChatClient1 client)
  {
    super(str, client);
  }

  public void doCommand()
  {
    try
    {
      if(getClient().isConnected())
      {
    	  getClient().sendToServer(new ServerQuitHandler(getClient().getId()));
        getClient().clientUI().display("Connection closed, exiting.");
        getClient().closeConnection();
      }
      else
      {
        getClient().clientUI().display("Connection already closed, exiting.");
      }
      System.exit(0);
    }
    catch(IOException ex)
    {
      getClient().clientUI().display("IOException " + ex + ", exiting.");
      System.exit(-1);
    }
  }
}



