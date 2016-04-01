package client;

import java.io.IOException;

public class newChannel extends ClientCommand {

    public newChannel(String str, ChatClient1 client) {
        super(str, client);
    }

    @Override
    public void doCommand() {
        // TODO Auto-generated method stub
        try {
            String message = "#newChannel " + getStr();
            getClient().sendToServer(message);//("#" + getStr());
        } catch (IOException e) {
            getClient().clientUI().display("Failed to send message to server");
        }
    }

}
