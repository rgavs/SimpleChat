package SimpleChatServer;

public class switchChannels extends ServerCommand {

    public switchChannels(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { //#switchChannels <username> <channelToLeave> <channelToJoin>
        // TODO Auto-generated method stub
        String[] args = getStr().split(" ");
        getServer().getChannel(args[1]).removeClient(args[0]);
        getServer().serverUI().display(args[0] + " left " + args[1] + "...");
        getServer().getChannel(args[2]).addClient(args[0]);
        getServer().serverUI().display(" and joined " + args[2]);
    }
}
