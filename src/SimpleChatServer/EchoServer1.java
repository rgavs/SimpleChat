package SimpleChatServer;

import java.io.*;
import java.util.*;

import ocsf.server.*;
import common.*;

/**
 * This class modifies EchoServer to complete to begin Chat phase 2.
 * It uses messages from Client to Server that are instances
 * of command classes that extend the class ServerMessageHandler.
 * This class delegates responsibility for handling a message to the
 * message itself.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Laganiegravere
 * @author Franccedilois Beacutelanger
 * @author Paul Holden
 * @author Chris Nevison
 * @version February 2012
 */
public class EchoServer1 extends AbstractServer {
    //Class variables *************************************************
    private boolean closed;
    private ChatIF myServerUI;
    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;
    private ArrayList<Channel> channels;
    private HashMap<String, String> accounts;//added by Shouheng

    //Constructors ****************************************************

    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer1(int port, ChatIF serverUI) {
        super(port);
        myServerUI = serverUI;
        closed = false;

        accounts = new HashMap<>();//added by Shouheng
        accounts.put("guest", "123");//added by Shouheng.

        initializeChannels();
        try {
            listen();
        } catch (IOException e) {
            serverUI().display("ERROR - Could not listen for clients!");
        }
    }

    public ChatIF serverUI() {
        return myServerUI;
    }

    private void initializeChannels() {
        channels = new ArrayList<>();
    }

    //Instance methods ************************************************

    /**
     * This method handles any messages received from the client.
     *
     * @param msg    The message received, an instance of a subclass of ServerMessageHandler
     * @param client The connection from which the message originated.
     */
    
    public void handleMessageFromClient(Object msg, ConnectionToClient client){
    	handleMessageFromClient(msg.toString(), client);
    	
    }//end handleMessageFromClient
    
    public void handleMessageFromClient(String msg, ConnectionToClient client) {
        if (msg.contains("##")) {
            String[] list = msg.split("##");
            String name = list[1];
            ConnectionToClient monitor = getConnection(name, getClientConnections());
            if (monitor != null) {
                sendToMonitor(list[0], monitor);
            }
        } else if (msg.startsWith("#checkmonitor")) {
            String[] list = msg.split(" ");
            ConnectionToClient monitor = getConnection(list[1], getClientConnections());
            if (monitor == null) {
                try {
                    client.sendToClient("$$" + list[1] + " is not connected. Try another one.");
                } catch (Exception ex) {
                }
            } else {
                try {
                    client.sendToClient("$$$" + list[1] + " will monitor for you.");
                } catch (Exception ex) {
                }
            }
        } else {
            ServerStringMessageHandler handler = new ServerStringMessageHandler (msg);
            handler.setServer(this);
            handler.setConnectionToClient(client);
            handler.handleMessage();
        }
    }

    public void sendToMonitor(String mess, ConnectionToClient monitor) {
        try {
            monitor.sendToClient(mess);
        } catch (Exception ex) {
        }
    }

    /**
     * Returns the connection to the person who will monitor the message
     *
     * @param id
     * @param allClients
     * @return Connection to the monitor
     */
    public ConnectionToClient getConnection(String id, Thread[] allClients) {
        for (int i = 0; i < allClients.length; i++) {
            ConnectionToClient client = (ConnectionToClient) allClients[i];
            String username = (String) client.getInfo("id");
            if (username.equals(id))
                return client;
        }
        return null;
    }

    public void addChannel(Channel chl) {
        channels.add(chl);
    }

    public void removeChannel(String channelName) {
        if (getChannel(channelName) != null) {
            channels.remove(getChannel(channelName));
        }
    }

    public ArrayList<Channel> enumerateChannels() {
        ArrayList<Channel> copy = new ArrayList<>();
        for (Channel chan : channels) {
            copy.add(chan);
        }
        return copy;
    }

    public Channel getChannel(String chan) {
        for (int i = 0; i < channels.size(); i++) {
            Channel chl = channels.get(i);
            if (chl.getChannelName().equals(chan))
                return chl;
        }
        serverUI().display("Requested channel " + chan + " does not exist!");
        return null;
    }

    /**
     * @author Shouheng Wu
     * <p>
     * This method checks whether the given ID is already an existing user
     */
    public boolean checkExistingAccount(String id) {
        return accounts.containsKey(id);
    }//end checkExistingAccount

    /**
     * @author Shouheng Wu
     * <p>
     * This method creates a user account by adding a id/password combination to the HashMap accounts
     */
    public void setNewAccount(String id, String password) {
        accounts.put(id, password);
    }//end class

    /**
     * @author Shouheng Wu
     * <p>
     * This method returns true if the provided password is correct
     */
    public boolean checkPassword(String id, String password) {
        return accounts.get(id).equals(password);
    }//end checkPassword

    public void handleMessageFromUser(String message) {
        if (message.charAt(0) == '@') {
            sendToChannel(message.substring(1));
        } else if (message.charAt(0) != '#') {
            sendToAllClients("SERVER MSG>" + message);
        } else {
            message = message.substring(1);
            createAndDoCommand(message);
        }
    }//end handleMessageFromUser
    
