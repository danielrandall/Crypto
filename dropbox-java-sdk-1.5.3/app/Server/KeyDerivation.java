package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Ciphers.Cipher;
import Linking.JavaKeyStore;
import Linking.KeyStores;

public class KeyDerivation {
	
	
	private static KeyStores keystore = new JavaKeyStore();
	
	
	public static byte[] retrieveKey(String user, String level, Cipher cipher) {
	
	//	keystore.removeKey(user + level);
		
		byte[] key;
		
		if (keystore.checkKeyExists(user + level))
			key = keystore.retrieveKey(user + level, "keystorepassword".toCharArray());
		else {
			key = cipher.generateKey();
			storeKey(user, level, key, "AES");
		}
		
		return key;
		
	}
	
	private static void storeKey(String user, String level, byte[] key, String algorithm) {
		
		
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
		
			passwordAccepted = keystore.checkPassword(keyPassword.toCharArray());
			
		//	keystore.clearKeystore();

		}
		
	}
	
}
