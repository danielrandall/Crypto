package Linking;

import java.sql.SQLException;
import java.util.Map;

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
        database.addUser(username, password, uid, accessKey, accessSecret, null);
        return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static boolean search(String username) throws SQLException, ClassNotFoundException {
    	return database.checkUserExists(username);
    }
    
    public static State load(String username) throws ClassNotFoundException, SQLException {
		Map<String, Object> userData = database.getUser(username);
		String uid = (String)userData.get("uid");
		String accessKey = (String)userData.get("accessKey");
		String accessSecret = (String)userData.get("accessSecret");
		return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static String getPassword(String username) {
    	Map<String, Object> userData = database.getUser(username);
		return (String)userData.get("Password");
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
