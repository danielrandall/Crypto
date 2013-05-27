package client.model;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import server.password.BCryptEncryptor;
import server.password.PasswordEncryptor;

import ciphers.SecurityVariables;
import client.model.linking.keystore.KeyStoreOperations;

public class Register {
	
	/* Message constants from server */
	private static final String TRUE = "1";
	
	/* Message constants to send to server */
	private static final String REGISTER = "2";
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	
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
		
		byte[][] keys = generateKeys();
		
		/* Store the keys in the key store */
		for (int i = 0; i < NUMBER_SECURITY_LEVELS; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(i + 1), keys[i]);
		
		byte[][] ivs = generateIVs();
		
		byte[][] encryptedKeys = FileOperations.encryptKeys(keys, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			ServerComms.sendBytes(encryptedKeys[i], encryptedKeys[i].length);
			ServerComms.sendBytes(ivs[i], ivs[i].length);
		}
		
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
		
		keys = null;
		privateKeyBytes = null;
		
		return true;
		
	}
	
	/* Returns true if the passwords match, false otherwise */
	public static boolean passwordCheck(char[] password, char[] reenterPassword) {
		
		return Arrays.equals(password, reenterPassword);
		
	}
	
	/* The users keys are generated.
	 * keys[0] holds the highest level key. ie. security level 1.
	 * keys[NUMBER_SECURITY_LEVELS] holds the lowest. */
	private static byte[][] generateKeys() {
		
		byte[][] keys = new byte[NUMBER_SECURITY_LEVELS][];
		
		for (int i = 0; i < NUMBER_SECURITY_LEVELS; i++)
			keys[i] = SecurityVariables.generateKey();
		
		return keys;
		
	}
	
	private static byte[][] generateIVs() {
		
		byte[][] ivs = new byte[NUMBER_SECURITY_LEVELS - 1][];
		
		/* Generate ivs to be used in encrypting the keys */
		for (int i = 0; i < NUMBER_SECURITY_LEVELS - 1; i++)
			ivs[i] = SecurityVariables.generateIV();
		
		return ivs;
		
	}
	

}
