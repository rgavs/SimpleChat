package SimpleChatServer;

import common.ServerMessageHandler;
import ocsf.server.ConnectionToClient;

/**
 * This class defines a message handler to simple request that a String be displayed.
 */
public class ServerStringMessageHandler extends ServerMessageHandler {
    private String myString;

    public ServerStringMessageHandler(String str) {
        myString = str;
    }

    /**
     * This method has the message String displayed on the server console and
     * sent to all clients.
     */
    void handleMess() {
        System.out.println(getClient().getInfo("id") + "> " + myString);
        getServer().sendToAllClients(getClient().getInfo("id") + "> " + myString);
    }

    public void handleMessage() {
        if (myString.charAt(0) == '@') {
            ConnectionToClient sender = getClient();
            ((EchoServer1) getServer()).sendToChannel(myString.substring(1));
        } else if (myString.charAt(0) != '#') {
            handleMess();
        } else {
            if (checkLogin())
                doLogin();
            else {
                String message = myString.substring(1);
                ((EchoServer1) getServer()).createAndDoCommand(message);
            }
        }
    }

    private boolean checkLogin() {
        return myString.split(" ")[0].startsWith("#login");
    }

    private void doLogin() {
        int indexBlank = myString.indexOf(" ");
        String id = myString.substring(indexBlank + 1);
        getClient().setInfo("id", id);
        System.out.println(id + " has joined");
        getServer().sendToAllClients("SERVER MSG> " + id + " has joined");
    }
}
