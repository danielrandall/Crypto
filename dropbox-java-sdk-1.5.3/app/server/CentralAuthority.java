package server;
import com.dropbox.client2.session.Session;
import Linking.Authentication;
import Linking.User;

/* TODO: update file list */

public class CentralAuthority {
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String ADD_FRIEND = "3";
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	public static void options(User user, ClientComms comms) {
		
		comms.toClient(user.getAccessTokens().key);
		comms.toClient(user.getAccessTokens().secret);
		
		while(true) {
			
			/* Get central option from user */
			String decision = comms.fromClient();
		
			if (decision.equals(UPLOAD_FILE))
				uploadEncryptedFile(user.getUsername(), comms);
			
			if (decision.equals(DOWNLOAD_FILE))
				downloadDecryptedFile(user.getUsername(), comms);
		
			if (decision.equals(ADD_FRIEND))
				addFriend(user.getUsername(), user.getSession(), comms);
		}
	
	}
	
	private static void addFriend(String username, Session session, ClientComms comms) {
		
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
		
		/* Add user to friends list */
		UserOperations.addUserToFriendsList(username, usernameToAdd, lowerBound, upperBound);
		
		User frienduser = Authentication.createUser(usernameToAdd);
		
		/* Share files with user */
		DropboxOperations.shareFilesWithFriend(usernameToAdd,
		            frienduser.getSession(), username,
		                                      session);
		
		
	}

	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(String username, ClientComms comms) {
		
		int securityLevel = Integer.parseInt(comms.fromClient());
		
		FileOperations.encryptFile(securityLevel, username, comms);
		
	}
	
	/* The user is requested for the file to be downloaded and the location for
	 * it to be downloaded to. The file is downloaded and then decrypted. */
	private static void downloadDecryptedFile(String username, ClientComms comms) {
		
		String rev = comms.fromClient();
		
		FileOperations.decryptFile(rev, username, comms);
		
	}
	

	
}