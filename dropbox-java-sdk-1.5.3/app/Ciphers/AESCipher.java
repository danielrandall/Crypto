package Ciphers;

public class AESCipher extends GNUCryptoCipher {
	
	private static final String CIPHER_TYPE = "AES";
	private static final String MODE_TYPE = "CFB";
	private static final String PADDING_SCHEME = "PKCS7";
	private static final int BLOCK_SIZE = 16;
	private static final int KEY_SIZE = 256;
	private static final int IV_SIZE = 256;
	
	private static final String PRNG_ALGORITHM = "MD";
	private static final String HASH_FUNCTION = "MD5";
	
	
	@Override
	public byte[] encrypt(byte[] file, String key, byte[] iv) {
		
		return baseEncrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
			                  file, key, iv, BLOCK_SIZE);
		
	}

	@Override
	public byte[] decrypt(byte[] file, String key, byte[] iv) {
		
		return baseDecrypt(CIPHER_TYPE, MODE_TYPE, PADDING_SCHEME,
                file, key, iv, BLOCK_SIZE);
		
	}

	@Override
	public byte[] generateKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] generateIV() {
		
		return getIV(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(), IV_SIZE);
		
	}


	
	

	
	
	

}
