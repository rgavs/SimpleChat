package client;

import java.io.IOException;

import common.ServerLoginHandler;

//import common.*;

public class logoff extends ClientCommand {

	public logoff(String str, ChatClient1 myClient){
		super(str,myClient);
	}
	@Override
	public void doCommand() {
		
		try{
			if (getClient().isConnected()){
				getClient().closeConnection();
				getClient().clientUI().display("Connection to the server closed.");
			}
			else 
				System.out.println("You were not connectd to the server");
		}
		catch(IOException ex) {
		}
		
	}

}
