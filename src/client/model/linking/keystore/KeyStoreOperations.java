package client.model.linking.keystore;

public class KeyStoreOperations {
	
	private static KeyStores keystore = new JavaKeyStore();
	
	public static byte[] retrieveOwnKey(String level) {
		
		return keystore.retrieveKey(level);
		
	}
	
	public static byte[] retrieveFriendKey(String user, String level) {
		
			return keystore.retrieveKey(user + level);
		
	}
	
	public static void storeOwnKey(String level, byte[] key) {
		
		keystore.storeKey(level, key, "AES");
		
	}
	
	public static void storeFriendKey(String username, String level, byte[] key) {
		
		keystore.storeKey(username + level, key, "AES");
		
	}
	
	public static void setup(String username, char[] password) {
		
		keystore.createKeystore(username, password);
		
	}
	
	public static void login(String username, char[] password) {
		
		keystore.checkPassword(username, password);
		
	}

}
