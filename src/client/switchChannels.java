package client;

import java.io.IOException;

public class switchChannels extends ClientCommand {
	
	public switchChannels(String str, ChatClient1 client) {
		super(str,client);
	}
	
	@Override
	public void doCommand() {
		// TODO Auto-generated method stub
		try {
			getClient().sendToServer("#switchChannels " + getClient().getId() + " " + getStr());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
		}
	}

}
