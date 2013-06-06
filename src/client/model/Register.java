package client.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import ciphers.SecurityVariables;
import client.model.keystore.KeyStoreOperations;

public class Register {
	
	/* Message constants from server */
	private static final String TRUE = "1";
	
	/* Message constants to send to server */
	private static final String REGISTER = "2";
	
	public static boolean userRegister(String username, char[] password) {
		
		/* Tell the server a user wishes to register */
		ServerComms.toServer(REGISTER);
		
		/* Give the server the desired username */
		ServerComms.toServer(username);
			
		/* If the server accepts the username */
		if (!ServerComms.fromServer().equals(TRUE))
			return false;
		
		byte[] passwordBytes = Login.charArraytoByteArray(password);
		
		ServerComms.sendBytes(passwordBytes, passwordBytes.length);
		
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
		
		generateSymmetricVariables();
		
		/* Generate public key pair */
		KeyPair keyPair = SecurityVariables.GenerateAsymmetricKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		
		/* Store the asymmetric keys in the key store */
		KeyStoreOperations.StorePublicKey(publicKeyBytes);
		KeyStoreOperations.StorePrivateKey(privateKeyBytes);
		
		/* Send the public to the server to store */
		ServerComms.sendBytes(publicKeyBytes, publicKeyBytes.length);
		
		
		privateKeyBytes = null;
		
		return true;
		
	}
	
	
	public static void generateSymmetricVariables() {
		
		byte[][] keys = SecurityVariables.generateAllKeys();
		byte[][] ivs = SecurityVariables.generateAllKeys();
		
		/* Store the keys in the key store */
		for (int i = 0; i < keys.length; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(i + 1), keys[i]);
		
		EncryptedKey[] encryptedKeys = FileOperations.encryptKeys(keys, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			byte[] encryptedKey = encryptedKeys[i].getEncryptedKey();
			ServerComms.sendBytes(encryptedKey, encryptedKey.length);
			ServerComms.getInt();
			
			byte[] iv = encryptedKeys[i].getIV();
			ServerComms.sendBytes(iv, iv.length);
			ServerComms.getInt();
		}
		
		keys = null;
		
	}
	
	
	/* Returns true if the passwords match, false otherwise */
	public static boolean passwordCheck(char[] password, char[] reenterPassword) {
		
		return Arrays.equals(password, reenterPassword);
		
	}
	

}
