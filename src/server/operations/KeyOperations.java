package server.operations;

import java.util.List;
import java.util.Map;

import server.databases.H2Keys;

public class KeyOperations {
	
	private static H2Keys keyDatabase = new H2Keys();
	
	/* Stores all of the keys and their respective information in the database.
	 * The indexes of arrays given must correspond to one another. */
	public static void storeKeys(String owner, byte[][] keys,
						int[] securityLevels, byte[][] ivs) {
		
		for (int i = 0; i < keys.length; i++)
			keyDatabase.addKey(owner, securityLevels[i], keys[i], ivs[i]);

	}
	
	
	public List<Map<String, Object>> getKeysforUser(String username, int maxSecurityLevel) {
		
		return keyDatabase.getKeys(username, maxSecurityLevel);
		
	}

}
