package client.model.linking.databases;

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

/* TODO: Foreign key: users. */

public class H2Files extends H2Database {
	
	/* Table information */
	private static final String TABLE_NAME = "files";
	
	/* File table attributes */
	public static final String FILEREV = "fileRev";
	public static final String OWNER = "owner";
	public static final String IV = "iv";
	public static final String SECURITY_LEVEL = "securityLevel";
	/* Attributes in table. Must be changed if the attributes change. */
	/* The first element of the array is to be the primary key */
	private static final String[] FILE_ATTRIBUTES = {FILEREV, OWNER, IV, SECURITY_LEVEL};
	
	/* User table element lengths */
	private static final String FILEREV_LENGTH = "25";
	private static final String OWNER_LENGTH = "25";
	private static final String IV_LENGTH = "256";
	private static final String SECURITY_LEVEL_LENGTH = "25";
	private static final String[] FILE_ATTRIBUTES_LENGTH = {FILEREV_LENGTH, OWNER_LENGTH, IV_LENGTH, SECURITY_LEVEL_LENGTH};
	
	/* Maps attributes to their length. Assumes they are both ordered in the same way */
	private static Map<String, String> fileAttributes;
	
	/* Pre: FILE_ATTRIBUTES <= FILE_ATTRIBUTES */
	public H2Files() {
		
		/* Construct the map if it has not been constructed before */
		if (fileAttributes == null) {
			assert FILE_ATTRIBUTES.length <= FILE_ATTRIBUTES_LENGTH.length : "Every attribute needs a length";
		
			fileAttributes = new HashMap<String, String>(FILE_ATTRIBUTES.length);
			for (int i = 0; i < FILE_ATTRIBUTES.length; i++)
				fileAttributes.put(FILE_ATTRIBUTES[i], FILE_ATTRIBUTES_LENGTH[i]);
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
		
		H2Files h = new H2Files();
		
		h.dropFileTable();
		h.createFileTable();
		
	//	String [] inputs = {"0123", "9876", s};
	//	h.addFile(inputs);
		
	//	h.removeFile("file1");
		
	}

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
	
	
	/* Pre: inputs are given in the correct order. */
	public void addFile(String fileRev, String owner, byte[] iv, int securityLevel) {
		
		Connection conn = getConnection();
		
		try {
			String command = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, fileRev);
			statement.setString(2, owner);
			statement.setBinaryStream(3, new ByteArrayInputStream(iv), iv.length);
			statement.setInt(4, securityLevel);
					
            statement.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void removeFile(String fileRev) {
		
		Connection conn = getConnection();
		
		try {
			Statement s = conn.createStatement();
			s.execute("DELETE FROM " + TABLE_NAME + " WHERE " + FILE_ATTRIBUTES[0] + " = '" + fileRev + "'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Map<String, Object> getFile(String username) {
		
		Connection conn = getConnection();

	    Map<String, Object> userData = new HashMap<String, Object>(FILE_ATTRIBUTES.length - 1);
	    
		try {
			Statement s = conn.createStatement();	
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + FILE_ATTRIBUTES[0] + " = '" + username + "'");
		
			if (r.next()) {
				String fileRev = r.getString(1);
				userData.put(FILE_ATTRIBUTES[0], fileRev);
				
				String owner = r.getString(2);
				userData.put(FILE_ATTRIBUTES[1], owner);
				
				InputStream in = r.getBinaryStream(3);
				byte[] b = new byte[in.available()];
				in.read(b);
				userData.put(FILE_ATTRIBUTES[2], b);
				
				int i = r.getInt(4);
				userData.put(FILE_ATTRIBUTES[3], i);
			}

			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userData;
	}

}
