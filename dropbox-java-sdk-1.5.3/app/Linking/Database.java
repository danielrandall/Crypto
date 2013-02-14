package Linking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private static final String DATABASE_LOCATION = "dropbox-java-sdk-1.5.3/app/users";
	private static final String TABLE_NAME = "users";

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	    
	 //   addUser("username", "password", "salt", "uid", "accesskey", "accesssecret");
	    	    
	    if (checkUserExists("username"))
	    	System.out.println("true");
	    else
	    	System.out.println("false");
	    
	    String[] argss = getUser("username");
	    if (argss.length == 3)
	    	for (int i = 0; i < 3; i++)
	    		System.out.println(argss[i]);
	    		
	}
	
	public static void createUserTable(Connection conn) throws SQLException {
		Statement s = conn.createStatement();
	    int result = s.executeUpdate("CREATE TABLE " + TABLE_NAME +
	    		"(" +
	    		"Username varchar(25) NOT NULL," +
	    		"Password varchar(50) NOT NULL," +
	    		"Salt varchar(50) NOT NULL," +
	    		"UID varchar(20) NOT NULL," +
	    		"AccessKey varchar(20) NOT NULL," +
	    		"AccessSecret varchar(20) NOT NULL," +
	    		"PRIMARY KEY (Username)" +
	    		")");
	    conn.close();
	}
	
	public static boolean checkUserExists(String username) throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
	    Connection conn = DriverManager.
	    getConnection("jdbc:h2:" + DATABASE_LOCATION, "sa", "");
	    
		Statement s = conn.createStatement();	
		//ResultSet r = s.executeQuery("SELECT EXISTS(SELECT 1 FROM users WHERE username = '" + username + "')");
		ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE Username = '" + username + "'");
		
		boolean hasNext = r.next();
		
		conn.close();
		
		return hasNext;
	}
	
	
	public static void addUser(String username, String password, String salt,
			                                     String uid, String accessKey, String accessSecret) throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
	    Connection conn = DriverManager.
	    getConnection("jdbc:h2:" + DATABASE_LOCATION, "sa", "");
		
		Statement s = conn.createStatement();
		s.execute("INSERT INTO " + TABLE_NAME + " VALUES ('" + username + "','" + password + "','" + salt + "','" +
			                                     uid + "','" + accessKey + "','" + accessSecret + "')");
		
		conn.close();
	}
	
	public static void removeUser(String username) throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
	    Connection conn = DriverManager.
	    getConnection("jdbc:h2:" + DATABASE_LOCATION, "sa", "");
		
		Statement s = conn.createStatement();
		s.execute("DELETE FROM " + TABLE_NAME + " WHERE Username = " + username);
		
		conn.close();
	}
	
	public static String[] getUser(String username) throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
	    Connection conn = DriverManager.
	    getConnection("jdbc:h2:" + DATABASE_LOCATION, "sa", "");
	    
		Statement s = conn.createStatement();	
		ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE Username = '" + username + "'");
	
		String[] userData =  new String[3];
		if (r.next()) {
			userData[0] = r.getString("UID");
			userData[1] = r.getString("accessKey");
			userData[2] = r.getString("accessSecret");
		}
		
		conn.close();

		return userData;
	}

}
