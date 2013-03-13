package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Linking.JavaKeyStore;
import Linking.KeyStores;

public class KeyDerivation {
	
	
	
	private static KeyStores keystore = new JavaKeyStore();
	
	
	public static byte[] retrieveKey(String file, String user, String level) {
		
		return keystore.retrieveKey(file, "keystorepassword".toCharArray());
		
	}
	
	public static void storeKey(String id, byte[] key, String algorithm) {
		
		keystore.storeKey(id, key, algorithm);
		
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
		}
		
	}
	
}
