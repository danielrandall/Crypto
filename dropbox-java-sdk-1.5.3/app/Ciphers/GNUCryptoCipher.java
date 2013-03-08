package Ciphers;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;
import gnu.crypto.pad.WrongPaddingException;
import gnu.crypto.prng.IRandom;
import gnu.crypto.prng.LimitReachedException;
import gnu.crypto.prng.MDGenerator;
import gnu.crypto.prng.PRNGFactory;
import gnu.crypto.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public abstract class GNUCryptoCipher implements Cipher {
	
	
	/* Cipher example: "AES"
	 * Cipher mode example: "CFB" */
	protected static byte[] baseEncrypt(String cipher, String cipherMode, String paddingScheme,
			                  byte[] plainText, String key, byte[] iv, int blockSize) {
		
		byte[] key_bytes = key.getBytes(); 
		
		IMode mode = ModeFactory.getInstance(cipherMode, cipher, blockSize);
	    Map<String, Object> attributes = new HashMap<String, Object>();
	     
	    // These attributes are defined in gnu.crypto.cipher.IBlockCipher.
	    attributes.put(IMode.KEY_MATERIAL, key_bytes);
	    attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(blockSize));
	     
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
	    
	    /* Pad the plaintext such that it meets the block size required */
	    byte[] paddedText = pad(paddingScheme, blockSize, plainText);
	    
        byte[] cipherText = new byte[paddedText.length];
        for (int i = 0; i + blockSize <= paddedText.length; i += blockSize)
	    	mode.update(paddedText, i, cipherText, i);
        
        return cipherText;
		
	}
	
	protected static String base64Encode(byte[] text) {
		
		return Base64.encode(text);
		
	}
	
	protected static byte[] base64Decode(String text) {
		
		byte[] decoded = null;
		
		try {
			decoded = Base64.decode(text);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decoded;
		
	}	
	
	protected static byte[] baseDecrypt(String cipher, String cipherMode, String paddingScheme,
            byte[] encryptedText, String key, byte[] iv, int blockSize) {
		
		byte[] key_bytes = key.getBytes(); 

		IMode mode = ModeFactory.getInstance(cipherMode, cipher, blockSize);
		Map<String, Object> attributes = new HashMap<String, Object>();

		// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
		attributes.put(IMode.KEY_MATERIAL, key_bytes);
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(blockSize));

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
		
		byte[] decryptedText = new byte[encryptedText.length];
		/* Decrypt the text */
		for (int i = 0; i + blockSize <= encryptedText.length; i += blockSize)
			mode.update(encryptedText, i, decryptedText, i);

		/* Remove the previously added padding */
		byte[] unpadded = unpad(paddingScheme, blockSize, decryptedText);

		return unpadded;
	}
	
	
	/* Adds padding, if necessary, to the given string such that it is equal to
	 * the desired block size.
	 * Pads as per the given padding scheme.
	 * Example padding scheme: "PKCS7". */
	protected static byte[] pad(String scheme, int blockSize, byte[] input) {
		
		IPad padding = PadFactory.getInstance(scheme);
		padding.init(blockSize);
		
		/* Get the bytes to append to the text */
		byte[] toAppend = padding.pad(input, 0, input.length);
		byte[] paddedText = new byte[input.length + toAppend.length];
		
		/* Copy the provided text */
	    System.arraycopy(input, 0, paddedText, 0, input.length);
	    /* Append the bytes to the end */
	    System.arraycopy(toAppend, 0, paddedText, input.length, toAppend.length);
	    
	    return paddedText;
	}
	
	protected static byte[] unpad(String scheme, int blockSize, byte[] paddedText) {
		
		IPad padding = PadFactory.getInstance(scheme);
		padding.init(blockSize);

		try {
			/* Get the number of bytes to discard */
			int bytesToDiscard = padding.unpad(paddedText, 0, paddedText.length);
			byte[] unpaddedText = new byte[paddedText.length - bytesToDiscard];
			
			/* Copy the relevant bytes */
			System.arraycopy(paddedText, 0, unpaddedText, 0, unpaddedText.length);
			return unpaddedText;
		} catch (WrongPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/* Generates a seed using Java's secure random class. With the seed the
	 * GNU Crypto PRNG and hashing algorithm of choice is used to generate a
	 * random number to be used as an IV.
	 * Example prngAlgorithm: "MD"
	 * Example hash function: "MD5" */
	protected static byte[] getIV(String prngAlgorithm, String hashFunction, byte[] seed, int length) {
		
		Map<String, Object> attrib = new HashMap<String, Object>();
		IRandom rand = PRNGFactory.getInstance(prngAlgorithm);
		
		attrib.put(MDGenerator.MD_NAME, hashFunction);
	    attrib.put(MDGenerator.SEEED, seed);
	    
	    rand.init(attrib);
	    
	    byte[] result = new byte[length];
	    try {
			rand.nextBytes(result, 0, result.length);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LimitReachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return result;     
	}
	
	/* A hash function with a n-bit output resist collisions up to work factor 2^(n/2) at least.
	 * If hash-mixing was not accumulating entropy up to at least n/2 bits then this could be turned
	 * into a collision attack on the hash function.
	 * 
	 * Hash as many hardware measures as possible.
	 * 
	 * Java isolates code from hardware
	 */
	protected static byte[] generateSeed() {
		
		/* Input to generateSeed(int) given in bytes */
		return new SecureRandom().generateSeed(16);
		
	}

}
