package Server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.dropbox.client2.session.WebAuthSession;

import Linking.State;

/* TODO: update file list */

public class CentralAuthority {
	
	public static void options(State state) {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 1 to upload an encrypted file");
		System.out.println("Enter 2 to download a decrypted file");
		System.out.println("Enter 3 to add friend");
		try {
			String decision = input.readLine();
			if (decision.equals("1"))
				uploadEncryptedFile(input, state.getSession(), state.getUID());

			if (decision.equals("2"))
				downloadDecryptedFile(input, state.getSession(), state.getUID());
			if	(decision.equals("3"))
				addUser(input, state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	private static void addUser(BufferedReader input, State state) {
		
		String username = null;
		
		boolean userExists = false;
		
		while (!userExists) {
		
			System.out.println("Enter user name to friend");
		
			try {
				username = input.readLine();
			
				userExists = UserOperations.checkUserExists(username);
				
				if (!userExists)
					System.out.println("User does not exist. Try again.");
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(BufferedReader input, WebAuthSession session, String uid) {
		
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
		if(file.exists())
			 FileOperations.encryptFile(file, fileName, session, securityLevel, uid);
		else
			System.out.println("File does not exist");
	}
	
	/* The user is requested for the file to be downloaded and the location for
	 * it to be downloaded to. The file is downloaded and then decrypted. */
	private static void downloadDecryptedFile(BufferedReader input, WebAuthSession session, String uid) {
		
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
				FileOperations.decryptFile(file, rev, uid);				
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