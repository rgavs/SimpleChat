package client;

public class setport extends NotConnectedClientCommand{

	public setport(String str, ChatClient1 client){
		super(str,client);
	}
	
	public void doCmd(){
		try{
			getClient().setPort(Integer.parseInt(getStr()));
		}
		catch(NumberFormatException ex){
			getClient().clientUI().display("The port has to be a number.");
			return;
		}
		
		getClient().clientUI().display("Port changed to " + getClient().getPort());
		
	}//end doCmd()
	
	
}
