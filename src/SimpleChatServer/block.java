package SimpleChatServer;

import java.util.*;

/**
 * Created  3/4/16.     Most recent edit: 03/21/16
 *
 * @author rgavs
 */

/**
 * This class takes commands from client in the form: "#block user user1 user2 ..."
 * to handle sloppy syntax, this will accept the following field separator values: "," ";" " "
 */

public class block extends ServerCommand {

    public block(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() {
        final String in = getStr();
        String user = in.split(" ")[1];
        String[] others = Arrays.copyOfRange(in.split(" ")[2].split("^[a-zA-Z]"), 1, getStr().split("^[a-zA-Z]").length - 1);
//        getServer().addBlock(user, others);
    }
}
