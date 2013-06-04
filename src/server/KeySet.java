package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KeySet implements Serializable {
	
	private static final long serialVersionUID = 7791601076569588134L;
	private String owner;
	private Map<Integer, byte[]> keys;
	
	public KeySet(String owner) {
		
		this.owner = owner;
		keys = new HashMap<Integer, byte[]>();
		
	}
	
	public void addKey(int securityLevel, byte[] key) {
		
		keys.put(securityLevel, key);
		
	}
	
	public String getOwner() {
		
		return owner;
		
	}
	
	
	public Map<Integer, byte[]> getKeys() {
		
		return keys;
		
	}
	

}
