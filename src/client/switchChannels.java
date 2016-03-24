package client;

import java.io.IOException;

/**
 * User string given in format <code> </code>
 */
// TODO implement method for client to get connected <code>Channel</code> name
public class switchChannels extends ClientCommand {

    public switchChannels(String str, ChatClient1 client) {
        super(str, client);
    }
    
    @Override
    public void doCommand() {
        try {
            getClient().sendToServer("#switchChannels " + getClient().getId() + " " + getStr());
        } catch (IOException e) {
            getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
        }
    }
}
