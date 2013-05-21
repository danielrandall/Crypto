package Ciphers;

import gnu.crypto.prng.IRandom;
import gnu.crypto.prng.LimitReachedException;
import gnu.crypto.prng.MDGenerator;
import gnu.crypto.prng.PRNGFactory;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class SecurityVariables {
	
	/* Key size used for the cipher. Recorded in bytes. */
	private static final int KEY_SIZE = 32;
	/* Size of initialisation vector used for the cipher. Recorded in bytes.
	 * The IV should be the size of the block_size */
	private static final int IV_SIZE = 16;
	
	private static final String PRNG_ALGORITHM = "MD";
	private static final String HASH_FUNCTION = "MD5";
	
	
	public static byte[] generateKey() {
		
		return getKey(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(16), KEY_SIZE);
		
	}

	public static byte[] generateIV() {
		
		return getIV(PRNG_ALGORITHM, HASH_FUNCTION, generateSeed(16), IV_SIZE);
		
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
	
	
	protected static byte[] getKey(String prngAlgorithm, String hashFunction, byte[] seed, int length) {
		
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
	protected static byte[] generateSeed(int size) {
		
		/* Input to generateSeed(int) given in bytes */
		return new SecureRandom().generateSeed(size);
		
	}

}
