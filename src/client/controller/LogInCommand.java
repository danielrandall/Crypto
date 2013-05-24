package client.controller;

import client.model.CentralAuthority;
import client.model.Login;
import client.view.MainFrame;


public class LogInCommand implements Command {

	MainFrame frame;
	
	@Override
	public void execute(Object[] objects) {
		
		frame = (MainFrame)objects[0];
		
		String username = frame.getUsername();
		char[] password = frame.getPassword();
		
		if (Login.userLogin(username, password)) {
			Object[][] rows = CentralAuthority.getUploadedFiles();
			Object[][] friends = CentralAuthority.getFriends();
			Object[][] friendRequests = CentralAuthority.getFriendRequests();
			frame.login(rows, friends, friendRequests);
			//Login.getRequests();
		} else
			frame.loginFail();
		
	}

}
