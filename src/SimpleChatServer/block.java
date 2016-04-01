package SimpleChatServer;

import ocsf.server.ConnectionToClient;

import java.io.IOException;
import java.util.*;

/**
 * Takes commands from client in the form: "#block sender channel user1 user2 ..."
 * Acceptable field separators: <code>"," ";" " "</code>
 */

public class block extends ServerCommand {

    public block(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() {
        final String in = getStr();
        String[] others = Arrays.copyOfRange(in.split(" "), 3, in.split("^[a-zA-Z]").length - 1);
        if (!getServer().getChannel(in.split(" ")[2]).getClient(in.split(" ")[1]).equals(null)) {
            getServer().serverUI().display("User '" + in.split(" ")[1] + "' is not in channel: '" + in.split(" ")[2] + "'.");
            return;
        }
        Channel chan = getServer().getChannel(in.split(" ")[2]);
        ConnectionToClient sender = chan.getClient(in.split(" ")[1]);
        if (chan.blocks.equals(null)) {
            Set<ConnectionToClient> ret = new HashSet<>(others.length);
            chan.blocks = new HashMap<>();
            for (String usr : others) {
                try {
                    if (chan.isInChannel(usr)) {
                        ret.add(chan.getClient(usr));
                    }
                } catch (NullPointerException e) {
                    try {
                        sender.sendToClient("Username " + usr + "not in channel " + in.split(" ")[3] + "'.");
                    } catch (IOException ex) {
                    }
                }
            }
            chan.blocks.put(sender, ret);
        } else {
            Set<ConnectionToClient> ret = new HashSet<>(others.length);
            for (String usr : others) {
                if (!chan.isInChannel(usr)) {
                    try {
                        sender.sendToClient("User '" + usr + "' is not in channel '" + chan.getChannelName() + "'.");
                    } catch (IOException e) {
                        getServer().serverUI().display(e.getMessage());
                    }
                }
                ret.add(chan.getClient(usr));
            }
            chan.blocks.put(sender, ret);
        }
    }
}
