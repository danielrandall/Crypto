package ciphers;

public interface SymmetricCipher {

	public byte[] encrypt(byte[] file, byte[] key, byte[] iv);
	public byte[] decrypt(byte[] file, byte[] key, byte[] iv);
	
}
