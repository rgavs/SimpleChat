package SimpleChatClient;

import client.ChatClient1;
import common.ChatIF;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole
 *
 * @author Franccedilois Beacutelanger
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Laganiegravere
 * @version July 2000
 *          <p>
 *          <p>
 *          Modified by Shouheng Wu to accommodate password feature, and to
 *          provide a default guest user that can be used to create new accounts
 *          February 28, 2016
 */
public class ClientConsole implements ChatIF {
    //Class variables *************************************************

    /**
     * The default port to connect on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Instance variables **********************************************

    /**
     * The instance of the client that created this ConsoleChat.
     */
    private ChatClient1 client;

    //Constructors ****************************************************

    /**
     * Constructs an instance of the ClientConsole UI.
     *
     * @param host The host to connect to.
     * @param port The port to connect on.
     */
    private ClientConsole(String host, int port, String id, String password) {
        try {
            client = new ChatClient1(host, port, this, id, password);
        } catch (Exception e) {
            System.out.println("Error: Can't setup connection! Terminating client.");
            System.exit(1);
        }
    }


    //Instance methods ************************************************

    /**
     * This method waits for input from the console.  Once it is
     * received, it sends it to the client's message handler.
     */
    private void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                client.handleMessageFromClientUI(message);
            }
        } catch (Exception ex) {
            System.out.println
                    ("Unexpected error while reading from console!");
        }
    }

    /**
     * This method overrides the method in the ChatIF interface.  It
     * displays a message onto the screen.
     *
     * @param message The string to be displayed.
     */
    public void display(String message) {
        System.out.println("> " + message);
    }


    //Class methods ***************************************************

    /**
     * This method is responsible for the creation of the Client UI.
     *
     * @param args The host to connect to.
     */
    public static void main(String[] args) {
        String host, id = "", password = "";
        int port;  //The port number

        try {
            id = args[0];
            password = args[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You can't login without providing both the username and the password.");
            System.exit(-1);
        }
        System.out.println("Logging in as " + id);

        try {
            host = args[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
        }

        try {
            port = Integer.parseInt(args[3]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            port = DEFAULT_PORT;
        }
        ClientConsole chat = new ClientConsole(host, port, id, password);
        chat.accept();  //Wait for console data
    }
}
//End of ConsoleChat class
