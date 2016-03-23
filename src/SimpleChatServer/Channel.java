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
     * Plaintext user names, only created from parse usernames
     */
    private ArrayList<String> usernames;

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
        usernames = parseChannelUsers(stringFromUser);
        clients = new ArrayList<ConnectionToClient>(usernames.size());
        setupChannelUsers(usernames, thisServer.getClientConnections());
    }

    /**
     * @param users      Array of type String of usernames
     * @param allClients Array of all ConnectionToClient clients
     */
    private void setupChannelUsers(ArrayList<String> users, Thread[] allClients) {
        for (String user1 : users) {
            System.out.println(user1);
        }
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
    private ArrayList<String> parseChannelUsers(String stringFromUser) { //(stringFromUser: channelName, user1, user2...
        if (usernames.size() < 0)
            usernames = new ArrayList<>();
        String[] unames = stringFromUser.split(", ");
        for (String user : unames) {
            usernames.add(user);
        }
        return usernames;
    }

    public Boolean removeClient(String user) {
        if (!usernames.contains(user))
            return false;
        return usernames.remove(user);
    }

    public String getChannelName() {
        return channelName;
    }

    public int numClients() {
        return clients.size();
    }

    public AbstractServer getServer() {
        return server;
    }

    public ArrayList<ConnectionToClient> enumerateClients() {
        return clients;
    }
}
