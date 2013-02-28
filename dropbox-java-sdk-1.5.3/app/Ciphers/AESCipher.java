package Ciphers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import gnu.crypto.cipher.CipherFactory;
import gnu.crypto.cipher.IBlockCipher;
import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.util.Base64;

/* TODO: get rid of ECB
 * Padding factory
 */

public class AESCipher extends GNUCryptoCipher {
	
	public static void main(String[] args) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
	//	if ("I am text to be hidden".equals(decrypt(encrypt("I am text to be hidden", "1234567812345678"), "1234567812345678")));
	//	System.out.println(encrypt());
	//	String s = new String(getIV("MD", "MD5", generateSeed(), 10), "UTF-8");
		byte[] iv = getIV("MD", "MD5", generateSeed(), 10);
		String cipherText = encrypt("AES","CFB", "PKCS7", "I am text to be hiddentoyou123456", "1234567812345678", iv);
		String plaintext = decrypt("AES","CFB", "PKCS7", cipherText, "1234567812345678", iv);
		System.out.println(cipherText);
	//	System.out.println(plaintext);
		System.out.println(plaintext.length());
		if (plaintext.equals("I am text to be hiddentoyou123456"))
				System.out.println("true");
				else System.out.println("t");
	}
	
	
	/* Cipher example: "AES"
	 * Cipher mode example: "CFB" */
	public static String encrypt(String cipher, String cipherMode, String paddingScheme,
			                  String plainText, String key, byte[] iv) {
		
		byte[] key_bytes = key.getBytes(); 
		
		IMode mode = ModeFactory.getInstance(cipherMode, cipher, 16);
	    Map<String, Object> attributes = new HashMap<String, Object>();
	     
	    // These attributes are defined in gnu.crypto.cipher.IBlockCipher.
	    attributes.put(IMode.KEY_MATERIAL, key_bytes);
	     
	    // These attributes are defined in IMode.
	    attributes.put(IMode.STATE, new Integer(IMode.ENCRYPTION));
	    attributes.put(IMode.IV, iv);
	    
	    try {
			mode.init(attributes);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    int blockSize = mode.currentBlockSize();
	    
	    /* Pad the plaintext such that it meets the block size required */
	    byte[] paddedText = pad(paddingScheme, blockSize, plainText.getBytes());
	    
        byte[] cipherText = new byte[paddedText.length];
        for (int i = 0; i + blockSize <= paddedText.length; i += blockSize)
	    	mode.update(paddedText, i, cipherText, i);
        
        return Base64.encode(cipherText);
		
	}
	
	
	public static String decrypt(String cipher, String cipherMode, String paddingScheme,
			                          String encryptedText, String key, byte[] iv) {
		
		byte[] key_bytes = key.getBytes(); 
		
		IMode mode = ModeFactory.getInstance(cipherMode, cipher, 16);
	    Map<String, Object> attributes = new HashMap<String, Object>();
	     
	    // These attributes are defined in gnu.crypto.cipher.IBlockCipher.
	    attributes.put(IMode.KEY_MATERIAL, key_bytes);
	     
	    // These attributes are defined in IMode.
	    attributes.put(IMode.STATE, new Integer(IMode.DECRYPTION));
	    attributes.put(IMode.IV, iv);
	    
	    try {
			mode.init(attributes);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    int blockSize = mode.currentBlockSize();
	    
	    /* Decode the previously encoded text */
	    byte[] ct = null;
		try {
			ct = Base64.decode(encryptedText);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] decryptedText = new byte[ct.length];
	    /* Decrypt the text */
	    for (int i = 0; i + blockSize <= ct.length; i += blockSize)
	    	mode.update(ct, i, decryptedText, i);
	    
	    /* Remove the previously added padding */
	    byte[] unpadded = unpad(paddingScheme, blockSize, decryptedText);
	    
		return new String(unpadded);
	}

}
