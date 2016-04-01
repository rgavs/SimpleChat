package client;

public class monitor extends ClientCommand {

    private String monitor;

    public monitor(String str, ChatClient1 myClient) {
        super(str, myClient);
        monitor = str;
    }

    public void doCommand() {
        //set the monitor of client.
        getClient().setMonitor(monitor);

        try {
            getClient().sendToServer("#checkmonitor " + monitor);
        } catch (Exception e) {
        }
    }
}
