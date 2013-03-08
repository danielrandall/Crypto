package Linking.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class H2Database implements Database {
	
	/* Database information */
	protected static final String DATABASE_LOCATION = "dropbox-java-sdk-1.5.3/app/users";
	
	/* Driver information */
	protected static final String DRIVER_LOCATION = "org.h2.Driver";
	protected static final String CONNECTION_PREFIX = "jdbc:h2:";
	
	public Connection getConnection() {
		
		try {
			
			Class.forName(DRIVER_LOCATION);
			return DriverManager.
					getConnection(CONNECTION_PREFIX + DATABASE_LOCATION, "sa", "");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

}
