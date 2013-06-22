package server.users;

import server.ClientComms;
import server.keystore.ServerKeyStoreOperations;
import server.operations.ServerFileOperations;
import server.operations.UserOperations;
import server.password.BCryptEncryptor;
import server.password.PasswordEncryptor;


import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;

public final class Authentication {   
	
	private static final String APP_KEY = "bpyf040d6hcvi5t";
    private static final String APP_SECRET = "96a0wcdkxlqxt3w";
    private static final AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
	
	public static final int HIGHEST_SECURITY_LEVEL = 1;
	
	
	/* Receive username and check whether the username is acceptable
	 * If the username is accepted then notify the user and proceed to register
	 * the new user.
	 * Else notify the user that the username is invalid. */
	public static User registerAttempt(ClientComms comms) {
			
		String username = comms.fromClient();
			
		boolean	 usernameAccepted = !UserOperations.checkUserExists(username);
			
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
		
		byte[] publicKeyBytes = ServerKeyStoreOperations.retrievePublicKey();
		
		comms.sendBytes(publicKeyBytes, publicKeyBytes.length);
		comms.getAcknowledgement();
		
		byte[] encryptedPasswordBytes = comms.getBytes();
		comms.sendAcknowledgement();
		
		byte[] privateKeyBytes = ServerKeyStoreOperations.retrievePrivateKey();
		byte[] passwordBytes = ServerFileOperations.decrypt(privateKeyBytes, encryptedPasswordBytes);
		
		
		
		String key = comms.fromClient();
		String secret = comms.fromClient();
		String uid = comms.fromClient();
		
		UserOperations.storeSymmetricKeys(username, HIGHEST_SECURITY_LEVEL + 1, false, comms);
		
		/* Receive the user's public key */
		byte[] publicKey = comms.getBytes();
		comms.sendAcknowledgement();

		User user = Authentication.createUser(username, passwordBytes, comms, key, secret, uid, publicKey);
		
		return user;
		
	}
	
    
    public static User login(ClientComms comms) {
    	
    	boolean accepted = false;
		String username = null;
		byte[] passwordBytes = null;
			
		username = comms.fromClient();
		
		comms.sendAcknowledgement();
		
		byte[] publicKeyBytes = ServerKeyStoreOperations.retrievePublicKey();
		
		comms.sendBytes(publicKeyBytes, publicKeyBytes.length);
		comms.getAcknowledgement();
		
		byte[] encryptedPasswordBytes = comms.getBytes();
		comms.sendAcknowledgement();
		
		byte[] privateKeyBytes = ServerKeyStoreOperations.retrievePrivateKey();
		passwordBytes = ServerFileOperations.decrypt(privateKeyBytes, encryptedPasswordBytes);
		
		char[] passwordChars = byteArraytoCharArray(passwordBytes);
			
    	accepted = checkUserPass(username, passwordChars);
    	
    	passwordBytes = "".getBytes();
		passwordChars = "".toCharArray();	
    	/* Tell client the user/pass was incorrect */
    	if (!accepted) {
    		comms.toClient(FALSE);
    		return null;
    	}
		
		/* Tell client the user/pass was correct */
		comms.toClient(TRUE);
    	
		User user = loadUser(username);
		
		comms.toClient(user.getAccessTokens().key);
		comms.toClient(user.getAccessTokens().secret);
		
		return user;
    }
    
    /* Loads a user given a username, creates a new session and returns the
     * user.
     * Used for existing users. */
    public static User loadUser(String username) {
    	
    	User user = User.load(username);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, user.getAccessTokens());
		user.setSession(session);
		
		return user;
    	
    }
    
    /* Creates a session and User. Returns the Users.
     * Used for newly register users */
    public static User createUser(String username, byte[] passwordBytes,
    		                          ClientComms comms, String key,
    		                          String secret, String uid, byte[] publicKey) {
    		
    		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, new AccessTokenPair(key, secret));
    		
    		char[] passwordChars = Authentication.byteArraytoCharArray(passwordBytes);
    		String hashedPassword = passwordEncryptor.hashPassword(new String(passwordChars));
    		
			User user = User.save(username, hashedPassword, uid, key, secret, publicKey);
			user.setSession(session);
        
        return user;
    	
    }
    
    /* Checks that the given password matches the stored password for that user */
    private static boolean checkUserPass(String username, char[] password) {
    	
    	if (UserOperations.checkUserExists(username)) {
    		return passwordEncryptor.checkPassword(new String(password), User.getPassword(username));
    	} else
    		return false;
    		
    }
    
    
    /* Converts a byte array to a char array without creating intermediate
	 * strings.
	 * This method is designed for converting passwords back into their
	 * original format.
	 * Strings are not preferred in this scenario as they
	 * are immutable. */
	private static char[] byteArraytoCharArray(byte[] bytes) {
		
		char[] chars2 = new char[bytes.length / 2];
		for (int i = 0; i < chars2.length; i++) 
		   chars2[i] = (char) ((bytes[i * 2] << 8) + bytes[i * 2 + 1]);
		
		return chars2;
	}
    

}