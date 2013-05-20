package client.model;

public class Client {
	
	/* Call necessary methods to setup */
    public static void setup() {
 
    	ServerComms.setup();
    	
    }
    
    
    /* Call necessary methods to clean up */
    public static void exit() {
    	
    	ServerComms.exit();
    	
    }
    
}

