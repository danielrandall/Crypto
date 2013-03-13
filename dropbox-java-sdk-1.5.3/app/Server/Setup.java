package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import Linking.JavaKeyStore;
import Linking.KeyStores;
import Login.Login;

import com.dropbox.client2.exception.DropboxException;

public class Setup {
	
	private static KeyStores keystore = new JavaKeyStore();
	
	public static void main(String[] args) throws MalformedURLException, SQLException, ClassNotFoundException, DropboxException, IOException, URISyntaxException {
		
		KeyDerivation.setup();
		
		Login.userLogin();

	}

}
