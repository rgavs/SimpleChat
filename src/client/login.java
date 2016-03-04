package client;

import common.*;
import java.io.IOException;

import SimpleChatServer.EchoServer1;

/**
 *  Implements client command to log in to the current host.
 *
 * @author Chris Nevison
 * @version February 2012
 * 
 * Modified by Shouheng to allow for password protection, and to change the 
 * error message in connectionException()
 *February 28 2016
 */
public class login extends NotConnectedClientCommand
{
  public login(String str, ChatClient1 client)
  {
    super(str, client);
  }

  public void doCmd()
  {	  
	  int indexOfBlank = getStr().indexOf(' ');
	  
	  if(indexOfBlank == -1){
		  getClient().clientUI().display("Incorrect username or password.");
		  return;
	  }//end if
	  String username = getStr().substring(0, indexOfBlank);
	  String password = getStr().substring(indexOfBlank+1);
	  
	  if(username.equals("")){
		  getClient().clientUI().display("Incorrect username or password.");
		  return;
	  }
	  
	  //check if password consists of blanks only
	  
	  getClient().setId(username);
	  getClient().setPassword(password);
	  
    try
    {
      getClient().openConnection();
      getClient().sendToServer(new ServerLoginHandler(getClient().getId(), getClient().getPassword()));
    }
    catch(IOException ex)
    {
      getClient().clientUI().display("Connection to " + getClient().getHost() + " failed.");
    }
  }

}

