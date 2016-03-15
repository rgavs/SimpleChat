package SimpleChatServer;

import ocsf.server.ConnectionToClient;

public class privateMessage extends ServerCommand {

    public privateMessage(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {
        final String str = getStr();
        int indexBlank = str.indexOf(' ');
        final String receiver = str.substring(0, indexBlank);
        final String message = str.substring(indexBlank + 1);

        Thread[] clientThreadList = getServer().getClientConnections();
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient connection = (ConnectionToClient) clientThreadList[i];
            try {
                if (connection.getInetAddress().getHostName().equals(receiver)) {
                    connection.sendToClient(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}