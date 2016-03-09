package client;

import java.io.IOException;

public class leaveChannel extends ClientCommand {
	
	public leaveChannel(String str,ChatClient1 client) {
		super(str,client);
	}
	
	@Override
	public void doCommand() {
		try {
			getClient().sendToServer("#leaveChannel " + getClient().getId() + " " + getStr());
		} catch (IOException e) {
			getClient().clientUI().display("Error in reaching server");
		}

	}

}
