package SimpleChatServer;

import java.io.IOException;

public class start extends ServerNotConnectedCommand{
	
	public start(String str, EchoServer1 server){
		super(str, server);
	}//end start
	
	public void doCmd(){
		try {
			getServer().listen();
		} 
		catch (IOException e) { }
		getServer().setClosed(false);
	}//end doCmb()
}//end class
