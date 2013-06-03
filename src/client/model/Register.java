package client.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import server.password.BCryptEncryptor;
import server.password.PasswordEncryptor;

import ciphers.SecurityVariables;
import client.model.keystore.KeyStoreOperations;

public class Register {
	
	/* Message constants from server */
	private static final String TRUE = "1";
	
	/* Message constants to send to server */
	private static final String REGISTER = "2";
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	private static final int HIGHEST_SECURITY_LEVEL = 1;
	
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();

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
		
		generateSymmetricVariables(HIGHEST_SECURITY_LEVEL);
		
		/* Generate public key pair */
		KeyPair keyPair = SecurityVariables.GenerateAsymmetricKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		
		PrivateKey privateKey = keyPair.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		
		/* Store the asymmetric keys in the key store */
		KeyStoreOperations.storeOwnKey("public", publicKeyBytes);
		KeyStoreOperations.storeOwnKey("private", privateKeyBytes);
		
		/* Send the public to the server to store */
		ServerComms.sendBytes(publicKeyBytes, publicKeyBytes.length);
		
		
		privateKeyBytes = null;
		
		return true;
		
	}
	
	/* Encrypted keys is 1 smaller than generated keys. */
	public static void generateSymmetricVariables (int highestSecurityLevel) {
		
		int numKeys = NUMBER_SECURITY_LEVELS - highestSecurityLevel + 1;
		
		byte[][] keys = SecurityVariables.generateKeys(numKeys);
		
		/* Store the keys in the key store */
		for (int i = 0; i < numKeys; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(highestSecurityLevel + i), keys[i]);
		
		byte[][] ivs;
		
		byte[][] keysToEncrypt;
		if (highestSecurityLevel == HIGHEST_SECURITY_LEVEL) {
			keysToEncrypt = keys;
		 	ivs = SecurityVariables.generateIVs(numKeys);
		} else {
			keysToEncrypt = new byte[numKeys + 1][];
			ivs = SecurityVariables.generateIVs(numKeys + 1);
			keysToEncrypt[0] = KeyStoreOperations.retrieveOwnKey(Integer.toString(highestSecurityLevel - 1));
			for (int i = 1; i < numKeys + 1; i++)
				keysToEncrypt[i] = keys[i - 1];
		}
		
		byte[][] encryptedKeys = FileOperations.encryptKeys(keysToEncrypt, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			ServerComms.sendBytes(encryptedKeys[i], encryptedKeys[i].length);
			ServerComms.sendBytes(ivs[i], ivs[i].length);
		}
		
		keys = null;
		
	}
	
	/*
	Probably not needed
	public static void updateSymmetricVariables(int highestSecurityLevel) {
		
		int numKeys = NUMBER_SECURITY_LEVELS - highestSecurityLevel + 1;
		
		byte[][] keys = SecurityVariables.generateKeys(numKeys);
		

		for (int i = 0; i < numKeys; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(highestSecurityLevel + i), keys[i]);
		
		byte[][] ivs = SecurityVariables.generateIVs(numKeys);
		
		byte[][] encryptedKeys = FileOperations.encryptKeys(keys, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			ServerComms.sendBytes(encryptedKeys[i], encryptedKeys[i].length);
			ServerComms.sendBytes(ivs[i], ivs[i].length);
		}
		
		keys = null;
		
	}
	*/
	
	/* Returns true if the passwords match, false otherwise */
	public static boolean passwordCheck(char[] password, char[] reenterPassword) {
		
		return Arrays.equals(password, reenterPassword);
		
	}
	

}
