package SimpleChatServer;

public class removeChannelUser extends ServerCommand {
    public removeChannelUser(String str, EchoServer1 thisServer) {
        super(str, thisServer);
    }

    @Override
    public void doCommand() { //#removeUser <channel> <username>
        String[] args = getStr().split(" ");
        getServer().getChannel(args[1]).removeClient(args[2]);
        getServer().serverUI().display(args[2] + " has been removed from " + args[1]);
    }
}
