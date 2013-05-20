package server;

import server.operations.UserOperations;
import client.model.linking.Authentication;
import client.model.users.User;
 
public class Protocol {
	
	private static final String LOGIN = "1";    
	private static final String REGISTER = "2"; 
	private static final String EXIT_CODE = "999"; 
	
	public static void begin(ClientComms comms) {
		
		User user = null;
		
		while (user == null) {
		
			/* Recieve login or register decision */
			String decision = comms.fromClient();
		
			if (decision.equals(LOGIN))
				user = Authentication.link(comms);
			
			if (decision.equals(REGISTER))
				user = UserOperations.register(comms);
			
			if (decision.equals(EXIT_CODE))
				return;
		
		}
		
		CentralAuthority.options(user, comms);
		
	}
	
}
