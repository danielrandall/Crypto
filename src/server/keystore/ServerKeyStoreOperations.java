package server.keystore;

import java.security.KeyPair;

import server.ciphers.SecurityVariables;

public class ServerKeyStoreOperations {
	
	private static final String PUBLIC_KEY_NAME = "public";
	private static final String PRIVATE_KEY_NAME = "private";
	
	private static KeyStores keystore = new JavaKeyStore();
	
	
	public static void main(String[] args) {
		
		ServerKeyStoreOperations.setup(null, "password".toCharArray());
		KeyPair kp = SecurityVariables.GenerateAsymmetricKeyPair();
		ServerKeyStoreOperations.StorePrivateKey(kp.getPrivate().getEncoded());
		 ServerKeyStoreOperations.StorePublicKey(kp.getPublic().getEncoded());
		 
		ServerKeyStoreOperations.retrievePublicKey();
		
	}
	
	
	
	public static void StorePublicKey(byte[] key) {
		
		keystore.storeKey(PUBLIC_KEY_NAME, key, "AES");
		
	}
	
	public static byte[] retrievePublicKey() {
		
		return keystore.retrieveKey(PUBLIC_KEY_NAME);
		
	}
	
	public static void StorePrivateKey(byte[] key) {
		
		keystore.storeKey(PRIVATE_KEY_NAME, key, "AES");
		
	}
	
	public static byte[] retrievePrivateKey() {
		
		return keystore.retrieveKey(PRIVATE_KEY_NAME);
		
	}
	
	public static void setup(String username, char[] password) {
		
		keystore.createKeystore(username, password);
		
	}
	
	public static void login(String username, char[] password) {
		
		keystore.checkPassword(username, password);
		
	}

}
