package client.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import Ciphers.AESCipher;
import Ciphers.Cipher;
import Ciphers.SecurityVariables;

public class FileOperations {
	
	private static Cipher cipher = new AESCipher();
	
	/* Encrypts a given file and returns encrypted file and the generated iv
	 * which is needed for decryption */
	public static byte[][] encryptFile(File file, String fileName, byte[] key) {
		
		InputStream fileStream;
		byte[] iv = SecurityVariables.generateIV();
		
		byte[][] encryptedFileInfo = new byte[2][];
		
		try {
			
			fileStream = new java.io.FileInputStream(file);
				
			byte[] fileContents = new byte[fileStream.available()];
			fileStream.read(fileContents);
			fileStream.close();
				
			byte[] encryptedFile = cipher.encrypt(fileContents, key, iv);
			
			encryptedFileInfo[0] = encryptedFile;
			encryptedFileInfo[1] = iv;
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encryptedFileInfo;
		
	}
	
	
	/* The contents of the given file are extracted and decrypted. The file
	 * is then overwritten with the plaintext. */
	public static void decryptFile(File file, String rev, byte[] iv, byte[] key) {
		
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

	/* Encrypts the keys with its predecessor using the given ivs */
	public static byte[][] encryptKeys(byte[][] keys, byte[][] ivs) {
		
		int numKeys = keys.length - 1;
		byte[][] encryptedKeys = new byte[numKeys][];
		
		for (int i = 0; i < numKeys; i++) {
			encryptedKeys[i] = cipher.encrypt(keys[i + 1], keys[i], ivs[i]);
		}
		
		return encryptedKeys;
		
	}
	
	
	public static byte[] decryptKey (byte[] keyToDecrpyt, byte[] previousKey, byte[] iv) {
		
		return cipher.decrypt(keyToDecrpyt, previousKey, iv);
		
	}

}
