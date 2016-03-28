package client;

import java.io.IOException;

public class allChannels extends ClientCommand {

    public allChannels(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() {
        try {
            getClient().sendToServer("#allChannels " + getStr());
        } catch (IOException e) {
            getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
        }
    }
}
