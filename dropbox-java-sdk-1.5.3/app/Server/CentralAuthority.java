package Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new 
        		             InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        
        ServerProtocol sp = new ServerProtocol();
        
        while ((inputLine = in.readLine()) != null) {
            outputLine = sp.processInput(inputLine);
            out.println(outputLine);
            if (outputLine.equals("end"))
               break;
       }
        
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
	}

}