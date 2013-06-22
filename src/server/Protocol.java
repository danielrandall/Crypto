package server;

import server.users.Authentication;
import server.users.User;
 
public class Protocol {
	
	private static final String LOGIN = "1";    
	private static final String REGISTER = "2"; 
	private static final String EXIT_CODE = "999"; 
	
	public static void begin(ClientComms comms) {
		
		User user = null;
		
		while (user == null) {
		
			/* Recieve login or register decision */
			String decision = comms.fromClient();
			
			if (decision == null || decision.equals(EXIT_CODE)) {
				comms.sendAcknowledgement();
				return;
			}
		
			if (decision.equals(LOGIN))
				user = Authentication.login(comms);
			
			if (decision.equals(REGISTER))
				user = Authentication.registerAttempt(comms);
		
		}
		
		ServerCentralAuthority.options(user, comms);
		
	}
	
}
