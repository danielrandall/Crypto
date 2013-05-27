package client.controller;

import client.model.Register;
import client.view.RegisterFrame;


public class RegisterCommand implements Command {
	
	private RegisterFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (RegisterFrame) objects[0];
		
		String username = frame.getUsername();
		char[] password = frame.getPassword();
		char[] reenterPassword = frame.getReenterPassword();
		
		if (!Register.passwordCheck(password, reenterPassword))
			frame.passwordError();
		else
			if (Register.userRegister(username, password)) {
				frame.register();
			} else
				frame.usernameError();		
		
	}
}
