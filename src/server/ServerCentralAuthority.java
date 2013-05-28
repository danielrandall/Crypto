package server;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;

import server.operations.KeyOperations;
import server.operations.ServerDropboxOperations;
import server.operations.ServerFileOperations;
import server.operations.UserOperations;
import server.users.Authentication;
import server.users.User;

/* TODO: update file list */

public class ServerCentralAuthority {
	
	/* Communication constants */
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String FRIEND_REQUEST = "3";
	private static final String REMOVE_FILE = "4";
	private static final String ACCEPT_FRIEND_REQUEST = "5";
	private static final String IGNORE_FRIEND_REQUEST = "6";
	private static final String DOWNLOAD_FRIEND_FILE = "7";
	private static final String REVOKE_USER = "8";
	
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
			
			if (decision.equals(DOWNLOAD_FRIEND_FILE))
				downloadFriendFile(user.getUsername(), comms);
			
			if (decision.equals(REVOKE_USER))
				revokeUser(user.getUsername(), comms);
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
		
		byte[] publicKey = UserOperations.getPublicKey(usernameToAdd);
		
		/* Send the user to add's public key */
		comms.sendBytes(publicKey, publicKey.length);
		
		/* Receive the encrypted key to send with the request */
		byte[] key = comms.getBytes();		
		
		UserOperations.addRequest(username, usernameToAdd, lowerBound, key);
		
	}
	
	/* sourceUsername is the user who made the request 
	 * destUsername is the user who has accepted the request. */
	private static void acceptFriendRequest(String destUsername, Session destSession, ClientComms comms) {
		
		String sourceUsername = comms.fromClient();
		
		/* Extract the request information */
		Object[] requestInfo = UserOperations.getRequestInfo(sourceUsername, destUsername);
		int securityLevel = (Integer) requestInfo[0];
		byte[] key = (byte[]) requestInfo[1];
		
		/* Send security level the user */
		comms.toClient(Integer.toString(securityLevel));
		
		/* Add user to friends list */
		UserOperations.addUserToFriendsList(sourceUsername, destUsername, securityLevel, securityLevel);
			
		User sourceUser = Authentication.loadUser(sourceUsername);
		Session sourceSession = sourceUser.getSession();
		
		/* Send key to the user. This line needs to be placed away from the
		 * send of the security level as the client needs time to process
		 * the messages. */
		comms.sendBytes(key, key.length);
		
		/* Send the encrypted lower keys to the user to derive */
		sendLowerEncryptedKeys(sourceUsername, securityLevel, comms);
		
		/* Send only relevant files to the user */
		String[][] userUploads = ServerDropboxOperations.getUserUploads(sourceUsername, sourceSession);
		List<String> permittedFiles = new ArrayList<String>();
		
		for (int i = 0; i < userUploads.length; i++) {
			int fileSecurityLevel = ServerFileOperations.getSecurityLevel(userUploads[i][1]);
			if (fileSecurityLevel >= securityLevel)
				permittedFiles.add(userUploads[i][0]);
		}
			
		/* Share relevant files with user */
		ServerDropboxOperations.shareFilesWithFriend(sourceUsername, sourceSession, destUsername, destSession, permittedFiles);
			
		UserOperations.removeRequest(sourceUsername, destUsername);
	}
	
	
	/* Retrieves all keys which the user is able to derive with the given
	 * security level and sends them one-by-one to the user. */
	private static void sendLowerEncryptedKeys(String sourceUsername,
			int securityLevel, ClientComms comms) {
		
		Map<Integer, Object[]> keys = KeyOperations.getDerivableKeys(sourceUsername, securityLevel);
		
		int numberOfWeakerKeys = keys.size();
		
		/* Tell the user how many weaker keys are being sent */
		comms.toClient(Integer.toString(numberOfWeakerKeys));
		
		for (int i = securityLevel + 1; i <= (securityLevel + keys.size()); i++) {
			
			/* Give the client time to process each key */
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Object[] keyAndIV = keys.get(i);
			
			byte[] key = (byte[]) keyAndIV[0];
			comms.sendBytes(key, key.length);
			
			byte[] iv = (byte[]) keyAndIV[1];
			comms.sendBytes(iv, iv.length);
			
		}
		
		
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
		
		sendFiletoFriends(username, fileName, securityLevel, session, comms);
		
	}

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
	
	
	private static void downloadFriendFile(String username,
			ClientComms comms) {

		String owner = comms.fromClient();
		String fileName = comms.fromClient();
		
		User user = Authentication.loadUser(owner);
		Session ownerSession = user.getSession();
		
		String rev = ServerDropboxOperations.getFileRev(owner, fileName, ownerSession);
		
		Object[] fileInfo = ServerFileOperations.getFileInfo(rev);
		
		byte[] iv = (byte[]) fileInfo[0];
		int securityLevel = (Integer) fileInfo[1];
		
		comms.sendBytes(iv, iv.length);
		comms.toClient(Integer.toString(securityLevel));
		
	}
	
	
	private static final void revokeUser(String username, ClientComms comms) {
		
		String usernameToRevoke = comms.fromClient();
		
		int securityLevel = UserOperations.getFriendSecurityLevel(username, usernameToRevoke);
		comms.toClient(Integer.toString(securityLevel));
		
		/* Receive the new encrypted keys and IVs and store them in the database */
		UserOperations.storeSymmetricKeys(username, securityLevel, true, comms);
		
		/* Remove the friends files from their Dropbox folder */
		User userToRevoke = User.load(usernameToRevoke);
		userToRevoke.getSession();
		ServerDropboxOperations.removeFriendsFiles(username, usernameToRevoke, userToRevoke.getSession());
		
		// Inform friends
		
	}

	
}