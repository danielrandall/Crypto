package ciphers;

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

public class RSACipher {
	
	private static final String ALGORITHM = "RSA";
	private static final String MODE = "ECB";
	private static final String PADDING = "PKCS1PADDING";
	private static final String PROVIDER = "SUN";
	
	/*
	public static void main(String[] args) {
		
		KeyPair keyPair = SecurityVariables.GenerateAsymmetricKeyPair();
		
		String plaintext = "helloThere";
		
		byte[] ciphertext = null;
		try {
			ciphertext = encrypt(plaintext.getBytes("UTF-16"), keyPair.getPublic());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(new String(ciphertext, "UTF-16"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		byte[] plaintextbytes = decrypt(ciphertext, keyPair.getPrivate());
		
		String plaintext2 = null;
		
		try {
			plaintext2 = new String(plaintextbytes, "UTF-16");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(plaintext2);
		
		if (plaintext.equals(plaintext2))
			System.out.println("worked!");
		
	}
*/
	
	public byte[] encrypt(byte[] file, Key key) {
	
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING);
			
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
		}
		
		return null;
	}
	
	

	public byte[] decrypt(byte[] file, Key key) {
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + MODE +
					"/" + PADDING);
			
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
		}
		
		return null;
	}

}
