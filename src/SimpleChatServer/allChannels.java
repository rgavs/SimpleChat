package SimpleChatServer;

public class allChannels extends ServerCommand {

    public allChannels(String myStr, EchoServer1 thisServer) {
        super(myStr, thisServer);
    }

    @Override
    public void doCommand() { //#allChannels
        String list = "";
        for (Channel chan : getServer().enumerateChannels()) {
            if (list.length() < 1)
                list = chan.getChannelName();
            else
                list += ", " + chan.getChannelName();
        }
        getServer().sendToAllClients("SERVER MSG> " + list);
        getServer().serverUI().display(list);
    }
}
