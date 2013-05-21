package server.operations;

import server.ClientComms;
import server.databases.H2Users;
import server.encryption.KeyDerivation;
import server.users.User;
import server.users.friends.FriendsList;
import server.users.friends.Interval;
import server.users.friends.Permissions;
import client.model.linking.Authentication;

public class UserOperations {
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	private static final String ALGORITHM = "AES";

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

	/* Receive username and check whether the username is acceptable
	 * If the username is accepted then notify the user and proceed to register
	 * the new user.
	 * Else notify the user that the username is invalid. */
	public static User registerAttempt(ClientComms comms) {
			
		String username = comms.fromClient();
			
		boolean	 usernameAccepted = !checkUserExists(username);
			
		if (!usernameAccepted) {
			comms.toClient(FALSE);
			return null;
		}
		
		comms.toClient(TRUE);
		
		return register(comms, username);
		
	}
	

	/* Receive the username and password.
	 * Store the information.
	 * Receive the generated keys for user's security levels.
	 * Store. */
	private static User register(ClientComms comms, String username) {
		
		String password = comms.fromClient();
		
		String key = comms.fromClient();
		String secret = comms.fromClient();
		String uid = comms.fromClient();

		User user = Authentication.createUser(username, password, comms, key, secret, uid);
		
		return user;
		
	}
	
}
