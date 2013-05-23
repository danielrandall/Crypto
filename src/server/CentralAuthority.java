package server;
import com.dropbox.client2.session.Session;

import server.operations.ServerDropboxOperations;
import server.operations.ServerFileOperations;
import server.operations.UserOperations;
import server.users.Authentication;
import server.users.User;

/* TODO: update file list */

public class CentralAuthority {
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String FRIEND_REQUEST = "3";
	private static final String REMOVE_FILE = "4";
	private static final String ACCEPT_FRIEND_REQUEST = "5";
	
	private static final String GET_SECURITY_LEVEL = "50";
	private static final String GET_FRIENDS = "51";
	private static final String GET_FRIEND_REQUESTS = "52";
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	private static final String EXIT_CODE = "999";
	
	public static void options(User user, ClientComms comms) {
		
		comms.toClient(user.getAccessTokens().key);
		comms.toClient(user.getAccessTokens().secret);
		
		while(true) {
			
			/* Get central option from user */
			String decision = comms.fromClient();
			
			if (decision == null || decision.equals(EXIT_CODE))
				return;
		
			if (decision.equals(UPLOAD_FILE))
				uploadEncryptedFile(user.getUsername(), comms);
			
			if (decision.equals(DOWNLOAD_FILE))
				downloadDecryptedFile(user.getUsername(), comms);
		
			if (decision.equals(FRIEND_REQUEST))
				friendRequest(user.getUsername(), comms);
			
			if (decision.equals(REMOVE_FILE))
				removeFile(user.getUsername(), comms);
			
			if (decision.equals(GET_SECURITY_LEVEL))
				getSecurityLevel(comms);
			
			if (decision.equals(GET_FRIENDS))
				getFriends(user.getUsername(), comms);
			
			if (decision.equals(GET_FRIEND_REQUESTS))
				getFriendRequests(user.getUsername(), comms);
			
			if (decision.equals(ACCEPT_FRIEND_REQUEST))
				acceptFriendRequest(user.getUsername(), user.getSession(), comms);
		}
	
	}
	
	private static void friendRequest(String username, ClientComms comms) {
		
		boolean userExists = false;
		String usernameToAdd = null;
		
		usernameToAdd = comms.fromClient();
			
		userExists = UserOperations.checkUserExists(usernameToAdd);
			
		if (userExists)
			comms.toClient(TRUE);
		else {
			comms.toClient(FALSE);
			return;
		}
			
		int lowerBound = Integer.parseInt(comms.fromClient());   // DEAL WITH NON INT ENTRY
		int upperBound = Integer.parseInt(comms.fromClient());   // DEAL WITH NON INT ENTRY
		
		UserOperations.addRequest(username, usernameToAdd, lowerBound);
		
	}
	
	private static void acceptFriendRequest(String username, Session destSession, ClientComms comms) {
		
		String usernameToAccept = comms.fromClient();
		
		int securityLevel = UserOperations.getRequestLevel(usernameToAccept, username);
		
		/* Add user to friends list */
		UserOperations.addUserToFriendsList(username, usernameToAccept, securityLevel, securityLevel);
			
		User frienduser = Authentication.loadUser(usernameToAccept);
		Session sourceSession = frienduser.getSession();
			
			/* Share files with user */
		ServerDropboxOperations.shareFilesWithFriend(usernameToAccept,
											destSession, username,
			                                      sourceSession);
			
		
	}

	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(String username, ClientComms comms) {
		
		int securityLevel = Integer.parseInt(comms.fromClient());
		
		ServerFileOperations.addFile(securityLevel, username, comms);
		
	}
	
	/* The user is requested for the file to be downloaded and the location for
	 * it to be downloaded to. The file is downloaded and then decrypted. */
	private static void downloadDecryptedFile(String username, ClientComms comms) {
		
		String rev = comms.fromClient();
		
		Object[] fileInfo = ServerFileOperations.getFileInfo(rev);
		
		byte[] iv = (byte[]) fileInfo[0];
		int securityLevel = (Integer) fileInfo[1];
		
		comms.sendBytes(iv, iv.length);
		comms.toClient(Integer.toString(securityLevel));
		
	}
	
	
	/* Removes the deleted file from the server database */
	private static void removeFile(String username,  ClientComms comms) {
		
		/* Receive unique file identifier from client */
		String fileRev = comms.fromClient();
		
		ServerFileOperations.removeFile(fileRev);
		
	}
	
	
	private static void getSecurityLevel(ClientComms comms) {
		
		String fileRev = comms.fromClient();
		
		int securityLevel = ServerFileOperations.getSecurityLevel(fileRev);

		comms.toClient(Integer.toString(securityLevel));
		
	}
	
	
	public static void getFriends(String username, ClientComms comms) {
		
		String[][] friends = UserOperations.getFriends(username);
		
		int length = friends.length;
		comms.toClient(Integer.toString(length));
		
		for (int i = 0; i < length; i++) {
			
			comms.toClient(friends[i][0]);
			comms.toClient(friends[i][1]);
			
		}
		
	}
	
	
	public static void getFriendRequests(String username, ClientComms comms) {
		
		String[][] friendRequests = UserOperations.getFriendRequests(username);
		
		int size = friendRequests.length;
		comms.toClient(Integer.toString(size));
		
		for (int i = 0; i < size; i++) {
			
			comms.toClient(friendRequests[i][0]);
			comms.toClient(friendRequests[i][1]);
			
		}
		
	}

	
}