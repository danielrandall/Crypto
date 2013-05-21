package server.users.friends;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FriendsList implements Serializable {
	
	private static final long serialVersionUID = 4200911073443895773L;
	
	private Map<String, Permissions> list = new HashMap<String, Permissions>();
	
	public boolean isFriend(String username) {
		
		return list.containsKey(username);
		
	}
	
	public void addFriend(String username, Permissions bounds) {
		
		list.put(username, bounds);
		
	}
	
	public void removeFriend(String username) {
		
		list.remove(username);
		
	}
	
	public void editFriendPermissions(String username, Permissions newBounds) {
		
		list.put(username, newBounds);
		
	}
	
	public Permissions getFriendPermissions(String username) {
		
		return list.get(username);
		
	}
	

}
