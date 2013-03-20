package Client.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {
	
	private BufferedReader stdIn = null;
	
	public View() {
		
		stdIn = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public String getInitialDecision() {
		
		outputToUser("Enter 1 to login");
		outputToUser("Enter 2 to register");
		
		return getInput();
		
	}
	
	
	public void outputToUser(String output) {
		
		System.out.println(output);
		
	}
	
	
	public String getInput() {
		
		String fromUser = null;
		
		try {
			fromUser = stdIn.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fromUser;
	}
	
	
	public void destroy() {
		
		try {
			stdIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public String getUsername() {
		
		outputToUser("Enter username");
		return getInput();
		
	}

	
	public String getPassword() {
		
		outputToUser("Enter password");
		return getInput();
		
	}

	
	public void userPassNotAccepted() {
		
		outputToUser("Username and password not accepted");
		
	}
	
	
	public void usernameNotAvailable() {
		
		outputToUser("Username not available");
		
	}
	
	public String getCentralDecision() {
		
		outputToUser("Enter 1 to upload an encrypted file");
		outputToUser("Enter 2 to download a decrypted file");
		outputToUser("Enter 3 to add friend");
		
		return getInput();
	}

	public String getFileLocation() {
		
		outputToUser("Enter file address");
		
		return getInput();
		
	}
	
	public String getSecurityLevel() {
		
		outputToUser("Enter level of security for the file (1 being the highest)");
		
		return getInput();
		
	}

	public void fileNotAccepted() {
		
		outputToUser("File does not exist");
		
	}

}
