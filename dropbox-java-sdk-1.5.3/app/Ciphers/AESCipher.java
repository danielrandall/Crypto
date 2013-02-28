package Ciphers;

public class AESCipher extends GNUCryptoCipher {
	
	private static final String CIPHER_TYPE = "AES";
	private static final String MODE_TYPE = "CFB";
	private static final String PADDING_SCHEME = "PKCS7";
	private static final int BLOCK_SIZE = 16;
	
	private static final String PRNG_ALGORITHM = "MD";
	private static final String HASH_FUNCTION = "MD5";
	
	
	@Override
	public String encrypt(String file, String key, byte[] iv) {
		
		return baseEncrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
			                  file, key, iv, BLOCK_SIZE);
		
	}

	@Override
	public String decrypt(String file, String key, byte[] iv) {
		
		return baseDecrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
                file, key, iv, BLOCK_SIZE);
		
	}
	
	

	
	
	

}
