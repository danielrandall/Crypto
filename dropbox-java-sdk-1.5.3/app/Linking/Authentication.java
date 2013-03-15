package Linking;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JOptionPane;

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
    
    public static State link(BufferedReader input) {
    	

    		String username = null;
    		String password = null;
		
    		boolean userPassAccepted = false; 
		
    		try {
		
    			while (!userPassAccepted) {
				
    				System.out.println("Enter username");
    				username = input.readLine();
    				
    				System.out.println("Enter password");
    				password = input.readLine();
			
    				userPassAccepted = checkPassword(username, password);
			
    				if (!userPassAccepted)
    					System.out.println("Username or password incorrect");
			
    			}
    			
    		} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    		}
    		
    		return createState(username);
    }
    
    public static State createState(String username) {
    	
    	State state = State.load(username);
		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, state.getAccessTokens());
		state.setSession(session);
		
		return state;
    	
    }
    
    public static State authenticate(String username, String password) {
    	
    	WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE);
        WebAuthInfo authInfo;
        State state = null;
		
        try {
        
			authInfo = session.getAuthInfo();
			RequestTokenPair pair = authInfo.requestTokenPair;
			String url = authInfo.url;
 
			Desktop.getDesktop().browse(new URL(url).toURI());
			JOptionPane.showMessageDialog(null, "Press ok to continue once you have authenticated.");
        
			String uid = session.retrieveWebAccessToken(pair);
			AccessTokenPair tokens = session.getAccessTokenPair();
			state = State.save(username, passwordEncryptor.hashPassword(password), uid, tokens.key, tokens.secret);
			state.setSession(session);
        
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
        
        return state;
    	
    }
    
    private static boolean checkPassword(String username, String password) {
    	
    	return passwordEncryptor.checkPassword(password, State.getPassword(username));
    		
    }
    

}