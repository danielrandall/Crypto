package Ciphers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import gnu.crypto.cipher.CipherFactory;
import gnu.crypto.cipher.IBlockCipher;
import gnu.crypto.util.Base64;

/* TODO: get rid of ECB
 * Padding factory
 */

public class AESCipher extends GNUCryptoCipher {
	
	public static void main(String[] args) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
	//	if ("I am text to be hidden".equals(decrypt(encrypt("I am text to be hidden", "1234567812345678"), "1234567812345678")));
	//	System.out.println(encrypt("AES", "PKCS7", "I am text to be hidden", "1234567812345678"));
		System.out.println(getIV("MD", "MD5", generateSeed(), 10));
	}
		
	/* Example: "AES" */
	public static String encrypt(String cipherType, String paddingScheme, String plaintext, String key) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		
		byte[] key_bytes = key.getBytes(); 
		
		IBlockCipher cipher = CipherFactory.getInstance(cipherType);
	    Map<String, Object> attributes = new HashMap<String, Object>();
	    attributes.put(IBlockCipher.KEY_MATERIAL, key_bytes);
	    cipher.init(attributes);
	    int blockSize = cipher.currentBlockSize();
	     
        byte[] paddedText = pad(paddingScheme, blockSize, plaintext.getBytes());
        byte[] cipherText = new byte[paddedText.length];
	     
	     for (int i = 0; i + blockSize <= paddedText.length; i += blockSize)
	           cipher.encryptBlock(paddedText, i, cipherText, i);
	     
	     return Base64.encode(cipherText);
	     
	}

	public static String decrypt(String cipherType, String paddingScheme, String encryptedText, String key) throws UnsupportedEncodingException, InvalidKeyException, IllegalStateException {
		
		byte[] key_bytes = key.getBytes(); 
		
		IBlockCipher cipher = CipherFactory.getInstance(cipherType);
	    Map<String, Object> attributes = new HashMap<String, Object>();
	    attributes.put(IBlockCipher.KEY_MATERIAL, key_bytes);
	    cipher.init(attributes);
	    int blockSize = cipher.currentBlockSize();	     
	     
	    byte[] ctt = Base64.decode(encryptedText);
	    
	    byte[] decryptedText = new byte[encryptedText.length()];
	     
	    for (int i = 0; i + blockSize <= ctt.length; i += blockSize)
	           cipher.decryptBlock(ctt, i, decryptedText, i);
	    
	    byte[] unpadded = unpad(paddingScheme, blockSize, decryptedText);
	    
	    String decryptedString = new String(unpadded);
		
		return decryptedString;
	}
	


}
