package client;

import java.io.IOException;

/**
 *  This abstract class provides should be extended by any client command class for
 *  a command that can only be executed when the client is not connected to a server
 *
 * @author Chris Nevison
 * @version February 2012
 */
public abstract class NotConnectedClientCommand extends ClientCommand
{
  public NotConnectedClientCommand(String str, ChatClient1 client)
  {
    super(str, client);
  }

  /**
   * This method checks whether the client is connected. If it is, then no action
   * is taken. If it is not connected, then the command is executed by a call
   * to the abstract doCmd method.
   */

  public void doCommand()
  {
    if(!getClient().isConnected())
    {
      doCmd();
    }
    else
    {
      getClient().clientUI().display("Currently connected to " + getClient().getHost() + ".\n Cannot  execute command.");
    }
  }

  /**
   * This method provides the slot that any command from the client UI to the client must fill by
   * implementing this method in the subclass that defines the command. This method will not be called
   * if the client is already connected to a server.
   */
  public abstract void doCmd();

}
