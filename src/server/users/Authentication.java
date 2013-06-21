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
    
    public static User link(ClientComms comms) {
    	
    	boolean accepted = false;
		String username = null;
		byte[] passwordBytes = null;
			
		username = comms.fromClient();
		
		comms.sendInt(1);
		
		byte[] publicKeyBytes = ServerKeyStoreOperations.retrievePublicKey();
		
		comms.sendBytes(publicKeyBytes, publicKeyBytes.length);
		comms.getInt();
		
		byte[] encryptedPasswordBytes = comms.getBytes();
		comms.sendInt(1);
		
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