package SimpleChatServer;

public class removeChannelUser extends ServerCommand {
	
	public removeChannelUser(String str, EchoServer1 thisServer) {
		super(str,thisServer);
	}
	
	@Override
	public void doCommand() { //#removeUser <channel> <username>
		
		int indexBlank = getStr().indexOf(' ');
		String channelName = getStr().substring(0,indexBlank);
		String user = getStr().substring(indexBlank +1);
		Channel chl = getServer().getChannel(channelName);
		chl.removeClient(user);
		getServer().serverUI().display(user + " has been removed from " + channelName);
	}

}
