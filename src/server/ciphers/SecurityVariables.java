package server.ciphers;

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
	
	
	/* ASYMMETRIC */
	
	/* Public key algorithm used */
	private static final String ASYMMETRIC_ALGORITHM = "RSA";
	private static final String ASYMMETRIC_ALGORITHM_PROVIDER = "SunRsaSign";
	
	/* The key size used in the asymmetric cipher. Recorded in bits */
	private static final int ASYMMETRIC_KEY_SIZE = 2048;
	
	
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
	

}
