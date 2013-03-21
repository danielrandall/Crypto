package Client.controller;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.JTextComponent;

import Client.model.Login;
import Client.view.MainFrame;

public class LogInCommand implements Command {


	/* Needs to be two in length */
	@Override
	public void execute(Object[] objects) {
		
		assert(objects.length == 3);
		
		MainFrame frame = (MainFrame)objects[0];
		String username = ((JTextComponent) objects[1]).getText();
		String password = ((JTextComponent) objects[2]).getText();
		
		if (Login.userLogin(username, password)) {
			System.out.println("true");
			frame.login();
		}
		else
			System.out.println("false");
		
	}

}
