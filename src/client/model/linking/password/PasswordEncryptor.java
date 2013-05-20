package client.model.linking.password;

public interface PasswordEncryptor {
	
	public String hashPassword(String password);
	
	public boolean checkPassword(String pass, String encrypted);

}