package SimpleChatServer;

/**
 * This class handles a request from a client to login to the server.
 *
 * @author Shouheng Wu
 * @version March 2016
 */
public class clientquit extends ServerCommand {
    public clientquit(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {

        System.out.println(getStr() + " has quited.");
    }
}
