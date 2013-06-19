package server.ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSASunJCECipher implements Ciphers {
	
	private static final String ALGORITHM = "RSA";
	private static final String MODE = "ECB";
	private static final String PADDING = "OAEPWITHSHA-512ANDMGF1PADDING";
	private static final String PROVIDER = "SunJCE";
	
	/*
	public static void main(String[] args) {
		
		Key key = SecurityVariables.GenerateAsymmetricKeyPair().getPublic();
		byte[] fileContents = SecurityVariables.generateKey();
		RSASunJCECipher cipher = new RSASunJCECipher();
		

		long startTime = 0;// = System.nanoTime();
		
		for (int i = -1000; i < 100000; i++) {
			if (i == 0)
				startTime = System.nanoTime();;
			byte[] encryptedFile = cipher.encrypt(fileContents, key, null);
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
*/
	

	
	public byte[] encrypt(byte[] file, byte[] key, byte[] iv) {
		
		PublicKey publicKey = convertBytesToPublicKey(key);
	
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING, PROVIDER);
			
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			return cipher.doFinal(file);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	

	public byte[] decrypt(byte[] file, byte[] key, byte[] iv) {
		
		PrivateKey privateKey = convertBytesToPrivateKey(key);
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING, PROVIDER);
			
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			return cipher.doFinal(file);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static PublicKey convertBytesToPublicKey(byte[] key) {
		
		try {
			
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			return keyFactory.generatePublic(new X509EncodedKeySpec(key));
			
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	public static PrivateKey convertBytesToPrivateKey(byte[] key) {
		
		try {
			
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key));
			
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
