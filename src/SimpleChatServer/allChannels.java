package SimpleChatServer;

import java.util.ArrayList;

public class allChannels extends ServerCommand {

    public allChannels(String myStr, EchoServer1 thisServer) {
        super(myStr, thisServer);
    }

    @Override
    public void doCommand() { //#allChannels
        // TODO Auto-generated method stub
        ArrayList<Channel> channels = getServer().enumerateChannels();
        String list = "";
        for (Channel chan : channels) {
            if (list.length() < 1)
                list = chan.getChannelName();
            else
                list += ", " + chan.getChannelName();
        }
        getServer().sendToAllClients("SERVER MSG> " + list);
        getServer().serverUI().display(list);
    }
}
