package client;

import java.io.IOException;

public class joinChannel extends ClientCommand {
<<<<<<< HEAD
	
	public joinChannel(String str,ChatClient1 client) { 
		super(str,client);
	}
	
	@Override
	public void doCommand() {
		try {
		getClient().sendToServer("#joinChannel " + getClient().getId() + " " + getStr());
		}
		catch (IOException e) {
		getClient().clientUI().display("Error in reaching server");
		}
	}

=======

    public joinChannel(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() {
        try {
            getClient().sendToServer("#joinChannel " + getClient().getId() + " " + getStr());
        } catch (IOException e) {
            getClient().clientUI().display("Error in reaching server");
        }
    }
>>>>>>> rgavs-local
}
