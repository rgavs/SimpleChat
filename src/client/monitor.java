package client;

import common.*;

public class monitor extends ClientCommand {
	
	public monitor(String str, ChatClient1 myClient){
		super(str, myClient);
		}
	
	public void doCommand() {
		if (getStr().equals(getClient().getId())){
			getClient().clientUI().display("You can't choose yourself as monitor. Choose another one!");
		}
		//set the monitor of client.
		else {
			getClient().setMonitor( getStr() );
		
		try{
			getClient().sendToServer("#checkmonitor "+getStr());
		}
		catch(Exception e){}
		}
	}
}
