package server.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class H2Requests extends H2Database {
	
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
		
	//	h.dropFileTable();
		//h.createFileTable();
		
		
	}
	
	/*
	public void dropFileTable() throws SQLException {
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    s.executeUpdate("DROP TABLE " + TABLE_NAME);
	    
	    conn.close();
	}
	
	
	
	public void createFileTable() throws SQLException {

		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    String command = "CREATE TABLE " + TABLE_NAME + "(";
	    
	    for (int i = 0; i < FILE_ATTRIBUTES.length; i++) {
	    	/* IV is input as byte[] and so needs type binary. */
	/*
	    	if (FILE_ATTRIBUTES[i].equals(IV))
	    		command = command + FILE_ATTRIBUTES[i] + " " + "binary("
  	                  +  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    	else if (FILE_ATTRIBUTES[i].equals(SECURITY_LEVEL))
	    		command = command + FILE_ATTRIBUTES[i] + " " + "int("
	  	                  +  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    	else
	    		command = command + FILE_ATTRIBUTES[i] + " " + "varchar("
	    				+  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    }
	    
	    command = command + "PRIMARY KEY (" + FILE_ATTRIBUTES[0] + ")";
	    command = command + ")";

	    s.executeUpdate(command);
	    
	    conn.close();
	}
	*/
	

}
