package server.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ciphers.SymmetricCipher;
import ciphers.SecurityVariables;
import client.model.keystore.JavaKeyStore;
import client.model.keystore.KeyStores;


public class KeyDerivation {
	
	
	private static KeyStores keystore = new JavaKeyStore();
	
	
	public static byte[] retrieveKey(String user, String level, SymmetricCipher cipher) {
	
	//	keystore.removeKey(user + level);
		
		byte[] key = null;
	/*	
		if (keystore.checkKeyExists(user + level))
		//	key = keystore.retrieveKey(user + level, "keystorepassword".toCharArray());
		else {
			key = SecurityVariables.generateKey();
			storeKey(user, level, key, "AES");
		}
		*/
		return key;
		
	}
	
	public static void storeKey(String user, String level, byte[] key, String algorithm) {
		
		keystore.storeKey(user + level, key, algorithm);
		
	}
	
	protected boolean userFilePermission(String file, String user) {
		
		return false;
		
	}
	
	public static void setup() {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String keyPassword = null;
		
		boolean passwordAccepted = false; 
		
		while (!passwordAccepted) {
			System.out.println("Enter keystore password");
			try {
				keyPassword = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			passwordAccepted = keystore.checkPassword("keystorelocation", keyPassword.toCharArray());
			
	//		keystore.clearKeystore();

		}
		
	}
	
}
