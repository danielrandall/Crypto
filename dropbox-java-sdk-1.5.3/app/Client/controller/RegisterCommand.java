package Client.controller;

import javax.swing.text.JTextComponent;

import Client.model.DropboxOperations;
import Client.model.Register;
import Client.view.MainFrame;
import Client.view.RegisterFrame;

public class RegisterCommand implements Command {
	
	private RegisterFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (RegisterFrame) objects[0];
		
		String username = ((JTextComponent) objects[1]).getText();
		String password = ((JTextComponent) objects[2]).getText();
		String reenterPassword = ((JTextComponent) objects[3]).getText();
		
		if (!password.equals(reenterPassword))
			frame.passwordError();
		else
			if (Register.userRegister(username, password)) {
				DropboxOperations.authenticate();
				frame.register();
			} else
				frame.usernameError();		
		
	}
}
