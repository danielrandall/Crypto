package Linking;

import java.sql.SQLException;
import java.util.Map;

import Linking.Databases.Database;
import Linking.Databases.H2Users;

import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.WebAuthSession;

public final class State {
   // private final AppKeyPair appKey;
    private AccessTokenPair linkKey;
  //  private final Map<String,AccessTokenPair> links = new HashMap<String,AccessTokenPair>();
    private String username;
    private String uid;
    private WebAuthSession session;
    private static final H2Users database = new H2Users(); // INTERFACE
    
    public State() {
    	
    }

    public State(String username, String uid, AccessTokenPair linkKey) {
        this.linkKey = linkKey;
        this.username = username;
        this.uid = uid;
    }
    
    public static State save(String username, String password, String uid, String accessKey,
    		            String accessSecret) throws SQLException, ClassNotFoundException {
    	String[] input = {username, password, uid, accessKey, accessSecret};
        database.addUser(input);
        return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static boolean search(String username) throws SQLException, ClassNotFoundException {
    	return database.checkUserExists(username);
    }
    
    public static State load(String username) throws ClassNotFoundException, SQLException {
		Map<String, String> userData = database.getUser(username);
		String uid = userData.get("UID");
		String accessKey = userData.get("AccessKey");
		String accessSecret = userData.get("AccessSecret");
		return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static String getPassword(String username) {
    	Map<String, String> userData = database.getUser(username);
		return userData.get("Password");
    }
    
    public AccessTokenPair getAccessTokens() {
    	return linkKey;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getUID() {
    	return uid;
    }
    
    public void setSession(WebAuthSession session) {
    	this.session = session;
    }
    
    public WebAuthSession getSession() {
    	return session;
    }
    
}
