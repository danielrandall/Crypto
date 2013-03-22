package Client.model;

public class Login {

	private static final String TRUE = "1";
	private static final String LOGIN = "1";
	
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
			acquireConnection();
			return true;
		} else
			return false;
			
	}

	private static void acquireConnection() {
		
		/* Acquire Dropbox key and secret for logged in user from server */
		String key = ServerComms.fromServer();
		String secret = ServerComms.fromServer();
		
		/* Create an active session for the user and store it in
		 * DropboxOperations */
		DropboxOperations.makeSession(key, secret);
		
	}

}
