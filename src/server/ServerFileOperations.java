package server;

import java.util.Map;

import client.model.linking.databases.H2Files;

import Ciphers.AESCipher;
import Ciphers.Cipher;

public class ServerFileOperations {
	
	private static H2Files database = new H2Files();
	
	private static Cipher cipher = new AESCipher();
	
	public static void encryptFile(int securityLevel, String username, ClientComms comms) {
		
		byte[] key = KeyDerivation.retrieveKey(username, Integer.toString(securityLevel), cipher);
		System.out.println("here");
		System.out.println(key.length);
		comms.sendBytes(key, key.length);
		System.out.println("not here");
				
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
