package Ciphers;

public interface Cipher {

	public byte[] encrypt(byte[] file, byte[] key, byte[] iv);
	public byte[] decrypt(byte[] file, byte[] key, byte[] iv);
	//public byte[] generateKey();
	//public byte[] generateIV();
	
}
