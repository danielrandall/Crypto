package Linking;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.RequestTokenPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession.WebAuthInfo;

public final class Authentication {   
	
	private static final String APP_KEY = "bpyf040d6hcvi5t";
    private static final String APP_SECRET = "96a0wcdkxlqxt3w";
    private static final AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
	private static final AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
    
    public static void link(String username) throws SQLException, ClassNotFoundException, MalformedURLException, DropboxException, IOException, URISyntaxException {
    	WebAuthSession session = null;
    	
    	if (State.search(username)) {
    		reauthenticate(session, username);
    	} else {
    		authenticate(session, username);
    	}
    }
    
    private static void authenticate(WebAuthSession session, String username) throws DropboxException, MalformedURLException, IOException, URISyntaxException, SQLException, ClassNotFoundException {
    	
    	session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo = session.getAuthInfo();
 
        RequestTokenPair pair = authInfo.requestTokenPair;
        String url = authInfo.url;
 
        Desktop.getDesktop().browse(new URL(url).toURI());
        JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        String uid = session.retrieveWebAccessToken(pair);
        AccessTokenPair tokens = session.getAccessTokenPair();
    
    	State state = State.save(username, uid, tokens.key, tokens.secret);
    	
    	System.out.println(state.getAccessTokens().key);
    }
    
    private static void reauthenticate(WebAuthSession session, String username) throws ClassNotFoundException, SQLException {
    	State state = State.load(username);
    	
    	System.out.println(state.getAccessTokens().key + "   2");
    }
    
    
    

}