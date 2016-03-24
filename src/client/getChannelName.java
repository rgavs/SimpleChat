package client;

// TODO implement method for client to get connected <code>Channel</code> name
public class getChannelName extends ClientCommand {

    public getChannelName(ChatClient1 client) {
        super(null, client);
    }

    @Override
    public void doCommand() {
        try {
            getClient().sendToServer("#getChannelName " + getClient().getId());
        } catch (Exception e) {
        }
    }
}
