package Login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import com.dropbox.client2.exception.DropboxException;

import Linking.Authentication;

public class Login {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws DropboxException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, SQLException, ClassNotFoundException, DropboxException, IOException, URISyntaxException {
		
		String username;
		if (args.length == 1) {
			username = args[0];
			Authentication.link(username);
		}
		

	}

}
