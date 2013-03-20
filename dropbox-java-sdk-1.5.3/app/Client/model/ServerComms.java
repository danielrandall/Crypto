package Client.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerComms {
	
	private static final String HOST_NAME = "localhost";
	private static final int PORT_NUMBER = 4436;
	
	private Socket socket = null;
	private PrintWriter out = null;
    private BufferedReader in = null;
    private DataOutputStream dOut = null;
    private DataInputStream dis = null;
    
    public ServerComms() {
    	
    	try {
    		
    		socket = new Socket(HOST_NAME, PORT_NUMBER);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dOut = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /* Cleans up after itself.
     * Call before ending this session. */
    public void destroy() {
    	
    	try {
    	
    		dOut.close();
    		in.close();
    		out.close();
			socket.close();
    	
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /* Sends string from user to the server */
    public void toServer(String fromUser) {
    	
    	out.println(fromUser);
    	
    }
    
    /* Gets string output from the server.
     * Call this function when expecting. */
    public String fromServer() {
    	
    	String fromServer = null;
    	
    	try {
    		
			fromServer = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return fromServer;
    	
    }
    
    
    public void sendBytes(byte[] bytes, int length) {
    	
    	try {
    		
    		dOut.writeInt(length);
			dOut.write(bytes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public byte[] getBytes() {
		
		byte[] data = null;
		
		try {
			
			int length = dis.readInt();
			data = new byte[length];
			dis.readFully(data);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}

}
