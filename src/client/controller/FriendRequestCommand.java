package client.controller;

import client.model.CentralAuthority;
import client.view.AddNewFriendFrame;
import client.view.FriendsPanel;


public class FriendRequestCommand implements Command {
	
	private AddNewFriendFrame frame;
	private FriendsPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		frame = (AddNewFriendFrame) objects[0];
		panel = (FriendsPanel) objects[1];
		
		String username = frame.getUsername();
		int securityLevel = frame.getSecurityLevel();
		
		if (CentralAuthority.friendRequest(username, securityLevel, securityLevel)) {
			Object[] friendInfo = {username, securityLevel};
			panel.addElementToTable(friendInfo);
			//frame.friendAdded();
		} else {
			frame.userDoesNotExist();
		}
		
		
			
	}

}
