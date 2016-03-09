package client;

import java.io.IOException;

public class privateChat extends ClientCommand {
	
	public privateChat (String str, ChatClient1 client) {
		super(str,client);
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		String msg_display = getStr();
		
		int indexBlank = msg_display.indexOf(" ");
		String receiver = msg_display.substring(0, indexBlank);
		String msg_body = msg_display.substring(indexBlank+1);
		
		String msg = "#privateMessage " + getClient().getId() + " " + getStr(); 
		try{
	    getClient().sendToServer(msg);
	    getClient().clientUI().display(getClient().getId() + " to " + receiver + " > " + msg_body);
		}
		catch (IOException ex){
			getClient().clientUI().display("Error in sending message to server.");
		}
		
	  }
}


