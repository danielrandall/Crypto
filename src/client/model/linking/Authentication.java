package client.model.linking;

import server.ClientComms;
import server.operations.UserOperations;

import client.model.linking.password.BCryptEncryptor;
import client.model.linking.password.PasswordEncryptor;
import client.model.users.User;

import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;

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
			
		username = comms.fromClient();
		password = comms.fromClient();
			
    	accepted = checkUserPass(username, password);
			
    	/* Tell client the user/pass was incorrect */
    	if (!accepted) {
    		comms.toClient(FALSE);
    		return null;
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
    
    public static User authenticate(String username, String password, ClientComms comms) {
    	/*
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
			*/
    	
    		String key = comms.fromClient();
    		String secret = comms.fromClient();
    		String uid = comms.fromClient();
    		
    		WebAuthSession session = new WebAuthSession(appKeys, ACCESS_TYPE, new AccessTokenPair(key, secret));
    		
			User user = User.save(username, passwordEncryptor.hashPassword(password), uid, key, secret);
			user.setSession(session);
        /*
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
		*/
        
        return user;
    	
    }
    
    private static boolean checkUserPass(String username, String password) {
    	
    	if (UserOperations.checkUserExists(username))
    		return passwordEncryptor.checkPassword(password, User.getPassword(username));
    	else
    		return false;
    		
    }
    

}