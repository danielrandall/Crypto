import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;

/**
 * A very basic dropbox example.
 * @author mjrb5
 */
public class DropboxTest {
 
    private static final String APP_KEY = "bpyf040d6hcvi5t";
    private static final String APP_SECRET = "96a0wcdkxlqxt3w";
    private static final AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
    private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
    private static DropboxAPI<WebAuthSession> mDBApi;
    private static File accessTokens = new File("dropbox-java-sdk-1.5.3/app/accessTokens");
 
    public static void main(String[] args) throws IOException, DropboxException, URISyntaxException {
        WebAuthSession session = null;
        
        if (!accessTokens.exists()) {
        	//authenticate
            session = authenticate(session);
        } else {
        	//re-authenticate
        	session = reauthenticate(session);
        }  
 
        mDBApi = new DropboxAPI<WebAuthSession>(session);
        System.out.println();
        System.out.print("Uploading file...");
        String fileContents = "Hello World!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
        Entry newEntry = mDBApi.putFile("/testing.txt", inputStream, fileContents.length(), null, null);
        System.out.println("Done. \nRevision of file: " + newEntry.rev);
         
    }
    
    private static WebAuthSession authenticate(WebAuthSession session) throws IOException, DropboxException, URISyntaxException {
    	accessTokens.createNewFile();
    	session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo = session.getAuthInfo();
 
        RequestTokenPair pair = authInfo.requestTokenPair;
        String url = authInfo.url;
 
        Desktop.getDesktop().browse(new URL(url).toURI());
        JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        session.retrieveWebAccessToken(pair);
 
        AccessTokenPair tokens = session.getAccessTokenPair();
        PrintWriter tokenWriter = new PrintWriter(accessTokens);
        tokenWriter.println(tokens.key);
        tokenWriter.println(tokens.secret);
        tokenWriter.close();
        
        return session;
    }
    
    private static WebAuthSession reauthenticate(WebAuthSession session) throws FileNotFoundException {
        //deal with empty
    	Scanner tokenScanner = new Scanner(accessTokens);       // Initiate Scanner to read tokens from TOKEN file
        String access_key = tokenScanner.nextLine();            // Read key
        String access_secret = tokenScanner.nextLine();         // Read secret
        tokenScanner.close(); 
        
    	return new WebAuthSession(appKeys, ACCESS_TYPE, new AccessTokenPair(access_key, access_secret));
    }
 
}
