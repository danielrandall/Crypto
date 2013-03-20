package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.dropbox.client2.session.WebAuthSession;

import Linking.Authentication;
import Linking.User;

/* TODO: update file list */

public class CentralAuthority {
	
	private static final String UPLOAD_FILE = "1";
	
	public static void options(User user, ClientComms comms) {
		
		/* Get central option from user */
		String decision = comms.fromClient();
		
			if (decision.equals(UPLOAD_FILE))
				uploadEncryptedFile(user.getSession(), user.getUsername(), comms);

		//	if (decision.equals("2"))
			//	downloadDecryptedFile(user.getSession(), user.getUsername());
		//	if	(decision.equals("3"))
		//		addUser(user);

	
	}
	
	private static void addUser(BufferedReader input, User user) {
		
		String usernameToAdd = null;
		
		boolean userExists = false;
		int lowerBound = 5;
		int upperBound = 5;
		
		try {
		
			while (!userExists) {
		
				System.out.println("Enter user name to friend");
		
				usernameToAdd = input.readLine();
			
				userExists = UserOperations.checkUserExists(usernameToAdd);
				
				if (!userExists)
					System.out.println("User does not exist. Try again.");
			}
			
			System.out.println("Enter lower bound security level");
			lowerBound = Integer.parseInt(input.readLine());   // DEAL WITH NON INT ENTRY
			
			System.out.println("Enter upper bound security level");
			upperBound = Integer.parseInt(input.readLine());   // DEAL WITH NON INT ENTRY
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Add user to friends list */
		UserOperations.addUserToFriendsList(user.getUsername(), usernameToAdd, lowerBound, upperBound);
		
		User frienduser = Authentication.createUser(usernameToAdd);
		
		/* Share files with user */
		DropboxOperations.shareFilesWithFriend(usernameToAdd,
		            frienduser.getSession(), user.getUsername(),
		                                      user.getSession());
		
		
	}

	/* The user is request for the address of the local file to be uploaded.
	 * If the file exists then it is encrypted and uploaded to Dropbox. */
	private static void uploadEncryptedFile(WebAuthSession session, String username, ClientComms comms) {
		
		String fileName = null;
		
		int securityLevel = Integer.parseInt(comms.fromClient());
		
		FileOperations.encryptFile(session, securityLevel, username, comms);
		
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