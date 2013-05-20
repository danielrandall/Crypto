package client.model.users;

import java.util.Map;

import server.databases.H2Users;


import client.model.users.friends.FriendsList;

import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.WebAuthSession;

public final class User {
   // private final AppKeyPair appKey;
    private AccessTokenPair linkKey;
  //  private final Map<String,AccessTokenPair> links = new HashMap<String,AccessTokenPair>();
    private String username;
    private String uid;
    private WebAuthSession session;
    private static final H2Users database = new H2Users(); // INTERFACE
    
    public User() {
    	
    }

    public User(String username, String uid, AccessTokenPair linkKey) {
    	
        this.linkKey = linkKey;
        this.username = username;
        this.uid = uid;
        
    }
    
    public static User save(String username, String password, String uid, String accessKey,
    		            String accessSecret) {
    	
        database.addUser(username, password, uid, accessKey, accessSecret, new FriendsList());
        return new User(username, uid, new AccessTokenPair(accessKey, accessSecret));
        
    }
    
    public static boolean search(String username) {
    	
    	return database.checkUserExists(username);
    	
    }
    
    public static User load(String username) {
    	
		Map<String, Object> userData = database.getUser(username);
		String uid = (String)userData.get(H2Users.UID);
		String accessKey = (String)userData.get(H2Users.ACCESSKEY);
		String accessSecret = (String)userData.get(H2Users.ACCESSSECRET);
		return new User(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static String getPassword(String username) {
    	
    	Map<String, Object> userData = database.getUser(username);
		return (String)userData.get(H2Users.PASSWORD);
		
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
