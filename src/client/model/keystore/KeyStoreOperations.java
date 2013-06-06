package client.model.keystore;

public class KeyStoreOperations {
	
	private static final String PUBLIC_KEY_NAME = "public";
	private static final String PRIVATE_KEY_NAME = "private";
	
	private static KeyStores keystore = new JavaKeyStore();
	
	
	
	public static byte[] retrieveOwnKey(String level) {

		return keystore.retrieveKey(level);
		
	}
	
	public static void storeOwnKey(String level, byte[] key) {
		
		keystore.storeKey(level, key, "AES");
		
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
	
	
	public static void storeFriendKey(String username, String level, byte[] key) {
		
		keystore.storeKey(username + level, key, "AES");
		
	}
	
	public static byte[] retrieveFriendKey(String user, String level) {
		
		return keystore.retrieveKey(user + level);
	
	}
	
	
	
	public static void setup(String username, char[] password) {
		
		keystore.createKeystore(username, password);
		
	}
	
	public static void login(String username, char[] password) {
		
		keystore.checkPassword(username, password);
		
	}

}
