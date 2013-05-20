package server;

import client.model.linking.Authentication;
import client.model.linking.databases.H2Users;
import client.model.users.User;
import client.model.users.friends.FriendsList;
import client.model.users.friends.Interval;
import client.model.users.friends.Permissions;

public class UserOperations {
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";

	private static H2Users database = new H2Users();
	
	
	public static boolean checkUserExists(String username) {
		
		return database.checkUserExists(username);
		
	}


	public static void addUserToFriendsList(String username,
			             String usernameToAdd, int lowerBound,
			             					int upperBound) {
		
		FriendsList friends = (FriendsList)database.getUser(username).get("friends");
		Permissions p = new Interval(lowerBound, upperBound);
		friends.addFriend(usernameToAdd, p);
		
		database.updateFriends(username, friends);
	}


	public static User register(ClientComms comms) {
			
		String username = comms.fromClient();
			
		boolean	usernameAccepted = !checkUserExists(username);
			
		if (!usernameAccepted) {
			comms.toClient(FALSE);
			return null;
		}
		
		comms.toClient(TRUE);
		
		String password = comms.fromClient();

		return Authentication.authenticate(username, password, comms);
		
	}
	
		

}
