package server;

import java.net.Socket;
 
public class ServerThread extends Thread {
	
    private ClientComms comms = null;
 
    
    public ServerThread(Socket socket) {
    	
    	super();
    	comms = new ClientComms(socket);
    	
    }
 
    
    /* Begin processing the client. */
    public void run() {
 
    	try {
    		
    		Protocol.begin(comms);
    	
    	} finally {
    		
    		/* Close connections */
    		comms.destroy();
    	}
    }
    
}