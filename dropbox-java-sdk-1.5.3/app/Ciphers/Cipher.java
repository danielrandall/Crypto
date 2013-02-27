package Ciphers;

public interface Cipher {

	public byte[] encrypt(byte[] file, byte[] key);
	public byte[] decrypt(byte[] file, byte[] key);
	
}
