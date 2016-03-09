package SimpleChatServer;

// TODO move messages to serverStopped
public class stop extends ServerCommand {
    public stop(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() {

        if (!getServer().isClosed()) {
            try {
                getServer().setClosed(true);
                getServer().stopListening(); //changes made by Shouheng
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("The server is already closed. No action was performed.");
    }
}
