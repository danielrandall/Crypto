package client.model;

public class Register {
	
	/* Message constants from server */
	private static final String TRUE = "1";
	
	/* Message constants to send to server */
	private static final String REGISTER = "2";

	public static boolean userRegister(String username, String password) {
		
		/* Tell the server a user wishes to register */
		ServerComms.toServer(REGISTER);
		
		/* Give the server the desired username */
		ServerComms.toServer(username);
			
		/* If the server accepts the username */
		if (!ServerComms.fromServer().equals(TRUE))
			return false;
			
		ServerComms.toServer(password);
		
		return true;
		
	}
	
	public static boolean passwordCheck(String password, String reenterPassword) {
		
		return password.equals(reenterPassword);
		
	}

}
