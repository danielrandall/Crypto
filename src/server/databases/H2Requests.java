package server.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H2Requests extends H2Database {
	
	/* The tables has the elements
	 * sourceUser, destUser, securityLevel
	 * sourceUser and destUser are the primary keys
	 */
	
	/* Table information */
	private static final String TABLE_NAME = "requests";
	
	/* File table attributes */
	public static final String SOURCE_USER = "sourceUser";
	public static final String DEST_USER = "destUser";
	public static final String SECURITY_LEVEL = "securityLevel";
	/* Attributes in table. Must be changed if the attributes change. */
	/* The first element of the array is to be the primary key */
	private static final String[] FILE_ATTRIBUTES = {SOURCE_USER, DEST_USER, SECURITY_LEVEL};
	
	/* User table element lengths */
	private static final String SOURCE_USER_LENGTH = "25";
	private static final String DEST_USER_LENGTH = "25";
	private static final String SECURITY_LEVEL_LENGTH = "25";
	private static final String[] FILE_ATTRIBUTES_LENGTH = {SOURCE_USER_LENGTH, DEST_USER_LENGTH, SECURITY_LEVEL_LENGTH};
	
	/* Maps attributes to their length. Assumes they are both ordered in the same way */
	private static Map<String, String> fileAttributes;
	
	
	/* Pre: FILE_ATTRIBUTES <= FILE_ATTRIBUTES */
	public H2Requests() {
		
		/* Construct the map if it has not been constructed before */
		if (fileAttributes == null) {
			assert FILE_ATTRIBUTES.length <= FILE_ATTRIBUTES_LENGTH.length : "Every attribute needs a length";
		
			fileAttributes = new HashMap<String, String>(FILE_ATTRIBUTES.length);
			for (int i = 0; i < FILE_ATTRIBUTES.length; i++)
				fileAttributes.put(FILE_ATTRIBUTES[i], FILE_ATTRIBUTES_LENGTH[i]);
		}
	}
	
	
	public static void main(String[] args) throws SQLException {
		
		H2Requests h = new H2Requests();
		
		h.dropFileTable();
		h.createFileTable();
		
		/*
	//	h.addRequest("mary", "jane", 1);
	//	h.addRequest("fred", "jane", 5);
	//	h.addRequest("marge", "jane", 3);
		
	//	h.removeRequest("mary", "jane");
		
		List<Map<String, Object>> l = h.getAllRequestsForUser("jane");
		
		for (int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i).get(SOURCE_USER));
			System.out.println(l.get(i).get(SECURITY_LEVEL));
			
		}
		*/
	}
	
	public void dropFileTable() throws SQLException {
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    s.executeUpdate("DROP TABLE " + TABLE_NAME);
	    
	    conn.close();
	}
	
	
	/* Create the table.
	 * Uses source user and dest user as primary keys */
	public void createFileTable() throws SQLException {

		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    String command = "CREATE TABLE " + TABLE_NAME + "(";
	    
	    for (int i = 0; i < FILE_ATTRIBUTES.length; i++) {
	    	if (FILE_ATTRIBUTES[i].equals(SECURITY_LEVEL))
	    		command = command + FILE_ATTRIBUTES[i] + " " + "int("
	  	                  +  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    	else
	    		command = command + FILE_ATTRIBUTES[i] + " " + "varchar("
	    				+  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    }
	    
	    command = command + "PRIMARY KEY (" + FILE_ATTRIBUTES[0] + ", " + FILE_ATTRIBUTES[1] + ")";
	    command = command + ")";

	    s.executeUpdate(command);
	    
	    conn.close();
	}
	
	
	/* Pre: inputs are given in the correct order. */
	public void addRequest(String sourceUser, String destUser, int securityLevel) {
		
		Connection conn = getConnection();
		
		try {
			String command = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, sourceUser);
			statement.setString(2, destUser);
			statement.setInt(3, securityLevel);
					
            statement.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Removes the desired user */
	public void removeRequest(String sourceUser, String destUser) {
		
		Connection conn = getConnection();
		
		try {
			Statement s = conn.createStatement();
			s.execute("DELETE FROM " + TABLE_NAME + " WHERE " + FILE_ATTRIBUTES[0] + " = '" + sourceUser + "'"
					   + " AND " + FILE_ATTRIBUTES[1] + " = '" + destUser + "'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* Returns all outstanding share requests made for the given user */
	public List<Map<String, Object>> getAllRequestsForUser(String username) {
		
		Connection conn = getConnection();

		List<Map<String, Object>> userData = new ArrayList<Map<String, Object>>();
	    
		try {
			Statement s = conn.createStatement();	
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME +
					" WHERE " + FILE_ATTRIBUTES[1] + " = '" + username + "'");
		
			while (r.next()) {
				Map<String, Object> data = new HashMap<String, Object>(FILE_ATTRIBUTES.length - 2);
				
				String sourceUser = r.getString(1);
				data.put(FILE_ATTRIBUTES[0], sourceUser);
				
				int i = r.getInt(3);
				data.put(FILE_ATTRIBUTES[2], i);
				
				userData.add(data);
			}

			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userData;
	}
	

}
