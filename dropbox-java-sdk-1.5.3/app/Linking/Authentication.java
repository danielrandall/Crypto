package Linking;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JOptionPane;

import server.ClientComms;
import server.UserOperations;

import Linking.Password.BCryptEncryptor;
import Linking.Password.PasswordEncryptor;
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
	
	private static final String TRUE = "1";
	private static final String FALSE = "0";
    
    public static User link(ClientComms comms) {
    	
    	boolean accepted = false;
		String username = null;
		String password = null;
		
		while (!accepted) {
			
			username = comms.fromClient();
			password = comms.fromClient();
			
    		accepted = checkUserPass(username, password);
			
    		/* Tell client the user/pass was incorrect */
    		if (!accepted)
    			comms.toClient(FALSE);
    	}
		
		/* Tell client the user/pass was correct */
		comms.toClient(TRUE);
    			
    	return createUser(username);
    }
    
    public static User createUser(String username) {
    	
    	User user = User.load(username);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, user.getAccessTokens());
		user.setSession(session);
		
		return user;
    	
    }
    
    public static User authenticate(String username, String password) {
    	
    	WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo;
        User user = null;
		
        try {
        
			authInfo = session.getAuthInfo();
			RequestTokenPair pair = authInfo.requestTokenPair;
			String url = authInfo.url;
 
			Desktop.getDesktop().browse(new URL(url).toURI());
			JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        
			String uid = session.retrieveWebAccessToken(pair);
			AccessTokenPair tokens = session.getAccessTokenPair();
			user = User.save(username, passwordEncryptor.hashPassword(password), uid, tokens.key, tokens.secret);
			user.setSession(session);
        
        } catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        
        return user;
    	
    }
    
    private static boolean checkUserPass(String username, String password) {
    	
    	if (UserOperations.checkUserExists(username))
    		return passwordEncryptor.checkPassword(password, User.getPassword(username));
    	else
    		return false;
    		
    }
    

}