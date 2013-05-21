package server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import server.encryption.KeyDerivation;

import com.dropbox.client2.exception.DropboxException;

public class Setup {
	
	public static void main(String[] args) throws MalformedURLException, SQLException, ClassNotFoundException, DropboxException, IOException, URISyntaxException {
		
		/*
		AESCipher cipher = new AESCipher();
		
		byte[][] b = new byte[5][];
		b[0] = "0000000000000000".getBytes();
		b[1] = "0000000000000001".getBytes();
		b[2] = "0000000000000002".getBytes();
		b[3] = "0000000000000003".getBytes();
		b[4] = "0000000000000004".getBytes();
		IterativeKeyEncryption kas = new IterativeKeyEncryption(b, cipher, "user");
		
		byte[] key = kas.getKey(b[0], 1, 2, new AESCipher());
		
		System.out.println(new String(key));
	*/
		
		
		
		
		KeyDerivation.setup();
		
		

	}
	
	public static void setup() {
		
	//	KeyDerivation.setup();
		
	}

}
