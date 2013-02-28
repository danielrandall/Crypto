package Server;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;

import com.dropbox.client2.session.WebAuthSession;

import Ciphers.AESCipher;
import Ciphers.Cipher;
import Linking.State;



public class CentralAuthority {
	
	private static Cipher cipher = new AESCipher();
	
	public static void options(State state) {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to upload an encrypted file");
		System.out.println("Enter 2 to download a decrypted file");
		
		try {
			String decision = input.readLine();
			if (decision.equals("1"))
				uploadEncryptedFile(input, state.getSession());

			if (decision.equals("2"))
				downloadDecryptedFile(input, state.getSession());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(BufferedReader input, WebAuthSession session) {
		
		InputStream fileStream;
		
		System.out.println("Enter file address");
		String fileName = null;
		try {
			fileName = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(fileName);
		if(file.exists()) {
			System.out.println("File exists");
			 try {
				fileStream = new java.io.FileInputStream(file);
				
				try {
					byte[] fileContents = new byte[fileStream.available()];
					fileStream.read(fileContents);
					fileStream.close();
					String encrypted = cipher.encrypt(new String(fileContents), "1234567891234567", "1234567891234567".getBytes());
					ByteArrayInputStream inputStream = new ByteArrayInputStream(encrypted.getBytes());
				    DropboxOperations.uploadFile(fileName, inputStream, session, encrypted.length());		    
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("File does not exist");
	}
	
	/* The user is requested for the file to be downloaded and the location for
	 * it to be downloaded to. The file is downloaded and then decrypted. */
	private static void downloadDecryptedFile(BufferedReader input, WebAuthSession session) {
		
		System.out.println("Enter file to download");
		String fileDownloadName = null;
		try {
			fileDownloadName = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Check exists on db
		System.out.println("Enter download location");
		String downloadLocation = null;
		try {
			downloadLocation = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(downloadLocation);
		if(file.exists())
			System.out.println("File exists at location. Aborted.");
		else {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(file);
				// Returns info
				DropboxOperations.downloadFile(session, fileDownloadName, outputStream);
				decryptFile(file);				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			    if (outputStream != null) {
			        try {
			            outputStream.close();
			        } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			    }
			}
		}
		
	}
	
	/* The contents of the given file are extracted and decrypted. The file
	 * is then overwritten with the plaintext. */
	private static void decryptFile(File file) {
		
		try {
			InputStream fileStream = new java.io.FileInputStream(file);
		    byte[] fileContents = new byte[fileStream.available()];
			fileStream.read(fileContents);
			fileStream.close();
			
			String decrypted = cipher.decrypt(new String(fileContents), "1234567891234567", "1234567891234567".getBytes());
			
			FileOutputStream fileWriter = new FileOutputStream(file, false);
			fileWriter.write(decrypted.getBytes());
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