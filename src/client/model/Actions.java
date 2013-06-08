package client.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ciphers.SecurityVariables;
import client.model.keystore.KeyStoreOperations;

public class Actions {
	
	private static final String TRUE = "1";
	
	private static final String UPLOAD_FILE = "1";
	private static final String DOWNLOAD_FILE = "2";
	private static final String FRIEND_REQUEST = "3";
	private static final String DELETE_FILE = "4";
	private static final String ACCEPT_FRIEND_REQUEST = "5";
	private static final String IGNORE_FRIEND_REQUEST = "6";
	private static final String DOWNLOAD_FRIEND_FILE = "7";
	private static final String REVOKE_USER = "8";
	
	private static final String GET_SECURITY_LEVEL = "50";
	private static final String GET_FRIENDS = "51";
	private static final String GET_FRIEND_REQUESTS = "52";
	private static final String CHECK_UPDATES = "53";
	
	/* Locations for downloading and unencrypting files */
	private static final String DOWNLOAD_LOCATION = "DownloadedAppFiles";	
	private static final String FRIEND_FILE_DOWNLOAD_LOCATION = "DownloadedAppFiles/FriendFiles";
	
	/* Location used to temporarily store files while they await re-encryption
	 * after keys have been revoked */
	private static final String TEMP_FILE_STORE = "tempstore";

	
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
		ServerComms.sendInt(lowerBound);
		ServerComms.sendInt(upperBound);
		
		encryptKey(Integer.toString(lowerBound));
		
