package client.model;

/* Holds the ciphertext of an encrypted key and the iv needed to decrypt it
 * in the future. */

public class EncryptedKey {
	
	private byte[] encryptedKey;
	private byte[] iv;

	public EncryptedKey(byte[] encryptedKey, byte[] iv) {
		
		this.encryptedKey = encryptedKey;
		this.iv = iv;
		
	}
	
	public byte[] getEncryptedKey() {
		
		return encryptedKey;
		
	}
	
	public byte[] getIV() {
		
		return iv;
		
	}
}
