package SimpleChatServer;

import java.io.*;

import common.ChatIF;

public class ServerConsole implements ChatIF {

    final public static int DEFAULT_PORT = 5555;

    // The instance of the server that is connected to this console
    EchoServer1 server;

    public ServerConsole(int port) {
        try {
            server = new EchoServer1(port, this);
        } catch (Exception e) {
            System.out.println("Error: Can't listen to clients.");
        }
    }//end ServerConsole()

    public void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while (true) {
                message = fromConsole.readLine();
                server.handleMessageFromUser(message);
            }
        } catch (Exception ex) {
            System.out.println
                    ("Unexpected error while reading from console!");
        }//end try-catch block
    }//end accept()

    public EchoServer1 getServer() {
        return server;
    }

    public void display(String message) {
        System.out.println("> " + message);
    }//end display()

    public static void main(String[] args) {
        int port;  //The port number

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch (Throwable t) {
            port = DEFAULT_PORT; //Set port to 5555
        }

        ServerConsole sconsole = new ServerConsole(port);
        sconsole.accept();  //Wait for console data
    }//end main()

 }