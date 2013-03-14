package Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;


import com.dropbox.client2.exception.DropboxException;

import Linking.Authentication;
import Linking.State;
import Server.CentralAuthority;
import Server.UserOperations;

public class Login {


	public static void userLogin() {
		
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String decision = null;
		State state = null;
		
		System.out.println("Enter 1 to login");
		System.out.println("Enter 2 to register");
		
		try {
			decision = input.readLine();
		
			if (decision.equals("1"))
		
				//		String username = "username";
				//		String password = "password";
				state = Authentication.link(input);
		
			if (decision.equals("2"))
				state = UserOperations.Register(input);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CentralAuthority.options(state);

	}

}
