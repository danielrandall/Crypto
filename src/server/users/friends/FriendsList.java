package server.users.friends;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public int getFriendListSize() {
		
		return list.size();
	
		
	}
	
	/* Returns all friends and their upper bound */
	public String[][] getAllFriends() {
		
		String[][] pairs = new String[list.size()][2]; 
		Iterator<Entry<String, Permissions>> it = list.entrySet().iterator();
		int i = 0;
		
		while(it.hasNext()) {
			Entry<String, Permissions> entry = it.next();
			
			pairs[i][0] = entry.getKey();
			pairs[i][1] = Integer.toString(entry.getValue().getLowerBound());
			
		}
		
		return pairs;
	}
	

}
