package client;

import java.io.IOException;

import common.ServerPrivateMessageHandler;
import common.ServerStringMessageHandler;

public class privatechat extends ClientCommand {
	
	public privatechat(String str, ChatClient1 client)
	  {
	    super(str, client);
	  }
	
	public void doCommand()
	  { 
		String msg_display = getStr(); 
		String msg = "#privateMessage " + getStr(); 
		try{
	    getClient().sendToServer(new ServerPrivateMessageHandler(msg));
	    getClient().clientUI().display(getClient().getId() + ">" + msg_display);
		}
		catch (IOException ex){}
		
	  }
}
