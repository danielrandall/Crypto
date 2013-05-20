package server;

import java.net.Socket;
 
public class ServerThread extends Thread {
	
    private ClientComms comms = null;
    private Socket socket;
 
    
    public ServerThread(Socket socket) {
    	
    	super();
    	comms = new ClientComms(socket);
    	this.socket = socket;
    	
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