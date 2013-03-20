package server;

import java.util.Map;

import Ciphers.AESCipher;
import Ciphers.Cipher;
import Linking.Databases.H2Files;

public class FileOperations {
	
	private static H2Files database = new H2Files();
	
	private static Cipher cipher = new AESCipher();
	
	public static void encryptFile(int securityLevel, String username, ClientComms comms) {
		
		byte[] key = KeyDerivation.retrieveKey(username, Integer.toString(securityLevel), cipher);
		comms.sendBytes(key, key.length);
				
		byte[] iv = comms.getBytes();
		String rev = comms.fromClient();
		
		/* Add to database */
		database.addFile(rev, username, iv, securityLevel);
	}
	
	
	/* The contents of the given file are extracted and decrypted. The file
	 * is then overwritten with the plaintext. */
	public static void decryptFile(String rev, String username, ClientComms comms) {
		
		Map<String, Object> data = database.getFile(rev);
		byte[] iv = (byte[])data.get(H2Files.IV);
		int level = (Integer)data.get(H2Files.SECURITY_LEVEL);
		
		byte[] key = KeyDerivation.retrieveKey(username, Integer.toString(level), cipher);
		
		comms.sendBytes(iv, iv.length);
		comms.sendBytes(key, key.length);
		
	}
	
}
