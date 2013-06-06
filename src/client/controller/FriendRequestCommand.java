package client.controller;

import client.model.Actions;
import client.view.AddNewFriendFrame;


public class FriendRequestCommand implements Command {
	
	private AddNewFriendFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (AddNewFriendFrame) objects[0];
		
		String username = frame.getUsername();
		int securityLevel = frame.getSecurityLevel();
		
		if (Actions.friendRequest(username, securityLevel, securityLevel))
			frame.friendAdded();
		else
			frame.userDoesNotExist();
		
		
			
	}

}
