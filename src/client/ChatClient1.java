// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package client;

import ocsf.client.*;
import common.*;

import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 * <p>
 * Modified to complete exercises E50 and E51 Uses reflection to create
 * ClientCommand subclasses for each command
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Laganiegrave
 * @author Franccedilois Beacutelanger
 * @author Chris Nevison
 * @version July 2012
 */

/**
 * Modified by Shouheng Wu to accommodate password protection for user accounts
 * February 28, 2016
 */
public class ChatClient1 extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable.  It allows the implementation of
     * the display method in the client.
     */
    private ChatIF myClientUI;

    private String myId;
    private String myPassword;
    private String monitor;

    //Constructors ****************************************************

    /**
     * Constructs an instance of the chat client.
     *
     * @param host     The server to connect to.
     * @param port     The port number to connect on.
     * @param clientUI The interface type variable.
     */

    public ChatClient1(String host, int port, ChatIF clientUI, String id, String password) {
        super(host, port); //Call the superclass constructor
        myClientUI = clientUI;
        myId = id;
        myPassword = password;
        monitor = null;
        try {
            openConnection();
            sendToServer("#login " + id + " " + password);
        } catch (Exception e) {
            clientUI.display("Could not open connection and/or send message to server.  Terminating client.");
            quit();
        }
    }

    public ChatIF clientUI() {
        return myClientUI;
    }

    public void setId(String newid) {
    	myId = newid;
    }
    
    public String getId() {
        return myId;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String name) {
        monitor = name;
    }

    public String getPassword() {
        return myPassword;
    }

    //Instance methods ************************************************

    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        if(msg.toString().startsWith("$$$")){
    	    clientUI().display(msg.toString().substring(3));
        }
        else if(msg.toString().startsWith("$$")){
        	setMonitor(null);
        	clientUI().display(msg.toString().substring(2));
        }
        else if (!msg.toString().startsWith("$$") && getMonitor() != null){
    	    clientUI().display(msg.toString());
    	    sendMessageToServer(msg.toString()+"##"+getMonitor());
        }
        else{
        	clientUI().display(msg.toString());
        }
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {
        if (message.charAt(0) != '#') {
            sendMessageToServer(message);
        } else {
            message = message.substring(1);
            createAndDoCommand(message);
        }
    }

    /**
     * This method handles a simple string message, not a command
     *
     * @param message The message from the UI
     */
    private void sendMessageToServer(String message) {
        if (isConnected()) {
            //ServerStringMessageHandler mess = new ServerStringMessageHandler(message);
            try {
                sendToServer(message);
            } catch (IOException e) {
                clientUI().display("IOException: " + e + "\nCould not send message to server.  Terminating client.");
            }
        } else {
            clientUI().display("Not connected to a server. Must login before sending a message.");
        }
    }

    /**
     * This method handles a command message after the '#' has been stripped
     * It uses reflection to create an instance of a subclass of ClientCommand whose name
     * is given by the first token in the message string
     *
     * @param message the command string (after '#' is stripped)
     */
    private void createAndDoCommand(String message) {
        String commandStr;
        int indexBlank = message.indexOf(' ');
        if (indexBlank == -1) {
            commandStr = "client." + message;
            message = "";
        } else {
            commandStr = "client." + message.substring(0, indexBlank);
            message = message.substring(indexBlank + 1);
        }

        try {
            Class[] param = {String.class, ChatClient1.class};
            ClientCommand cmd = (ClientCommand) Class.forName(commandStr).getConstructor(param).newInstance(message, this);
            cmd.doCommand();
        } catch (Exception ex) {
            clientUI().display("\nNo such command " + commandStr + "\nNo action taken.");
        }
    }

    public void connectionException(Exception ex) {
        //clientUI().display("Connection exception. Terminating this client");//Modified by Shouheng
    }

    public void connectionClosed() {
        //clientUI().display("Connection closed.");
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }
}// End of ChatClient class
