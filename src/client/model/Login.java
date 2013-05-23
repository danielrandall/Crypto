package client.model;

import client.model.linking.keystore.KeyStoreOperations;
import client.model.linking.password.BCryptEncryptor;
import client.model.linking.password.PasswordEncryptor;

public class Login {

	/* Meaning of messages from the Server */
	private static final String TRUE = "1";
	
	/* Meaning of messages from the Client */
	private static final String LOGIN = "1";
	
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();
	
	/* Given the username and password check it with server
	 * TODO: Send the password encrypted.
	 *       Possible ways to do that are:
	 *       - SSL
	 *       - Public/private keys */
	public static boolean userLogin(String username, String password) {
		
		ServerComms.toServer(LOGIN);

		ServerComms.toServer(username);
		ServerComms.toServer(password);
		
		if (ServerComms.fromServer().equals(TRUE)) {
			DropboxOperations.setUsername(username);
			acquireConnection();
			KeyStoreOperations.login(username, password);
			return true;
		} else
			return false;
			
	}

	/* Method to acquire the necessary information to authenticate with the
	 * Dropbox servers.
	 */
	private static void acquireConnection() {
		
		/* Acquire Dropbox key and secret for logged in user from server */
		String key = ServerComms.fromServer();
		String secret = ServerComms.fromServer();
		
		/* Create an active session for the user and store it in
		 * DropboxOperations */
		DropboxOperations.makeSession(key, secret);
		
	}

}
