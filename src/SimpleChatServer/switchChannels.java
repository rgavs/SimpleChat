package SimpleChatServer;

public class switchChannels extends ServerCommand {

    public switchChannels(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { //#switchChannels <username> <channelToLeave> <channelToJoin>
        // TODO Auto-generated method stub
		String[] args = getStr().split(" ");
        getServer().getChannel(args[2]).removeClient(args[1]);
        getServer().serverUI().display(args[1]+" left "+ args[2] +"...");
        getServer().getChannel(args[3]).addClient(args[1]);
        getServer().serverUI().display(" and joined " + args[3]);
    }
}
q