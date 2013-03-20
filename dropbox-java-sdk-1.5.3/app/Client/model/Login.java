package Client.model;

import Client.view.View;

public class Login {


	public static boolean userLogin(ServerComms comm, View view) {
		
		boolean accepted = false; 
		String username;
		String password;
		
			while (!accepted) {
				
				username = view.getUsername();
				password = view.getPassword();

				comm.toServer(username);
				comm.toServer(password);
				
				if (comm.fromServer().equals("1"))
					return true;
				else
					view.userPassNotAccepted();
			}
			
		/* Should never be reached */
		return false;
	}

}
