package Client.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.dropbox.client2.session.Session;

import Client.view.View;

public class CentralAuthority {
	
	private static final String TRUE = "1";
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String ADD_FRIEND = "3";
	
	/*
	public static void options(ServerComms comms, View view) {
		
		String key = ServerComms.fromServer();
		String secret = ServerComms.fromServer();
		
		Session session = DropboxOperations.makeSession(key, secret);
		
		String option = view.getCentralDecision();
		
		ServerComms.toServer(option);
		
		if (option.equals(UPLOAD_FILE))
			uploadFile(comms, view, session);
		
		if (option.equals(DOWNLOAD_FILE))
			downloadFile(comms, view, session);
		
		if (option.equals(ADD_FRIEND))
			addFriend(comms, view, session);
		
	}
    */

	private static void addFriend(ServerComms comms, View view, Session session) {
		
		boolean userExists = false;
		String usernameToAdd = null;
		String response = null;

		while (!userExists) {
	
			usernameToAdd = view.addUser();
		
			/* Send username to server */
			ServerComms.toServer(usernameToAdd);
			
			/* Recieve response */
			response = ServerComms.fromServer();
			
			if (response.equals(TRUE))
				userExists = true;
			else
				view.userNotExist();
		}
		
		String lowerBound = view.getLowerBound();
		String upperBound = view.getUpperBound();
		
		ServerComms.toServer(lowerBound);
		ServerComms.toServer(upperBound);
	}


	public static void uploadFile(String fileLocation, int securityLevel) {
		
		ServerComms.toServer(UPLOAD_FILE);
		
		File file = new File(fileLocation);
		
		/* Send security level of file to server */
		ServerComms.toServer(Integer.toString(securityLevel));
		
		FileOperations.encryptFile(file, fileLocation, DropboxOperations.getSession());
		
	}
	
	private static void downloadFile(ServerComms comms, View view, Session session) {
		
		String fileDownloadName = view.getFileName();
		
		String downloadLocation = view.getDownloadLocation();
		
		File file = new File(downloadLocation);
		
		if (file.exists()) {
			view.fileExists();
			return;
		}
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Returns info
		String rev = DropboxOperations.downloadFile(session, fileDownloadName, outputStream);
		
		/* Send file information to the server */
		ServerComms.toServer(rev);
		
		FileOperations.decryptFile(file, rev, comms);	
		
		
	}

}
