package client.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import client.model.linking.keystore.KeyStoreOperations;

public class CentralAuthority {
	
	private static final String TRUE = "1";
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String FRIEND_REQUEST = "3";
	private static final String DELETE_FILE = "4";
	private static final String ACCEPT_FRIEND_REQUEST = "5";
	private static final String IGNORE_FRIEND_REQUEST = "6";
	private static final String DOWNLOAD_FRIEND_FILE = "7";
	
	private static final String GET_SECURITY_LEVEL = "50";
	private static final String GET_FRIENDS = "51";
	private static final String GET_FRIEND_REQUESTS = "52";
	
	private static final String DOWNLOAD_LOCATION = "DownloadedAppFiles";	
	private static final String FRIEND_FILE_DOWNLOAD_LOCATION = "DownloadedAppFiles/FriendFiles";

	
	public static boolean friendRequest(String usernameToAdd, int lowerBound, int upperBound) {
		
		String response = null;
		
		/* Tell server a user is to be added */
		ServerComms.toServer(FRIEND_REQUEST);
	
		/* Send username to server */
		ServerComms.toServer(usernameToAdd);
			
		/* Receive response */
		response = ServerComms.fromServer();
			
		if (!response.equals(TRUE))
			return false;
		
		/* Send bounds to the server */
		ServerComms.toServer(Integer.toString(lowerBound));
		ServerComms.toServer(Integer.toString(upperBound));
		
		/* Get the highest level key to send to the user */
		byte[] key = KeyStoreOperations.retrieveOwnKey(
				Integer.toString(lowerBound));
		
		/* Send key to the server */
		ServerComms.sendBytes(key, key.length);
		
		return true;
		
	}
	
	
	public static void acceptFriendRequest(String username) {
		
		ServerComms.toServer(ACCEPT_FRIEND_REQUEST);
		ServerComms.toServer(username);
		
		/* Retrieve the most influential key (ie. the highest security level
		 * you have access to) and its security level from the server */
		String securityLevel = ServerComms.fromServer();
		byte[] highestKey = ServerComms.getBytes();
		
		/* Store the key */
		KeyStoreOperations.storeFriendKey(username, securityLevel, highestKey);
		
		int securityLevelInt = Integer.parseInt(securityLevel);
		
		byte[] previousKey = highestKey;
		
		/* Receive from the server the number of keys to decrypt and use */
		int numberOfWeakerKeys = Integer.parseInt(ServerComms.fromServer());
		
		/* Retrieve the following, encrypted, keys from the server in order.
		 * Decrypt them with their previous key and store them with their
		 * associated security levels. */
		for (int i = securityLevelInt + 1; i <= securityLevelInt + numberOfWeakerKeys; i++) {
			
			byte[] encryptedKey = ServerComms.getBytes();
			byte[] iv = ServerComms.getBytes();
			
			byte[] decryptedKey = FileOperations.decryptKey(encryptedKey, previousKey, iv);
			
			KeyStoreOperations.storeFriendKey(username, Integer.toString(i), decryptedKey);
			
			previousKey = decryptedKey;
			
		}
		
	}
	
	
	/* TODO implement on server side */
	public static void ignoreFriendRequest(String username) {
		
		ServerComms.toServer(IGNORE_FRIEND_REQUEST);
		
		ServerComms.toServer(username);
		
	}


	public static void uploadFile(String fileLocation, int securityLevel) {
		
		/* Tell the server the user wishes to upload a file. */
		ServerComms.toServer(UPLOAD_FILE);
		
		/* Send security level of file to server. */
		ServerComms.toServer(Integer.toString(securityLevel));
		
		byte[] key = KeyStoreOperations.retrieveOwnKey(Integer.toString(securityLevel));
		//byte[] key = ServerComms.getBytes();
		
		File file = new File(fileLocation);
		String fileName = file.getName();
		
		byte[][] encryptedFileInfo = FileOperations.encryptFile(file, fileName, key);
		
		byte[] encryptedFile = encryptedFileInfo[0];
		byte[] iv = encryptedFileInfo[1];
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(encryptedFile);
		String rev = DropboxOperations.uploadFile(fileName, inputStream, encryptedFile.length);
		
		/* Send iv, needed for decryption, to be stored in the server. */
		ServerComms.sendBytes(iv, iv.length);
		/* Send file id to the server for file identification. */
		ServerComms.toServer(rev);
		/* Send file name to the server for file transfer to friends. */
		ServerComms.toServer(fileName);
		
	}
	
