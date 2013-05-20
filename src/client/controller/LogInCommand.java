package client.controller;

import javax.swing.text.JTextComponent;

import client.model.Login;
import client.view.MainFrame;


public class LogInCommand implements Command {


	/* Needs to be two in length */
	@Override
	public void execute(Object[] objects) {
		
		assert(objects.length == 3);
		
		MainFrame frame = (MainFrame)objects[0];
		String username = ((JTextComponent) objects[1]).getText();
		String password = ((JTextComponent) objects[2]).getText();
		
		if (Login.userLogin(username, password))
			frame.login();
		else
			System.out.println("false");
		
	}

}
