package server.databases;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H2Keys extends H2Database {
	
	
	/* This class holds keys for each user encrypted with the key one level
	 * above it.
	 * The primary key is 'owner' and 'securityLevel'. */
	
	
	/* Table information */
	private static final String TABLE_NAME = "keys";
	
	/* File table attributes */
	public static final String OWNER = "owner";
	public static final String SECURITY_LEVEL = "securityLevel";
	public static final String KEY = "key";
	public static final String IV = "iv";
	
	/* Attributes in table. Must be changed if the attributes change. */
	/* The first element of the array is to be the primary key */
	private static final String[] FILE_ATTRIBUTES = {OWNER, SECURITY_LEVEL, KEY, IV};
	
	/* User table element lengths */
	private static final String OWNER_LENGTH = "25";
	private static final String SECURITY_LEVEL_LENGTH = "25";
	private static final String KEY_LENGTH = "256";
	private static final String IV_LENGTH = "256";
	
	private static final String[] FILE_ATTRIBUTES_LENGTH = {OWNER_LENGTH,
		                  SECURITY_LEVEL_LENGTH, KEY_LENGTH, IV_LENGTH};

	/* Maps attributes to their length. Assumes they are both ordered in the same way */
	private static Map<String, String> fileAttributes;
	
	/* Pre: FILE_ATTRIBUTES <= FILE_ATTRIBUTES */
	public H2Keys() {
		
		/* Construct the map if it has not been constructed before */
		if (fileAttributes == null) {
			assert FILE_ATTRIBUTES.length <= FILE_ATTRIBUTES_LENGTH.length : "Every attribute needs a length";
		
			fileAttributes = new HashMap<String, String>(FILE_ATTRIBUTES.length);
			for (int i = 0; i < FILE_ATTRIBUTES.length; i++)
				fileAttributes.put(FILE_ATTRIBUTES[i], FILE_ATTRIBUTES_LENGTH[i]);
		}
		
	}
	
	public static void main(String[] args) throws SQLException {
		
		H2Keys h = new H2Keys();
		
		h.dropKeyTable();
		h.createKeyTable();
		
	}

	public void dropKeyTable() throws SQLException {
		
		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    s.executeUpdate("DROP TABLE " + TABLE_NAME);
	    
	    conn.close();
	}
	
	
	
	public void createKeyTable() throws SQLException {

		Connection conn = getConnection();
		
		Statement s = conn.createStatement();
	    String command = "CREATE TABLE " + TABLE_NAME + "(";
	    
	    for (int i = 0; i < FILE_ATTRIBUTES.length; i++) {
	    	/* IV is input as byte[] and so needs type binary. */
	    	if (FILE_ATTRIBUTES[i].equals(KEY) || FILE_ATTRIBUTES[i].equals(IV))
	    		command = command + FILE_ATTRIBUTES[i] + " " + "binary("
  	                  +  fileAttributes.get(FILE_ATTRIBUTES[i]) + ") NOT NULL,";
	    	else if (FILE_ATTRIBUTES[i].equals(SECURITY_LEVEL))
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
	

	public void addKey(String owner, int securityLevel, byte[] key, byte[] iv) {
		
		Connection conn = getConnection();
		
		try {
			String command = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, owner);
			statement.setInt(2, securityLevel);
			statement.setBinaryStream(3, new ByteArrayInputStream(key),
					                  key.length);
			statement.setBinaryStream(4, new ByteArrayInputStream(iv),
	                  iv.length);
					
            statement.executeUpdate();
			
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void updateKey(String owner, int securityLevel, byte[] key, byte[] iv) {
		
		Connection conn = getConnection();

		try {
			String command = "UPDATE " + TABLE_NAME
			          + " SET " + KEY + "= ? AND " + IV + "= ? WHERE " + OWNER
			          + " = '" + owner + "' AND " + SECURITY_LEVEL + " = " +
			          securityLevel;
	
			PreparedStatement statement = conn.prepareStatement(command);
			
			statement.setBinaryStream(1, new ByteArrayInputStream(key),
	                  key.length);
			statement.setBinaryStream(2, new ByteArrayInputStream(iv),
					iv.length);
			
			statement.executeUpdate();
	
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/* Removes all keys for an owner that are under the influence of a given
	 * security level, including itself. */
	public void removeKey(String owner, int securityLevel) {
		
		Connection conn = getConnection();
		
		try {
			Statement s = conn.createStatement();
			s.execute("DELETE FROM " + TABLE_NAME + " WHERE " +
			          FILE_ATTRIBUTES[0] + " = '" + owner + "'" + " AND " +
					  FILE_ATTRIBUTES[1] + " >= '" + securityLevel + "'");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* Returns all of the encrypted keys and their respective security level
	 * for a given user that are under the influence of a specific security
	 * level. */
	public List<Map<String, Object>> getKeys(String username, int securityLevel) {
		
		Connection conn = getConnection();

		List<Map<String, Object>> userData = new ArrayList<Map<String, Object>>();
	    
		try {
			Statement s = conn.createStatement();	
			ResultSet r = s.executeQuery("SELECT * FROM " + TABLE_NAME +
					" WHERE " + FILE_ATTRIBUTES[0] + " = '" + username + "'"
					+ " AND " + SECURITY_LEVEL + " > " + securityLevel);
		
			while (r.next()) {
				Map<String, Object> data = new HashMap<String, Object>(FILE_ATTRIBUTES.length - 2);
				
				int i = r.getInt(2);
				data.put(FILE_ATTRIBUTES[1], i);
			
				InputStream in = r.getBinaryStream(3);
				byte[] b = new byte[in.available()];
				in.read(b);
				data.put(FILE_ATTRIBUTES[2], b);
				
				in = r.getBinaryStream(4);
				b = new byte[in.available()];
				in.read(b);
				data.put(FILE_ATTRIBUTES[3], b);
				
				userData.add(data);
			
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
