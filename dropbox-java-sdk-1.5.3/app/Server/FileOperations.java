package Server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.session.WebAuthSession;

import Ciphers.AESCipher;
import Ciphers.Cipher;
import Linking.Databases.Database;
import Linking.Databases.H2Database;
import Linking.Databases.H2Files;

public class FileOperations {
	
	private static H2Files database = new H2Files();
	
	private static Cipher cipher = new AESCipher();
	
	public static void encryptFile(File file, String fileName, WebAuthSession session, byte[] iv, int securityLevel) {
		
		InputStream fileStream;
		
		
		Entry entry = null;
		try {
			fileStream = new java.io.FileInputStream(file);
		
			try {
				byte[] fileContents = new byte[fileStream.available()];
				fileStream.read(fileContents);
				fileStream.close();
				
				byte[] key = "1234567891234567".getBytes();
				byte[] encrypted = cipher.encrypt(fileContents, key, iv);
				
				ByteArrayInputStream inputStream = new ByteArrayInputStream(encrypted);
				entry = DropboxOperations.uploadFile(fileName, inputStream, session, encrypted.length);
				
				KeyDerivation.storeKey(entry.rev, key, "AES");
		    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Add to database */
		database.addFile(entry.rev, "", iv);
	}
	
	
	/* The contents of the given file are extracted and decrypted. The file
	 * is then overwritten with the plaintext. */
	public static void decryptFile(File file, String rev) {
		
		Map<String, Object> data = database.getFile(rev);
		byte[] iv = (byte[])data.get("iv");
		String level = (String)data.get("securityLevel");
		
		byte[] key = KeyDerivation.retrieveKey((String)data.get("fileRev"), null, level);
		
		try {
			InputStream fileStream = new java.io.FileInputStream(file);
		    byte[] fileContents = new byte[fileStream.available()];
			fileStream.read(fileContents);
			fileStream.close();
			
			byte[] decrypted = cipher.decrypt(fileContents, key, iv);
			
			FileOutputStream fileWriter = new FileOutputStream(file, false);
			fileWriter.write(decrypted);
			fileWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
}
