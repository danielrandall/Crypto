package Linking.Password;

public class BCryptEncryptor implements PasswordEncryptor {
	
	public String hashPassword(String password) {
		// Hash a password for the first time
		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}


	public boolean checkPassword(String pass, String encrypted) {
		// Check that an unencrypted password matches one that has
		// previously been hashed
		
		return BCrypt.checkpw(pass, encrypted);
	}
}