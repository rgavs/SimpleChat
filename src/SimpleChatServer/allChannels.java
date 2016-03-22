package SimpleChatServer;

public class allChannels extends ServerCommand {

	public allChannels(String myStr, EchoServer1 thisServer) {
		super(myStr,thisServer);
	}
	
	@Override
	public void doCommand() { //#allChannels
		// TODO Auto-generated method stub
		Channel[] channels = getServer().enumerateChannels();
		String list = "";
		for (int i = 0; i<channels.length; i++) {
			list += channels[i].getChannelName() + ", ";
		}
		getServer().sendToAllClients("SERVER MSG> " +list);
		getServer().serverUI().display(list);
	}

}
