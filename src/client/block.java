package client;

/**
 * Created  3/4/16.
 *
 * @author rgavs
 *         <p>
 *         This class takes commands from client in the form: "#block channel user1 user2 ..." ; prepends client id
 *         and forwards them to the server, which handles execution --> "#block sender channel user1 user2 ..."
 *         <p>
 */

public class block extends ClientCommand {

    public block(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() {
        try {
            getClient().sendToServer("#block " + getClient().getId() + " " + getStr());
        } catch (Exception e) {
            getClient().clientUI().display("Error attempting to reach server");
        }
    }
}
