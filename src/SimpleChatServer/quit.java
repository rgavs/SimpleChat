package SimpleChatServer;

import java.io.IOException;

import SimpleChatServer.ServerConsole;
import SimpleChatServer.ServerCommand;

/**
 *  Implements client command to quit, after first closing any connection to the current host.
 */
public class quit extends ServerCommand
{
	public quit(String str, EchoServer1 server)
	{
		super(str, server);
	}

	public void doCommand()
	{

		if(!getServer().isClosed())
		{
			getServer().setClosed(true);
			getServer().serverUI().display("Connection closed, exiting.");
		}
		else
		{
			getServer().serverUI().display("Connection already closed, exiting.");
		}
		System.exit(0);


	}
}
