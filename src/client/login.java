package client;

import common.*;
import java.io.IOException;

/**
 *  Implements client command to log in to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 */
public class login extends NotConnectedClientCommand
{
  public login(String str, ChatClient1 client)
  {
    super(str, client);
  }

  public void doCmd()
  {
    try
    {
      getClient().openConnection();
      getClient().clientUI().display("Connection to " + getClient().getHost() + " opened.");
      getClient().sendToServer("#login " + getClient().getId());//(new ServerLoginHandler(getClient().getId()));
    }
    catch(IOException ex)
    {
      getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
    }
  }

}

