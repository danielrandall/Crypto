package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSASunJCECipher {
	
	private static final String ALGORITHM = "RSA";
	private static final String MODE = "ECB";
	private static final String PADDING = "PKCS1PADDING";
	private static final String PROVIDER = "SunJCE";
	
	
public static void main(String[] args) {
		
		Key key = SecurityVariables.GenerateAsymmetricKeyPair().getPublic();
		byte[] fileContents = SecurityVariables.generateKey();
		RSASunJCECipher cipher = new RSASunJCECipher();
		
		/* Test */
		long startTime = 0;// = System.nanoTime();
		
		for (int i = -1000; i < 10000; i++) {
			if (i == 0)
				startTime = System.nanoTime();;
			byte[] encryptedFile = cipher.encrypt(fileContents, key);
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
	

	
	public byte[] encrypt(byte[] file, Key key) {
	
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING, PROVIDER);
			
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
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
	
	

	public byte[] decrypt(byte[] file, Key key) {
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING, PROVIDER);
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			
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

}