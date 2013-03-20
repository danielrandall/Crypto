package Client.model;

import Client.view.View;

public class Register {
	
	private static final String TRUE = "1";
	//private static final String FALSE = "0";

	public static void userRegister(ServerComms comm, View view) {
		
		String username = null;
		String password = null;
		String trueFalse = null;
		
		boolean usernameAccepted = false; 
		
		while (!usernameAccepted) {
			
			username = view.getUsername();
		
			comm.toServer(username);
			
			trueFalse = comm.fromServer();
			
			/* If the server accepts the username then exit the loop */
			if (trueFalse.equals(TRUE))
				usernameAccepted = true;
			else
				System.out.println("Username unavailable");
			
		}

		password = view.getPassword();
		
		comm.toServer(password);
		
	}

}
