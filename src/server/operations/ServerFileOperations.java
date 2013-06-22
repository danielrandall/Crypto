package server.operations;

import java.util.List;
import java.util.Map;

import server.ServerFile;
import server.ciphers.Ciphers;
import server.ciphers.RSASunJCECipher;
import server.databases.H2Files;



public class ServerFileOperations {
	
	private static H2Files database = new H2Files();
	
	private static Ciphers cipher = new RSASunJCECipher();
	
	public static void addFile(int securityLevel, String username,
				byte[] iv, String rev) {
		
		/* Add to database */
		database.addFile(rev, username, iv, securityLevel);
	}
	
	
	/* Removes the given file from the server database */
	public static void removeFile(String fileRev) {
		
		database.removeFile(fileRev);
		
	}
	
	
	/* Retrieves a files IV and security level */
	public static ServerFile getFileInfo(String rev) {
		
		Map<String, Object> data = database.getFile(rev);
		
		if (data == null) {
			
			return null;
		}
		
		byte[] iv = (byte[])data.get(H2Files.IV);
		int level = (Integer)data.get(H2Files.SECURITY_LEVEL);
		
		ServerFile file = new ServerFile(rev, iv, level);
		
		return file;
		
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
	
	public static byte[] encrypt(byte[] key, byte[] file) {
		
		return cipher.encrypt(file, key, null);
		
	}
	
	public static byte[] decrypt(byte[] key, byte[] file) {
		
		return cipher.decrypt(file, key, null);
		
	}
	
}
