package Linking;

import java.util.Map;

public interface Database {
	
	public boolean checkUserExists(String username);
	public void addUser(String[] inputs);
	public void removeUser(String username);
	public Map<String, String> getUser(String username);

}
