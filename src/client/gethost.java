package client;

public class gethost extends ClientCommand {

	public gethost(String str, ChatClient1 client)
	  {
	    super(str, client);
	  }

	public void doCommand(){

		getClient().clientUI().display("The host is " + getClient().getHost());

	}

}
