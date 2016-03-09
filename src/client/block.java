package client;

import java.util.*;

/**
 * Created  3/4/16.
 *
 * @author rgavs
 *         <p>
 *         This class takes commands from client in the form: "#block user user1 user2 ..."
 *         to handle sloppy syntax, this will accept the following field separator values: "," ";" " "
 *         <p>
 *         TODO: determine regex of what characters are allowed in a username: i.e. can it include ^[\p{Punct] ?
 */

public class block extends ClientCommand {

    public block(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() { // TODO: check @tmoopenn pull request #26 regarding Client-Server interaction only using Strings
        String[] users = Arrays.copyOfRange(getStr().split("^[a-zA-Z]"), 1, getStr().split("^[a-zA-Z]").length - 1);
    }
}
