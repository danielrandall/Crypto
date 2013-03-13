package Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;


import com.dropbox.client2.exception.DropboxException;

import Linking.Authentication;
import Linking.Databases.H2Users;

public class Login {


	public static void userLogin() throws MalformedURLException, SQLException, ClassNotFoundException, DropboxException, IOException, URISyntaxException {
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		String username = "username";
		String password = "password";

		
		Authentication.link(username, password);



	}

}
