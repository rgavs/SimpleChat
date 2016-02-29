package client;

import common.ServerNewAccountHandler;

//import ocsf.client.*;
//import java.io.IOException;


/*Written by Shouheng
 *February 2016
 *
 *This class is used to process client commands of the format "#setaccount usr pwd", which 
 *would result in the creation of a new account whose username is usr and its password is pwd
 */
public class setaccount extends ClientCommand {

	public setaccount(String str, ChatClient1 client) {
		super(str,client);
	}


	@Override
	public void doCommand() {
		int indexBlank = getStr().indexOf(' ');
		if(indexBlank == -1){
			getClient().clientUI().display("You did not enter a valid pair of username and password.");
			return;
		}//end if
		String username = getStr().substring(0, indexBlank);
		String password = getStr().substring(indexBlank + 1);
		
		if(username.equals("")){
			getClient().clientUI().display("The username can't be blank.");
			return;
		}
		
		//return if the password consists of blanks only;
		
		try{
			if (getClient().isConnected()){
				getClient().sendToServer(new ServerNewAccountHandler(username, password));
			}
			else
				getClient().clientUI().display("Must be disconnected from server to set account.");
		}
		catch(Exception ex) {
			getClient().clientUI().display("Failed to set account.");
		}

	}
}



