package SimpleChatServer;

import java.io.*;
import common.ChatIF;

import SimpleChatClient.ClientConsole;

public class ServerConsole implements ChatIF{
	
	final public static int DEFAULT_PORT = 5555;
	
	// The instance of the server that is connected to this console
	private EchoServer1 server;
	
	public ServerConsole(String host, int port){
			server = new EchoServer1(port);	
			
			try{
				server.setConsole(this);
				server.listen();
			}
			catch(Exception ex){
				System.out.println("ERROR - Could not listen for clients!");
			}
		
	}//end ServerConsole()
	
	public void accept(){
		
		try{
			BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
			String message;
			
			while (true)
		      {
		        message = fromConsole.readLine();
		        server.handleMessageFromUser(message);
		      }
			
		}catch (Exception ex){
		      System.out.println
		        ("Unexpected error while reading from console!");
		}//end try-catch block
			
		
	}//end accept()

	public EchoServer1 getServer() {
		return server;
	}
	
	public void display(String message)
	{
	  System.out.println("> " + message);
	}//end display()
	
	public static void main(String[] args)
	  {
		String host = "";
	    int port = 0;  //The port number

	    ServerConsole sconsole = new ServerConsole(host, DEFAULT_PORT);
	    sconsole.accept();  //Wait for console data
	  }//end main()

}
