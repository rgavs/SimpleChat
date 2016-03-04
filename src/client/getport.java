package client;

public class getport extends ClientCommand {
	
	public getport(String str, ChatClient1 client) {
		super(str,client);
	}

	@Override
	public void doCommand() {
		
		getClient().clientUI().display("Currently connected to port " + getClient().getPort());;

	}

}
