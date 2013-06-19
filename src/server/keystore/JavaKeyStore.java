package server.keystore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.spec.SecretKeySpec;

public class JavaKeyStore implements KeyStores {
	
	private static final String KEY_STORE_ALGORITHM = "JCEKS";
	
	private KeyStore keystore;
	private  char[] keystorePassword;
	private final String keystoreLocation = "serverkeys";
	
	/* TODO: passwords for specific keys */
	
	
	/* Existing keystores:
	 * 1.
	 * name: "keystore"
	 * password: "keystorepassword"
	 */
	
	
	
	
	public boolean checkPassword(String username, char[] password) {

	    java.io.FileInputStream input = null;
	    
	    try {
	    	
	    	/* Default type does not allow for storage of non-private
	    	 * (symmetric) keys */
	    	keystore = KeyStore.getInstance(KEY_STORE_ALGORITHM);
	    		    	
	        input = new java.io.FileInputStream(keystoreLocation);
	        
	        keystore.load(input, password);
	        
	        keystorePassword = password;
	        
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Incorrect password");
			return false;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (input != null) {
	            try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	    
	    return true;
		
	}
	
	
	public void createKeystore(String keystoreName, char[] password) {
		
	    try {
	    	
	    	keystore = KeyStore.getInstance(KEY_STORE_ALGORITHM);
	    	
	    	//   input = new java.io.FileInputStream(keystoreLocation);
	        
	    	/* Create empty keystore */
	        keystore.load(null, password);
	        
	        keystorePassword = password;
	        
	        storeKS();
	        
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
  
	public void storeKey(String id, byte[] key, String algorithm) {

		SecretKeySpec skSpec = new SecretKeySpec(key, algorithm);
		
		SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(skSpec);
		
		try {
			keystore.setEntry(id, skEntry, new KeyStore.PasswordProtection(keystorePassword));
			
			storeKS();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public byte[] retrieveKey(String id) {
		
		byte[] key = null;
		
		try {
			key = keystore.getKey(id, keystorePassword).getEncoded();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return key;
	}
	
	public void removeKey(String id) {
		
		try {
			keystore.deleteEntry(id);
			
			storeKS();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean checkKeyExists(String id) {
		
		boolean exists = false;
		
		try {
			exists = keystore.containsAlias(id);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exists;
		
	}
	
	public void clearKeystore() {
		
		try {
			Enumeration<String> keys = keystore.aliases();
			
			while (keys.hasMoreElements())
				keystore.deleteEntry(keys.nextElement());
				storeKS();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void storeKS() {
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(keystoreLocation);
			
	        /* Permanently store keystore */
	        keystore.store(fos, keystorePassword);
	        
	        fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
