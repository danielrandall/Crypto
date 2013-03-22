package Client.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.session.Session;
import Ciphers.AESCipher;
import Ciphers.Cipher;

public class FileOperations {
	
	private static Cipher cipher = new AESCipher();
	
	public static void encryptFile(File file, String fileName, Session session) {
		
		InputStream fileStream;
		byte[] iv = cipher.generateIV();
		
		try {
			
			fileStream = new java.io.FileInputStream(file);
				
			byte[] fileContents = new byte[fileStream.available()];
			fileStream.read(fileContents);
			fileStream.close();
				
			byte[] key = ServerComms.getBytes();
				
			byte[] encrypted = cipher.encrypt(fileContents, key, iv);
				
			ByteArrayInputStream inputStream = new ByteArrayInputStream(encrypted);
			Entry entry = DropboxOperations.uploadFile(fileName, inputStream, session, encrypted.length);
				
			/* Send iv */
			ServerComms.sendBytes(iv, iv.length);
			/* Send file id */
			ServerComms.toServer(entry.rev);
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/* The contents of the given file are extracted and decrypted. The file
	 * is then overwritten with the plaintext. */
	
	public static void decryptFile(File file, String rev) {
		
		byte[] iv = ServerComms.getBytes();
		byte[] key = ServerComms.getBytes();
		
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
