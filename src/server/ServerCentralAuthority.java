package server;
import java.util.ArrayList;
import java.util.List;

import com.dropbox.client2.session.Session;

import server.operations.ServerDropboxOperations;
import server.operations.ServerFileOperations;
import server.operations.UserOperations;
import server.users.Authentication;
import server.users.User;

/* TODO: update file list */

public class ServerCentralAuthority {
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String FRIEND_REQUEST = "3";
	private static final String REMOVE_FILE = "4";
	private static final String ACCEPT_FRIEND_REQUEST = "5";
	private static final String IGNORE_FRIEND_REQUEST = "6";
	
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
				uploadEncryptedFile(user.getUsername(), user.getSession(), comms);
			
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
			
			if (decision.equals(IGNORE_FRIEND_REQUEST))
				ignoreFriendRequest(user.getUsername(), comms);
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
	
	/* sourceUsername is the user who made the request 
	 * destUsername is the user who has accepted the request. */
	private static void acceptFriendRequest(String destUsername, Session destSession, ClientComms comms) {
		
		String sourceUsername = comms.fromClient();
		
		int securityLevel = UserOperations.getRequestLevel(sourceUsername, destUsername);
		
		/* Add user to friends list */
		UserOperations.addUserToFriendsList(sourceUsername, destUsername, securityLevel, securityLevel);
			
		User sourceUser = Authentication.loadUser(sourceUsername);
		Session sourceSession = sourceUser.getSession();
		
		String[][] userUploads = ServerDropboxOperations.getUserUploads(sourceUsername, sourceSession);
		List<String> permittedFiles = new ArrayList<String>();
		
		for (int i = 0; i < userUploads.length; i++) {
			int fileSecurityLevel = ServerFileOperations.getSecurityLevel(userUploads[i][1]);
			if (fileSecurityLevel >= securityLevel)
				permittedFiles.add(userUploads[i][0]);
		}
			
			/* Share files with user */
		ServerDropboxOperations.shareFilesWithFriend(sourceUsername, sourceSession, destUsername, destSession, permittedFiles);
			
		UserOperations.removeRequest(sourceUsername, destUsername);
	}
	
	
	public static void ignoreFriendRequest(String destUsername, ClientComms comms) {
		
		String sourceUsername = comms.fromClient();
		
		UserOperations.removeRequest(sourceUsername, destUsername);
		
	}

	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(String username,
			Session session, ClientComms comms) {
		
		int securityLevel = Integer.parseInt(comms.fromClient());
		byte[] iv = comms.getBytes();
		String rev = comms.fromClient();
		String fileName = comms.fromClient();
		
		ServerFileOperations.addFile(securityLevel, username, comms, iv, rev);
		
		sendFiletoFriends(username, rev, securityLevel, session, comms);
		
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
			
			System.out.println(friends[i][0]);
			System.out.println(friends[i][1]);
			
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
	
	
	private static void sendFiletoFriends(String sourceUsername, String fileName,
			int securityLevel, Session sourceSession, ClientComms comms) {
		
		String[][] friends = UserOperations.getFriends(sourceUsername);
		List<String> permittedFiles = new ArrayList<String>();
		permittedFiles.add(fileName);
		
		List<String> permittedUsers = new ArrayList<String>();
		for (int i = 0; i < friends.length; i++)
			if (Integer.parseInt(friends[i][1]) <= securityLevel)
				permittedUsers.add(friends[i][0]);
			
		for (String destUsername : permittedUsers) {
			User destUser = Authentication.loadUser(destUsername);
			Session destSession = destUser.getSession();
			
			ServerDropboxOperations.shareFilesWithFriend(sourceUsername,
					sourceSession, destUsername, destSession, permittedFiles);
		}
		
	}

	
}