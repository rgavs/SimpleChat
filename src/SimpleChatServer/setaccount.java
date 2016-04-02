package SimpleChatServer;

import java.io.IOException;

/**
 * Written by Shouheng Wu
 * February 28, 2016
 * This class handles a request from a client to create a new account.
 */
public class setaccount extends ServerCommand {
    public setaccount(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {

        String[] msg = getStr().split(" ");
        String username = msg[0];
        String password = msg[1];
        String origin = msg[2];

        if (getServer().checkExistingAccount(username)) {
            try {
                getServer().getConnection(origin, getServer().getClientConnections()).sendToClient("A user named " + username + " already exists.");
            } catch (IOException e) {

            }

        }//end if
        else {
            getServer().setNewAccount(username, password);
            try {
                getServer().getConnection(origin, getServer().getClientConnections()).sendToClient("User " + username + " successfully created.");
                System.out.println("User \"" + username + "\" successfully created.");
            } catch (IOException e) {

            }
        }
    }
}
