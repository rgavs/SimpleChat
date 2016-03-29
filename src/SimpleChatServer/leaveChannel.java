package SimpleChatServer;

public class leaveChannel extends ServerCommand {
    public leaveChannel(String str, EchoServer1 server) {
        super(str, server);
    }

    @Override
    public void doCommand() { //#leaveChannel user channel 
        try {
            String[] strs = getStr().split(" ");
            getServer().getChannel(strs[1]).removeClient(strs[0]);
        } catch (Exception e) {
            getServer().serverUI().display("Error leaving channel");
        }
    }
}
