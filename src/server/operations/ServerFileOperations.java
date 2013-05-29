package server.operations;

import java.util.List;
import java.util.Map;

import ciphers.AESGNUCipher;
import ciphers.SymmetricCipher;

import server.ClientComms;
import server.ServerFile;
import server.databases.H2Files;
import server.databases.H2Requests;
import server.encryption.KeyDerivation;



public class ServerFileOperations {
	
	private static H2Files database = new H2Files();
	
	private static SymmetricCipher cipher = new AESGNUCipher();
	
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

	
	/* Returns all file revs and security levels for files under the influence
	 * of a given security level for a given owner. */
	public static ServerFile[] getAllFilesUnderSecurityLevel(String owner,
			int securityLevel) {
		
		List<Map<String, Object>> files =
				database.getFilesUnderLevel(owner,securityLevel);

		int size = files.size();
		
		ServerFile[] fileInfo = new ServerFile[size];
		for (int i = 0; i < size; i++) {
			String rev = (String) files.get(i).get(H2Files.FILEREV);
			byte[] iv = (byte[]) files.get(i).get(H2Files.IV);
			int fileSLevel = (Integer) files.get(i).get(H2Files.SECURITY_LEVEL);
			fileInfo[i] = new ServerFile(rev, iv, fileSLevel);
		}
		
		return fileInfo;
		
	}
	
}
