package Linking;

import java.sql.SQLException;
import com.dropbox.client2.session.AccessTokenPair;

public final class State {
   // private final AppKeyPair appKey;
    private AccessTokenPair linkKey;
  //  private final Map<String,AccessTokenPair> links = new HashMap<String,AccessTokenPair>();
    private String username;
    private String uid;
    private final static String FILE_NAME = "dropbox-java-sdk-1.5.3/app/users.json";
    
    public State() {
    	
    }

    public State(String username, String uid, AccessTokenPair linkKey) {
        this.linkKey = linkKey;
        this.username = username;
        this.uid = uid;
    }
    
    // Stores the current state variables onto the hard disks
    public static State save(String username, String uid, String accessKey,
    		            String accessSecret) throws SQLException, ClassNotFoundException {
        Database.addUser(username, "password", "salt", uid, accessKey, accessSecret);
        return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
    }
    
    public static boolean search(String username) throws SQLException, ClassNotFoundException {
    	return Database.checkUserExists(username);
    }
    
    public static State load(String username) throws ClassNotFoundException, SQLException {
		String[] userData = Database.getUser(username);
		if (userData.length == 3) {
			String uid = userData[0];
			String accessKey = userData[1];
			String accessSecret = userData[2];
			return new State(username, uid, new AccessTokenPair(accessKey, accessSecret));
		} else
			return null;    	
    }
    
    public AccessTokenPair getAccessTokens() {
    	return linkKey;
    }
    
    public String getUsername() {
    	return username;
    }
    
}
