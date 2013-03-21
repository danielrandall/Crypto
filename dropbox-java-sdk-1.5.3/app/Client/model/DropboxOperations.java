package Client.model;

import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DeltaEntry;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;

public class DropboxOperations {
	
	private static final String APP_KEY = "bpyf040d6hcvi5t";
    private static final String APP_SECRET = "96a0wcdkxlqxt3w";
    private static final AppKeyPair KEY_PAIR = new AppKeyPair(APP_KEY, APP_SECRET);
	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	
	private static Session session;
	
    public static Session makeSession(String key, String secret) {
    	
    	AccessTokenPair atp = new AccessTokenPair(key, secret);
		
    	session = new WebAuthSession(KEY_PAIR, ACCESS_TYPE, atp);
    	
    	return session;
    	
    }
    
    public static Session getSession() {
    	
    	return session;
    	
    }

	/* Uploads a file to the location dropboxPath for the current logged in user.
	 * Will not overwrite existing file. Pass in revision (rev - entry.rev)
	 * if you want to overwrite the file with the same name. */
	public static Entry uploadFile(String dropboxPath, InputStream inputStream,
			                        Session sourceSession, int length) {
		
		DropboxAPI<Session> mDBApi = new DropboxAPI<Session>(sourceSession);
		Entry newEntry = null;
		
		try {
			newEntry = mDBApi.putFile(dropboxPath, inputStream, length, null, null);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newEntry;
	}
	
	
	public static String downloadFile(Session session, String path, OutputStream stream) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		
		try {
			return client.getFile(path, null, stream, null).getMetadata().rev;
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* Copies a file from the sourcePath to the targetPath. This method will primarily be used
	 * to transfer files between accounts.
	 * The a file/folder at the target path can not already exist. */
	public static Entry copyBetweenAccounts(Session sourceSession, Session targetSession,
			                           String sourcePath, String targetPath) {
		
		DropboxAPI<Session> sourceClient = new DropboxAPI<Session>(sourceSession);
		DropboxAPI<Session> targetClient = new DropboxAPI<Session>(targetSession);
		
		Entry entry = null;
		
        try {
			DropboxAPI.CreatedCopyRef cr = sourceClient.createCopyRef(sourcePath);
			entry = targetClient.addFromCopyRef(cr.copyRef, targetPath);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return entry;
	}
	
	/* Initially pass in a null cursor and then pass in the returned cursor in future calls.
	 * Page limit of -1 means no limit. */
	public static String updateCache(Session session, String cursor, int pageLimit,
											Session targetSession) {
		
		DropboxAPI<Session> client = new DropboxAPI<Session>(session);
		int pageNum = 0;
		
		while (pageLimit < 0 || pageNum < pageLimit) {
			DropboxAPI.DeltaPage<DropboxAPI.Entry> page;
			
			try {
				page = client.delta(cursor);
				pageNum++;
			
				if (page.reset) {
					/* TODO: reset tree */
				}
            
				/* Apply the entries one by one. */
				for (DeltaEntry<DropboxAPI.Entry> e : page.entries) {
				//	applyDelta(e);
					copyBetweenAccounts(session, targetSession, e.lcPath, e.lcPath);
				}
            
				cursor = page.cursor;     
			
				if (!page.hasMore)
					break;
			
			} catch (DropboxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return cursor;
	}
	

	/* Get updated list of files and then share them with desired user. */
	public static void shareFilesWithFriend(String usernameToAdd,
			            Session friendSession, String username,
			                        Session session) {
		
		updateCache(session, null, -1, friendSession);
		
	}
	
	
}