		return true;
		
	}
	
	private static void encryptKey(String securityLevel) {
		
		/* Receive the user to add's public key */
		byte[] usersPublicKey = ServerComms.getBytes();
		
		/* Get the highest level key to send to the user */
		byte[] keyToSend = KeyStoreOperations.retrieveOwnKey(
				securityLevel);
		
		/* Encrypt the highest key with the user to add's public key */
		byte[] encryptedKey = FileOperations.asymmetricEncrypt(keyToSend, usersPublicKey);
		
		/* Send key to the server */
		ServerComms.sendBytes(encryptedKey, encryptedKey.length);
		
	}
	
	
	public static void acceptFriendRequest(String username) {
		
		ServerComms.toServer(ACCEPT_FRIEND_REQUEST);
		ServerComms.toServer(username);
		
		/* Retrieve the most influential key (ie. the highest security level
		 * you have access to) and its security level from the server */
		String securityLevel = ServerComms.fromServer();
		ServerComms.sendInt(1);
		
		/* Retrieve this user's private key */
		byte[] privateKeyBytes = KeyStoreOperations.retrievePrivateKey();
		
		KeyDerivation.deriveKeys(username, privateKeyBytes, securityLevel);
		
	}
	
	
	/* TODO implement on server side */
	public static void ignoreFriendRequest(String username) {
		
		ServerComms.toServer(IGNORE_FRIEND_REQUEST);
		
		ServerComms.toServer(username);
		
	}


	public static String uploadFile(String fileLocation, int securityLevel) {
		
		/* Tell the server the user wishes to upload a file. */
		ServerComms.toServer(UPLOAD_FILE);
		
		/* Send security level of file to server. */
		ServerComms.toServer(Integer.toString(securityLevel));
		
		/* Receive Acknowledgement */
		ServerComms.getInt();
		
		byte[] key = KeyStoreOperations.retrieveOwnKey(Integer.toString(securityLevel));
		
		File file = new File(fileLocation);
		String fileName = file.getName();
		
		byte[][] encryptedFileInfo = FileOperations.encryptFile(file, fileName, key);
		
		byte[] encryptedFile = encryptedFileInfo[0];
		byte[] iv = encryptedFileInfo[1];
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(encryptedFile);
		String[] fileInfo = DropboxOperations.uploadFile(fileName, inputStream, encryptedFile.length);
		
		String newFileName = fileInfo[0];
		String rev = fileInfo[1];
		
		/* Send iv, needed for decryption, to be stored in the server. */
		ServerComms.sendBytes(iv, iv.length);
		/* Send file id to the server for file identification. */
		ServerComms.toServer(rev);
		/* Send file name to the server for file transfer to friends. */
		ServerComms.toServer(fileName);
		
		return newFileName;
		
	}
	
	/* Downloads and unencrypts the requested file to a pre-determined download
	 * location.
	 */
	public static void downloadFile(String fileName) {		
		
		/* Tell server a file is being downloaded */
		ServerComms.toServer(DOWNLOAD_FILE);
		
		String rev = processDownload(fileName, DOWNLOAD_LOCATION);
		/* Send file information to the server */
		ServerComms.toServer(rev);
		
		/* Retrieve necessary information from the server */
		byte[] iv = ServerComms.getBytes();
		String securityLevel = ServerComms.fromServer();
		
		byte[] key = KeyStoreOperations.retrieveOwnKey(securityLevel);
		
		FileOperations.decryptFile(new File(DOWNLOAD_LOCATION + "/" + fileName), iv, key);	
		
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
	
	
	/* Revokes the users.
	 * Sends new keys + ivs to the server.
	 * The new encrypted files need to be sent after the new keys and
	 * corresponding ivs have sent as the server is awaiting them. */
	public static void revokeUser(String username) {
		
		ServerComms.toServer(REVOKE_USER);
		ServerComms.toServer(username);
		
		int securityLevelOfUser = Integer.parseInt(ServerComms.fromServer());
		
		/* Receive the number files under the influence of the security
		 * level */
		int numberFilesEffected = Integer.parseInt(ServerComms.fromServer());

		Map<String, ClientFile> filesToUnencrypt =
				new HashMap<String, ClientFile>();
		
		for (int i = 0; i < numberFilesEffected; i++) {
			
			String rev = ServerComms.fromServer();
			ServerComms.sendInt(1);
			
			byte[] iv = ServerComms.getBytes();
			int fileSLevel = Integer.parseInt(ServerComms.fromServer());
			
			filesToUnencrypt.put(rev, new ClientFile(rev, iv, fileSLevel));
		}
		
		/* Find the corresponding file names for each file to unencrypt */
		filesToUnencrypt = DropboxOperations.findFileNames(filesToUnencrypt);
		
		/* Decrypt all of the files and place in a temporary store.
		 * Remove all of the files from Dropbox. */
		Iterator<Entry<String, ClientFile>> it = filesToUnencrypt.entrySet().iterator();
		while (it.hasNext()) {
			
			ClientFile file = it.next().getValue();
			
			String fileName = file.getFileName();
			byte[] iv = file.getIV();
			int securityLevel = file.getSecurityLevel();
			
			byte[] key = KeyStoreOperations.retrieveOwnKey(Integer.toString(securityLevel));
			
			/* Download the file */
			processDownload(fileName, TEMP_FILE_STORE);
			
			/* Decrypt the file */
			FileOperations.decryptFile(new File(TEMP_FILE_STORE + "/" + fileName), iv, key);	
			
			/* Remove the file from Dropbox */
			DropboxOperations.removeFile(fileName);
		}
			
		/* Generate new keys */
		updateSymmetricVariables(securityLevelOfUser);
		
		/* Get number of friends to notify */
		int numFriends = Integer.parseInt(ServerComms.fromServer());
		ServerComms.sendInt(1);
		
		/* Receive public key and highest security level of friend, encrypt and
		 * send to the server */
		for (int i = 0; i < numFriends; i++) {
			String maxSecurityLevel = ServerComms.fromServer();
			ServerComms.sendInt(1);
			
			encryptKey(maxSecurityLevel);
		}
		
		if (!(ServerComms.getInt() == 1))
			System.out.println("out of synce before file re-uploading");
		
		/* Encrypt and upload each decrypted file as normal.
		 * Delete each temp file when done with it. */
		it = filesToUnencrypt.entrySet().iterator();
		while (it.hasNext()) {
			ClientFile file = it.next().getValue();
			
			String fileName = file.getFileName();
			int securityLevel = file.getSecurityLevel();

			
			uploadFile(TEMP_FILE_STORE + "/" + fileName, securityLevel);
			
			File temp_File = new File(TEMP_FILE_STORE + "/" + fileName);
			temp_File.delete();
		}
		
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
	
	/* Creates an output stream and requests Dropbox to download the file there */
	private static String processDownload(String fileName, String folderLocation) {
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File downloadFolder = new File(folderLocation);
		if (!downloadFolder.exists())
			downloadFolder.mkdir();
		
		String downloadLocation = folderLocation + "/" + fileName;
		
		File file = new File(downloadLocation);
		
		if (file.exists()) 
			return null;
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Returns file info */
		String rev = DropboxOperations.downloadFile(fileName, outputStream);
		
		return rev;
		
	}

	public static void checkUpdates() {
		
		ServerComms.toServer(CHECK_UPDATES);
		
		int numUpdates = Integer.parseInt(ServerComms.fromServer());
		
		/* Retrieve this user's private key */
		byte[] privateKeyBytes = KeyStoreOperations.retrievePrivateKey();
		
		for (int i = 0; i < numUpdates; i++) {
			
			String username = ServerComms.fromServer();
			
			/* Retrieve the most influential key (ie. the highest security level
			 * you have access to) and its security level from the server */
			String securityLevel = ServerComms.fromServer();
			
			KeyDerivation.deriveKeys(username, privateKeyBytes, securityLevel);
			
		}
		
	}
	
	
	/* Encrypted keys is 1 smaller than generated keys. */
	private static void updateSymmetricVariables (int highestSecurityLevel) {
		
		int numLevels = SecurityVariables.getNumberLevels();
		int numKeys = numLevels - highestSecurityLevel + 1;;
		
		byte[][] keys = SecurityVariables.generateKeys(numKeys);		
		byte[][] ivs = SecurityVariables.generateIVs(numLevels);
		
		/* Store the keys in the key store */
		for (int i = 0; i < numKeys; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(highestSecurityLevel + i), keys[i]);
		
		byte[][] keysToEncrypt;
		if (numKeys == numLevels) {
			keysToEncrypt = keys;
		 	ivs = SecurityVariables.generateIVs(numKeys);
		} else {
			keysToEncrypt = new byte[numKeys + 1][];
			ivs = SecurityVariables.generateIVs(numKeys + 1);
			keysToEncrypt[0] = KeyStoreOperations.retrieveOwnKey(Integer.toString(highestSecurityLevel - 1));
			for (int i = 1; i < numKeys + 1; i++)
				keysToEncrypt[i] = keys[i - 1];
		}
		
		EncryptedKey[] encryptedKeys = FileOperations.encryptKeys(keysToEncrypt, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			byte[] encryptedKey = encryptedKeys[i].getEncryptedKey();
			ServerComms.sendBytes(encryptedKey, encryptedKey.length);
			ServerComms.getInt();
			
			byte[] iv = encryptedKeys[i].getIV();
			ServerComms.sendBytes(iv, iv.length);
			ServerComms.getInt();
		}
		
		keys = null;
		
	}

}
