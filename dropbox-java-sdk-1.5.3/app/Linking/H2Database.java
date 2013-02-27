package Linking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/* TODO: check table exists
 * TODO: deal with exceptions */

public class H2Database implements Database {
	
	/* Database information */
	private static final String DATABASE_LOCATION = "dropbox-java-sdk-1.5.3/app/users";
	private static final String TABLE_NAME = "users";
	
	/* Driver information */
	private static final String DRIVER_LOCATION = "org.h2.Driver";
	private static final String CONNECTION_PREFIX = "jdbc:h2:";
	
	/* User table attributes */
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String UID = "UID";
	private static final String ACCESSKEY = "AccessKey";
	private static final String ACCESSSECRET = "AccessSecret";
	/* Attributes in table. Must be changed if the attributes change. */
	/* The first element of the array is to be the primary key */
	private static final String[] USER_ATTRIBUTES = {USERNAME, PASSWORD, UID, ACCESSKEY, ACCESSSECRET};
	
	/* User table element lengths */
	private static final String USERNAME_LENGTH = "25";
	private static final String PASSWORD_LENGTH = "60"; /* BCrypt hashes passwords to length 60 */
	private static final String UID_LENGTH = "20";
	private static final String ACCESSKEY_LENGTH = "20";
	private static final String ACCESSSECRET_LENGTH = "20";
	private static final String[] USER_ATTRIBUTES_LENGTH = {USERNAME_LENGTH, PASSWORD_LENGTH, UID_LENGTH, ACCESSKEY_LENGTH, ACCESSSECRET_LENGTH};
	
	/* Maps attributes to their length. Assumes they are both ordered in the same way */
	private static Map<String, String> userAttributes;
	
	/* Pre: USER_ATTRIBUTES <= USER_ATTRIBUTES */
	public H2Database() {
		
		/* Construct the map if it has not been constructed before */
		if (userAttributes == null) {
			assert USER_ATTRIBUTES.length <= USER_ATTRIBUTES_LENGTH.length : "Every attribute needs a length";
		
			userAttributes = new HashMap<String, String>(USER_ATTRIBUTES.length);
			for (int i = 0; i < USER_ATTRIBUTES.length; i++)
				userAttributes.put(USER_ATTRIBUTES[i], USER_ATTRIBUTES_LENGTH[i]);
		}
		
	}
	/*
	public void createUserTable() throws SQLException {
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.
				getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
		Statement s = conn.createStatement();
	    s.executeUpdate("CREATE TABLE " + TABLE_NAME +
	    		"(" +
	    		"Username varchar(25) NOT NULL," +
	    		"Password varchar(60) NOT NULL," +       
	    		"UID varchar(20) NOT NULL," +
	    		"AccessKey varchar(20) NOT NULL," +
	    		"AccessSecret varchar(20) NOT NULL," +
	    		"PRIMARY KEY (Username)" +
	    		")");
	    System.out.println("created");
	    conn.close();
	}
	*/
	public void dropUserTable() throws SQLException {
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.
				getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
		Statement s = conn.createStatement();
	    s.executeUpdate("DROP TABLE " + TABLE_NAME);
	    conn.close();
	}
	
	
	
	public void createUserTable() throws SQLException {
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection conn = DriverManager.
				getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
		Statement s = conn.createStatement();
	    String command = "CREATE TABLE " + TABLE_NAME + "(";
	    for (int i = 0; i < USER_ATTRIBUTES.length; i++) {
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
		
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    boolean hasNext = false;
		
		try {
			Connection conn = DriverManager.
			getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
			Statement s = conn.createStatement();
			
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ATTRIBUTES[0] + " = " + username);
			hasNext = r.next();
		
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasNext;
	}
	
	/* Pre: inputs are given in the correct order. */
	public void addUser(String[] inputs) {
		
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.
			getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
			if (inputs.length == USER_ATTRIBUTES.length) {	    
				// Need to check lengths
					Statement s = null;
					s = conn.createStatement();
					String command = "INSERT INTO " + TABLE_NAME + " VALUES (";
					for (int i = 0; i < inputs.length; i++) {
						command = command + "'" + inputs[i] + "'";
						if (i != inputs.length - 1)
							command = command + ",";
					}
					command = command + ")";

					s.execute(command);
				} else 
					throw new IllegalArgumentException("Incorrect number of inputs." +
							"Number required: " + USER_ATTRIBUTES.length +
							" Number supplied: " + inputs.length);
			System.out.println("added");
				conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void removeUser(String username) {
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Connection conn = DriverManager.
					getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
			Statement s = conn.createStatement();
			s.execute("DELETE FROM " + TABLE_NAME + " WHERE " + USER_ATTRIBUTES[0] + " =" + username);
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public Map<String, String> getUser(String username) {
		try {
			Class.forName(DRIVER_LOCATION);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    Map<String, String> userData = new HashMap<String, String>(USER_ATTRIBUTES.length - 1);
	    
		try {
			Connection conn = DriverManager.
			getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
			Statement s = conn.createStatement();	
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ATTRIBUTES[0] + " = " + username);
		
			if (r.next()) {
				for (int i = 1; i < USER_ATTRIBUTES.length; i++) 
					userData.put(USER_ATTRIBUTES[i], r.getString(USER_ATTRIBUTES[i]));
			}
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userData;
	}

}
