package SimpleChatServer;

public class leaveChannel extends ServerCommand {

    public leaveChannel(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { //#leaveChannel user channel 
        try {
            String[] strs = getStr().split(" ");
            for (Channel chan : getServer().enumerateChannels()) {
                if (chan.getChannelName() == strs[2]) {
                    chan.removeClient(strs[1]);
                }
            }
        } catch (Exception e) {
            getServer().serverUI().display("Error leaving channel");
        }
    }
}
