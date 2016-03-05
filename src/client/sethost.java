package client;

//import ocsf.client.*;
//import java.io.IOException;

public class sethost extends NotConnectedClientCommand {

	public sethost(String str, ChatClient1 client) {
		super(str,client);
	}


	@Override
	public void doCmd() {
		try{
			if (!getClient().isConnected()){
				getClient().setHost(getStr());
				getClient().clientUI().display("Host set to " + getStr());
			}
			else
				System.out.println("Must be disconnected from server to change host");
		}
		catch(Exception ex) {
			getClient().clientUI().display("Failed to set host");
		}

	}
}



