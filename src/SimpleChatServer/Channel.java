package SimpleChatServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;


public class Channel {

    //instance variables
    /**
     * The thread group associated with client threads. Each member of the
     * thread group is a <code> ConnectionToClient </code>.
     */
    private ArrayList<ConnectionToClient> clients;

     /**
     * Name of the channel given by integer.
     */
    private String channelName;

    /**
     * Server to which the channel belongs
     */
	private EchoServer1 server;

    /**
     * Blocks/exclusions in where to send messages
     */
    
    public Channel(String channelName, AbstractServer thisServer, ArrayList<ConnectionToClient> myClients) {
        server = (EchoServer1) thisServer;
        this.channelName = channelName;
        clients = myClients;
    }

	public Channel(String stringFromUser, EchoServer1 thisServer) {
        channelName = setupChannelName(stringFromUser);
        clients = new ArrayList<>(parseChannelUsers(stringFromUser).size());
        setupChannelUsers(parseChannelUsers(stringFromUser),  (ConnectionToClient[]) thisServer.getClientConnections());
    }

    /**
     * @param users      ArrayList of type String of usernames
     * @param allClients ArrayList of all ConnectionToClient clients
     */
    private void setupChannelUsers(ArrayList<String> users, ConnectionToClient[] allClients) {
        users.forEach(System.out::println);
        for (String usr : users) {
            for (ConnectionToClient cli : allClients) {
                if(usr.equals(cli.getId())){
                    clients.add(cli);
                    break;
                }
            }
        }
        clients.forEach(System.out::println);
    }

    /**
     * Finds ClientConnection, casts to ConnectionToClient, and adds to <code>users</code> and <code>clients</code>.
     */
    public void addClient(String client) {
        for (Thread thr : getServer().getClientConnections()) {
            if (((ConnectionToClient) thr).getInfo("id").equals(client)) {
                try{
                    clients.add((ConnectionToClient) thr);
                    ((ConnectionToClient) thr).sendToClient("You have been added to the channel " + channelName);
                    return;
                } catch(IOException e) {
                    server.serverUI().display("error in sending message");
                }
            }
        }
        System.out.println(client + " was unable to be found. Channel clients remain unchanged");
    }

    /**
     * Parses the string from the user for the name of the channel and returns it.
     * @param str
     * @return names of the channel
     */
    private String setupChannelName(String str) {
        return channelName = str.split(",")[0];
    }

    /**
     * Takes the string from a user and parses it for the username of each user
     * within the string.
     *
     * @param stringFromUser comma-separated list of usernames
     * @return array of strings with usernames
     */
    private ArrayList<String> parseChannelUsers(String stringFromUser) { // channelName, user1, user2...
        ArrayList<String> ret = new ArrayList<>(stringFromUser.split(",").length - 1);
        for (int i = 1; i < ret.size(); i++)
            ret.add(stringFromUser.split(",")[i].trim());
        return ret;
    }

    public void removeClient(String user) {
        for (ConnectionToClient cli : clients)
            if (cli.getInfo("id").equals(user)){
                clients.remove(cli);
            }
    }
    
    public boolean isInChannel(String username){
        for(ConnectionToClient user : clients){
            if(user.getInfo("id").equals(username))
                return true;
        }
        return false;
    }

    public String getChannelName() {
        return channelName;
    }

    public int numClients() {
        return clients.size();
    }

    private EchoServer1 getServer() {
        return server;
    }

    public ArrayList<ConnectionToClient> enumerateClients() {
        return clients;
    }
    
    //Send messages to all channel clients from one channel client
    public void sendToClients(Object msg, String senderID) {
        //Object[] channelClients = chl.enumerateClients();
        for (ConnectionToClient client : clients) {
            try {
                ((ConnectionToClient) client).sendToClient(senderID + " " + channelName + "> " + msg);
            } catch (Exception ex) {
                getServer().serverUI().display("Error in sending message");
            }
        }
    }
}
