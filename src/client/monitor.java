package client;

public class monitor extends ClientCommand {

    public monitor(String str, ChatClient1 myClient) {
        super(str, myClient);
    }

    public void doCommand() {
         //set the monitor of client.
        getClient().setMonitor(getClient().getMonitor());
    }
}
