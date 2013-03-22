package Client.model;

public class Register {
	
	private static final String TRUE = "1";
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

}
