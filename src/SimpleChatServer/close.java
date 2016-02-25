package SimpleChatServer;

import java.io.IOException;

import SimpleChatServer.ServerConsole;
import SimpleChatServer.ServerCommand;

/**
 * Implements client command to quit, after first closing any connection to the
 * current host.
 */
public class close extends ServerCommand {
	public close(String str, EchoServer1 server) {
		super(str, server);
	}

	public void doCommand() {

		if (!getServer().isClosed()) {
			// try{

			// getServer().close();
			// }
			// catch(IOException ex)
			// {
			// getServer().getConsole().display("IOException encountered while
			// trying to close the server. "
			// + "The server is not closed.\n");
			// return;
			// }//end try-catch block
			getServer().handleMessageFromUser("WARNING-Server has CLOSED.");
			getServer().setClosed(true);
			getServer().serverUI().display("Connection closed");

		} else {
			getServer().serverUI().display("Connection already closed, exiting.");
			getServer().setClosed(true);
		}

	}
}
