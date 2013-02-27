package Server;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.dropbox.client2.session.WebAuthSession;

import Linking.State;



public class CentralAuthority {
	
	
	public static void options(State state) {
		
		System.out.println("Enter 1 to upload a file");
        System.out.println("YOU JUST PRESSED 1");
        
      //  String fileContents = "Hello World!";
     //   ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
        
     //   DropboxOperations.uploadFile("/testing.txt", inputStream, state.getSession(), fileContents.length());
	//	DropboxOperations.copyBetweenAccounts(state.getSession(), state.getSession(), "/testing.txt", "/testingcop.txt");
	DropboxOperations.updateCache(state.getSession(), null, -1);
	
	}
	
	
	
	
	
	
	
	
	
	
	/*
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
*/
}