package SimpleChatServer;

import java.io.*;

import SimpleChatServer.*;

/**
 * This class handles a request from a client to login to the server.
 *
 * @author Shouheng Wu
 * @version March 2016
 */
public class login extends ServerCommand {
	public login(String str, EchoServer1 server) {
		super(str, server);
	}

	public void doCommand() {
		
		int indexOfBlank = getStr().indexOf(" ");
		String username = getStr().substring(0, indexOfBlank);
		String password = getStr().substring(indexOfBlank + 1);

		if (!getServer().checkExistingAccount(username) || !getServer().checkPassword(username, password)) {
            try {
                getServer().getConnection(username, getServer().getClientConnections()).sendToClient("Incorrect username/password.");
                getServer().getConnection(username, getServer().getClientConnections()).close();
            } catch (IOException e) {

            }
		
            return;
        }//end if
		else{
	        	getServer().sendToAllClients("SERVER MSG> " + username + " has joined");
	        	System.out.println(username + " has logged on.");
			}
		}
}
