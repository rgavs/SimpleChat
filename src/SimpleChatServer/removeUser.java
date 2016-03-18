package SimpleChatServer;



public class removeUser extends ServerCommand {
	
	public removeUser(String str, EchoServer1 thisServer) {
		super(str,thisServer);
	}
	
	@Override
	public void doCommand() {
		int indexBlank = getStr().indexOf(' ');
		String channelName = getStr().substring(0,indexBlank);
		String user = getStr().substring(indexBlank +1);
		Channel chl = getServer().getChannel(channelName);
		chl.removeClient(user);
		System.out.println("ok");
	}
}
