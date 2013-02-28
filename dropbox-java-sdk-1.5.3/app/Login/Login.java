package Login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;


import com.dropbox.client2.exception.DropboxException;

import Linking.Authentication;
import Linking.Databases.H2Users;

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
		
		//H2Database h2 = new H2Database();
		//h2.removeUser("username");

		String username = "username";
		String password = "password";

			Authentication.link(username, password);



	}

}
