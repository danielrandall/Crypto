package client.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CentralAuthority {
	
	private static final String TRUE = "1";
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String ADD_FRIEND = "3";

	public static boolean addFriend(String usernameToAdd, int lowerBound, int upperBound) {
		
		/* Tell server a user is to be added */
		ServerComms.toServer(ADD_FRIEND);
		
		String response = null;
	
		/* Send username to server */
		ServerComms.toServer(usernameToAdd);
			
		/* Receive response */
		response = ServerComms.fromServer();
			
		if (!response.equals(TRUE))
			return false;
		
		/* Send bounds to the server */
		ServerComms.toServer(Integer.toString(lowerBound));
		ServerComms.toServer(Integer.toString(upperBound));
		
		return true;
		
	}


	public static void uploadFile(String fileLocation, int securityLevel) {
		
		/* Tell the server the user wishes to upload a file */
		ServerComms.toServer(UPLOAD_FILE);
		
		/* Send security level of file to server */
		ServerComms.toServer(Integer.toString(securityLevel));
		
		File file = new File(fileLocation);
		
		FileOperations.encryptFile(file, file.getName(), DropboxOperations.getSession());
		
	}
	
	public static void downloadFile(String fileDownloadName, String downloadLocation) {
		
		/* Tell server a file is being downloaded */
		ServerComms.toServer(DOWNLOAD_FILE);
		
		File file = new File(downloadLocation);
		
		/*
		if (file.exists()) {
			view.fileExists();
			return;
		}
		*/
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Returns info
		String rev = DropboxOperations.downloadFile(DropboxOperations.getSession(), fileDownloadName, outputStream);
		
		/* Send file information to the server */
		ServerComms.toServer(rev);
		
		FileOperations.decryptFile(file, rev);	
		
		
	}

}
