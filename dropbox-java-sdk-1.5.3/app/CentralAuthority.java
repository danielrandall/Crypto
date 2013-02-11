import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class CentralAuthority {
	
	public static void main(String[] args) throws IOException {
		 
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }
 
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
        clientSocket.close();
        serverSocket.close();
	}

}
