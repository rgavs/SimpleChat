package SimpleChatServer;

public class newChannel extends ServerCommand {
	
	public newChannel(String str, EchoServer1 thisServer) {
		super(str,thisServer);
	}
	
	public void doCommand() { //#newChannel <channelName>,<user1>,<user2>
		Channel chl = new Channel(getStr(),getServer());
		Channel[] chls = getServer().enumerateChannels();
		for (int i = 0; i<getServer().numOfChannels(); i++){
			if (chls[i].getChannelName().equals(chl.getChannelName()))
				return;
		}
		getServer().addChannel(chl);
		getServer().serverUI().display("New channel with the name " + chl.getChannelName() + " created");
	}
	
}
