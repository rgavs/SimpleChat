package SimpleChatServer;
import java.io.IOException;


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
			try{
				getServer().close();
				getServer().serverUI().display("Connection closed, exiting");
				getServer().setClosed(true);
			}
			catch(IOException e){
				getServer().serverUI().display("An exception has occurred. Server cannot be closed.");
			}
		}//end if
		else
		{
			getServer().serverUI().display("Connection already closed, exiting.");
		}//end else

		System.exit(0);
	}
}
