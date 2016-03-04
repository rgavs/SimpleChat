package SimpleChatServer;

//import client.ChatClient1;

public abstract class ServerCommand {

	private String myString;
	private EchoServer1 myServer;

	public ServerCommand(String str, EchoServer1 thisserver)
	{
		myString = str;
		myServer = thisserver;
	}

	/**
	 * This method provides the command access to the client
	 *
	 * @return the client
	 */
	protected EchoServer1 getServer()
	{
		return myServer;
	}

	/**
	 * This method provides the command access to the command String (stripped of the command)
	 *
	 * @return command String
	 */
	protected String getStr()
	{
		return myString;
	}

	/**
	 * This method provides the slot that any command from the client UI to the client must fill by
	 * implementing this method in the subclass that defines the command.
	 */
	abstract public void doCommand();
}


