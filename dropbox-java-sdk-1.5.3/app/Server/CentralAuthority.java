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

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.session.WebAuthSession;

import Ciphers.AESCipher;
import Ciphers.Cipher;
import Linking.State;

/* TODO: update file list */

public class CentralAuthority {
	
	private static Cipher cipher = new AESCipher();
	
	public static void options(State state) {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to upload an encrypted file");
		System.out.println("Enter 2 to download a decrypted file");
	//	System.out.println("Enter 3 to see files");
		byte[] iv = cipher.generateIV();
		try {
			String decision = input.readLine();
			if (decision.equals("1"))
				uploadEncryptedFile(input, state.getSession(), iv);

			if (decision.equals("2"))
				downloadDecryptedFile(input, state.getSession(), iv);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(BufferedReader input, WebAuthSession session, byte[] iv) {
		
		String fileName = null;
		
		int securityLevel = 1;
			
		System.out.println("Enter file address");
		try {
			fileName = input.readLine();
				
			System.out.println("Enter level of security for the file (1 being the highest)");
			securityLevel = Integer.parseInt(input.readLine());   // DEAL WITH NON INT ENTRY 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(fileName);
		if(file.exists()) {
			System.out.println("File exists");
			 FileOperations.encryptFile(file, fileName, session, iv, securityLevel);
		} else
			System.out.println("File does not exist");
	}
	
	/* The user is requested for the file to be downloaded and the location for
	 * it to be downloaded to. The file is downloaded and then decrypted. */
	private static void downloadDecryptedFile(BufferedReader input, WebAuthSession session, byte[] iv) {
		
		String fileDownloadName = null;

		System.out.println("Enter file to download");
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
				String rev = DropboxOperations.downloadFile(session, fileDownloadName, outputStream);
				FileOperations.decryptFile(file, rev);				
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
	

	
}