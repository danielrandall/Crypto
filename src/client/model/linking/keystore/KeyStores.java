package client.model.linking.keystore;

public interface KeyStores {
	
	public byte[] retrieveKey(String id);
	
	public void removeKey(String id);
	
	public boolean checkPassword(String username, char[] password);
	
	public void storeKey(String id, byte[] key, String algorithm);
	
	public boolean checkKeyExists(String id);
	
	public void clearKeystore();
	
	public void createKeystore(String keystoreName, char[] password);

}
