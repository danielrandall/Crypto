package Ciphers;

public interface Cipher {

	public byte[] encrypt(byte[] file, String key, byte[] iv);
	public byte[] decrypt(byte[] file, String key, byte[] iv);
	public byte[] generateKey();
	public byte[] generateIV();
	
}