	/* Downloads and unencrypts the requested file to a pre-determined download
	 * location.
	 */
	public static void downloadFile(String fileName) {
		
		/*
		 fileDownloadName - name of file
		 downloadLocation - directory and file name
		 public static void downloadFile(String fileDownloadName, String downloadLocation) {
		
		ServerComms.toServer(DOWNLOAD_FILE);
		
		File file = new File(downloadLocation);
		
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String rev = DropboxOperations.downloadFile(fileDownloadName, outputStream);
		
		ServerComms.toServer(rev);
		
		byte[] iv = ServerComms.getBytes();
		String securityLevel = ServerComms.fromServer();
		
		byte[] key = KeyStoreOperations.retrieveOwnKey(securityLevel);
		
		FileOperations.decryptFile(file, rev, iv, key);	
		*/
		
		
		File downloadFolder = new File(DOWNLOAD_LOCATION);
		if (!downloadFolder.exists())
			downloadFolder.mkdir();
		
		String downloadLocation = DOWNLOAD_LOCATION + "/" + fileName;
		
		File file = new File(downloadLocation);
		
		if (file.exists()) 
			return;
		
		
		/* Tell server a file is being downloaded */
		ServerComms.toServer(DOWNLOAD_FILE);
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Returns info */
		String rev = DropboxOperations.downloadFile(fileName, outputStream);
		
		/* Send file information to the server */
		ServerComms.toServer(rev);
		
		/* Retrieve necessary information from the server */
		byte[] iv = ServerComms.getBytes();
		String securityLevel = ServerComms.fromServer();
		
		byte[] key = KeyStoreOperations.retrieveOwnKey(securityLevel);
		
		FileOperations.decryptFile(file, iv, key);	
		
	}
	
	
	public static void downloadFriendFile(String fileName, String owner) {
		
		/* Tell server a file belonging to a friend is being downloaded */
		ServerComms.toServer(DOWNLOAD_FRIEND_FILE);
		
		/* Send file information to the server */
		ServerComms.toServer(owner);
		ServerComms.toServer(fileName);
		
		/* Receive back the IV and security level needed for decryption */
		byte[] iv = ServerComms.getBytes();
		String securityLevel  = ServerComms.fromServer();
		
		//System.out.println(securityLevel);
		
		String downloadFolderLocation = FRIEND_FILE_DOWNLOAD_LOCATION + "/" + owner;
		
		File downloadFolder = new File(downloadFolderLocation);
		if (!downloadFolder.exists())
			downloadFolder.mkdirs();
		
		String downloadFileLocation = downloadFolderLocation + "/" + fileName;
		
		File file = new File(downloadFileLocation);
		
		if (file.exists()) 
			return;
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Returns info */
		DropboxOperations.downloadFriendFile(fileName, owner, outputStream);
		
		byte[] key = KeyStoreOperations.retrieveFriendKey(owner, securityLevel);
		
		FileOperations.decryptFile(file, iv, key);
		
	}
	
	
	public static void removeFile(String fileName) {
		
		/* Tell server a file is being removed. */
		ServerComms.toServer(DELETE_FILE);
		
		String rev = DropboxOperations.removeFile(fileName);
		
		ServerComms.toServer(rev);
		
	}
	
	/* Retrieves uploaded file names and their security level */
	public static Object[][] getUploadedFiles() {
		
		String[][] uploadedFiles = DropboxOperations.getUploadedFiles();
		int size = uploadedFiles.length;

		Object[][] filesAndSecurityLevels = new Object[size][2];
		
		
		for (int i = 0; i < size; i++) {
			
			ServerComms.toServer(GET_SECURITY_LEVEL);

			ServerComms.toServer(uploadedFiles[i][1]);
			
			String s = ServerComms.fromServer();

			int securityLevel = Integer.parseInt(s);
		
			filesAndSecurityLevels[i][0] = uploadedFiles[i][0];
			filesAndSecurityLevels[i][1] = securityLevel;
			
		}
		
		return filesAndSecurityLevels;
			
	}
	
	
	public static Object[][] getFriends() {
		
		ServerComms.toServer(GET_FRIENDS);
		int size = Integer.parseInt(ServerComms.fromServer());
		Object[][] friends = new Object[size][2];
		
		for (int i = 0; i < size; i++) {

			String username = ServerComms.fromServer();

			int securityLevel = Integer.parseInt(ServerComms.fromServer());
			
			friends[i][0] = username;
			friends[i][1] = securityLevel;
			
		}
		
		return friends;
		
	}
	
	
	public static Object[][] getFriendRequests() {
		
		ServerComms.toServer(GET_FRIEND_REQUESTS);
		
		int size = Integer.parseInt(ServerComms.fromServer());
		Object[][] friendRequests = new Object[size][2];
		
		for (int i = 0; i < size; i++) {
			
			String username = ServerComms.fromServer();
			int securityLevel = Integer.parseInt(ServerComms.fromServer());
			
			friendRequests[i][0] = username;
			friendRequests[i][1] = securityLevel;
			
		}
		
		return friendRequests;
		
	}
	
	
	public static Object[][] getFriendFiles() {
		
		return DropboxOperations.getFriendFiles();
		
	}

}
