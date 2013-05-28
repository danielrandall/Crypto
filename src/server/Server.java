package server;

import java.io.IOException;
import java.net.ServerSocket;

/* If multiple users do not work then sockets/ports may be why */
public class Server {
	
	private static final int PORT_NUMBER = 4451;
	private static final int NUM_CONNECTIONS = 2;
	
	private static ServerSocket serverSocket = null;
	private static boolean listening = true;
	
    public static void main(String[] args) throws IOException {
        
        addShutdownHook();
 
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            
            Setup.setup();
            
            int connections = 0;
            
            while (listening) {
                new ServerThread(serverSocket.accept()).start();
                if (connections >= NUM_CONNECTIONS)
                	listening = false;
            }
            
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT_NUMBER);
            System.exit(-1);
            
        } finally {
        	serverSocket.close();
        }
    }
    
    
    
    private static void addShutdownHook() {
    	
    	Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
    	
    }
    
}
