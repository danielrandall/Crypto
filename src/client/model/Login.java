package client.model;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import server.password.BCryptEncryptor;
import server.password.PasswordEncryptor;

import client.model.keystore.KeyStoreOperations;

public class Login {

	/* Meaning of messages from the Server */
	private static final String TRUE = "1";
	
	/* Meaning of messages from the Client */
	private static final String LOGIN = "1";
	
	private static final PasswordEncryptor passwordEncryptor = new BCryptEncryptor();
	
	/* Given the username and password check it with server
	 * TODO: Send the password encrypted.
	 *       Possible ways to do that are:
	 *       - SSL
	 *       - Public/private keys */
	public static boolean userLogin(String username, char[] password) {

		ServerComms.toServer(LOGIN);
		
		byte[] passwordBytes = charArraytoByteArray(password);


		ServerComms.toServer(username);
		
		/* Receive Acknowledgement */
		ServerComms.getInt();
	
		ServerComms.sendBytes(passwordBytes, passwordBytes.length);

		if (ServerComms.fromServer().equals(TRUE)) {
			DropboxOperations.setUsername(username);
			acquireConnection();
			KeyStoreOperations.login(username, password);
			
			password = "".toCharArray();
			passwordBytes = "".getBytes();
			return true;
		} else
			return false;
			
	}
	

	/* Method to acquire the necessary information to authenticate with the
	 * Dropbox servers.
	 */
	private static void acquireConnection() {
		
		/* Acquire Dropbox key and secret for logged in user from server */
		String key = ServerComms.fromServer();
		
		String secret = ServerComms.fromServer();
		
		/* Create an active session for the user and store it in
		 * DropboxOperations */
		DropboxOperations.makeSession(key, secret);
		
	}
	
	
	/* Converts a char array to a byte array without creating intermediate
	 * strings.
	 * This method is designed for converting passwords into a format which can
	 * be easily encrypted. Strings are not preferred in this scenario as they
	 * are immutable.
	 *
		Alternative method:
		
		byte[] passwordBytes2 = new byte[chars.length*2];
		for(int i=0; i<chars.length; i++) {
		    passwordBytes2[2*i] = (byte) ((chars[i]&0xFF00)>>8); 
		    passwordBytes2[2*i+1] = (byte) (chars[i]&0x00FF); 
		}
	*/
	public static byte[] charArraytoByteArray(char[] chars) {
		
		byte[] bytes = new byte[chars.length * 2];
		for (int i = 0; i < chars.length; i++) {
		   bytes[i * 2] = (byte) (chars[i] >> 8);
		   bytes[i * 2 + 1] = (byte) chars[i];
		}
		
		return bytes;
	}
	

}
