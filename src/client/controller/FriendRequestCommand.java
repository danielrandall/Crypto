package client.controller;

import client.model.CentralAuthority;
import client.view.AddNewFriendFrame;
import client.view.FriendsPanel;


public class FriendRequestCommand implements Command {
	
	private AddNewFriendFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (AddNewFriendFrame) objects[0];
		
		String username = frame.getUsername();
		int securityLevel = frame.getSecurityLevel();
		
		if (CentralAuthority.friendRequest(username, securityLevel, securityLevel))
			frame.friendAdded();
		else
			frame.userDoesNotExist();
		
		
			
	}

}
