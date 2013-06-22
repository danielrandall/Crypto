package client.model;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;

public class DropboxOperations {
	
	private static final String APP_KEY = "bpyf040d6hcvi5t";
    private static final String APP_SECRET = "96a0wcdkxlqxt3w";
    private static final AppKeyPair KEY_PAIR = new AppKeyPair(APP_KEY, APP_SECRET);
	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	
	/* The name of folder which houses the files this user uploads */
	private static final String OWN_FILE_FOLDER = " My Files";
	/* The name of folder which houses the files shared with this user */
	private static final String FRIENDS_FILE_FOLDER = " Friends Files";
	
	private static String username;
	
	private static Session session;
	
	
	/*
	public static void main(String[] args) {
		
		String[] stuff = authenticate();
		session = makeSession(stuff[0], stuff[1]);
		setUsername("username");
		
		File downloadFolder = new File("DownloadedAppFiles");
		if (!downloadFolder.exists())
			downloadFolder.mkdir();
		
		String downloadLocation = "DownloadedAppFiles" + "/" + "Costa Rican Frog.jpg";
		
		File file = new File(downloadLocation);
		
		FileOutputStream outputStream = null;
		
		try {
			
			outputStream = new FileOutputStream(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String rev = downloadFile("Costa Rican Frog.jpg", outputStream);
		
		System.out.println(rev);
	}
	*/
	
	
	/* Given the necessary information this method creates a Dropbox session
	 * and stores it and returns it.
	 */
    public static void makeSession(String key, String secret) {
    	
    	AccessTokenPair atp = new AccessTokenPair(key, secret);
    	session = new WebAuthSession(KEY_PAIR, ACCESS_TYPE, atp);
    	
    }
    
    
    /* Function used to upload large files.
     * Large files are more volatile than small files and can be dropped
     * by the network. FOr this reason it is uploaded in chunks instead of
     * all at once. */
    public static String[] chunkedUpload(String path, InputStream inputStream,
			int length) {
    	
    	DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		Entry newEntry = null;
		
		try {
			@SuppressWarnings("rawtypes")
			DropboxAPI.ChunkedUploader uploader = client.getChunkedUploader(inputStream, length);
	         int retryCounter = 0;
	         
	         while(!uploader.isComplete()) {
	             try {
	                 uploader.upload();
	             } catch (DropboxException e) {
	                 if (retryCounter > 10) break;  // Give up after a while.
	                 retryCounter++;
	                 // Maybe wait a few seconds before retrying?
	             } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         newEntry = uploader.finish(username + OWN_FILE_FOLDER + "/" + path, null);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String[] fileInfo = {newEntry.fileName(), newEntry.rev};

		return fileInfo;
    	
    }

	/* Uploads a file to the location dropboxPath for the current logged in user.
	 * Will not overwrite existing file.
	 * Pass in revision (rev - entry.rev) if you want to overwrite the file
	 * with the same name.
	 * Requires the username to be set beforehand.
	 * Returns the filename and rev of the file */
	public static String[] uploadFile(String path, InputStream inputStream,
										int length) {
	
		/* All files larger than 150MB are to uploaded in chunks */
		if (length > 1258291200)
			return chunkedUpload(path, inputStream, length);
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		Entry newEntry = null;
		
		try {
			newEntry = client.putFile(username + OWN_FILE_FOLDER + "/" + path, inputStream, length, null, null);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String[] fileInfo = {newEntry.fileName(), newEntry.rev};

		return fileInfo;
	}
	
	
	/* Prompts a user to authenticate the app with Dropbox.
	 * Once the authentication information (key and secret) is acquired it is
	 * sent to the server to be stored and a new Session is created locally and
	 * stored.
	 */
	public static String[] authenticate() {
		
		WebAuthSession session = new WebAuthSession(KEY_PAIR, ACCESS_TYPE);
        WebAuthInfo authInfo;
        
        String[] authenticationInfo = new String[3];
		
        try {
        
			authInfo = session.getAuthInfo();
			RequestTokenPair pair = authInfo.requestTokenPair;
			String url = authInfo.url;
 
			/* Open a window in the user's browser so they can authenticate
			 * the app */
			Desktop.getDesktop().browse(new URL(url).toURI());
			JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        
			String uid = session.retrieveWebAccessToken(pair);
			AccessTokenPair tokens = session.getAccessTokenPair();
			
			authenticationInfo[0] = tokens.key;
			authenticationInfo[1] = tokens.secret;
			authenticationInfo[2] = uid;
			
			makeSession(tokens.key, tokens.secret);
			
        } catch (DropboxException e) {
			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return authenticationInfo;
		
	}
	
	/* Sets username */
	public static void setUsername(String name) {
		
		username = name;
		
	}
	
	
	/* Sets up the app folders.
	 * Errors if file/folder already exists in that location. */
	public static void folderSetup() {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		
		try {
				client.createFolder(username + OWN_FILE_FOLDER);
		} catch (DropboxException e) {
		}
			
		try {
			client.createFolder(username + FRIENDS_FILE_FOLDER);
		} catch (DropboxException e) {
		}

		
	}
	
	public static String removeFile(String fileName) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String filePath = "/" + username + OWN_FILE_FOLDER + "/" + fileName;
	
		String rev = null;
		
		try {
			Entry entry = client.metadata(filePath, 0, null, false, null);
			rev = entry.rev;
			client.delete(filePath);
		
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rev;
		
	}
	
	
	public static String downloadFile(String fileName, OutputStream stream) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String filePath = "/" + username + OWN_FILE_FOLDER + "/" + fileName;
		
		try {
			return client.getFile(filePath, null, stream, null).getMetadata().rev;
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static String downloadFriendFile(String fileName, String friendName,
			OutputStream stream) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String filePath = "/" + username + FRIENDS_FILE_FOLDER + "/" + friendName
				+ "/" + fileName;
		
		try {
			return client.getFile(filePath, null, stream, null).getMetadata().rev;
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* TODO: Find out how to apply delta call to just the desired folder or
	 * find out how to use delta properly.
	 * - Make the files are not deleted. */
	/* Returns an array containing a all uploaded files names and their revs */
	public static String[][] getUploadedFiles() {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String[][] files = null;
		
		try {
			Entry entry = client.metadata("/" + username + OWN_FILE_FOLDER, 0, null, true, null);
			List<Entry> allFiles = entry.contents;
			
			int size = allFiles.size();
			files = new String[size][2];
			
			for (int i = 0; i < size; i++) {
				Entry e = allFiles.get(i);
				files[i][0] = e.fileName();
				files[i][1] = e.rev;
			}
			
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
		
	}
	
	/* Returns the names of files and their owners.
	 * Ignores non-directory entries in the 'Friend' folder - they should not
	 * be there.
	 * While the app will not put non-directory entries in there, it is trivial
	 * for the user to manipulate it themselves. */
	public static String[][] getFriendFiles() {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String[][] files = null;
		
		try {
			Entry usersFriendFolder = client.metadata("/" + username + FRIENDS_FILE_FOLDER, 0, null, true, null);
			List<Entry> allFriendsFolders = usersFriendFolder.contents;

			int size = allFriendsFolders.size();

			List<String[]> fileList = new ArrayList<String[]>();
			
			for (int i = 0; i < size; i++) {
				Entry friendDir = allFriendsFolders.get(i);
				if (!friendDir.isDir)
					continue;
				else {
					/* User the directory name as the owners name */
					String owner = friendDir.fileName();
					
					String path =  "/" + username + FRIENDS_FILE_FOLDER + "/"
					                   + owner;
					
					List<Entry> friendFiles = client.metadata(path, 0, null,
							                            true, null).contents;
					
					/* For each file in the friend folder add it to the
					 * array */
					for (int j = 0; j < friendFiles.size(); j++) {
						Entry friendFile = friendFiles.get(j);
						
						String[] file = new String[2];
						file[0] = friendFile.fileName();
						file[1] = owner;
						
						fileList.add(file);
					}
				}
				
				files = fileList.toArray(new String[0][0]);
			}
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
		
	}
	
	/* Given a group of revs and associated information, this function
	 * searches through this user's list of uploaded files and attaches the
	 * filename to each one. */
	public static Map<String, ClientFile> findFileNames(Map<String, ClientFile> files) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		
		try {
			Entry uploadedEntries = client.metadata("/" + username + OWN_FILE_FOLDER, 0, null, true, null);
			List<Entry> allFiles = uploadedEntries.contents;
			
			int size = allFiles.size();
			
			for (int i = 0; i < size; i++) {
				Entry fileEntry = allFiles.get(i);
				String rev = fileEntry.rev;
				if (files.containsKey(rev))
					files.get(rev).setFileName(fileEntry.fileName());
			}
			
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
		
	}
	
	
	public static void updateFile(String fileName, String owner,
			InputStream inputStream, int length) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String path = username + FRIENDS_FILE_FOLDER + "/" + owner + "/" + fileName;
		
		try {
			client.putFileOverwrite(path, inputStream, length, null);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static String[][] getNewlyAddedFiles(String friend) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		String[][] files = null;
		List<String[]> fileList = new ArrayList<String[]>();
		String path =  "/" + username + FRIENDS_FILE_FOLDER + "/"
                + friend;
		
		try {
		//	Entry usersFriendFolder = client.metadata(path, 0, null, true, null);
     		List<Entry> friendFiles = client.metadata(path, 0, null,
							                            true, null).contents;
					
			/* For each file in the friend folder add it to the
			* array */
			for (int j = 0; j < friendFiles.size(); j++) {
				Entry friendFile = friendFiles.get(j);
						
				String[] file = new String[2];
				file[0] = friendFile.fileName();
				file[1] = friend;
						
				fileList.add(file);
	
			}
				
			files = fileList.toArray(new String[0][0]);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return files;
	}

}
