package Linking;

public interface KeyStores {
	
	public byte[] retrieveKey(String id, char[] password);
	
	public void removeKey(String id);
	
	public boolean checkPassword(char[] password);
	
	public void storeKey(String id, byte[] key, String algorithm);
	
	public boolean checkKeyExists(String id);
	
	public void clearKeystore();

}
