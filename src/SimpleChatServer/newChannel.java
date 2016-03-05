package SimpleChatServer;

public class newChannel extends ServerCommand {
	
	public newChannel(String str, EchoServer1 thisServer) {
		super(str,thisServer);
	}
	
	public void doCommand() {
//		Channel chl = new Channel(getStr(),getServer());
		getServer().addChannel(new Channel(getStr(), getServer()));
		getServer().serverUI().display("New channel with the name " + (new Channel(getStr(), getServer())).getChannelName() + " created");
	}
}
