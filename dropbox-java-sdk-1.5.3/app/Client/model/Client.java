package Client.model;

import java.io.IOException;

import Client.view.View;

public class Client {
	
	private static final String LOGIN = "1";    
	private static final String REGISTER = "2";  
	
    public static void main(String[] args) throws IOException {
 
    	ServerComms comms = null;
    	View view = null;

        try {	
        	
        	/* Connect to server */
            comms = new ServerComms();
            
            /* Setup view */
            view = new View();
            
            /* Get first choice */
            String decision = view.getInitialDecision();
            
            if (decision.equals(LOGIN)) {
            	comms.toServer(decision);
            	Login.userLogin(comms, view);
            }
            
            if (decision.equals(REGISTER)) {
            	comms.toServer(decision);
            	Register.userRegister(comms, view);
            }
            
            CentralAuthority.options(comms, view);
            
        } finally {
        	/* Clean up */
        	view.destroy();
        	comms.destroy();
        }
    }
    
}

