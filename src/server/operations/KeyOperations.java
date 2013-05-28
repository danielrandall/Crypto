package server.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.databases.H2Keys;

public class KeyOperations {
	
	private static H2Keys keyDatabase = new H2Keys();
	
	/* Stores all of the keys and their respective information in the database.
	 * The indexes of arrays given must correspond to one another. */
	public static void storeKeys(String owner, byte[][] keys,
						int[] securityLevels, byte[][] ivs, boolean update) {
		
		if (update)
			for (int i = 0; i < keys.length; i++)
				keyDatabase.updateKey(owner, securityLevels[i], keys[i], ivs[i]);
		else
			for (int i = 0; i < keys.length; i++)
				keyDatabase.addKey(owner, securityLevels[i], keys[i], ivs[i]);

	}
	
	/* Retrieves all of the keys under the authority of the given security
	 * level (ie. all the the keys with a lower level) from the database and
	 * and converts the list received into a more friendly format. */
	public static Map<Integer, Object[]> getDerivableKeys(String username,
			int maxSecurityLevel) {
		
		List<Map<String, Object>> keyList = keyDatabase.getKeys(username, maxSecurityLevel);
		Map<Integer, Object[]> keyMap = new HashMap<Integer, Object[]>();
		
		for (int i = 0; i < keyList.size(); i++) {
			Map<String, Object> key = keyList.get(i);
			Object[] keyAndIV = new Object[2];
			
			keyAndIV[0] = key.get(H2Keys.KEY);
			keyAndIV[1] = key.get(H2Keys.IV);
			keyMap.put((Integer) key.get(H2Keys.SECURITY_LEVEL), keyAndIV);
		}
		
		return keyMap;
		
	}

}
