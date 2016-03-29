package client;

import java.io.IOException;

public class privatechat extends ClientCommand {

    public privatechat(String str, ChatClient1 client) {
        super(str, client);
    }

    public void doCommand() {
        Object msg = "#privatechat " + getStr() + " " + getClient().getId();
        try {
            getClient().sendToServer(msg);
        } catch (IOException ex) {
        }
    }
}
