package client.controller;

import client.model.CentralAuthority;
import client.view.AddNewFriendFrame;
import client.view.FriendRequestsPanel;

public class AddFriendCommand implements Command {
	
	private FriendRequestsPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		panel = (FriendRequestsPanel) objects[0];
		
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String username = (String) info[0];
			CentralAuthority.acceptFriendRequest(username);
			
	//		panel.friendAdded();
		}
	
			
	}
	

}
