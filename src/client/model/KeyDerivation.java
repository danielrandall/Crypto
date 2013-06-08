package client.model;

import java.security.PrivateKey;

import client.model.keystore.KeyStoreOperations;

public class KeyDerivation {
	
	
	public static void deriveKeys(String username, byte[] privateKey, String securityLevel) {
		
		/* Retrieve the most influential key (ie. the highest security level
		 * you have access to) and its security level from the server */
		byte[] encryptedHighestKey = ServerComms.getBytes();		
		
		/* Decrypt the highest key with the user's private key */
		byte[] highestKey = FileOperations.asymmetricDecrypt(encryptedHighestKey, privateKey);
		
		/* Store the key */
		KeyStoreOperations.storeFriendKey(username, securityLevel, highestKey);
		
		int securityLevelInt = Integer.parseInt(securityLevel);
		
		byte[] previousKey = highestKey;
		
		/* Receive from the server the number of keys to decrypt and use */
		int numberOfWeakerKeys = Integer.parseInt(ServerComms.fromServer());
		ServerComms.sendInt(1);
		
		/* Retrieve the following, encrypted, keys from the server in order.
		 * Decrypt them with their previous key and store them with their
		 * associated security levels. */
		for (int i = securityLevelInt + 1; i <= securityLevelInt + numberOfWeakerKeys; i++) {
			
			byte[] encryptedKey = ServerComms.getBytes();
			ServerComms.sendInt(1);
			
			byte[] iv = ServerComms.getBytes();
			ServerComms.sendInt(1);
			
			byte[] decryptedKey = FileOperations.decryptKey(encryptedKey, previousKey, iv);
			
			KeyStoreOperations.storeFriendKey(username, Integer.toString(i), decryptedKey);
			
			previousKey = decryptedKey;
			
		}
		
	}

}
