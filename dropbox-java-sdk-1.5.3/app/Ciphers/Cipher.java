package Ciphers;

public interface Cipher {

	public String encrypt(String file, String key, byte[] iv);
	public String decrypt(String file, String key, byte[] iv);
	
}
