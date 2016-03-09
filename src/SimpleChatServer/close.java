package SimpleChatServer;
import java.io.IOException;

import SimpleChatServer.ServerConsole;
import SimpleChatServer.ServerCommand;

/**
 *  Implements client command to quit, after first closing any connection to the current host.
 */
public class close extends ServerCommand
{
	public close(String str, EchoServer1 server)
	{
		super(str, server);
	}

	public void doCommand()
	{

		if(!getServer().isClosed())
		{
			try {
			getServer().close();
			getServer().setClosed(true);
			getServer().serverUI().display("Connection closed");
			}
			catch(IOException e){}

		}
		else
		{
			getServer().serverUI().display("Connection already closed, exiting.");
		}


	}
}
