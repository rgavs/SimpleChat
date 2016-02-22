package SimpleChatServer;

//import client.ChatClient1;


public abstract class ServerNotConnectedCommand extends ServerCommand
{
  public ServerNotConnectedCommand(String str, EchoServer1 server)
  {
    super(str,server);
  }

  /**
   * This method checks whether the client is connected. If it is, then no action
   * is taken. If it is not connected, then the command is executed by a call
   * to the abstract doCmd method.
   */

  public void doCommand()
  {
    if(getServer().isClosed())
    {
      doCmd();
    }
    else
    {
      getServer().getConsole().display("Currently Online.\n Cannot  execute command.");
    }
  }

  /**
   * This method provides the slot that any command from the client UI to the client must fill by
   * implementing this method in the subclass that defines the command. This method will not be called
   * if the client is already connected to a server.
   */
  public abstract void doCmd();

}
