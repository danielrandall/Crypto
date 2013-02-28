package Linking;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Linking.Password.BCryptEncryptor;
import Linking.Password.PasswordEncryptor;
import Server.CentralAuthority;

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
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();
    
    public static void link(String username, String password) throws SQLException, ClassNotFoundException, MalformedURLException, DropboxException, IOException, URISyntaxException {
    	
    	if (!State.search(username)) {
    		State state = authenticate(username, password);
    		CentralAuthority.options(state);
    	} else {
    		/* TODO: Deal with incorrect password. */
    		checkPassword(username, password);
    		State state = State.load(username);
    		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, state.getAccessTokens());
    		state.setSession(session);
    		CentralAuthority.options(state);
    	}
    }
    
    private static State authenticate(String username, String password) throws DropboxException, MalformedURLException, IOException, URISyntaxException, SQLException, ClassNotFoundException {
    	
    	WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo = session.getAuthInfo();
 
        RequestTokenPair pair = authInfo.requestTokenPair;
        String url = authInfo.url;
 
        Desktop.getDesktop().browse(new URL(url).toURI());
        JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        
		String uid = session.retrieveWebAccessToken(pair);
        AccessTokenPair tokens = session.getAccessTokenPair();
        State state = State.save(username, passwordEncryptor.hashPassword(password), uid, tokens.key, tokens.secret);
        state.setSession(session);
        
        return state;
    	
    }
    
    private static boolean checkPassword(String username, String password) {
    	
    	return passwordEncryptor.checkPassword(password, State.getPassword(username));
    		
    }
    

}