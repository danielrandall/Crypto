package Client.model;

public class Login {

	private static final String TRUE = "1";
	private static final String LOGIN = "1";
	
	public static boolean userLogin(String username, String password) {
		
		ServerComms.toServer(LOGIN);

		ServerComms.toServer(username);
		ServerComms.toServer(password);
				
		return (ServerComms.fromServer().equals(TRUE));
			
	}

}
