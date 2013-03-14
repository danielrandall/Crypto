package Ciphers;

public class AESCipher extends GNUCryptoCipher {
	
	private static final String CIPHER_TYPE = "AES";
	private static final String MODE_TYPE = "CFB";
	private static final String PADDING_SCHEME = "PKCS7";
	/* Block size used for the cipher. Recorded in bytes. */
	private static final int BLOCK_SIZE = 16;
	/* Key size used for the cipher. Recorded in bits. */
	private static final int KEY_SIZE = 32;
	private static final int IV_SIZE = 256;
	
	private static final String PRNG_ALGORITHM = "MD";
	private static final String HASH_FUNCTION = "MD5";
	
	
	@Override
	public byte[] encrypt(byte[] file, byte[] key, byte[] iv) {
		
		return baseEncrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
			                  file, key, iv, BLOCK_SIZE);
		
	}

	@Override
	public byte[] decrypt(byte[] file, byte[] key, byte[] iv) {
		
		return baseDecrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
                file, key, iv, BLOCK_SIZE);
		
	}

	@Override
	public byte[] generateKey() {
		
		return getKey(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(), KEY_SIZE);
		
	}

	@Override
	public byte[] generateIV() {
		
		return getIV(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(), IV_SIZE);
		
	}


	
	

	
	
	

}
