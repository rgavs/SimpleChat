package SimpleChatServer;

public class switchChannels extends ServerCommand {
	
	public switchChannels(String str,EchoServer1 server) {
		super(str,server);
	}

	@Override
	public void doCommand() { //#switchChannels <username> <channeltoleave> <channeltojoin>
		// TODO Auto-generated method stub
		int indexBlank = getStr().indexOf(" ");
		String username = getStr().substring(0, indexBlank);
		int nextIndexBlank = getStr().indexOf(" ", indexBlank+1);
		String channelToLeave = getStr().substring(indexBlank+1,nextIndexBlank);
		String channelToJoin = getStr().substring(nextIndexBlank+1);
		Channel currentchl = getServer().getChannel(channelToLeave);
		Channel nextChl = getServer().getChannel(channelToJoin);
		currentchl.removeClient(username);
		nextChl.addClient(username,getServer());
		System.out.println("here");
		getServer().serverUI().display(username + "left " + channelToLeave + " and joined " + channelToJoin);
		
	}

}
