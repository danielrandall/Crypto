package client.model;

import client.model.linking.keystore.KeyStoreOperations;
import client.model.linking.password.BCryptEncryptor;
import client.model.linking.password.PasswordEncryptor;
import Ciphers.SecurityVariables;

public class Register {
	
	/* Message constants from server */
	private static final String TRUE = "1";
	
	/* Message constants to send to server */
	private static final String REGISTER = "2";
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();

	public static boolean userRegister(String username, String password) {
		
		/* Tell the server a user wishes to register */
		ServerComms.toServer(REGISTER);
		
		/* Give the server the desired username */
		ServerComms.toServer(username);
			
		/* If the server accepts the username */
		if (!ServerComms.fromServer().equals(TRUE))
			return false;
			
		/* Hash the password */
		String hashedPassword = passwordEncryptor.hashPassword(password);
		ServerComms.toServer(hashedPassword);
		
		String[] authenticationInfo = null;
		while (authenticationInfo == null)
			authenticationInfo = DropboxOperations.authenticate();
		
		String key = authenticationInfo[0];
		String secret = authenticationInfo[1];
		String uid = authenticationInfo[2];
		
		ServerComms.toServer(key);
		ServerComms.toServer(secret);
		ServerComms.toServer(uid);
		
		KeyStoreOperations.setup(username, password);
		
		DropboxOperations.setUsername(username);
		DropboxOperations.folderSetup();
		
		byte[][] keys = generateKeys();
		
		/* Send to the server to be stored */
		for (int i = 0; i < NUMBER_SECURITY_LEVELS; i++)
			//ServerComms.sendBytes(keys[i], keys[i].length);
			KeyStoreOperations.storeOwnKey(Integer.toString(i + 1), keys[i]);
		
		return true;
		
	}
	
	/* Returns true if the passwords match, false otherwise */
	public static boolean passwordCheck(String password, String reenterPassword) {
		
		return password.equals(reenterPassword);
		
	}
	
	/* The users keys are generated.
	 * keys[0] holds the highest level key. ie. security level 1.
	 * keys[NUMBER_SECURITY_LEVELS] holds the lowest.
	 */
	private static byte[][] generateKeys() {
		
		byte[][] keys = new byte[NUMBER_SECURITY_LEVELS][];
		
		for (int i = 0; i < NUMBER_SECURITY_LEVELS; i++)
			keys[i] = SecurityVariables.generateKey();
		
		return keys;
		
	}
	
	


}
