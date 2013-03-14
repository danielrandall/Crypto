package Server;

import Linking.Databases.H2Users;

public class UserOperations {

	private static H2Users database = new H2Users();
	
	
	public static boolean checkUserExists(String username) {
		
		return database.checkUserExists(username);
		
	}
	
		

}
