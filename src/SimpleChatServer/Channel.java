package SimpleChatServer;

import java.util.*;
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
	 * Client requested blocks: HashMap of associations 0..* of 
	 * <code> ConnectionToClient </code> to <code> ConnectionToClient </code>
	 */
	private HashMap<String,String[]> blocks;
	
	/**
	 * Server to which the channel belongs
	 */
	private AbstractServer server;
	
	public Channel(String channelName, AbstractServer thisServer, ArrayList<ConnectionToClient> myClients) {
		server = thisServer;
		this.channelName = channelName; 
		clients = myClients;
	}
	
	public Channel(String stringFromUser, AbstractServer thisServer) {
		channelName = setupChannelName(stringFromUser);
		String[] users = parseChannelUsers(stringFromUser);
		Thread[] allClients = thisServer.getClientConnections();
		clients = new ArrayList<ConnectionToClient>(users.length);
		setupChannelUsers(users, allClients);
	}
	
	/**
	 * @param users Array of type String of usernames 
	 * @param allClients Array of all ConnectionToClient clients
	 */
	private void setupChannelUsers(String[] users, Thread[] allClients) {
		for (String user1 : users) {
			System.out.println(user1);
		}
		here: for (int i = 0; i<users.length; i++) {
			for (int k =0; i < allClients.length; k++) {
				ConnectionToClient client = (ConnectionToClient) allClients[k];
				String username = (String) client.getInfo("id");
				String user = users[i];
				if (user == null) 
					break here; 
				else {
					if (user.equals(username)) {
					clients.add (client);
					break here;
					}
				}
			}	
		}
		for (ConnectionToClient client : clients) {
			System.out.println(client);
		}
	}
	
	/**
	 * Parses the string from the user for the name of the channel and returns it.
	 * @param str	from user
	 * @return names of the channel
	 */
	private String setupChannelName(String str) {
		return str.split(",")[0];
	}
	
	/**
	 * Takes the string from a user and parses it for the username of each user 
	 * within the string.
	 * @param stringFromUser    stringFromUser: channelName, user1, user2...
	 * @return 					array of strings with usernames
	 */
	private String[] parseChannelUsers(String stringFromUser) {
		return Arrays.copyOfRange(stringFromUser.split(","),1,stringFromUser.split(",").length-1);
	}
	
	public String getChannelName() {
		return channelName;
	}
	
	public int numClients(){
		return clients.size();
	}
	
	public AbstractServer getServer() {
		return server;
	}
	
	public Object[] enumerateClients() {
		return clients.toArray();
	}
}
