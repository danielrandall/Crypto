package Linking.Databases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/* TODO: check table exists
 * TODO: deal with exceptions */

public class H2Users extends H2Database {
	
	/* Table information */
	private static final String TABLE_NAME = "users";
	
	/* User table attributes */
	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String UID = "uid";
	private static final String ACCESSKEY = "accessKey";
	private static final String ACCESSSECRET = "accessSecret";
	private static final String FRIENDS = "friends";
	/* Attributes in table. Must be changed if the attributes change. */
	/* The first element of the array is to be the primary key */
	private static final String[] USER_ATTRIBUTES = {USERNAME, PASSWORD, UID,
		                                     ACCESSKEY, ACCESSSECRET, FRIENDS};
	
	/* User table element lengths */
	private static final String USERNAME_LENGTH = "25";
	private static final String PASSWORD_LENGTH = "60"; /* BCrypt hashes passwords to length 60 */
	private static final String UID_LENGTH = "20";
	private static final String ACCESSKEY_LENGTH = "20";
	private static final String ACCESSSECRET_LENGTH = "20";
	private static final String FRIENDS_LENGTH = "10000";
	private static final String[] USER_ATTRIBUTES_LENGTH = {USERNAME_LENGTH,
		                        PASSWORD_LENGTH, UID_LENGTH, ACCESSKEY_LENGTH,
		                                 ACCESSSECRET_LENGTH, FRIENDS_LENGTH};
	
	/* Maps attributes to their length. Assumes they are both ordered in the same way */
	private static Map<String, String> userAttributes;
	
	/* Pre: USER_ATTRIBUTES <= USER_ATTRIBUTES */
	public H2Users() {
		
		/* Construct the map if it has not been constructed before */
		if (userAttributes == null) {
			assert USER_ATTRIBUTES.length <= USER_ATTRIBUTES_LENGTH.length :
				                          "Every attribute needs a length";
		
			userAttributes = new HashMap<String, String>(USER_ATTRIBUTES.length);
			for (int i = 0; i < USER_ATTRIBUTES.length; i++)
				userAttributes.put(USER_ATTRIBUTES[i], USER_ATTRIBUTES_LENGTH[i]);
		}
		
	}

	public void dropUserTable() throws SQLException {
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    s.executeUpdate("DROP TABLE " + TABLE_NAME);
	    conn.close();
	}
	
	
	
	public void createUserTable() throws SQLException {
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    String command = "CREATE TABLE " + TABLE_NAME + "(";
	    
	    for (int i = 0; i < USER_ATTRIBUTES.length; i++) {
	    	if (USER_ATTRIBUTES[i].equals(FRIENDS))
	    		command = command + USER_ATTRIBUTES[i] + " " + "other("
	    		    +  userAttributes.get(USER_ATTRIBUTES[i]) + ") NOT NULL,";
	    	else
	    		command = command + USER_ATTRIBUTES[i] + " " + "varchar("
	    		   	+  userAttributes.get(USER_ATTRIBUTES[i]) + ") NOT NULL,";
	    }
	    
	    command = command + "PRIMARY KEY (" + USER_ATTRIBUTES[0] + ")";
	    command = command + ")";

	    s.executeUpdate(command);
	    
	    conn.close();
	}
	
	
	/* Pre: USER_ATTRIBUTES[0] is the primary key */
	public boolean checkUserExists(String username) {
		
		Connection conn = getConnection();
		
	    boolean hasNext = false;
		
		try {
			Statement s = conn.createStatement();
			
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME +
					     " WHERE " + USER_ATTRIBUTES[0] + " = " + username);
			hasNext = r.next();
		
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasNext;
	}
	
	/* Pre: inputs are given in the correct order. */
	public void addUser(String username, String password, String uid,
			          String accessKey, String accessSecret, Object friends) {
		
		Connection conn = getConnection();
		
		try {
			String command = "INSERT INTO " + TABLE_NAME
					          + " VALUES (?,?,?,?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, uid);
			statement.setString(4, accessKey);
			statement.setString(5, accessSecret);
			statement.setObject(6, friends);
					
            statement.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void removeUser(String username) {

		Connection conn = getConnection();
		
		try {
			Statement s = conn.createStatement();
			s.execute("DELETE FROM " + TABLE_NAME + " WHERE " + USER_ATTRIBUTES[0] + " =" + username);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> getUser(String username) {
		
		Connection conn = getConnection();

	    Map<String, Object> userData = new HashMap<String, Object>(USER_ATTRIBUTES.length - 1);
	    
		try {
			Statement s = conn.createStatement();	
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ATTRIBUTES[0] + " = '" + username + "'");
		
			if (r.next()) {
				
				String password = r.getString(2);
				userData.put(USER_ATTRIBUTES[1], password);
				
				String uid = r.getString(3);
				userData.put(USER_ATTRIBUTES[2], uid);
				
				String accessKey = r.getString(4);
				userData.put(USER_ATTRIBUTES[3], accessKey);
				
				String accessSecret = r.getString(5);
				userData.put(USER_ATTRIBUTES[4], accessSecret);
				
				Object friends = r.getObject(6);
				userData.put(USER_ATTRIBUTES[5], friends);
			}

			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userData;
		
	}

}
