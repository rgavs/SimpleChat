package SimpleChatServer;

import ocsf.server.ConnectionToClient;

public class privateMessage extends ServerCommand {

    public privateMessage(String str, EchoServer1 server) {
        super(str, server);
    }

    public void doCommand() {
        final String receiver = getStr().split(" ")[0];
        final String message = getStr().split(" ")[1];

        Thread[] clientThreadList = getServer().getClientConnections();
        for (Thread thr : clientThreadList){ // i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient connection = (ConnectionToClient) thr;
            try {
                if (connection.getInetAddress().getHostName().equals(receiver)) {
                    connection.sendToClient(message);
                }
            } catch (Exception e) {
            } 
        }
    }
}
