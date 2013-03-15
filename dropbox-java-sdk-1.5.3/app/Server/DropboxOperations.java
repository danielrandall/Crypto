package Server;

import java.io.InputStream;
import java.io.OutputStream;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DeltaEntry;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.WebAuthSession;

public class DropboxOperations {

	/* Uploads a file to the location dropboxPath for the current logged in user.
	 * Will not overwrite existing file. Pass in revision (rev - entry.rev)
	 * if you want to overwrite the file with the same name. */
	public static Entry uploadFile(String dropboxPath, InputStream inputStream,
			                        WebAuthSession sourceSession, int length) {
		
		DropboxAPI<WebAuthSession> mDBApi = new DropboxAPI<WebAuthSession>(sourceSession);
		Entry newEntry = null;
		
		try {
			newEntry = mDBApi.putFile(dropboxPath, inputStream, length, null, null);
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newEntry;
	}
	
	
	public static String downloadFile(WebAuthSession session, String path, OutputStream stream) {
		
		DropboxAPI<WebAuthSession> client = new DropboxAPI<WebAuthSession>(session);
		
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
	public static Entry copyBetweenAccounts(WebAuthSession sourceSession, WebAuthSession targetSession,
			                           String sourcePath, String targetPath) {
		
		DropboxAPI<WebAuthSession> sourceClient = new DropboxAPI<WebAuthSession>(sourceSession);
		DropboxAPI<WebAuthSession> targetClient = new DropboxAPI<WebAuthSession>(targetSession);
		
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
	public static String updateCache(WebAuthSession session, String cursor, int pageLimit,
											WebAuthSession targetSession) {
		
		DropboxAPI<WebAuthSession> client = new DropboxAPI<WebAuthSession>(session);
		int pageNum = 0;
		 boolean changed = false;
		
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
					changed = true;
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
	
	
	private static void applyDelta(DeltaEntry<DropboxAPI.Entry> e) {
	
        DropboxAPI.Entry md = e.metadata;
		
		if (md != null) {
			System.out.println("+ " + e.lcPath);
		} else {
    	   System.out.println("- " + e.lcPath);
		}
	}


	/* Get updated list of files and then share them with desired user. */
	public static void shareFilesWithFriend(String usernameToAdd,
			            WebAuthSession friendSession, String username,
			                        WebAuthSession session) {
		
		updateCache(session, null, -1, friendSession);
		
	}
	
	
}
