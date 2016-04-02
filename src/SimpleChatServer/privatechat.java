package SimpleChatServer;

import java.io.IOException;

public class privatechat extends ServerCommand {

    public privatechat(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {
        final String receiver = getStr().split(" ")[0];
        final String message = getStr().split(" ")[1];
        final String sender = getStr().split(" ")[2];


        try {
            getServer().getConnection(sender, getServer().getClientConnections()).sendToClient("[privatechat to] " + receiver + "> " + message);

            getServer().getConnection(receiver, getServer().getClientConnections()).sendToClient("[privatechat from] " + sender + "> " + message);
        } catch (IOException e) {
        }
    }
}

