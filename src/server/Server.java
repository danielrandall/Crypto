package server;

import java.io.IOException;
import java.net.ServerSocket;

 
public class Server {
	
	private static final int PORT_NUMBER = 4428;
	
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
 
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            
            Setup.setup();
            
            while (listening)
                new ServerThread(serverSocket.accept()).start();
            
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + PORT_NUMBER);
            System.exit(-1);
            
        } finally {
        	serverSocket.close();
        }
    }
    
}
