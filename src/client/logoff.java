package client;

import java.io.IOException;

import common.ServerLoginHandler;
import common.ServerLogoffHandler;


//import common.*;

public class logoff extends ClientCommand {

	public logoff(String str, ChatClient1 myClient){
		super(str,myClient);
	}
	@Override
	public void doCommand() {
		
		try{
			if (getClient().isConnected()){
			getClient().sendToServer(new ServerLogoffHandler(getClient().getId()));
			getClient().clientUI().display("Connection to " + getClient().getHost() + " closed.");
			getClient().closeConnection();
			}
			else 
				System.out.println("You are already disconnected from this server");
		}
		
	catch(IOException ex) {
		getClient().getHost();
	}
		
	}

}
