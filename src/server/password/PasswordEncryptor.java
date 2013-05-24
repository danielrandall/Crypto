package server.password;

public interface PasswordEncryptor {
	
	public String hashPassword(String password);
	
	public boolean checkPassword(String password, String encrypted);

}