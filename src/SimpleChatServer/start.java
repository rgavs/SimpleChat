package SimpleChatServer;

import java.io.IOException;

public class start extends ServerNotConnectedCommand{
	// TODO move messages to serverStarted
	public start(String str, EchoServer1 server){
		super(str, server);
	}//end start
	
	public void doCmd(){
		try
		{
			getServer().listen();
			getServer().setClosed(false);
			getServer().handleMessageFromUser("Server has started listening.");
			getServer().serverUI().display("Server listening for connections on port " + getServer().getPort());
		}
		catch(IOException e)
		{
			getServer().serverUI().display("IOException while starting server.");
			return;
		}//end try-catch block
		
	}//end doCmb()
	
}//end class
