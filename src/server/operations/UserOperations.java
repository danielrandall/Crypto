package server.operations;

import java.util.List;
import java.util.Map;

import server.ClientComms;
import server.databases.H2Requests;
import server.databases.H2Users;
import server.encryption.KeyDerivation;
import server.users.Authentication;
import server.users.User;
import server.users.friends.FriendsList;
import server.users.friends.Interval;
import server.users.friends.Permissions;

public class UserOperations {
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	private static final String ALGORITHM = "AES";

	private static H2Users database = new H2Users();
	private static H2Requests requestDatabase = new H2Requests();
	
	
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
	
	
	public static String[][] getFriends(String username) {
		
		Map<String, Object> data = database.getUser(username);
		FriendsList friendsList = (FriendsList) data.get(H2Users.FRIENDS); 
		
		return friendsList.getAllFriends();
		
	}
	
	public static String[][] getFriendRequests(String username) {
		
		List<Map<String, Object>> requests = requestDatabase.getAllRequestsForUser(username);
		int size = requests.size();
		
		String[][] friendRequests = new String[size][2];
		for (int i = 0; i < size; i++) {
			friendRequests[i][0] = (String) requests.get(i).get(H2Requests.SOURCE_USER);
			friendRequests[i][1] = Integer.toString((Integer) requests.get(i).get(H2Requests.SECURITY_LEVEL));
		}
		
		return friendRequests;
		
	}
	
	public static void addRequest(String sourceUser, String destUser, int securityLevel) {
		
		requestDatabase.addRequest(sourceUser, destUser, securityLevel);
		
	}
	

	public static void removeRequest(String sourceUser, String destUser) {
		
		requestDatabase.removeRequest(sourceUser, destUser);
		
	}
	
	public static int getRequestLevel(String sourceUser, String destUser) {
		
		return requestDatabase.getRequestLevel(sourceUser, destUser);
		
	}
	
	
}
