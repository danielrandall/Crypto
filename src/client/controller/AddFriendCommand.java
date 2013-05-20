package client.controller;

import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import client.model.CentralAuthority;
import client.view.MenuFrame;


public class AddFriendCommand implements Command {
	
	private MenuFrame frame;

	/* PRE: Spinner value must be an int */
	@Override
	public void execute(Object[] objects) {
		
		frame = (MenuFrame) objects[0];
		
		String usernameToAdd = ((JTextComponent) objects[1]).getText();
		int lowerBound = (Integer) ((JSpinner) objects[2]).getValue();
		int upperBound = (Integer) ((JSpinner) objects[3]).getValue();
		
		CentralAuthority.addFriend(usernameToAdd, lowerBound, upperBound);
			
	}

}
