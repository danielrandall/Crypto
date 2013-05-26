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
	
	public static void addFile(int securityLevel, String username,
			ClientComms comms, byte[] iv, String rev) {
		
		/* Add to database */
		database.addFile(rev, username, iv, securityLevel);
	}
	
	
	/* Removes the given file from the server database */
	public static void removeFile(String fileRev) {
		
		database.removeFile(fileRev);
		
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
	
	public static int getSecurityLevel(String fileRev) {
		
		return (Integer) getFileInfo(fileRev)[1];
		
	}
	
}
