package client;

import common.*;

import java.io.IOException;

/**
 * Implements client command to log in to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 *          <p>
 *          Modified by Shouheng to allow for password protection, and to change the
 *          error message in connectionException()
 *          February 28 2016
 */
public class login extends NotConnectedClientCommand {
    
    public login(String str, ChatClient1 client) {
        super(str, client);
    }

    public void doCmd() {    
        if (!getStr().contains(" ")) {
            getClient().clientUI().display("Incorrect username or password.");
            return;
        } // end if
        String username = getStr().split(" ")[0];
        String password = getStr().split(" ")[1];

        if (username.equals("")) {
            getClient().clientUI().display("Incorrect username or password.");
            return;
        }

        //check if password consists of blanks only
        try {
            getClient().openConnection();
            getClient().sendToServer("#login " + username + " " + password);
             
        } catch (IOException ex) {
            getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
        }
    }
}

