package server.encryption;

import ciphers.SymmetricCipher;

public interface KeyAssignmentScheme {

	public byte[] getKey(byte[] key, int sourceKeyPosition, int desiredKey, SymmetricCipher cipher);
	
}
