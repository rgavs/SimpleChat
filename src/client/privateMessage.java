package client;

import java.io.IOException;

public class privateMessage extends ClientCommand {
	
	public privateMessage(String str, ChatClient1 client)
	  {
	    super(str, client);
	  }
	
	public void doCommand()
	{ 
		Object msg = "#private " + getStr(); 
		try{
	    	getClient().sendToServer(msg);
		}
		catch (IOException ex){}
	}
}
