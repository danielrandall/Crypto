package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientComms {
	
	private BufferedReader in = null;
	private Socket socket = null;
	private PrintWriter out = null;
	private DataInputStream dis = null;
	private DataOutputStream dOut = null;
	private static ObjectOutputStream OOut = null;
    private static ObjectInputStream OIn = null;
	
	public ClientComms(Socket socket) {
		
		try {
			
			this.socket = socket;
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);
			dis = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());
			OOut = new ObjectOutputStream(socket.getOutputStream());
			OIn = new ObjectInputStream(socket.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
    
    
    public void sendInt(int i) {
    	
    	try {
			dOut.writeInt(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
    	
    }
    
    
    public int getInt() {
    	
    	int i = 0;
    	
    	try {
			i = dis.readInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
    	
    	return i;
    	
    }
    
	
	
	/* Close all open streams. */
	public void destroy() {
		
		try {	
			
			dis.close();
			out.close();
			in.close();
			OOut.close();
			OIn.close();
			
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void toClient(String output) {
		
		out.println(output);
		
	}
	
	
	public String fromClient() {
		
		String input = null;
		
		try {
			
			input = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input;
		
		
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


    public void sendBytes(byte[] bytes, int length) {
    	
    	try {
    		
    		dOut.writeInt(length);
			dOut.write(bytes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
