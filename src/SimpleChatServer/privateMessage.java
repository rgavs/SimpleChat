package SimpleChatServer;

import ocsf.server.ConnectionToClient;

public class privateMessage extends ServerCommand {
	
	public privateMessage(String str, EchoServer1 thisServer) {
		super(str,thisServer);
	}
	
	@Override
	public void doCommand() { 
		// TODO Auto-generated method stub
		final String str = getStr(); 
	    int indexBlank = str.indexOf(' ');
	    int nextBlank = str.indexOf(" ", indexBlank +1);
	    final String sender = str.substring(0, indexBlank);
	    final String receiver = str.substring(indexBlank+1, nextBlank);
	    final String message = str.substring(nextBlank+1);
		
		Thread[] clientThreadList = getServer().getClientConnections();
		for (int i=0; i<clientThreadList.length; i++)
	    {
	    	ConnectionToClient connection = (ConnectionToClient)clientThreadList[i];
	    	if (((String)connection.getInfo("id")).equals(receiver)){
	            try{
	                connection.sendToClient(sender + "> " + message);
	            }
	            
	            catch (Exception ex) {
	            	getServer().serverUI().display("Error in sending message to client");
	            }
	        }
	    }
	}
}


