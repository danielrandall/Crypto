package client.ciphers;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import client.model.EncryptedKey;
import client.model.FileOperations;
import client.model.ServerComms;
import client.model.keystore.KeyStoreOperations;

public class SecurityVariables {
	
	private static final int NUMBER_SECURITY_LEVELS = 5;
	private static final int HIGHEST_SECURITY_LEVEL = 1;
	
	/* SYMMETRIC */
	
	/* Key size used for the symmetric cipher. Recorded in bytes. */
	private static final int SYMMETRIC_KEY_SIZE = 16;
	/* Size of initialisation vector used for the cipher. Recorded in bytes.
	 * The IV should be the size of the block_size */
	private static final int IV_SIZE = 16;
	
	
	
	/* ASYMMETRIC */
	
	/* Public key algorithm used */
	private static final String ASYMMETRIC_ALGORITHM = "RSA";
	private static final String ASYMMETRIC_ALGORITHM_PROVIDER = "SunRsaSign";
	
	/* The key size used in the asymmetric cipher. Recorded in bits */
	private static final int ASYMMETRIC_KEY_SIZE = 2048;
	
	/* Random number generator used for symmetric key and iv */
	private static SecureRandom random;
	private static int currentForSeed;
	private static final int MAX_PER_SEED = 20;
	
	
	public static byte[] generateKey() {
		
		if (random == null)
			randomSetup();
		
		if (currentForSeed >= MAX_PER_SEED)
			generateSeed(SYMMETRIC_KEY_SIZE);
		
		byte[] key = new byte[SYMMETRIC_KEY_SIZE];
		random.nextBytes(key);
		
		currentForSeed++;
		
		return key;
		
	}

	public static byte[] generateIV() {
		
		if (random == null)
			randomSetup();
		
		if (currentForSeed >= MAX_PER_SEED)
			generateSeed(IV_SIZE);
		
		byte[] iv = new byte[IV_SIZE];
		random.nextBytes(iv);
		
		currentForSeed++;
		
		return iv;
		
	}
	
	private static void randomSetup() {
		
		random = new SecureRandom();
		generateSeed(IV_SIZE);
		currentForSeed = 0;
		
	}
	
	/* Input to generateSeed(int) given in bytes */
	protected static void generateSeed(int size) {		
		
		random.setSeed(random.generateSeed(size));
		
	}
	
	
	
	/* Check randomness with key size */
	public static KeyPair GenerateAsymmetricKeyPair() {
		
		KeyPair keyPair = null;
		
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(ASYMMETRIC_ALGORITHM, ASYMMETRIC_ALGORITHM_PROVIDER);
			generator.initialize(ASYMMETRIC_KEY_SIZE);
			
			keyPair = generator.generateKeyPair();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return keyPair;
		
	}
	
	
	/* The users keys are generated.
	 * keys[0] holds the highest level key. ie. security level 1.
	 * keys[numLevels] holds the lowest. */
	public static byte[][] generateKeys(int numLevels) {
		
		byte[][] keys = new byte[numLevels][];
		
		for (int i = 0; i < numLevels; i++)
			keys[i] = generateKey();
		
		return keys;
		
	}
	
	public static byte[][] generateIVs(int numLevels) {
		
		byte[][] ivs = new byte[numLevels - 1][];
		
		/* Generate ivs to be used in encrypting the keys */
		for (int i = 0; i < numLevels - 1; i++)
			ivs[i] = generateIV();
		
		return ivs;
		
	}
	
	
	public static byte[][] generateAllKeys() {
		
		return generateKeys(NUMBER_SECURITY_LEVELS);
		
	}
	
	
	
	public static byte[][] generateAllIVs() {
		
		return generateIVs(NUMBER_SECURITY_LEVELS);
		
	}
	
	
	public static int getNumberLevels() {
		
		return NUMBER_SECURITY_LEVELS;
		
	}
	
	/* A hash function with a n-bit output resist collisions up to work factor 2^(n/2) at least.
	 * If hash-mixing was not accumulating entropy up to at least n/2 bits then this could be turned
	 * into a collision attack on the hash function.
	 * 
	 * Hash as many hardware measures as possible.
	 * 
	 * Java isolates code from hardware
	 */
	
	
	
	
	/* Encrypted keys is 1 smaller than generated keys. */
	public static void generateSymmetricVariables (int highestSecurityLevel) {
		
		int numKeys = NUMBER_SECURITY_LEVELS - highestSecurityLevel + 1;
		
		byte[][] keys = SecurityVariables.generateKeys(numKeys);
		
		/* Store the keys in the key store */
		for (int i = 0; i < numKeys; i++)
			KeyStoreOperations.storeOwnKey(Integer.toString(highestSecurityLevel + i), keys[i]);
		
		byte[][] ivs;
		
		byte[][] keysToEncrypt;
		if (highestSecurityLevel == HIGHEST_SECURITY_LEVEL) {
			keysToEncrypt = keys;
		 	ivs = SecurityVariables.generateIVs(numKeys);
		} else {
			keysToEncrypt = new byte[numKeys + 1][];
			ivs = SecurityVariables.generateIVs(numKeys + 1);
			keysToEncrypt[0] = KeyStoreOperations.retrieveOwnKey(Integer.toString(highestSecurityLevel - 1));
			for (int i = 1; i < numKeys + 1; i++)
				keysToEncrypt[i] = keys[i - 1];
		}
		
		EncryptedKey[] encryptedKeys = FileOperations.encryptKeys(keysToEncrypt, ivs);
		
		for (int i = 0; i < encryptedKeys.length; i++) {
			byte[] encryptedKey = encryptedKeys[i].getEncryptedKey();
			ServerComms.sendBytes(encryptedKey, encryptedKey.length);
			
			byte[] iv = encryptedKeys[i].getIV();
			ServerComms.sendBytes(iv, iv.length);
		}
		
		keys = null;
		
	}

}
