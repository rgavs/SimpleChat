package client;

import common.*;

public class endmonitor extends ClientCommand {
	
	public endmonitor(String str, ChatClient1 myClient){
		super(str, myClient);
	}
	
	public void doCommand() {
		if (getClient().isConnected() && getClient().getMonitor()!=null){
			getClient().clientUI().display("You have ended monitoring.");
			getClient().setMonitor(null);
		}
		else if(!getClient().isConnected()){
			getClient().clientUI().display("Please login to end your monitoring.");
		}
		else if(getClient().getMonitor()==null){
			 getClient().clientUI().display("You haven't started monitoring.");
		}
	}

}
