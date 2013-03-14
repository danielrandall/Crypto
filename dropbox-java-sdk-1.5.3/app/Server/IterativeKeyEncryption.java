package Server;

import Ciphers.Cipher;

public class IterativeKeyEncryption implements KeyAssignmentScheme {
	
	/* Maximum number of permitted security levels */
	private static final int SECURITY_LEVELS = 5;
	/* Represents the number of security levels stored.
	 * The highest key need not be stored as it will never need to be derived
	 * This variable exists to prevent the need to write a lot of -1 or -2. */
	private static final int USED_SECURITY_LEVELS = SECURITY_LEVELS - 1;
	
	/* User for which this KAS is for */
	private String user;
	/* Holds the keys to be derived from */
	private byte[][] keys;
	/* ivs used to encrypt the keys */
	private byte[][] ivs;
	
	
	/* Passed in the generated keys for each level in order of highest to lowest.
	 * The number of keys passed in must match the number of security levels stated in this class.
	 * The constructor encrypts each key with the one above it and stores it.
	 */
	public IterativeKeyEncryption(byte[][] rawKeys, Cipher cipher, String user) {
		
		keys = new byte[USED_SECURITY_LEVELS][];
		ivs = new byte[USED_SECURITY_LEVELS][];
		
		this.user = user;
		
		/* Encrypt keys accordingly */
		for (int i = 1; i < USED_SECURITY_LEVELS - 1; i++) {
				byte[] iv = cipher.generateIV();
				byte[] key = cipher.encrypt(rawKeys[i], rawKeys[i - 1], iv);
				
				ivs[i - 1] = iv;
				keys[i - 1] = key;
		}
		
	}
	
	/* Iteratively derives the desired key given a source key and the desired key location
	 * Returns null if the key is unobtainable.
	 */
	public byte[] getKey(byte[] key, int sourceKeyPosition, int desiredKey, Cipher cipher) {
		
		assert(sourceKeyPosition < desiredKey);
		
		int targetKeyPosition = desiredKey - 1;
		int currentKeyPosition = sourceKeyPosition - 1;
		byte[] currentKey = key;
		
		while (!(currentKeyPosition == targetKeyPosition)) {
			
			currentKey = cipher.decrypt(keys[currentKeyPosition + 1], currentKey, ivs[currentKeyPosition + 1]);
			
			if(key.equals(null))
				return null;
			
			currentKeyPosition++;
			
		}
		
		return key;
		
	}
	
	public void addKey(byte[] key) {
		
	}
	
	public void removeKey(byte[] key) {
		
		
	}
	

}
