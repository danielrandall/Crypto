package client.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import server.KeySet;

public class ServerComms {
	
	/* Connection info */
	private static final String HOST_NAME = "localhost";
	private static final int PORT_NUMBER = 4451;
	
	/* Server communication constants */
	private static final String EXIT_CODE = "999";
	
	private static Socket socket = null;
	private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static DataOutputStream dOut = null;
    private static DataInputStream dis = null;
    private static ObjectOutputStream OOut = null;
    private static ObjectInputStream OIn = null;
    
    public static void main(String[] args) {
    	
    	setup();
    	
    	KeySet keySet = new KeySet("username");
    	sendObject(keySet);
    	KeySet newKeySet = (KeySet) getObject();
    	
    	System.out.println(newKeySet.getOwner());
    	
    	destroy();
    	
    }
    
    
    
    public static void sendObject(Object object) {
    	
    	try {
			OOut.writeObject(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public static Object getObject() {
    	
    	Object object = null;
    	
    	try {
			object = OIn.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return object;
    	
    }
    
    
    /* Sends string from user to the server */
    public static void toServer(String fromUser) {
    	
    	out.println(fromUser);
    	
    }
    
    /* Gets string output from the server.
     * Call this function when expecting. */
    public static String fromServer() {
    	
    	String fromServer = null;
    	
    	try {
    		
			fromServer = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return fromServer;
    	
    }
    
    
    /* Sends bytes to the server. */
    public static void sendBytes(byte[] bytes, int length) {
    	
    	try {
    		
    		dOut.writeInt(length);
			dOut.write(bytes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    /* Gets byte output from the server.
     * Call this function when expecting. */
    public static byte[] getBytes() {
		
		byte[] data = null;
		
		try {
			
			int length = dis.readInt();
			data = new byte[length];
			
			//for (int i = 0; i < length; i++)
			//	data[i] = dis.readByte();
			
			dis.readFully(data, 0, length);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
		
	}
    
    
    public static void sendInt(int i) {
    	
    	try {
			dOut.writeInt(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
    	
    }
    
    
    public static int getInt() {
    	
    	int i = 0;
    	
    	try {
			i = dis.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
    	
    	return i;
    	
    }	

	public static void setup() {
		
		try {
    		
    		socket = new Socket(HOST_NAME, PORT_NUMBER);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dOut = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			OOut = new ObjectOutputStream(socket.getOutputStream());
			OIn = new ObjectInputStream(socket.getInputStream());
			
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/* Called when communication with the server ceases for this session */
	public static void exit() {
		
		toServer(EXIT_CODE);
		destroy();
		
	}
	
	
	/* Cleans up after itself.
     * Call before ending this session. */
    private static void destroy() {
    	
    	try {
    	
    		dOut.close();
    		in.close();
    		out.close();
    		OOut.close();
			OIn.close();
			
			socket.close();
    	
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
