package SimpleChatServer;

public class setport extends ServerNotConnectedCommand{

	public setport(String str, EchoServer1 server){
		super(str,server);
	}
	
	public void doCmd(){
		try
		{
			getServer().setPort(Integer.parseInt(getStr()));
		}
		catch(NumberFormatException ex){
			getServer().serverUI().display("The port has to be a number.");
			return;
		}
		
		getServer().serverUI().display("Port changed to " + getServer().getPort());
		
	}
	
}

