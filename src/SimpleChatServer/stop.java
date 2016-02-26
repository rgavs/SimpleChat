package SimpleChatServer;

import java.io.IOException;

import client.ChatClient1;
import SimpleChatServer.ServerConsole;
import SimpleChatServer.ServerCommand;
// TODO move messages to serverStopped
public class stop extends ServerCommand {
	public stop(String str, EchoServer1 server){
		super(str,server);
	}
	@Override
	public void doCommand() {

		if (!getServer().isClosed()){
			getServer().stopListening(); //changed by Shouheng
			getServer().serverStopped();
			}
		else
			System.out.println("The server is already closed. No action was performed.");
	}
}
