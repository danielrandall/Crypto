package ciphers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESSunJCECipher {
	
	private static final String ALGORITHM = "AES";
	private static final String MODE = "CFB";
	private static final String PADDING = "PKCS5PADDING";
	private static final String PROVIDER = "SunJCE";
	
	
	public static void main(String[] args) {
		
		byte[] key = SecurityVariables.generateKey();
		byte[] iv = SecurityVariables.generateIV();
		byte[] fileContents = null;
		AESSunJCECipher cipher = new AESSunJCECipher();
		
		/* Read file */
		String fileLocation = "testfiles/multipage111.pdf";
		File file = new File(fileLocation);
		
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
		
		for (int i = -1000; i < 1000; i++) {
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
	
	
	public byte[] encrypt(byte[] file, byte[] key, byte[] iv) {
		
		try {
			
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE + "/" + PADDING, PROVIDER);
			
			SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
			
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
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	public byte[] decrypt(byte[] file, byte[] key, byte[] iv) {
		
		try {
			
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE + "/" + PADDING, PROVIDER);
			
			SecretKey keySpec = new SecretKeySpec(key, ALGORITHM);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
			
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
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
