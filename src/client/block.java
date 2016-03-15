package client;

import java.io.IOException;

/**
 * Created  3/4/16.
 * @author rgavs
 *      <p>
 *      This class takes commands from client in the form: "#block user user1 user2 ..."
 *      to handle sloppy syntax, this will accept the following field separator values: "," ";" " "
 *      <p>
 */

public class block extends ClientCommand {

    public block(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() {
        String msg = "#block " + getClient().getId() + " " + getStr();
        try {
            getClient().sendToServer(msg);
        } catch (IOException e) {
            getClient().clientUI().display("IOException: "+e.getMessage());
        }
    }
}