package SimpleChatServer;

import common.ServerMessageHandler;

public class ServerMessageHandler1 extends ServerMessageHandler {
	private String message;
	
	public void setMessage(String msg){
		message = msg; 
	}
	
	public void handleMessage(){
		try{
			String clientId = (String)getClient().getInfo("id");
			if(message.charAt(0) != '#')
		    {
			  getServer().sendToAllClients(clientId + " MSG>" + message);
		    }
		    else
		    {
		      message = message.substring(1);
		      ((EchoServer1)getServer()).createAndDoCommand(message);
		    }
		
		}
		catch (Exception ex){}
		
		
		
	}

}
