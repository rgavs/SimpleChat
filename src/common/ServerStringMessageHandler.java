package common;

import SimpleChatServer.EchoServer1;
import ocsf.server.ConnectionToClient;

/**
 * This class defines a message handler to simple request that a String be displayed.
 */
public class ServerStringMessageHandler extends ServerMessageHandler//extends ServerNonLoginHandler
{
    private String myString;

    public ServerStringMessageHandler(String str) {
        myString = str;

    }

    /**
     * This method has the message String displayed on the server console and
     * sent to all clients.
     */
    public void handleMess() {
        System.out.println((String) getClient().getInfo("id") + "> " + myString);
        getServer().sendToAllClients((String) getClient().getInfo("id") + "> " + myString);
    }

    public void handleMessage() {
        if (myString.charAt(0) == '@') {
            ConnectionToClient sender = getClient();//(String) getClient().getInfo("id");
            ((EchoServer1) getServer()).sendToChannel(myString.substring(1), sender);

        } else if (myString.charAt(0) != '#') {
            handleMess();
        } else {
            if (checkLogin())
                doLogin();
            String message = myString.substring(1);
            ((EchoServer1) getServer()).createAndDoCommand(message);

        }
    }

    private boolean checkLogin() {
        int indexBlank = myString.indexOf(" ");
        String str = myString.substring(1, indexBlank);
        return str.equals("login");
    }

    private void doLogin() {
        String[] msg = myString.split(" ");
        String id = msg[1];
        getClient().setInfo("id", id);
    }

    /**
     * Searches string at index 0 for $ which indicates that the channel
     * number is at index 1. If the index 1 is an int, the function returns
     * that int. Else return -1.
     * @return int
     */
}
