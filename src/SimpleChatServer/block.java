package SimpleChatServer;

import java.util.*;

/**
 * Takes commands from client in the form: "#block user user1 user2 ..."
 * Acceptable field separators: <code>"," ";" " "</code>
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
        for(String usr : others){
            getServer().getChannel("");
        }
    }
}
