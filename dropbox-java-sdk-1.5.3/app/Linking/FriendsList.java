package Linking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FriendsList implements Serializable {
	
	private static final long serialVersionUID = 4200911073443895773L;
	
	private Map<String, Interval> list = new HashMap<String, Interval>();
	
	public boolean isFriend(String username) {
		
		return list.containsKey(username);
		
	}
	
	public void addFriend(String username, Interval bounds) {
		
		list.put(username, bounds);
		
	}
	
	public void removeFriend(String username) {
		
		list.remove(username);
		
	}
	
	public void editFriendPermissions(String username, Interval newBounds) {
		
		list.put(username, newBounds);
		
	}
	

}
