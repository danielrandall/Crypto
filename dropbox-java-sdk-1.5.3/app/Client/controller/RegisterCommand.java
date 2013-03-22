package Client.controller;

import javax.swing.text.JTextComponent;

import Client.model.DropboxOperations;
import Client.model.Register;
import Client.view.MainFrame;

public class RegisterCommand implements Command {
	
	MainFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (MainFrame) objects[0];
		
		String username = ((JTextComponent) objects[1]).getText();
		String password = ((JTextComponent) objects[2]).getText();
		
		if (Register.userRegister(username, password)) {
			DropboxOperations.authenticate();
			frame.login();
		} else
			System.out.println("false");		
		
	}
}
