package client.controller;

import javax.swing.text.JTextComponent;

import client.model.Login;
import client.view.MainFrame;


public class LogInCommand implements Command {

	MainFrame frame;
	
	/* Needs to be two in length */
	@Override
	public void execute(Object[] objects) {
		
		assert(objects.length == 3);
		
		frame = (MainFrame)objects[0];
		
		String username = frame.getUsername();
		String password = frame.getPassword();
		
		if (Login.userLogin(username, password)) {
			Object[][] rows = Login.getUploadedFiles();
			frame.login(rows);
			//Login.getRequests();
		} else
			frame.loginFail();
		
	}

}