    private void sendToChannel(String message) { //for sending server msg
  	  String channelName;
  	  int indexBlank = message.indexOf(' ');
  	  if(indexBlank == -1) {
  			serverUI().display("Invalid input");
  	  }
  	  else {
  		  channelName = message.substring(0, indexBlank);
  		  String msg = message.substring(indexBlank + 1);
  		  Channel chl = getChannel(channelName);
  		  if (chl == null) 
  		  		serverUI().display("Channel with the name " + channelName + " does not exist.");
  		  	else {
  		  		chl.sendServerMsg(msg);
  		  		serverUI().display(message);
  		  	}
  	  }
    }
    
    public void sendToChannel(String message, ConnectionToClient sender) {
  	  String channelName;
  	  int indexBlank = message.indexOf(' ');
  	  if(indexBlank == -1) {
  		  try {
  			sender.sendToClient("Invalid input");
  		} catch (IOException e) {

  		}
//  		  channelName = message;
//  		  String msg = "";
//  		  sendToChannelClients(channelName, msg,senderID);
  	  }
  	  else {
  		  channelName = message.substring(0, indexBlank);
  		  String msg = message.substring(indexBlank + 1);
  		  Channel chl = getChannel(channelName);
  		  if (chl != null && chl.isInChannel((String)sender.getInfo("id")))//if (getChannel(channelName).isInChannel((String)sender.getInfo("id")))
  			  sendToChannelClients(msg, channelName,(String)sender.getInfo("id"));
  		else
  			try {
  				sender.sendToClient("SERVER MSG> You are not in that channel");
  			} catch (IOException e) {

  			}
  	  }
    }
    
    private void sendToChannelClients(Object msg, String channel,String senderID) {
	  	Channel chl = getChannel(channel);
	  	if (chl == null) {
	  		serverUI().display("Channel with the name " + channel + " does not exist.");
	  		}
	  	else {
	  		 chl.sendToClients(msg, senderID);
		  }
			  
  		} 

//    public void sendToChannel(String message) {
//        String channelName = message.split(" ")[0];
//        if (message.indexOf(' ') < 0)
//            serverUI().display("Invalid input.");
//        else if (!channels.contains(channelName))
//            serverUI().display("No channel exists with the name: " + channelName);
//        else
//            channels.get(channels.indexOf(channelName)).sendToClients(message.substring(message.indexOf(' ')), "SERVER");
//    }

//    public void sendToChannel(String message, ConnectionToClient sender) { //send messages to channel from clients of channel
//        String channelName = message.split(" ")[0];
//        if (message.indexOf(' ') < 0) {
//            try {
//                sender.sendToClient("Invalid input. Please enter in the form: \'#<channelName> <message>\'");
//            } catch (IOException e) {
//                serverUI().display("IOException: " + e.getMessage());
//            }
//        } else if (getChannel(channelName) != null) {
//            String msg = message.substring(message.indexOf(' '));
//            Channel chan = getChannel(message.split(" ")[0]);
//            if (chan != null) {
//                if (!chan.isInChannel((String) sender.getInfo("id"))) {
//                    try {
//                        sender.sendToClient("SERVER MSG> You are not in that channel");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    chan.sendToClients(msg, (String) sender.getInfo("id"));
//                }
//            } else {
//                try {
//                    sender.sendToClient("SERVER MSG> No channel named \'" + chan.getChannelName() + "\' exists.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    public void createAndDoCommand(String message) {
        String commandStr;
        int indexBlank = message.indexOf(' ');
        if (indexBlank == -1) {
            commandStr = "SimpleChatServer." + message;
            message = "";
        } else {
            commandStr = "SimpleChatServer." + message.substring(0, indexBlank);
            message = message.substring(indexBlank + 1);
        }
        try {
            Class[] param = {String.class, EchoServer1.class};
            ServerCommand cmd = (ServerCommand) Class.forName(commandStr).getConstructor(param).newInstance(message, this);
            cmd.doCommand();
        } catch (Exception ex) {
            serverUI().display("\nNo such command " + commandStr + "\nNo action taken.");
        }

    }//end createAndDoCommand

    /**
     * This method overrides the one in the superclass.  Called
     * when the server starts listening for connections.
     */
    protected void serverStarted() {
        closed = false;
        serverUI().display("Server listening for connections on port " + getPort());
        sendToAllClients("SERVER MSG> Server has started listening.");
    }

    /**
     * This method overrides the one in the superclass.  Called
     * when the server stops listening for connections.
     */
    protected void serverStopped() {
        closed = true;
        serverUI().display("Server has stopped listening for connections.");
        sendToAllClients("SERVER MSG> Server has stopped listening.");
    }

    boolean isClosed() {
        return closed;
    }

    void setClosed(boolean status) {
        closed = status;
    }//end setStatus

    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        sendToAllClients("SERVER MSG>  " + client.getInfo("id") + " has disconnected.");
    }//end clientException

    protected synchronized void clientConnected(ConnectionToClient client) {
        //sendToAllClients("SERVER MSG> A client has connected.");
    }//end clientConnected

    protected synchronized void clientDisconnected(ConnectionToClient client) {
        sendToAllClients("SERVER MSG> A client has disconnected.");
    }//end clientConnected
}
