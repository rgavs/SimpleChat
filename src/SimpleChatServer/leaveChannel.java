package SimpleChatServer;

public class leaveChannel extends ServerCommand {

    public leaveChannel(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { //#leaveChannel user channel 
        String str = getStr();
        ((Channel) (str.split(" ")[2])). str.split(" ").[1]
        int indexBlank = str.indexOf(" ");
        String user = str.substring(0, indexBlank);
        String channelName = str.substring(indexBlank + 1);
        Channel chl = getServer().getChannel(channelName);
        try {
            chl.removeClient(user);
        } catch (Exception e) {
            getServer().serverUI().display("Error in leaving channel");
        }
    }
}
