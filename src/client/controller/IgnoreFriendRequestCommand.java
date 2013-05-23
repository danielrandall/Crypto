package client.controller;

import client.model.CentralAuthority;
import client.view.FriendRequestsPanel;

public class IgnoreFriendRequestCommand implements Command {

	@Override
	public void execute(Object[] objects) {
		
		FriendRequestsPanel panel = (FriendRequestsPanel) objects[0];
		
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String username = (String) info[0];
			CentralAuthority.ignoreFriendRequest(username);
			
			panel.friendIgnored();
		}
		
		
		
	}

}
