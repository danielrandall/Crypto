package Login;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;


import com.dropbox.client2.exception.DropboxException;

import Linking.Authentication;

public class Login {


	public static void userLogin() throws MalformedURLException, SQLException, ClassNotFoundException, DropboxException, IOException, URISyntaxException {
		
		String username = "username";
		String password = "password";

		
		Authentication.link(username, password);



	}

}
