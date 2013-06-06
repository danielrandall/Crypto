package client.controller;

import client.model.Actions;
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
			Object[][] rows = Actions.getUploadedFiles();
			Object[][] friends = Actions.getFriends();
			Object[][] friendRequests = Actions.getFriendRequests();
			Object[][] friendFiles = Actions.getFriendFiles();
			Actions.checkUpdates();
			frame.login(rows, friends, friendRequests, friendFiles);
			//Login.getRequests();
		} else
			frame.loginFail();
		
	}

}
