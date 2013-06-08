package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AESGNUCipher extends GNUCryptoCipher {
	
	private static final String CIPHER_TYPE = "AES";
	private static final String MODE_TYPE = "CBC";
	private static final String PADDING_SCHEME = "PKCS7";
	/* Block size used for the cipher. Recorded in bytes. */
	private static final int BLOCK_SIZE = 16;
	
	/* Key size used for the cipher. Recorded in bytes.
	private static final int KEY_SIZE = 32;
	/* Size of initialisation vector used for the cipher. Recorded in bytes.
	 * The IV should be the size of the block_size
	private static final int IV_SIZE = BLOCK_SIZE;
	
	private static final String PRNG_ALGORITHM = "MD";
	private static final String HASH_FUNCTION = "MD5";
	*/
	
	public static void main(String[] args) {
		
		byte[] key = SecurityVariables.generateKey();
		byte[] iv = SecurityVariables.generateIV();
		byte[] fileContents = null;
		
		/* Read file */
		String fileLocation = "testfiles/200MB.zip";
		File file = new File(fileLocation);
		
		AESGNUCipher cipher = new AESGNUCipher();
		
		try {
			
			InputStream fileStream = new java.io.FileInputStream(file);
			fileContents = new byte[fileStream.available()];
			fileStream.read(fileContents);
			fileStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/* Test */
		long startTime = 0;// = System.nanoTime();
		
		for (int i = -100; i < 100; i++) {
			if (i == 0)
				startTime = System.nanoTime();;
			byte[] encryptedFile = cipher.encrypt(fileContents, key, iv);
		}
		
		long endTime = System.nanoTime();

		long duration = endTime - startTime;
		
		System.out.println("startTime = " + startTime);
		System.out.println("");
		System.out.println("endTime = " + endTime);
		System.out.println("");
		System.out.println("duration = " + duration);
		double seconds = (double)duration / 1000000000.0;
		System.out.println("");
		System.out.println("duration in seconds = " + seconds);
	}
	
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
/*
	@Override
	public byte[] generateKey() {
		
		return getKey(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(), KEY_SIZE);
		
	}

	@Override
	public byte[] generateIV() {
		
		return getIV(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(), IV_SIZE);
		
	}
*/
}
