package server.operations;

import java.util.List;
import java.util.Map;

import server.ClientComms;
import server.databases.H2KeyUpdates;
import server.databases.H2Requests;
import server.databases.H2Users;
import server.users.Authentication;
import server.users.User;
import server.users.friends.FriendsList;
import server.users.friends.Interval;
import server.users.friends.Permissions;

public class UserOperations {
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	public static final int NUMBER_SECURITY_LEVELS = 5;
	public static final int HIGHEST_SECURITY_LEVEL = 1;

	private static H2Users database = new H2Users();
	private static H2Requests requestDatabase = new H2Requests();
	private static H2KeyUpdates updateDatabase = new H2KeyUpdates();
	
	
	public static void main (String[] args) {
		
		FriendsList fl = (FriendsList) database.getUser("username").get(H2Users.FRIENDS);
		fl.removeFriend("daniel");
		fl.removeFriend("katie");
		
		database.updateFriends("username", fl);
		
	}
	
	
	public static boolean checkUserExists(String username) {
		
		return database.checkUserExists(username);
		
	}


	public static void addUserToFriendsList(String username,
			             String usernameToAdd, int lowerBound,
			             					int upperBound) {
		
		FriendsList friends = (FriendsList)database.getUser(username).get(H2Users.FRIENDS);
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
	 * Receive the generated keys for user's security levels.UserOperations.getRequestLevel(sourceUsername, destUsername);
	 * Store. */
	private static User register(ClientComms comms, String username) {
		
		//String password = comms.fromClient();
		
		byte[] passwordBytes = comms.getBytes();
		
		String key = comms.fromClient();
		String secret = comms.fromClient();
		String uid = comms.fromClient();
		
		storeSymmetricKeys(username, HIGHEST_SECURITY_LEVEL + 1, false, comms);
		
		/* Receive the user's public key */
		byte[] publicKey = comms.getBytes();

		User user = Authentication.createUser(username, passwordBytes, comms, key, secret, uid, publicKey);
		
		return user;
		
	}
	
	/* Given the number of keys it is to receive.
	 * The variable update indicates whether the keys are being updated - false for new entry, true for update. */
	public static void storeSymmetricKeys(String username,
			int highestSecurityLevel, boolean update, ClientComms comms) {
		
		int numKeys = NUMBER_SECURITY_LEVELS - highestSecurityLevel + 1;
		
		byte[][] encryptedKeys = new byte[numKeys][];
		byte[][] ivs = new byte[numKeys][];
		int[] securityLevels = new int[numKeys];
		
		for (int i = 0; i < numKeys; i++) {
			encryptedKeys[i] = comms.getBytes();
			ivs[i] = comms.getBytes();
			securityLevels[i] = highestSecurityLevel + i;
		}
		
		KeyOperations.storeKeys(username, encryptedKeys, securityLevels, ivs, update);
	}
	
	
	public static String[][] getFriends(String username) {
		
		Map<String, Object> data = database.getUser(username);
		FriendsList friendsList = (FriendsList) data.get(H2Users.FRIENDS); 
		
		return friendsList.getAllFriends();
		
	}
	
	/* Returns each username of the given user's friends along with their
	 * their respective allocated security level. */
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
	
	
	public static int getFriendSecurityLevel(String username, String friend) {
		
		Map<String, Object> data = database.getUser(username);
		FriendsList friendsList = (FriendsList) data.get(H2Users.FRIENDS);
		
		return friendsList.getFriendPermissions(friend).getLowerBound();
		
	}
	
	public static void removeFriend(String username, String usernameToRevoke) {
		
		FriendsList friendsList = (FriendsList) database.getUser(username).get(H2Users.FRIENDS);
		friendsList.removeFriend(usernameToRevoke);
		
		database.updateFriends(username, friendsList);
		
	}
	
	/* Returns a user's public key */
	public static byte[] getPublicKey(String username) {
		
		Map<String, Object> data = database.getUser(username);
		byte[] publicKey = (byte[]) data.get(H2Users.PUBLIC_KEY); 
		
		return publicKey;
		
	}
	
	public static void addRequest(String sourceUser, String destUser,
			int securityLevel, byte[] key) {
		
		requestDatabase.addRequest(sourceUser, destUser, securityLevel, key);
		
	}
	

	public static void removeRequest(String sourceUser, String destUser) {
		
		requestDatabase.removeRequest(sourceUser, destUser);
		
	}
	
	/*
	public static int getRequestLevel(String sourceUser, String destUser) {
		
		return requestDatabase.getRequestLevel(sourceUser, destUser);
		
	}
	*/
	
	public static Object[] getRequestInfo(String sourceUser, String destUser) {

		Map<String, Object> data = requestDatabase.getRequestInfo(sourceUser, destUser);
		Object[] info = new Object[data.size()];
		
		info[0] = data.get(H2Requests.SECURITY_LEVEL);
		info[1] = data.get(H2Requests.KEY);
		
		return info;
	}
	
	
	public static void addUpdate(String sourceUser, String destUser,
			int securityLevel, byte[] key) {
		
		updateDatabase.addUpdate(sourceUser, destUser, securityLevel, key);
		
	}
	

	public static void removeUpdate(String sourceUser, String destUser) {
		
		updateDatabase.removeUpdate(sourceUser, destUser);
		
	}
	
	
	public static String[][] getUpdates(String username) {
		
		List<Map<String, Object>> requests = updateDatabase.getAllUpdatesForUser(username);
		int size = requests.size();
		
		String[][] friendRequests = new String[size][2];
		for (int i = 0; i < size; i++) {
			friendRequests[i][0] = (String) requests.get(i).get(H2KeyUpdates.SOURCE_USER);
			friendRequests[i][1] = Integer.toString((Integer) requests.get(i).get(H2KeyUpdates.SECURITY_LEVEL));
		}
		
		return friendRequests;
		
	}
	
	public static Object[] getUpdateInfo(String sourceUser, String destUser) {

		Map<String, Object> data = updateDatabase.getUpdateInfo(sourceUser, destUser);
		Object[] info = new Object[data.size()];
		
		info[0] = data.get(H2Requests.SECURITY_LEVEL);
		info[1] = data.get(H2Requests.KEY);
		
		return info;
	}
	
}
