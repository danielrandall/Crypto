package Client.controller;

import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import Client.model.CentralAuthority;
import Client.view.MenuFrame;

public class AddFriendCommand implements Command {
	
	private MenuFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (MenuFrame) objects[0];
		
		String usernameToAdd = ((JTextComponent) objects[1]).getText();
		
		int lowerBound = (int) ((JSpinner) objects[2]).getValue();
		int upperBound = (int) ((JSpinner) objects[3]).getValue();
		
		CentralAuthority.addFriend(usernameToAdd, lowerBound, upperBound);
			
	}

}
