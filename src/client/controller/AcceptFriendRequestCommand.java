package client.controller;

import client.model.Actions;
import client.view.FriendRequestsPanel;

public class AcceptFriendRequestCommand implements Command {
	
	private FriendRequestsPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		panel = (FriendRequestsPanel) objects[0];
		
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String username = (String) info[0];
			Actions.acceptFriendRequest(username);
			
			panel.friendAdded();
		}
	
			
	}
	

}
