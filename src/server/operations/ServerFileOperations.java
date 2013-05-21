package server.operations;

import java.util.Map;

import server.ClientComms;
import server.databases.H2Files;
import server.encryption.KeyDerivation;


import Ciphers.AESCipher;
import Ciphers.Cipher;

public class ServerFileOperations {
	
	private static H2Files database = new H2Files();
	
	private static Cipher cipher = new AESCipher();
	
	public static void encryptFile(int securityLevel, String username, ClientComms comms) {
		
		//byte[] key = KeyDerivation.retrieveKey(username, Integer.toString(securityLevel), cipher);

		//comms.sendBytes(key, key.length);
				
		byte[] iv = comms.getBytes();
		String rev = comms.fromClient();
		
		/* Add to database */
		database.addFile(rev, username, iv, securityLevel);
	}
	
	
	/* Retrieves a files IV and security level */
	public static Object[] getFileInfo(String rev) {
		
		Map<String, Object> data = database.getFile(rev);
		
		Object[] fileInfo = new Object[2];
		
		byte[] iv = (byte[])data.get(H2Files.IV);
		int level = (Integer)data.get(H2Files.SECURITY_LEVEL);
		
		fileInfo[0] = iv;
		fileInfo[1] = level;
		
		return fileInfo;	
		
	}
	
}
