package Server;

import java.io.BufferedReader;
import java.io.IOException;

import Linking.Authentication;
import Linking.FriendsList;
import Linking.Interval;
import Linking.Permissions;
import Linking.State;
import Linking.Databases.H2Users;

public class UserOperations {

	private static H2Users database = new H2Users();
	
	
	public static boolean checkUserExists(String username) {
		
		return database.checkUserExists(username);
		
	}


	public static void addUserToFriendsList(String username,
			             String usernameToAdd, int lowerBound,
			             					int upperBound) {
		
		FriendsList friends = (FriendsList)database.getUser(username).get("friends");
		Permissions p = new Interval(lowerBound, upperBound);
		friends.addFriend(usernameToAdd, p);
		
		database.updateFriends(username, friends);
	}


	public static State Register(BufferedReader input) {
		
		String username = null;
		String password = null;
		
		boolean usernameAccepted = false; 
		
		try {
		
			while (!usernameAccepted) {
				
				System.out.println("Enter username");
				username = input.readLine();
			
			
				usernameAccepted = !checkUserExists(username);
			
				if (!usernameAccepted)
					System.out.println("Username unavailable");
			
			}
		
			System.out.println("Enter password");
			password = input.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Authentication.authenticate(username, password);
		
	}
	
		

}
