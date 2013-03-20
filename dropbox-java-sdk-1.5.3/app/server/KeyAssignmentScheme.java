package server;

import Ciphers.Cipher;

public interface KeyAssignmentScheme {

	public byte[] getKey(byte[] key, int sourceKeyPosition, int desiredKey, Cipher cipher);
	
}
