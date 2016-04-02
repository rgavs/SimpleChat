package SimpleChatServer;

public class joinChannel extends ServerCommand {

    public joinChannel(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { // #joinChannel user channel
        String str = getStr();
        int indexBlank = str.indexOf(" ");
        String user = str.substring(0, indexBlank);
        String channelName = str.substring(indexBlank + 1);
        Channel chl = getServer().getChannel(channelName);
        try {
            System.out.println("ok");
            chl.addClient(user);
        } catch (Exception e) {
            getServer().serverUI().display("Error in joining channel");
        }
    }
}
