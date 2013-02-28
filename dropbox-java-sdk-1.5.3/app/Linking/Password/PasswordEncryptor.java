package Linking.Password;

public interface PasswordEncryptor {
	
	public String hashPassword(String password);
	
	public boolean checkPassword(String pass, String encrypted);

}