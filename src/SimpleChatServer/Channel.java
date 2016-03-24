package SimpleChatServer;

import java.util.ArrayList;

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
    private AbstractServer server;

    /**
     * Blocks/exclusions in where to send messages
     */
    
    public Channel(String channelName, AbstractServer thisServer, ArrayList<ConnectionToClient> myClients) {
        server = thisServer;
        this.channelName = channelName;
        clients = myClients;
    }

    public Channel(String stringFromUser, AbstractServer thisServer) {
        channelName = setupChannelName(stringFromUser);
        clients = new ArrayList<>(parseChannelUsers(stringFromUser).size());
        setupChannelUsers(parseChannelUsers(stringFromUser), thisServer.getClientConnections());
    }

    /**
     * @param users      Array of type String of usernames
     * @param allClients Array of all ConnectionToClient clients
     */
    private void setupChannelUsers(ArrayList<String> users, Thread[] allClients) {
        users.forEach(System.out::println);
        here:
        for (int i = 0; i < users.size(); i++) {
            for (int k = 0; i < allClients.length; k++) {
                ConnectionToClient client = (ConnectionToClient) allClients[k];
                String username = (String) client.getInfo("id");
                String user = users.get(i);
                if (user == null)
                    break here;
                else {
                    if (user.equals(username)) {
                        clients.add(client);
                        break here;
                    }
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
                clients.add((ConnectionToClient) thr);
                System.out.println(client + " successfully added to Channel!");
                return;
            }
        }
        System.out.println(client + " was unable to be found. Channel clients remain unchanged");
    }

    /**
     * Parses the string from the user for the name of the channel and returns it.
     *
     * @param str from user
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
            if (cli.getInfo("id").equals(user))
                clients.remove(cli);
    }

    public String getChannelName() {
        return channelName;
    }

    public int numClients() {
        return clients.size();
    }

    private AbstractServer getServer() {
        return server;
    }

    public ArrayList<ConnectionToClient> enumerateClients() {
        return clients;
    }
}
